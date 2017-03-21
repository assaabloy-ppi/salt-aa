package saltaa;

/**
 * A useful subset of the 25 TweetNaCl functions is exposed 
 * through this interface.
 * 
 * crypto_sign and related functions implement the ed25519 signature scheme.
 * 
 * crypto_box and related functions implement public key authenticated encryption 
 * using x25519+xsalsa20+poly1305.
 * 
 * @author Frans Lundberg
 */
public interface SaltLib {

    public static final int crypto_sign_PUBLICKEYBYTES = 32;
    public static final int crypto_sign_SECRETKEYBYTES = 64;
    public static final int crypto_sign_BYTES = 64;
 
    /**
     * The crypto_sign_keypair_seeded function takes a secret key and generates 
     * a corresponding public key. The secret key is in sk[0], sk[1], ..., sk[crypto_sign_SECRETKEYBYTES-1]
     * It puts the public key into pk[0], pk[1], ..., pk[crypto_sign_PUBLICKEYBYTES-1].
     * It then returns 0. 
     * 
     * Changes from original (crypto_sign_keypair): this function is deterministic, no random 
     * data is used. Note, the secret key can be random data.
     * 
     * @param pk Resulting public key.
     * @param sk Input secret key.
     * @throws IllegalArgumentException If one of the parameters is not of the correct size.
     */
    public void crypto_sign_keypair_not_random(byte[] pk, byte[] sk);
    
    /**
     * The crypto_sign function signs a message m[0], ..., m[mlen-1] using the signer's secret 
     * key sk[0], sk[1], ..., sk[crypto_sign_SECRETKEYBYTES-1], puts the signed message 
     * into sm[0], sm[1], ..., sm[smlen-1]. It then returns 0.
     * The length of sm is m.length + crypto_sign_BYTES.
     *
     * @throws IllegalArgumentException
     */
    public void crypto_sign(byte[] sm, byte[] m, byte[] sk);
    
    /**
     * The crypto_sign_open function verifies the signature in sm[0], ..., sm[smlen-1] 
     * using the signer's public key pk[0], pk[1], ..., pk[crypto_sign_PUBLICKEYBYTES-1]. 
     * The crypto_sign_open function puts the message into m[0], m[1], ..., m[mlen-1].
     * The caller must allocate at least sm.length bytes for m. 
     * If the signature fails verification, crypto_sign_open throws BadSignature, 
     * possibly after modifying m[0], m[1], etc. 
     * mlen is sm.length-crypto_sign_BYTES.
     * 
     * @throws BadSignature
     * @throws IllegalArgumentException
     */
    public void crypto_sign_open(byte[] m, byte[] sm, byte[] pk);
}
