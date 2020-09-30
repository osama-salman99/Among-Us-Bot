import javax.swing.*;

// If the same handle exists, remove the first one.
// Add restart button

public class MainClass {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        AmongUsApp app = new AmongUsApp();
        new AmongUsBot(app);
    }
}
