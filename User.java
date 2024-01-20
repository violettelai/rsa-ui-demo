import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import javax.swing.*;
import java.math.BigInteger;
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

    public BigInteger[] retrievePublicKey(){
        return pk;
    }

    // Both users can send & receive message
    void sendMessage(String message) {
        String encryptedMessage = encrypt(message);
        User receiver = retrieveReceiverObject();
        if (receiver != null) {
            receiver.receiveMessage(encryptedMessage);
        }
    }

    void receiveMessage(String message) {
        ViewMessageArea.append("Received Encrypted Message: " + message + "\n");
        ViewMessageArea.append("Plaintext: " + decrypt(message) + "\n");
        System.out.println(decrypt(message));
        ViewMessageArea.append("--------------------------------------\n");
    }

    // Retrieve Receiver User Object
    User retrieveReceiverObject() {
        // Alice's receiver is Bob and vice versa
        String receiverName = "";
        if(username.equals("Alice")) receiverName = "Bob";
        else receiverName = "Alice";

        Frame[] frames = Frame.getFrames();
        for (Frame frame : frames) {
            if (frame instanceof User && ((User) frame).username.equals(receiverName)) {
                return (User) frame;
            }
        }
        JOptionPane.showMessageDialog(null, "Bob is not logged in!", "Error", JOptionPane.ERROR_MESSAGE);
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

        // System.out.println("pk: (" + pk[0] + ","+pk[1]+")");
        // System.out.println("sk: (" + sk[0] + ","+sk[1]+")");
        // BigInteger a = new BigInteger("2");
        // System.out.println("2^1024: " + a.pow(1024));
        // BigInteger i = new BigInteger("1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111", 2);
        // System.out.println(i);
    }

    String encrypt(String m) {
        BigInteger message = new BigInteger(m.getBytes());

        // message must be smaller than n?
        // if(message.compareTo(this.pk[1])!=1) System.out.println("m: " + message + " < n :" +this.pk[1]);
        // else return "message too large";
        
        BigInteger c = message.modPow(this.pk[0], this.pk[1]);
        return c.toString();
    }

    String decrypt(String c) {
        BigInteger ciphertext = new BigInteger(c);
        BigInteger m = ciphertext.modPow(this.sk[0], this.sk[1]);
        return new String(m.toByteArray());
    }

    void menu() {
        final JFrame f = gui.createFrame(this.username + " Window");

        final JButton CREATE_MESSAGE_BUTTON = new JButton("Create Message");
        final JButton LOGOUT_BUTTON = new JButton("Log Out");

        JPanel buttonPanel = gui.createHoriPanel();
        buttonPanel.add(LOGOUT_BUTTON);

        JPanel createMessagePanel = gui.createHoriPanel();
        final JTextField messageInputBox = new JTextField("20 characters only");
        createMessagePanel.add(messageInputBox);
        createMessagePanel.add(CREATE_MESSAGE_BUTTON);
        
        ViewMessageArea = new JTextArea();
        ViewMessageArea.setEditable(false);
        ViewMessageArea.setRows(15);

        JScrollPane scrollPane = new JScrollPane(ViewMessageArea);
        scrollPane.setPreferredSize(new Dimension(400, 250));

        JPanel boxPanel = gui.createBoxPanel();
        // boxPanel.add(Box.createVerticalStrut(35));
        boxPanel.add(buttonPanel);
        boxPanel.add(createMessagePanel);
        // boxPanel.setBackground(Color.RED);
        boxPanel.add(scrollPane);

        f.add(boxPanel, BorderLayout.NORTH);
        f.setVisible(true);

        ActionListener buttonAction = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(e.getSource()== CREATE_MESSAGE_BUTTON){
                    String message = messageInputBox.getText();
                    if(message.length() >= 20){
                        JOptionPane.showMessageDialog(null, "Message Too Long!", "Error", JOptionPane.ERROR_MESSAGE);
                    }else{
                        sendMessage(message);
                    }
                    // createMsg();
                }else if(e.getSource()== LOGOUT_BUTTON){
                    f.dispose();
                }
            }
        };

        CREATE_MESSAGE_BUTTON.addActionListener(buttonAction);
        LOGOUT_BUTTON.addActionListener(buttonAction);
    }

    void createMsg() {
        // send to: receiver
        // enter your message: xxxxxx... = m
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter receiver's username: ");
        String receiver = scanner.nextLine();
        System.out.print("Enter your message: ");
        String message = scanner.nextLine();

        // Placeholder code for demonstration purposes
        String c = this.encrypt(message);
        System.out.println("Successfully sent: " + c);
        this.menu();
    }

    void viewMsg() {
        // getmsglist
        // no from date
        // 1. alice 20240101 23:11
        // 2. bob 20240102 11:12
        // c = choice
        // display(c)
    }

    void display(String c) {
        // m = decrypt(c)
        // print(m)
        // viewMsg()
    }

    void logout() {
        System.out.println("Logged out successfully.");
    }
}
