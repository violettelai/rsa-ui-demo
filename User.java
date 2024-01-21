import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Base64;
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
    private JTextArea PKArea = new JTextArea();
    private JTextArea SKArea = new JTextArea();
    static GUI gui = new GUI();

    public User(String username) {
        this.username = username;
        this.keygen(1024); // Call key generation method when creating a new user
    }

    // Both users can send & receive message
    void sendMessage(String message) {
        // to get receiver public key for encryption, secret key for decryption
        User receiver = retrieveReceiverObject();
        BigInteger encryptedMessage = receiver.encrypt(message);
        int error = encryptedMessage.intValue();
        if (receiver != null) {
            if(error != -1)
                receiver.receiveMessage(encryptedMessage);
        }
    }

    void receiveMessage(BigInteger message) {
        byte[] cByteArray = message.toByteArray();
        String c = Base64.getEncoder().encodeToString(cByteArray);

        ViewMessageArea.append("Received Encrypted Message: " + c + "\n");
        ViewMessageArea.append("Plaintext: " + decrypt(message) + "\n");
        ViewMessageArea.append("--------------------------------------\n");
    }

    // Retrieve Receiver User Object
    User retrieveReceiverObject() {
        // Alice's receiver is Bob and vice versa
        String receiverName = "";
        if(this.username.equals("Alice")) receiverName = "Bob";
        else receiverName = "Alice";

        Frame[] frames = Frame.getFrames();
        for (Frame frame : frames) {
            if (frame instanceof User && ((User) frame).username.equals(receiverName)) {
                return (User) frame;
            }
        }
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

        byte[] eByte = this.pk[0].toByteArray();
        String eString = Base64.getEncoder().encodeToString(eByte);
        byte[] dByte = this.sk[0].toByteArray();
        String dString = Base64.getEncoder().encodeToString(dByte);
        byte[] nByte = this.sk[1].toByteArray();
        String nString = Base64.getEncoder().encodeToString(nByte);

        PKArea.append("e: " + eString + "\nn: " + nString + "\n");
        SKArea.append("d: " + dString + "\nn: " + nString + "\n");
    }

    BigInteger encrypt(String m) {
        //convert string to byte, encoding format UTF8, convert byte to biginteger for calculation
        BigInteger message = new BigInteger(m.getBytes(StandardCharsets.UTF_8));

        BigInteger c;
        if(message.compareTo(this.pk[1]) == 1){
            JOptionPane.showMessageDialog(null, "Message Too Large!", "Error", JOptionPane.ERROR_MESSAGE);
            c = new BigInteger("-1");
        }else{
            c = message.modPow(this.pk[0], this.pk[1]);
        }

        return c;
    }

    String decrypt(BigInteger ciphertext) {
        // convert string to biginteger
        // BigInteger ciphertext = new BigInteger(c);
        BigInteger m = ciphertext.modPow(this.sk[0], this.sk[1]);
        
        //convert biginteger to byte
        byte[] decMByteArray = m.toByteArray();
        
        // convert byte to string, encoding format UTF8
        String s = new String(decMByteArray, StandardCharsets.UTF_8);

        return s;
    }

    JPanel menu() {
        final JButton SEND_MESSAGE_BUTTON = new JButton("Send Message");
        
        final JLabel usernameLabel = new JLabel(username);
        usernameLabel.setPreferredSize(new Dimension(100, 30));
        usernameLabel.setFont(new Font("Tahoma", Font.CENTER_BASELINE, 16));

        PKArea.setSize(new Dimension(310, 60));
        PKArea.setEditable(false);
        PKArea.setLineWrap(true);
        PKArea.setWrapStyleWord(true);

        SKArea.setSize(new Dimension(310, 60));
        SKArea.setEditable(false);
        SKArea.setLineWrap(true);
        SKArea.setWrapStyleWord(true);

        JPanel keyPanel = gui.createVertPanel(2, 2, 10, -10);
        keyPanel.add(new JLabel("Pk(e, n)"));
        keyPanel.add(new JLabel("Sk(d, n)"));
        keyPanel.add(gui.createScrollPane(PKArea));
        keyPanel.add(gui.createScrollPane(SKArea));
        keyPanel.setPreferredSize(new Dimension(600, 130));

        JPanel createMessagePanel = gui.createHoriPanel();
        final JTextField messageInputBox = new JTextField("Enter message here");
        messageInputBox.setPreferredSize(new Dimension(450, 50));
        createMessagePanel.add(messageInputBox);
        createMessagePanel.add(SEND_MESSAGE_BUTTON);
        
        ViewMessageArea = new JTextArea();
        ViewMessageArea.setEditable(false);
        ViewMessageArea.setRows(15);

        JScrollPane scrollPane = new JScrollPane(ViewMessageArea);
        scrollPane.setPreferredSize(new Dimension(400, 250));

        JPanel boxPanel = gui.createBoxPanel();
        boxPanel.add(usernameLabel);
        boxPanel.add(keyPanel);
        boxPanel.add(createMessagePanel);
        boxPanel.add(scrollPane);
        boxPanel.setBorder(BorderFactory.createLineBorder(Color.gray));

        ActionListener buttonAction = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(e.getSource()== SEND_MESSAGE_BUTTON){
                    String message = messageInputBox.getText();
                    if(message.length() < 20){
                        JOptionPane.showMessageDialog(null, "Message Too Short!", "Error", JOptionPane.ERROR_MESSAGE);
                    }else{
                        sendMessage(message);
                    }
                }
            }
        };
        SEND_MESSAGE_BUTTON.addActionListener(buttonAction);

        return boxPanel;
    }
}