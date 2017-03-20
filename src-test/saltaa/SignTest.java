package saltaa;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import saltaa.SaltLibFactory.LibType;

@RunWith(Parameterized.class)
public class SignTest {
    @Parameterized.Parameters
    public static List<SaltLib> data() {
        return Arrays.asList(javaLib());
        //return Arrays.asList(javaLib(), nativeLib());  // TODO once other libs implemented, test all of them.
    }
    
    private static SaltLib javaLib() {
        return SaltLibFactory.getLib(LibType.JAVA);
    }
    
    private static SaltLib nativeLib() {
        return SaltLibFactory.getLib(LibType.NATIVE);
    }
    
    private SaltLib lib;
   
    public SignTest(SaltLib lib) {
        this.lib = lib;
    }
    
    @Test
    public void testSignKeyPair() {
        byte[] sk = SaltTestData.aSigSec;
        byte[] pk = new byte[SaltLib.crypto_sign_PUBLICKEYBYTES];
        
        lib.crypto_sign_keypair_seeded(pk, sk);
        
        byte[] pkExpected = SaltTestData.aSigPub;
        Assert.assertArrayEquals(pkExpected, pk);
    }

    @Test
    public void testSign1() {
        byte[] m = new byte[]{1};
        byte[] sm = new byte[m.length + SaltLib.crypto_sign_BYTES];
        byte[] sk = SaltTestData.aSigSec;
        
        lib.crypto_sign(sm, m, sk);
        
        byte[] pk = SaltTestData.aSigPub;
        byte[] m2 = new byte[m.length + SaltLib.crypto_sign_BYTES];
        
        lib.crypto_sign_open(m2, sm, pk);
    }
    
    @Test(expected=BadSignature.class)
    public void testBadSignature() {
        byte[] m = new byte[]{1};
        byte[] sm = new byte[m.length + SaltLib.crypto_sign_BYTES];
        byte[] sk = SaltTestData.aSigSec;
        
        lib.crypto_sign(sm, m, sk);
        
        sm[0] = (byte) (sm[0] + 1);   // destroying signature by changing a byte
        byte[] pk = SaltTestData.aSigPub;
        byte[] m2 = new byte[m.length + SaltLib.crypto_sign_BYTES];
        
        lib.crypto_sign_open(m2, sm, pk);
    }
}
