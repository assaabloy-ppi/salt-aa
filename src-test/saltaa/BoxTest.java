package saltaa;

import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import saltaa.nperf.SaltTestData;

/**
 * Tests crypto_box_x functions.
 */
@RunWith(Parameterized.class)
public class BoxTest {
    private SaltLib lib;
    
    @Parameterized.Parameters
    public static List<SaltLib> data() {
        return SaltLibFactory.getAllOperationalLibs();
    }
    
    public BoxTest(SaltLib lib) {
        this.lib = lib;
    }
    
    @Test
    public void testKeyPair1() {
        byte[] sk = SaltTestData.aEncSec;
        byte[] pk = new byte[SaltLib.crypto_box_PUBLICKEYBYTES];
        byte[] pkExpected = SaltTestData.aEncPub;
        
        lib.crypto_box_keypair_not_random(pk, sk);
        
        Assert.assertArrayEquals(pkExpected, pk);
    }
    
    /**
     * Tests Diffie-Hellman key agreement (x25519), function
     * crypto_box_beforenm.
     */
    @Test
    public void testBeforeNm() {
        byte[] ask = SaltTestData.aEncSec;
        byte[] apk = SaltTestData.aEncPub;
        byte[] bsk = SaltTestData.bEncSec;
        byte[] bpk = SaltTestData.bEncPub;
        byte[] k1 = new byte[SaltLib.crypto_box_BEFORENMBYTES];
        byte[] k2 = new byte[SaltLib.crypto_box_BEFORENMBYTES];
        
        lib.crypto_box_beforenm(k1, bpk, ask);
        lib.crypto_box_beforenm(k2, apk, bsk);
        
        Assert.assertArrayEquals(k1, k2);
    }
    
    /**
     * Tests encrypting a 1-byte long message 'A'.
     */
    @Test
    public void testBox1() {
        byte[] ask = SaltTestData.aEncSec;
        byte[] apk = SaltTestData.aEncPub;
        byte[] bsk = SaltTestData.bEncSec;
        byte[] bpk = SaltTestData.bEncPub;
        byte[] k1 = new byte[SaltLib.crypto_box_BEFORENMBYTES];
        byte[] k2 = new byte[SaltLib.crypto_box_BEFORENMBYTES];
        byte[] n = new byte[SaltLib.crypto_box_NONCEBYTES];
        
        lib.crypto_box_beforenm(k1, bpk, ask);
        lib.crypto_box_beforenm(k2, apk, bsk);
        
        byte[] message = new byte[]{'A'};
        byte[] m = new byte[SaltLib.crypto_box_ZEROBYTES + message.length];
        byte[] c = new byte[m.length];
        byte[] m2 = new byte[m.length];
        System.arraycopy(message, 0, m, SaltLib.crypto_box_ZEROBYTES, message.length);
        
        lib.crypto_box_afternm(c, m, n, k1);
        lib.crypto_box_open_afternm(m2, c, n, k2);
        
        byte[] message2 = Arrays.copyOfRange(m2, SaltLib.crypto_box_ZEROBYTES, m2.length);
        
        //System.out.println("BoxTest");
        //System.out.println("c: " + Hex.create(c));
        //System.out.println("m: " + Hex.create(m));
        //System.out.println("k1: " + Hex.create(k1));
        //System.out.println("k2: " + Hex.create(k2));
        //System.out.println("m2: " + Hex.create(m2));
        //System.out.println("n: " + Hex.create(n));
        //System.out.println("message: " + Hex.create(message));
        //System.out.println("message2: " + Hex.create(message2));
        
        Assert.assertArrayEquals(message, message2);
    }
    
    
    /**
     * Tests encrypting a 1-byte long message 'A', modifies the ciphertext 
     * and expects 
     */
    @Test(expected=BadEncryptedDataException.class)
    public void testBox2() {
        byte[] ask = SaltTestData.aEncSec;
        byte[] apk = SaltTestData.aEncPub;
        byte[] bsk = SaltTestData.bEncSec;
        byte[] bpk = SaltTestData.bEncPub;
        byte[] k1 = new byte[SaltLib.crypto_box_BEFORENMBYTES];
        byte[] k2 = new byte[SaltLib.crypto_box_BEFORENMBYTES];
        byte[] n = new byte[SaltLib.crypto_box_NONCEBYTES];
        
        lib.crypto_box_beforenm(k1, bpk, ask);
        lib.crypto_box_beforenm(k2, apk, bsk);
        
        byte[] message = new byte[]{'A'};
        byte[] m = new byte[SaltLib.crypto_box_ZEROBYTES + message.length];
        byte[] c = new byte[m.length];
        byte[] m2 = new byte[m.length];
        System.arraycopy(message, 0, m, SaltLib.crypto_box_ZEROBYTES, message.length);
        
        lib.crypto_box_afternm(c, m, n, k1);
        
        c[c.length - 1] = (byte) (c[c.length - 1] + 1);
        
        lib.crypto_box_open_afternm(m2, c, n, k2);
    }
}
