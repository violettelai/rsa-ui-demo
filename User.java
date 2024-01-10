import java.util.Scanner;
import java.math.BigInteger;
import java.security.SecureRandom;

public class User {
    private String username;
    private BigInteger[] pk;
    private BigInteger[] sk;

    public User(String username) {
        this.username = username;
        this.keygen(1024); // Call key generation method when creating a new user
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
