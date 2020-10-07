import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("DuplicatedCode")
public abstract class Comparator {
    public static final int IN_GAME = 0;
    public static final int VOTING = 1;
    public static final int LOBBY = 2;
    public static final int UNKNOWN = 3;
    private static final int IN_GAME_X_OFFSET_1 = 1787;
    private static final int IN_GAME_X_OFFSET_2 = 13;
    private static final int IN_GAME_Y_OFFSET_1 = 157;
    private static final int IN_GAME_Y_OFFSET_2 = 803;
    private static final int VOTING_X_OFFSET_1 = 200;
    private static final int VOTING_X_OFFSET_2 = 200;
    private static final int VOTING_Y_OFFSET_1 = 30;
    private static final int VOTING_Y_OFFSET_2 = 870;
    private static final int LOBBY_X_OFFSET_1 = 899;
    private static final int LOBBY_X_OFFSET_2 = 899;
    private static final int LOBBY_Y_OFFSET_1 = 930;
    private static final int LOBBY_Y_OFFSET_2 = 102;
    private static final float accuracy = 0.2F;
    private static boolean initialized;
    private static BufferedImage inGame;
    private static BufferedImage voting;
    private static BufferedImage lobby;

    public static void initialize() throws IOException {
        inGame = ImageIO.read(new File("res/in_game.png"));
        lobby = ImageIO.read(new File("res/lobby.png"));
        voting = ImageIO.read(new File("res/voting.png"));

        initialized = true;
    }

    public static int getState(BufferedImage screenshot) {
        if (!initialized) {
            try {
                initialize();
            } catch (IOException exception) {
                exception.printStackTrace();
                return UNKNOWN;
            }
        }
        if (getInGamePercentage(screenshot) >= accuracy) {
            return IN_GAME;
        } else if (getVotingPercentage(screenshot) >= accuracy) {
            return VOTING;
        } else if (getLobbyPercentage(screenshot) >= accuracy) {
            return LOBBY;
        }
        return UNKNOWN;
    }

    private static float getInGamePercentage(BufferedImage screenshot) {
        int similar = 0;
        int numOfPixels = 0;
        for (int x = IN_GAME_X_OFFSET_1; x < screenshot.getWidth() - IN_GAME_X_OFFSET_2; x++) {
            for (int y = IN_GAME_Y_OFFSET_1; y < screenshot.getHeight() - IN_GAME_Y_OFFSET_2; y++) {
                if (screenshot.getRGB(x, y) == inGame.getRGB(x, y)) {
                    similar++;
                }
                numOfPixels++;
            }
        }

        float percentage = (float) similar / numOfPixels;
        System.out.println(percentage);
        return percentage;
    }

    private static float getLobbyPercentage(BufferedImage screenshot) {
        int similar = 0;
        int numOfPixels = 0;
        for (int x = LOBBY_X_OFFSET_1; x < screenshot.getWidth() - LOBBY_X_OFFSET_2; x++) {
            for (int y = LOBBY_Y_OFFSET_1; y < screenshot.getHeight() - LOBBY_Y_OFFSET_2; y++) {
                if (screenshot.getRGB(x, y) == lobby.getRGB(x, y)) {
                    similar++;
                }
                numOfPixels++;
            }
        }
        float percentage = (float) similar / numOfPixels;
        System.out.println(percentage);
        return percentage;
    }

    private static float getVotingPercentage(BufferedImage screenshot) {
        int similar = 0;
        int numOfPixels = 0;
        for (int x = VOTING_X_OFFSET_1; x < screenshot.getWidth() - VOTING_X_OFFSET_2; x++) {
            for (int y = VOTING_Y_OFFSET_1; y < screenshot.getHeight() - VOTING_Y_OFFSET_2; y++) {
                if (screenshot.getRGB(x, y) == voting.getRGB(x, y)) {
                    similar++;
                }
                numOfPixels++;
            }
        }
        float percentage = (float) similar / numOfPixels;
        System.out.println(percentage);
        return percentage;
    }
}
