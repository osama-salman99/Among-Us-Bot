import javax.swing.*;
import java.awt.*;

public class PlayerPanel extends JPanel {
    private static final String KILL_TEXT = "Kill";
    private static final String UN_KILL_TEXT = "Unkill";
    private final Player player;

    public PlayerPanel(Player player) {
        this.player = player;
        setLayout(new GridLayout(1, 2));
        setBackground(Color.BLACK);
        initializeButtons();
    }

    private void initializeButtons() {
        JTextField nameLabel = new JTextField(player.getName());
        JButton killButton = new JButton(KILL_TEXT);

        killButton.setForeground(Color.WHITE);
        nameLabel.setForeground(Color.WHITE);

        killButton.setOpaque(false);
        killButton.setFocusPainted(false);
        killButton.setBorderPainted(false);
        killButton.setContentAreaFilled(false);

        nameLabel.setOpaque(false);

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
