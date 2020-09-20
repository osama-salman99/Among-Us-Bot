import net.dv8tion.jda.api.entities.Member;

public class Player {
    private final PlayerPanel playerPanel;
    private final String name;
    private final Member member;
    private boolean killed;
    private boolean muted;

    public Player(Member member) {
        this.member = member;
        this.name = member.getEffectiveName();
        this.killed = false;
        this.playerPanel = new PlayerPanel(this);
    }

    public void mute() {
        muteToggle(muted = true);
        System.out.println(name + " got muted");
    }

    public void unmute() {
        if (killed) {
            return;
        }
        muteToggle(muted = false);
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
        if (!muted) {
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
            return member.equals(((Player) object).member);
        }
        if (object instanceof Member) {
            return member.equals(object);
        }
        return false;
    }
}
