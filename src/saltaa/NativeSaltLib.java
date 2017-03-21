package saltaa;

import org.libsodium.jni.SodiumJNI;

/**
 * libsodium-jni based implementation of SaltLib based on https://github.com/joshjdevl/libsodium-jni
 */
public class NativeSaltLib implements SaltLib {

    static {
        System.loadLibrary("sodiumjni");
    }
   
    private static SodiumJNI sodiumJNI() {
        SodiumJNI.sodium_init();
        return SingletonHolder.SODIUMJNI_INSTANCE;
    }
    
    private static final class SingletonHolder {
        public static final SodiumJNI SODIUMJNI_INSTANCE = new SodiumJNI();
    }

    @Override
    public String getName() {
        return "libsodium-jni";
    }

    @Override
    public void crypto_sign_keypair_not_random(byte[] pk, byte[] sk) {
		SodiumJNI.crypto_sign_seed_keypair(pk, sk, sk);   
    }

    @Override
    public void crypto_sign(byte[] sm, byte[] m, byte[] sk) {
        int[] dummy = new int[1];       
		SodiumJNI.crypto_sign(sm, dummy, m, m.length, sk);
    }

    @Override
    public void crypto_sign_open(byte[] m, byte[] sm, byte[] pk) {
        int[] dummy = new int[1];             
		int res = SodiumJNI.crypto_sign_open(m, dummy, sm, sm.length, pk);
        if (res != 0) {
            throw new BadSignature();
        }
    }
}
