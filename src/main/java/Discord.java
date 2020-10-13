import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Thread.sleep;


public class Discord {
    WebDriver driver;
    List<Member> members;
    ArrayList<ListenerAdapter> listeners = new ArrayList<>();
    boolean listening = false;
    private static final int REFRESH_TIME = 1000;
    public Discord() {
        File login = new File("res/login.txt");
        Scanner loginScan = null;
        try {
            loginScan = new Scanner(login);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String username = loginScan.nextLine();
        String password = loginScan.nextLine();
        System.setProperty("webdriver.gecko.driver", "res/geckodriver.exe");
        driver = new FirefoxDriver();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> driver.quit()));
        driver.get("https://discord.com/login");
        driver.findElement(By.name("email")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.cssSelector("button.marginBottom8-AtZOdT")).click();
        while(driver.getCurrentUrl().equals("https://discord.com/login"))
        {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        driver.get("https://discord.com/channels/689967346944704567/689967347510804523");
        new WebDriverWait(driver, 20)
                .until(ExpectedConditions.elementToBeClickable(By.className("wrapper-1BJsBx")));
        members = getMembers();
    }

    public void addListener(ListenerAdapter listenerAdapter)
    {
        listeners.add(listenerAdapter);
        if(!listening)
        {
            listening = true;
            Thread t1 = new Thread(this::checkMembers);
            t1.start();
        }
    }

    private void checkMembers()
    {
        while (true)
        {
            List<Member> newMembers = getMembers();
            for (Member newMember: newMembers)
                if(!members.contains(newMember))
                    for(ListenerAdapter listener: listeners)
                        listener.OnMemberJoined(newMember);

            for (Member member: members)
                if(!newMembers.contains(member))
                    for(ListenerAdapter listener: listeners)
                        listener.OnMemberLeft(member);

            members = newMembers;
            try {
                sleep(REFRESH_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Member> getMembers()
    {
        List<WebElement> membersElements = driver.findElements(By.className("content-3xS9Lh"));
        List<Member> members = new ArrayList<>();
        for (WebElement element: membersElements)
        {
            members.add(new Member(driver, element));
        }
        return members;
    }
}
