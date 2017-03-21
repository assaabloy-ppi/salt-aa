package saltaa;

import com.iwebpp.crypto.TweetNaclFast;

/**
 * Pure Java implementation of SaltLib based on https://github.com/InstantWebP2P/tweetnacl-java.
 */
public class JavaSaltLib implements SaltLib {

    @Override
    public String getName() {
        return "JAVA";
    }
    
    @Override
    public void crypto_sign_keypair_not_random(byte[] pk, byte[] sk) {
        TweetNaclFast.crypto_sign_keypair(pk, sk, true);
    }

    @Override
    public void crypto_sign(byte[] sm, byte[] m, byte[] sk) {
        long dummy = 0;
        TweetNaclFast.crypto_sign(sm, dummy, m, 0, m.length, sk);
    }

    @Override
    public void crypto_sign_open(byte[] m, byte[] sm, byte[] pk) {
        long dummy = 0;
        int res = TweetNaclFast.crypto_sign_open(m, dummy, sm, 0, sm.length, pk);
        if (res != 0) {
            throw new BadSignature();
        }
        System.arraycopy(m, SaltLib.crypto_sign_BYTES, m, 0, sm.length-SaltLib.crypto_sign_BYTES);
    }
}
