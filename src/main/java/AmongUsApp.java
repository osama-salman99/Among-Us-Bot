import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AmongUsApp {
    private static final String APP_NAME = "Among Us Bot";
    private static final String SIZE_PANEL_TEXT = "Size:";
    private static final String SHOW_OVERLAY_TEXT = "Show overlay";
    private static final String HIDE_OVERLAY_TEXT = "Hide overlay";
    private static final String MUTE_TEXT = "Mute";
    private static final String UNMUTE_TEXT = "Unmute";
    private static final int MAXIMUM_WIDTH = 300;
    private static final int MINIMUM_WIDTH = 200;
    private static final int COLUMN_HEIGHT = 100;
    private static final int INCREASE_VALUE = 20;
    private final ArrayList<Player> players;
    private JFrame mainFrame;
    private JFrame overlayFrame;
    private boolean overlayShown;
    private int currentWidth;

    public AmongUsApp() {
        currentWidth = MAXIMUM_WIDTH;
        players = new ArrayList<>();
        overlayShown = false;
        initializeFrame();
        addButtons();
        createOverlay();
        showFrame();

        addPlayer(new Player("21", "4342"));
        addPlayer(new Player("21", "4342"));
        addPlayer(new Player("21", "4342"));
    }

    private void initializeFrame() {
        mainFrame = new JFrame(APP_NAME);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setSize(300, 200);
    }

    private void addButtons() {
        JPanel mainPanel = new JPanel(new GridLayout(3, 1));
        JLabel sizeTextArea = new JLabel(SIZE_PANEL_TEXT, SwingConstants.CENTER);
        JPanel sizeButtonsPanel = new JPanel(new GridLayout(1, 2));
        JButton increaseSizeButton = new JButton("+");
        JButton decreaseSizeButton = new JButton("-");
        JButton overlayButton = new JButton(SHOW_OVERLAY_TEXT);

        increaseSizeButton.addActionListener(event -> increaseSize());
        decreaseSizeButton.addActionListener(event -> decreaseSize());

        sizeButtonsPanel.add(increaseSizeButton);
        sizeButtonsPanel.add(decreaseSizeButton);
        mainPanel.add(sizeTextArea);
        mainPanel.add(sizeButtonsPanel);

        overlayButton.addActionListener(event -> {
            if (overlayShown) {
                overlayButton.setText(SHOW_OVERLAY_TEXT);
                hideOverlay();
            } else {
                overlayButton.setText(HIDE_OVERLAY_TEXT);
                showOverlay();
            }

            overlayShown = !overlayShown;
        });

        mainPanel.add(overlayButton);
        mainFrame.add(mainPanel);
    }

    private void createOverlay() {
        overlayFrame = new JFrame();
        overlayFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        overlayFrame.setUndecorated(true);
        overlayFrame.setSize(currentWidth, COLUMN_HEIGHT);
        overlayFrame.setLayout(new GridLayout(0, 1));

        JPanel mutePanel = new JPanel(new GridLayout(1, 2));
        JButton muteAllButton = new JButton(MUTE_TEXT);
        JButton unmuteAllButton = new JButton(UNMUTE_TEXT);

        muteAllButton.addActionListener(event -> muteAll());
        unmuteAllButton.addActionListener(event -> unmuteAll());

        mutePanel.add(muteAllButton);
        mutePanel.add(unmuteAllButton);

        overlayFrame.add(mutePanel);
    }

    private void muteAll() {
        for (Player player : players) {
            player.mute();
        }
    }

    private void unmuteAll() {
        for (Player player : players) {
            player.unmute();
        }
    }

    private void hideOverlay() {
        overlayFrame.setVisible(false);
    }

    private void showOverlay() {
        overlayFrame.setVisible(true);
    }

    private void increaseSize() {
        if (currentWidth <= (MAXIMUM_WIDTH - INCREASE_VALUE)) {
            currentWidth += INCREASE_VALUE;
            updateOverlaySize();
        }
    }

    private void decreaseSize() {
        if (currentWidth >= (MINIMUM_WIDTH + INCREASE_VALUE)) {
            currentWidth -= INCREASE_VALUE;
            updateOverlaySize();
        }
    }

    private void showFrame() {
        mainFrame.setVisible(true);
    }

    private void updateOverlaySize() {
        int width = currentWidth;
        int height = (players.size() + 1) * COLUMN_HEIGHT;
        overlayFrame.setSize(width, height);
    }

    public void addPlayer(Player player) {
        players.add(player);
        overlayFrame.add(player.getPlayerPanel());
        updateOverlaySize();
    }
}
