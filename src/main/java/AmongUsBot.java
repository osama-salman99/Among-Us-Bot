import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AmongUsBot extends ListenerAdapter {
    private static final String TOKEN_FILE_PATH = "res/token_file.txt";
    private static final String PASSWORD_INIT = "Password is: ";
    private static final Pattern PATTERN = Pattern.compile("^Password is: [a-zA-Z0-9]+$");
    private static String token;
    private final AmongUsApp app;
    private JDA jda;
    private Guild guild;
    private String password;

    public AmongUsBot(AmongUsApp app) {
        this.app = app;
        this.guild = null;
        this.password = null;
        if (token == null) {
            readToken();
        }
        try {
            jda = JDABuilder.createDefault(token).build().awaitReady();
        } catch (LoginException | InterruptedException exception) {
            exception.printStackTrace();
        }
        jda.addEventListener(this);
        new Thread(() -> {
            String password;
            do {
                password = app.getPassword();
            } while (!password.equals(AmongUsBot.this.password));
            app.enableFrame();
            addCurrentPlayers();
        }).start();
    }

    private static void readToken() {
        Scanner scanner;
        try {
            scanner = new Scanner(new File(TOKEN_FILE_PATH));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        token = scanner.nextLine();
    }

    private void addCurrentPlayers() {
        System.out.println("Guild : " + guild);
        List<VoiceChannel> channels = guild.getVoiceChannels();
        for (VoiceChannel channel : channels) {
            System.out.println("Channel: " + channel);
            List<Member> members = channel.getMembers();
            for (Member member : members) {
                System.out.println("Member: " + member);
                addPlayer(member);
            }
            System.out.println();
        }
    }

    @Override
    public void onGuildVoiceUpdate(@NotNull GuildVoiceUpdateEvent event) {
        super.onGuildVoiceUpdate(event);
        if (event.getChannelJoined() != null) {
            addPlayer(event.getEntity());
        } else if (event.getChannelLeft() != null) {
            removePlayer(event.getEntity());
        }
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        super.onGuildMessageReceived(event);
        String message = event.getMessage().getContentRaw();
        Matcher matcher = PATTERN.matcher(message);
        if (!matcher.matches()) {
            return;
        }
        this.password = message.replace(PASSWORD_INIT, "");
        this.guild = event.getGuild();
    }

    private void addPlayer(Member member) {
        app.addPlayer(new Player(member));
    }

    private void removePlayer(Member member) {
        app.removePlayer(member);
    }
}
