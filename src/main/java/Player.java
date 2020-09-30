import net.dv8tion.jda.api.entities.Member;

public class Player {
    private static boolean allMuted;
    private final PlayerPanel playerPanel;
    private final String name;
    private final Member member;
    private boolean killed;

    public Player(Member member) {
        this.member = member;
        this.name = "@" + member.getEffectiveName();
        this.killed = false;
        this.playerPanel = new PlayerPanel(this);
    }

    public static void setAllMuted(boolean allMuted) {
        Player.allMuted = allMuted;
    }

    public void mute() {
        muteToggle(true);
        System.out.println(name + " got muted");
    }

    public void unmute() {
        if (killed) {
            return;
        }
        muteToggle(false);
    }

    private void muteToggle(boolean mute) {
        member.mute(mute).submit();
        System.out.println(name + " got unmuted");
    }

    public void kill() {
        killed = true;
        mute();
    }

    public void unKill() {
        killed = false;
        if (!allMuted) {
            unmute();
        }
    }

    public PlayerPanel getPlayerPanel() {
        return playerPanel;
    }

    public boolean isKilled() {
        return killed;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Player) {
            return member.getEffectiveName().equals(((Player) object).getName());
        }
        if (object instanceof Member) {
            return member.getEffectiveName().equals(((Member) object).getEffectiveName());
        }
        return false;
    }
}
