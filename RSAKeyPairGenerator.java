import java.math.BigInteger;
import java.security.SecureRandom;

public class RSAKeyPairGenerator {

    public static class KeyPair {
        private final BigInteger publicKey;
        private final BigInteger privateKey;
        private final BigInteger modulus;

        public KeyPair(BigInteger publicKey, BigInteger privateKey, BigInteger modulus) {
            this.publicKey = publicKey;
            this.privateKey = privateKey;
            this.modulus = modulus;
        }

        public BigInteger getPublicKey() {
            return this.publicKey;
        }

        public BigInteger getPrivateKey() {
            return this.privateKey;
        }

        public BigInteger getModulus() {
            return this.modulus;
        }
    }

    public static KeyPair generateRSAKeyPair(int keySize) {
        SecureRandom random = new SecureRandom();

        // Generate two distinct probable primes p and q
        BigInteger p = BigInteger.probablePrime(keySize / 2, random);
        BigInteger q;
        do {
            q = BigInteger.probablePrime(keySize / 2, random);
        } while (q.equals(p));

        // Calculate n = pq
        BigInteger n = p.multiply(q);

        // Calculate Euler's totient function φ(n)
        BigInteger phiN = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        // Choose encryption exponent e = 65537 and ensure it is relatively prime to φ(n)
        BigInteger e = new BigInteger("65537");
        while (!e.gcd(phiN).equals(BigInteger.ONE)) {
            p = BigInteger.probablePrime(keySize / 2, random);
            q = BigInteger.probablePrime(keySize / 2, random);
            n = p.multiply(q);
            phiN = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        }

        // Compute the value for the decryption exponent d
        BigInteger d = e.modInverse(phiN);

        // Return public and private keys
        return new KeyPair(e, d, n);
    }
}
