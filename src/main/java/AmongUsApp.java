import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class AmongUsApp {
    private static final String APP_NAME = "Among Us Bot";
    private static final String SIZE_PANEL_TEXT = "Size:";
    private static final String SHOW_OVERLAY_TEXT = "Show overlay";
    private static final String HIDE_OVERLAY_TEXT = "Hide overlay";
    private static final String MUTE_TEXT = "Mute";
    private static final String UNMUTE_TEXT = "Unmute";
    private static final String RESTART_TEXT = "Restart";
    private static final String AUT_DET_STOP_TEXT = "Stop auto detection";
    private static final String AUT_DET_RESUME_TEXT = "Resume auto detection";
    private static final String UNKNOWN_TEXT = "Unknown";
    private static final String LOBBY_TEXT = "Lobby";
    private static final String IN_GAME_TEXT = "In-game";
    private static final String VOTING_TEXT = "Voting";
    private static final int MAXIMUM_WIDTH = 300;
    private static final int MINIMUM_WIDTH = 200;
    private static final int COLUMN_HEIGHT = 25;
    private static final int INCREASE_VALUE = 10;
    private static final int AUT_DET_DELAY = 1000; // in milliseconds
    private final ArrayList<Player> players;
    private final ScreenCapturer screenCapturer;
    private final int SCREEN_HEIGHT;
    private JFrame mainFrame;
    private JFrame overlayFrame;
    private JButton stateButton;
    private Thread autoDetectionThread;
    private boolean overlayShown;
    private boolean autoDetectionOn;
    private int currentWidth;

    public AmongUsApp() {
        SCREEN_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        System.out.println(SCREEN_HEIGHT);
        currentWidth = (MAXIMUM_WIDTH + MINIMUM_WIDTH) / 2;
        players = new ArrayList<>();
        overlayShown = false;
        autoDetectionOn = true;
        screenCapturer = new ScreenCapturer();
        initializeFrame();
        addButtons();
        createOverlay();
        showFrame();
        Runtime.getRuntime().addShutdownHook(new Thread(this::unmuteAll));
    }

    private void initializeFrame() {
        mainFrame = new JFrame(APP_NAME);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setSize(300, 200);
        mainFrame.setEnabled(false);
    }

    // Hi

    private void addButtons() {
        JPanel mainPanel = new JPanel(new GridLayout(4, 1));
        JLabel sizeTextArea = new JLabel(SIZE_PANEL_TEXT, SwingConstants.CENTER);
        JPanel sizeButtonsPanel = new JPanel(new GridLayout(1, 2));
        JButton increaseSizeButton = new JButton("+");
        JButton decreaseSizeButton = new JButton("-");
        JButton overlayButton = new JButton(SHOW_OVERLAY_TEXT);
        JButton autoDetectionButton = new JButton(AUT_DET_STOP_TEXT);

        increaseSizeButton.addActionListener(event -> increaseSize());
        decreaseSizeButton.addActionListener(event -> decreaseSize());
        autoDetectionButton.addActionListener(e -> {
            if (autoDetectionOn) {
                stopAutoDetection();
                autoDetectionButton.setText(AUT_DET_RESUME_TEXT);
            } else {
                resumeAutoDetection();
                autoDetectionButton.setText(AUT_DET_STOP_TEXT);
            }
        });

        sizeButtonsPanel.add(increaseSizeButton);
        sizeButtonsPanel.add(decreaseSizeButton);

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

        mainPanel.add(sizeTextArea);
        mainPanel.add(sizeButtonsPanel);
        mainPanel.add(overlayButton);
        mainPanel.add(autoDetectionButton);
        mainFrame.add(mainPanel);
    }

    private void createOverlay() {
        overlayFrame = new JFrame();
        overlayFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        overlayFrame.setUndecorated(true);
        overlayFrame.setAlwaysOnTop(true);
        overlayFrame.setSize(currentWidth, COLUMN_HEIGHT);
        overlayFrame.setLayout(new GridLayout(0, 1));
        overlayFrame.setOpacity(0.80F);

        JPanel mutePanel = new JPanel(new GridLayout(1, 2));
        JPanel restartPanel = new JPanel(new GridLayout(1, 1));
        JPanel statePanel = new JPanel(new GridLayout(1, 1));
        JButton muteAllButton = new JButton(MUTE_TEXT);
        JButton unmuteAllButton = new JButton(UNMUTE_TEXT);
        JButton restartButton = new JButton(RESTART_TEXT);
        stateButton = new JButton(UNKNOWN_TEXT);

        stateButton.addActionListener(event -> quickHide());

        muteAllButton.addActionListener(event -> muteAll());
        muteAllButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                muteAllButton.setForeground(Color.YELLOW);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                muteAllButton.setForeground(Color.RED);
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        unmuteAllButton.addActionListener(event -> unmuteAll());
        unmuteAllButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                unmuteAllButton.setForeground(Color.YELLOW);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                unmuteAllButton.setForeground(Color.GREEN);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        restartButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                restartButton.setForeground(Color.YELLOW);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                restartButton.setForeground(Color.WHITE);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        restartButton.addActionListener(event -> restartGame());

        muteAllButton.setForeground(Color.RED);
        muteAllButton.setOpaque(false);
        muteAllButton.setFocusPainted(false);
        muteAllButton.setBorderPainted(false);
        muteAllButton.setContentAreaFilled(false);

        unmuteAllButton.setForeground(Color.GREEN);
        unmuteAllButton.setOpaque(false);
        unmuteAllButton.setFocusPainted(false);
        unmuteAllButton.setBorderPainted(false);
        unmuteAllButton.setContentAreaFilled(false);

        restartButton.setForeground(Color.WHITE);
        restartButton.setOpaque(false);
        restartButton.setFocusPainted(false);
        restartButton.setBorderPainted(false);
        restartButton.setContentAreaFilled(false);

        stateButton.setForeground(Color.WHITE);
        stateButton.setOpaque(false);
        stateButton.setFocusPainted(false);
        stateButton.setBorderPainted(false);
        stateButton.setContentAreaFilled(false);

        mutePanel.add(muteAllButton);
        mutePanel.add(unmuteAllButton);
        restartPanel.add(restartButton);
        statePanel.add(stateButton);

        mutePanel.setBackground(Color.BLACK);
        restartPanel.setBackground(Color.BLACK);
        statePanel.setBackground(Color.BLACK);

        overlayFrame.add(statePanel);
        overlayFrame.add(mutePanel);
        overlayFrame.add(restartPanel);
    }

    private void muteAll() {
        for (Player player : players) {
            new Thread(() -> {
                try {
                    player.mute();
                } catch (PlayerDisconnectedException exception) {
                    System.out.println(exception.getMessage());
                    players.remove(player);
                }
            }).start();
        }
        Player.setAllMuted(true);
    }

    private void unmuteAll() {
        for (Player player : players) {
            new Thread(() -> {
                try {
                    player.unmute();
                } catch (PlayerDisconnectedException exception) {
                    System.out.println(exception.getMessage());
                    players.remove(player);
                }
            }).start();
        }
        Player.setAllMuted(false);
    }

    private void hideOverlay() {
        overlayFrame.setVisible(false);
    }

    private void showOverlay() {
        overlayFrame.setVisible(true);
        overlayFrame.setLocation(0, SCREEN_HEIGHT - overlayFrame.getHeight());
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
        if (players.contains(player)) {
            removePlayer(player);
        }
        players.add(player);
        overlayFrame.add(player.getPlayerPanel());
        refreshOverlay();
    }

    public void removePlayer(Player player) {
        int index = players.indexOf(player);
        overlayFrame.remove(players.remove(index).getPlayerPanel());
        refreshOverlay();
    }

    private synchronized void refreshOverlay() {
        updateOverlaySize();
        if (overlayFrame.isVisible()) {
            hideOverlay();
            showOverlay();
        }
    }

    public String getPassword() {
        String password = JOptionPane.showInputDialog(mainFrame, "Please enter your server's password");
        if (password == null) {
            System.exit(0);
        }
        return password;
    }

    public void enableFrame() {
        mainFrame.setEnabled(true);
        resumeAutoDetection();
    }

    private void restartGame() {
        unKillAll();
        unmuteAll();
    }

    private void unKillAll() {
        for (Player player : players) {
            try {
                player.unKill();
            } catch (PlayerDisconnectedException exception) {
                System.out.println(exception.getMessage());
                players.remove(player);
            }
        }
    }

    private void stopAutoDetection() {
        screenCapturer.stop();
        autoDetectionOn = false;
    }

    private void resumeAutoDetection() {
        screenCapturer.resume();
        autoDetectionOn = true;
        if (autoDetectionThread != null) {
            if (autoDetectionThread.isAlive()) {
                return;
            }
        }
        autoDetectionThread = new Thread(new Runnable() {
            @Override
            public synchronized void run() {
                while (autoDetectionOn) {
                    BufferedImage screenshot = screenCapturer.getScreenshot();
                    int state = Comparator.getState(screenshot);
                    switch (state) {
                        case Comparator.IN_GAME:
                            System.out.println("State: in-game");
                            stateButton.setText(IN_GAME_TEXT);
                            muteAll();
                            break;
                        case Comparator.VOTING:
                            System.out.println("State: voting");
                            stateButton.setText(VOTING_TEXT);
                            unmuteAll();
                            break;
                        case Comparator.LOBBY:
                            System.out.println("State: lobby");
                            stateButton.setText(LOBBY_TEXT);
                            restartGame();
                            break;
                        case Comparator.UNKNOWN:
                            System.out.println("State: unknown");
                            stateButton.setText(UNKNOWN_TEXT);
                            // Do nothing
                            break;
                    }
                    try {
                        wait(AUT_DET_DELAY);
                    } catch (InterruptedException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        });
        autoDetectionThread.start();
    }

    private void quickHide() {
        new Thread(new Runnable() {
            @Override
            public synchronized void run() {
                hideOverlay();
                try {
                    wait(2000);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                    showOverlay();
                }
                showOverlay();
            }
        }).start();
    }
}
