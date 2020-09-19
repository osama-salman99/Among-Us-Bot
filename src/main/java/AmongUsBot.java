import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class AmongUsBot extends ListenerAdapter {
    private static final String TOKEN = "NzU2NTE4Mzc5MDg2NDEzOTY1.X2TApQ.lpHit6ZK31MYvaqg7LUmPfZeToI";
    private AmongUsApp app;

    public AmongUsBot(AmongUsApp app) {
        this.app = app;
    }

    @Override
    public void onGuildVoiceUpdate(@NotNull GuildVoiceUpdateEvent event) {
        super.onGuildVoiceUpdate(event);
        System.out.println(event.getChannelJoined());
        System.out.println(event.getChannelLeft());
        System.out.println(event.getEntity());
    }
}
