import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class PlayerPanel extends JPanel {
    private static final String KILL_TEXT = "Kill";
    private static final String UN_KILL_TEXT = "Unkill";
    private final Player player;
    private JButton killButton;

    public PlayerPanel(Player player) {
        this.player = player;
        setLayout(new GridLayout(1, 2));
        setBackground(Color.BLACK);
        initializeButtons();
    }

    private void initializeButtons() {
        JTextField nameTextField = new JTextField(player.getName());
        killButton = new JButton(KILL_TEXT);

        killButton.setForeground(Color.WHITE);
        killButton.setOpaque(false);
        killButton.setFocusPainted(false);
        killButton.setBorderPainted(false);
        killButton.setContentAreaFilled(false);

        nameTextField.setForeground(Color.WHITE);
        nameTextField.setOpaque(false);
        nameTextField.setBorder(null);
        nameTextField.setHorizontalAlignment(SwingConstants.CENTER);

        killButton.addActionListener(event -> {
            if (!player.isKilled()) {
                player.kill();
            } else {
                player.unKill();
            }
        });

        nameTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (nameTextField.getText().equals(player.getName())) {
                    nameTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (nameTextField.getText().equals("")) {
                    nameTextField.setText(player.getName());
                }
            }
        });

        add(nameTextField);
        add(killButton);
    }

    public void putKillText() {
        killButton.setText(KILL_TEXT);
    }

    public void putUnKillText() {
        killButton.setText(UN_KILL_TEXT);
    }
}
