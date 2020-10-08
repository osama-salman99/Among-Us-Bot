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
        try {
            if (allMuted) {
                mute();
            } else {
                unmute();
            }
        } catch (PlayerDisconnectedException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static void setAllMuted(boolean allMuted) {
        Player.allMuted = allMuted;
    }

    public void mute() throws PlayerDisconnectedException {
        muteToggle(true);
        System.out.println(name + " got muted");
    }

    public void unmute() throws PlayerDisconnectedException {
        if (killed) {
            return;
        }
        muteToggle(false);
    }

    private void muteToggle(boolean mute) throws PlayerDisconnectedException {
        try {
            member.mute(mute).submit();
        } catch (IllegalStateException exception) {
            System.out.println(exception.getMessage());
            throw new PlayerDisconnectedException(Player.this);
        }
        System.out.println(name + " got unmuted");
    }

    public void kill() throws PlayerDisconnectedException {
        killed = true;
        playerPanel.putUnKillText();
        mute();
    }

    public void unKill() throws PlayerDisconnectedException {
        killed = false;
        playerPanel.putKillText();
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
            return member.getEffectiveName().equals(((Player) object).member.getEffectiveName());
        }
        if (object instanceof Member) {
            return member.getEffectiveName().equals(((Member) object).getEffectiveName());
        }
        return false;
    }
}
