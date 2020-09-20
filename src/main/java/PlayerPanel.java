import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        JLabel nameLabel = new JLabel(player.getName(), SwingConstants.CENTER);
        JButton killButton = new JButton(KILL_TEXT);

        killButton.setForeground(Color.WHITE);
        nameLabel.setForeground(Color.WHITE);

        killButton.setOpaque(false);
        killButton.setFocusPainted(false);
        killButton.setBorderPainted(false);
        killButton.setContentAreaFilled(false);

        killButton.addActionListener(new ActionListener() {
            @Override
            public synchronized void actionPerformed(ActionEvent e) {
                killButton.setForeground(Color.YELLOW);
                new Thread(new Runnable() {
                    @Override
                    public synchronized void run() {
                        try {
                            wait(200);
                        } catch (InterruptedException exception) {
                            exception.printStackTrace();
                        }
                        killButton.setForeground(Color.WHITE);
                    }
                }).start();
                if (!player.isKilled()) {
                    player.kill();
                    killButton.setText(UN_KILL_TEXT);
                } else {
                    player.unKill();
                    killButton.setText(KILL_TEXT);
                }
            }
        });

        add(nameLabel);
        add(killButton);
    }
}
