import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AmongUsBot extends ListenerAdapter{
    private static final String PASSWORD_INIT = "Password is: ";
    private static final Pattern PATTERN = Pattern.compile("^" + PASSWORD_INIT + "[a-zA-Z0-9]+$");
    private static final String TOKEN_FILE_PATH = "res/token_file.txt";
    private static String token;
    private final AmongUsApp app;
    private String password;
    private Discord discord;
    private boolean confirmed;

    public AmongUsBot(AmongUsApp app) {
        this.app = app;
        this.password = null;
        this.confirmed = true;

        new Thread(() -> {
            this.discord = new Discord();
            this.discord.addListener(this);
//            addCurrentPlayers();
        }).start();

        new Thread(() -> {
//            String password;
//            do {
//                password = app.getPassword();
//            } while (!password.equals(AmongUsBot.this.password));
            confirmed = true;
            app.enableFrame();
        }).start();
    }

    public void addCurrentPlayers() {
        List<Member> members = discord.getMembers();
        for (Member member : members) {
            System.out.println("Member: " + member);
            addPlayer(member);
        }
    }


    private void addPlayer(Member member) {
        app.addPlayer(new Player(member));
        System.out.println(member.name + " joined");
    }

    private void removePlayer(Member member) {
        app.removePlayer(new Player(member));
        System.out.println(member.name + " disconnected");
    }

    @Override
    public void OnMemberJoined(Member member) {
        addPlayer(member);
    }

    @Override
    public void OnMemberLeft(Member member) {
        removePlayer(member);
    }
}
