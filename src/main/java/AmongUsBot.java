import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class AmongUsBot extends ListenerAdapter {
    private static String token;
    private final AmongUsApp app;
    private JDA jda;

    public AmongUsBot(AmongUsApp app) {
        this.app = app;
        if (token == null) {
            readToken();
        }
        try {
            jda = JDABuilder.createDefault(token).build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
        jda.addEventListener(this);
        addCurrentPlayers();
    }

    private static void readToken() {
        Scanner scanner;
        try {
            scanner = new Scanner(new File("D:\\osama\\OneDrive\\Documents\\Other\\token_file.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        token = scanner.nextLine();
    }

    private void addCurrentPlayers() {
        List<Guild> guilds = jda.getGuilds();
        for (Guild guild : guilds) {
            List<GuildChannel> channels = guild.getChannels();
            for (GuildChannel channel : channels) {
                List<Member> members = channel.getMembers();
                for (Member member : members) {
                    addPlayer(member);
                }
            }
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

    private void addPlayer(Member member) {
        app.addPlayer(new Player(member));
    }

    private void removePlayer(Member member) {
        app.removePlayer(member);
    }
}
