import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.event.*;

class RSA{
    static GUI gui = new GUI();
    public static void main (String args[]){
        final JFrame f = gui.createFrame("Account Login");

        //Only closing main frame will exit program
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int ans = JOptionPane.showConfirmDialog(null, "Do you want to exit the program?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(ans == JOptionPane.YES_OPTION) System.exit(0);
            }
        });

        final JButton SENDER_BUTTON = new JButton("Login as Sender");
        final JButton RECEIVER_BUTTON = new JButton("Login as Receiver");
        final JButton EXIT_BUTTON = new JButton("Exit Program");

        JPanel buttonPanel = gui.createVertPanel(3, 1, 0, 50);
        buttonPanel.setMaximumSize(new Dimension(300,230));
        buttonPanel.add(SENDER_BUTTON);
        buttonPanel.add(RECEIVER_BUTTON);
        buttonPanel.add(EXIT_BUTTON);

        JPanel boxPanel = gui.createBoxPanel();
        boxPanel.add(Box.createVerticalStrut(35));
        boxPanel.add(buttonPanel);

        f.add(boxPanel, BorderLayout.CENTER);
        f.setVisible(true);

        ActionListener buttonAction = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(e.getSource()== SENDER_BUTTON){
                    // f.dispose();
                    User sender = new User("Sender");
                    sender.menu();
                }else if(e.getSource()== RECEIVER_BUTTON){
                    // f.dispose();
                    User receiver = new User("Receiver");
                    receiver.menu();
                }else if(e.getSource()== EXIT_BUTTON){
                    // f.dispose();
                    System.exit(0);
                }
            }
        };

        SENDER_BUTTON.addActionListener(buttonAction);
        RECEIVER_BUTTON.addActionListener(buttonAction);
        EXIT_BUTTON.addActionListener(buttonAction);
    }

    static boolean login(){
        User user = new User("Alice");  // Assuming Alice is the username
        user.menu();
        return(true);
    }
}
