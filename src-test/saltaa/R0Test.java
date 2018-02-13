package saltaa;

import org.junit.Assert;
import org.junit.Test;

import saltaa.nperf.SaltTestData;

/**
 * Tests with the "r0" data in SaltTestData.
 * 
 * @author Frans Lundberg
 */
public class R0Test {
    
    @Test
    public void testPubKey() {
    	SaltLib lib = SaltLibFactory.getLib(SaltLibFactory.LibType.JAVA);
    	byte[] sk = SaltTestData.r0SigSec;
    	byte[] pk = new byte[SaltLib.crypto_sign_PUBLICKEYBYTES];
    	lib.crypto_sign_keypair_not_random(pk, sk);
    	Assert.assertArrayEquals(SaltTestData.r0SigPub, pk);
    }
    
    @Test
    public void testSignature() {
    	SaltLib lib = SaltLibFactory.getLib(SaltLibFactory.LibType.JAVA);
    	byte[] m = SaltTestData.r0Message;
    	byte[] sm = new byte[SaltLib.crypto_sign_BYTES + m.length];
    	byte[] sk = SaltTestData.r0SigSec;
    	lib.crypto_sign(sm, m, sk);
    	Assert.assertArrayEquals(SaltTestData.r0SignedMessage, sm);
    }
    
    @Test
    public void testVerify() {
    	SaltLib lib = SaltLibFactory.getLib(SaltLibFactory.LibType.JAVA);
    	byte[] pk = SaltTestData.r0SigPub;
    	byte[] sm = SaltTestData.r0SignedMessage;
    	byte[] m = new byte[sm.length];
    	lib.crypto_sign_open(m, sm, pk);
    }
    
    @Test(expected=BadSignatureException.class)
    public void testVerifyWithBadSignature() {
    	SaltLib lib = SaltLibFactory.getLib(SaltLibFactory.LibType.JAVA);
    	byte[] pk = SaltTestData.r0SigPub;
    	byte[] sm = SaltTestData.r0SignedMessage.clone();
    	sm[sm.length - 1] = 0;
    	byte[] m = new byte[sm.length];
    	lib.crypto_sign_open(m, sm, pk);
    }
}
