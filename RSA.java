import java.awt.*;
import javax.swing.*;

class RSA{
    static GUI gui = new GUI();
    public static void main (String args[]){
        final JFrame f = gui.createFrame("RSA");

        User alice = new User("Alice");
        JPanel aPanel = alice.menu();

        User bob = new User("Bob");
        JPanel bPanel = bob.menu();

        JPanel boxPanel = gui.createBoxPanel();
        boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.X_AXIS));
        boxPanel.add(aPanel);
        boxPanel.add(Box.createHorizontalStrut(35));
        boxPanel.add(bPanel);

        f.add(boxPanel, BorderLayout.CENTER);
        f.setVisible(true);
    }
}
