import java.awt.*;
import java.awt.image.BufferedImage;

public class ScreenCapturer {
    private static final int DELAY = 1000; // in milliseconds
    private final Robot robot;
    private final Rectangle screenRect;
    private BufferedImage screenshot;
    private boolean on;
    private Thread captureThread;

    public ScreenCapturer() {
        try {
            this.robot = new Robot();
        } catch (AWTException exception) {
            exception.printStackTrace();
            throw new RuntimeException(exception);
        }
        this.screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        this.on = true;
    }

    private void captureScreen() {
        screenshot = robot.createScreenCapture(screenRect);
    }

    public BufferedImage getScreenshot() {
        if (screenshot == null) {
            captureScreen();
        }
        return screenshot;
    }

    public void stop() {
        this.on = false;
    }

    public void resume() {
        this.on = true;
        startCapturing();
    }

    private void startCapturing() {
        if (captureThread != null) {
            if (captureThread.isAlive()) {
                return;
            }
        }
        captureThread = new Thread(new Runnable() {
            @Override
            public synchronized void run() {
                while (on) {
                    captureScreen();
                    try {
                        wait(DELAY);
                    } catch (InterruptedException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        });
        captureThread.start();
    }
}
