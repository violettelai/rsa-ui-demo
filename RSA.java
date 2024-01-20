import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.event.*;

class RSA{
    static GUI gui = new GUI();
    public static void main (String args[]){
        final JFrame f = gui.createFrame("Account Login");
        f.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        // Only closing main frame will exit program
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int ans = JOptionPane.showConfirmDialog(null, "Do you want to exit the program?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(ans == JOptionPane.YES_OPTION) System.exit(0);
            }
        });

        final JButton ALICE_BUTTON = new JButton("Login as Alice");
        final JButton BOB_BUTTON = new JButton("Login as Bob");
        final JButton EXIT_BUTTON = new JButton("Exit Program");

        JPanel buttonPanel = gui.createVertPanel(3, 1, 0, 50);
        buttonPanel.setMaximumSize(new Dimension(300,230));
        buttonPanel.add(ALICE_BUTTON);
        buttonPanel.add(BOB_BUTTON);
        buttonPanel.add(EXIT_BUTTON);

        JPanel boxPanel = gui.createBoxPanel();
        boxPanel.add(Box.createVerticalStrut(35));
        boxPanel.add(buttonPanel);

        f.add(boxPanel, BorderLayout.CENTER);
        f.setVisible(true);

        ActionListener buttonAction = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(e.getSource()== ALICE_BUTTON){
                    User alice = new User("Alice");
                    alice.menu();
                }else if(e.getSource()== BOB_BUTTON){
                    User bob = new User("Bob");
                    bob.menu();
                }else if(e.getSource()== EXIT_BUTTON){
                    System.exit(0);
                }
            }
        };
        ALICE_BUTTON.addActionListener(buttonAction);
        BOB_BUTTON.addActionListener(buttonAction);
        EXIT_BUTTON.addActionListener(buttonAction);
    }
}
