package saltaa;

import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import nperf.SaltTestData;

/**
 * Tests sign functions for a set of libraries.
 */
@RunWith(Parameterized.class)
public class SignTest {
    @Parameterized.Parameters
    public static List<SaltLib> data() {
        return SaltLibFactory.getAllLibs();
    }
    
    private SaltLib lib;
   
    public SignTest(SaltLib lib) {
        this.lib = lib;
    }
    
    @Test
    public void testSignKeyPair() {
        byte[] sk = SaltTestData.aSigSec;
        byte[] pk = new byte[SaltLib.crypto_sign_PUBLICKEYBYTES];
        
        lib.crypto_sign_keypair_not_random(pk, sk);
        
        byte[] pkExpected = SaltTestData.aSigPub;
        Assert.assertArrayEquals(pkExpected, pk);
    }

    @Test
    public void testSign1() {
        byte[] m = new byte[]{3, 3, 3, 3};
        byte[] sm = new byte[SaltLib.crypto_sign_BYTES + m.length];
        byte[] sk = SaltTestData.aSigSec;
        
        lib.crypto_sign(sm, m, sk);
        
        byte[] pk = SaltTestData.aSigPub;
        byte[] m2 = new byte[SaltLib.crypto_sign_BYTES + m.length];
        
        lib.crypto_sign_open(m2, sm, pk);
        
        //byte[] m3 = Arrays.copyOfRange(m2, SaltLib.crypto_sign_BYTES, m2.length);
        Assert.assertArrayEquals(m, Arrays.copyOf(m2, m.length));
    }
    
    @Test(expected=BadSignature.class)
    public void testBadSignature() {
        byte[] m = new byte[]{1};
        byte[] sm = new byte[SaltLib.crypto_sign_BYTES + m.length];
        byte[] sk = SaltTestData.aSigSec;
        
        lib.crypto_sign(sm, m, sk);
        
        sm[0] = (byte) (sm[0] + 1);   // destroying signature by changing a byte
        byte[] pk = SaltTestData.aSigPub;
        byte[] m2 = new byte[SaltLib.crypto_sign_BYTES + m.length];
        
        lib.crypto_sign_open(m2, sm, pk);
    }
}
