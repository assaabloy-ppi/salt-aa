package saltaa;

import org.junit.Assert;
import org.junit.Test;
import saltaa.SaltLib;
import saltaa.SaltLibFactory;
import saltaa.SaltLibFactory.LibType;

public class SignTest {
    
    @Test
    public void testSignKeyPair() {
        SaltLib lib = SaltLibFactory.getLib(LibType.JAVA);
        byte[] sk = SaltTestData.aSigSec;
        byte[] pk = new byte[SaltLib.crypto_sign_PUBLICKEYBYTES];
        
        lib.crypto_sign_keypair_seeded(pk, sk);
        
        byte[] pkExpected = SaltTestData.aSigPub;
        Assert.assertArrayEquals(pkExpected, pk);
    }

    @Test
    public void testSign1() {
        SaltLib lib = SaltLibFactory.getLib(LibType.JAVA);
        byte[] m = new byte[]{1};
        byte[] sm = new byte[m.length + SaltLib.crypto_sign_BYTES];
        byte[] sk = SaltTestData.aSigSec;
        
        lib.crypto_sign(sm, m, sk);
        
        byte[] pk = SaltTestData.aSigPub;
        byte[] m2 = new byte[m.length + SaltLib.crypto_sign_BYTES];
        
        lib.crypto_sign_open(m2, sm, pk);
    }
}
