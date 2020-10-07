public class PlayerDisconnectedException extends Throwable {
    private final Player player;

    public PlayerDisconnectedException(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
