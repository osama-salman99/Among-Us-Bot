public class Player {
    private static boolean allMuted;
    private final PlayerPanel playerPanel;
    private final String name;
    private final Member member;
    private boolean killed;

    public Player(Member member) {
        this.member = member;
        this.name = "@" + member.name;
        this.killed = false;
        this.playerPanel = new PlayerPanel(this);
        if (allMuted) {
            try {
                mute();
            } catch (org.openqa.selenium.StaleElementReferenceException exception) {
                System.out.println(exception.getMessage());
            }
        }
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
        System.out.println(name + " got unmuted");
    }

    private void muteToggle(boolean mute) {
        try {
            member.mute(mute);
        } catch (IllegalStateException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void kill() {
        killed = true;
        playerPanel.putUnKillText();
        mute();
    }

    public void unKill() {
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
            return member.name.equals(((Player) object).member.name);
        }
        if (object instanceof Member) {
            return member.name.equals(((Member) object).name);
        }
        return false;
    }
}
