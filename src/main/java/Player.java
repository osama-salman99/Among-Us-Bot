public class Player {
    private final PlayerPanel playerPanel;
    private boolean killed;
    private String id;
    private String name;

    public Player(String id, String name) {
        this.id = id;
        this.name = name;
        this.killed = false;
        this.playerPanel = new PlayerPanel(this);
    }

    public void mute() {
        // TODO
    }

    public void unmute() {
        if (killed) {
            return;
        }
        // TODO
    }

    public void kill() {
        killed = true;
        mute();
    }

    public void unKill() {
        killed = false;
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
}
