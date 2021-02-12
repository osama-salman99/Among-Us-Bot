import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class Member {
    String name;
    WebElement element;
    WebDriver driver;
    public Member(WebDriver driver, WebElement element) {
        this.driver = driver;
        this.element = element;
        this.name = element.findElement(By.className("username-lm8y6T")).getText();
        System.out.println(this.name);
    }

    void mute(boolean mute)
    {
        Actions action = new Actions(driver);
        action.moveToElement(element).contextClick().build().perform();
        boolean muted = Boolean.parseBoolean(driver.findElement(By.id("user-context-voice-mute")).getAttribute("aria-checked"));
        if(mute ^ muted){
            driver.findElement(By.id("user-context-voice-mute")).click();
        }
        action = new Actions(driver);
        action.sendKeys(Keys.ESCAPE).perform();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Member)
            return name.equals(((Member)obj).name);
        return false;
    }
}
