import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import javax.swing.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import javax.swing.event.*;

public class User extends JFrame{
    private String username;
    private BigInteger[] pk;
    private BigInteger[] sk;
    private JTextArea ViewMessageArea;
    static GUI gui = new GUI();

    public User(String username) {
        this.username = username;
        this.keygen(1024); // Call key generation method when creating a new user
    }

    // Alice's receiver is Bob and vice versa
    String retrieveReceiverName(){
        if(this.username.equals("Alice")) return "Bob";
        else return "Alice";
    }

    // Both users can send & receive message
    void sendMessage(String message) {
        // to get receiver public key for encryption, secret key for decryption
        User receiver = retrieveReceiverObject();
        String encryptedMessage = receiver.encrypt(message);
        if (receiver != null) {
            receiver.receiveMessage(encryptedMessage);
        }
    }

    void receiveMessage(String message) {
        ViewMessageArea.append("Received Encrypted Message: " + message + "\n");
        ViewMessageArea.append("Plaintext: " + decrypt(message) + "\n");
        ViewMessageArea.append("--------------------------------------\n");
    }

    // Retrieve Receiver User Object
    User retrieveReceiverObject() {
        String receiverName = retrieveReceiverName();

        Frame[] frames = Frame.getFrames();
        for (Frame frame : frames) {
            if (frame instanceof User && ((User) frame).username.equals(receiverName)) {
                return (User) frame;
            }
        }
        JOptionPane.showMessageDialog(null, retrieveReceiverName() + " is not logged in!", "Error", JOptionPane.ERROR_MESSAGE);
        return null;
    }

    void keygen(int keySize) {
        SecureRandom random = new SecureRandom();

         // Choose encryption exponent e = 65537 and ensure it is relatively prime to φ(n)
        BigInteger p, q, n, phiN;
        BigInteger e = new BigInteger("65537");

        do{
            // Generate two distinct probable primes p and q
            p = BigInteger.probablePrime(keySize/2, random);
            do {
                q = BigInteger.probablePrime(keySize/2, random);
            } while (q.equals(p));
            
            // Calculate n = pq
            n = p.multiply(q);

            // Calculate Euler's totient function φ(n) = (p-1)(q-1)
            phiN = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        }while(!e.gcd(phiN).equals(BigInteger.ONE));

        // Compute the value for the decryption exponent d
        BigInteger d = e.modInverse(phiN);

        // Set public and private key pairs
        this.pk = new BigInteger[2];
        this.pk[0] = e;
        this.pk[1] = n;
        this.sk = new BigInteger[2];
        this.sk[0] = d;
        this.sk[1] = n;
    }

    String encrypt(String m) {
        //convert string to byte, encoding format UTF8, convert byte to biginteger for calculation
        BigInteger message = new BigInteger(m.getBytes(StandardCharsets.UTF_8));
        BigInteger c = message.modPow(this.pk[0], this.pk[1]);
        
        return c.toString(); //Convert BigInteger to string
    }

    String decrypt(String c) {
        //convert string to biginteger
        BigInteger ciphertext = new BigInteger(c);
        BigInteger m = ciphertext.modPow(this.sk[0], this.sk[1]);
        
        //convert biginteger to byte
        byte[] decMByteArray = m.toByteArray();
        
        //convert byte to string, encoding format UTF8
        String s = new String(decMByteArray, StandardCharsets.UTF_8);

        return s;
    }

    void menu() {
        final JFrame f = gui.createFrame(this.username + " Window");

        final JButton SEND_MESSAGE_BUTTON = new JButton("Send Message");
        final JButton LOGOUT_BUTTON = new JButton("Log Out");

        JPanel buttonPanel = gui.createHoriPanel();
        buttonPanel.add(LOGOUT_BUTTON);

        JPanel createMessagePanel = gui.createHoriPanel();
        final JTextField messageInputBox = new JTextField("Enter message here");
        createMessagePanel.add(messageInputBox);
        createMessagePanel.add(SEND_MESSAGE_BUTTON);
        
        ViewMessageArea = new JTextArea();
        ViewMessageArea.setEditable(false);
        ViewMessageArea.setRows(15);

        JScrollPane scrollPane = new JScrollPane(ViewMessageArea);
        scrollPane.setPreferredSize(new Dimension(400, 250));

        JPanel boxPanel = gui.createBoxPanel();
        boxPanel.add(buttonPanel);
        boxPanel.add(createMessagePanel);
        boxPanel.add(scrollPane);

        f.add(boxPanel, BorderLayout.NORTH);
        f.setVisible(true);

        ActionListener buttonAction = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(e.getSource()== SEND_MESSAGE_BUTTON){
                    String message = messageInputBox.getText();
                    if(message.length() < 20){
                        JOptionPane.showMessageDialog(null, "Message Too Short!", "Error", JOptionPane.ERROR_MESSAGE);
                    }else{
                        sendMessage(message);
                    }
                }else if(e.getSource()== LOGOUT_BUTTON){
                    f.dispose();
                }
            }
        };
        SEND_MESSAGE_BUTTON.addActionListener(buttonAction);
        LOGOUT_BUTTON.addActionListener(buttonAction);
    }
}