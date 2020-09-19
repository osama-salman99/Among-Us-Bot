import javax.swing.*;

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
