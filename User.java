import java.util.Scanner;
import java.math.BigInteger;

public class User {
    private String username;
    private BigInteger publicKey;
    private BigInteger privateKey;
    private BigInteger modulus;

    public User(String username) {
        this.username = username;
        this.keygen(); // Call key generation method when creating a new user
    }

    void keygen() {
        // Generate RSA key pair using the RSAKeyPairGenerator
        RSAKeyPairGenerator.KeyPair keyPair = RSAKeyPairGenerator.generateRSAKeyPair(512);

        // Set user's public and private keys
        this.publicKey = keyPair.getPublicKey();
        this.privateKey = keyPair.getPrivateKey();
        this.modulus = keyPair.getModulus();
    }

    String encrypt(String m) {
        // Encrypt message using public key
        BigInteger message = new BigInteger(m.getBytes());
        BigInteger encryptedMessage = message.modPow(this.publicKey, this.modulus);
        return encryptedMessage.toString();
    }

    String decrypt(String c) {
        // Decrypt ciphertext using private key
        BigInteger ciphertext = new BigInteger(c);
        BigInteger decryptedMessage = ciphertext.modPow(this.privateKey, this.modulus);
        return new String(decryptedMessage.toByteArray());
    }

    void menu() {
        // action
        // 1. send message
        // 2. view message
        // 3. logout
        // choice

        // if (choice == 1)
        // createMsg()
        // else if (choice == 2)
        // viewMsg()
        // else
        // logout()

        // Placeholder code for demonstration purposes
        int choice = 1;  // Assuming choice 1 for sending a message
        if (choice == 1) {
            this.createMsg();
        } else if (choice == 2) {
            this.viewMsg();
        } else {
            this.logout();
        }
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
        String encryptedMessage = this.encrypt(message);
        System.out.println("Successfully sent: " + encryptedMessage);
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
