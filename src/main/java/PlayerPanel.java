import javax.swing.*;
import java.awt.*;

public class PlayerPanel extends JPanel {
    private static final String KILL_TEXT = "Kill";
    private static final String UN_KILL_TEXT = "Unkill";
    private final Player player;

    public PlayerPanel(Player player) {
        this.player = player;
        setLayout(new GridLayout(1, 2));
        initializeButtons();
    }

    private void initializeButtons() {
        JLabel nameLabel = new JLabel(player.getName(), SwingConstants.CENTER);
        JButton killButton = new JButton(KILL_TEXT);

        killButton.addActionListener(event -> {
            if (!player.isKilled()) {
                player.kill();
                killButton.setText(UN_KILL_TEXT);
            } else {
                player.unKill();
                killButton.setText(KILL_TEXT);
            }
        });

        add(nameLabel);
        add(killButton);
    }
}
