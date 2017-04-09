package saltaa;

import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import nperf.SaltTestData;

/**
 * Tests crypto_hash functions.
 */
@RunWith(Parameterized.class)
public class HashTest {
    private SaltLib lib;    

    @Parameterized.Parameters
    public static List<SaltLib> data() {
        return SaltLibFactory.getAllOperationalLibs();
    }
    
    public HashTest(SaltLib lib) {
        this.lib = lib;
    }
    
    @Test
    public void testEmptyMsg() {
        byte[] msg = new byte[] {};
        byte[] out = new byte[SaltLib.crypto_hash_BYTES];
        byte[] pkExpected = new byte[] {
                    (byte)0xcf, (byte)0x83, (byte)0xe1, 0x35, 0x7e, (byte)0xef, (byte)0xb8, 
                    (byte)0xbd, (byte)0xf1, 0x54, 0x28, 0x50, (byte)0xd6, 0x6d, (byte)0x80, 0x07,
                    (byte)0xd6, 0x20, (byte)0xe4, 0x05, 0x0b, 0x57, 0x15, (byte)0xdc, (byte)0x83, (byte)0xf4, 
                    (byte)0xa9, 0x21, (byte)0xd3, 0x6c, (byte)0xe9, (byte)0xce,
                    0x47, (byte)0xd0, (byte)0xd1, 0x3c, 0x5d, (byte)0x85, (byte)0xf2, (byte)0xb0, (byte)0xff,
                    (byte)0x83, 0x18, (byte)0xd2, (byte)0x87, 0x7e, (byte)0xec, 0x2f,
                    0x63, (byte)0xb9, 0x31, (byte)0xbd, 0x47, 0x41, 0x7a, (byte)0x81, (byte)0xa5, 
                    0x38, 0x32, 0x7a, (byte)0xf9, 0x27, (byte)0xda, 0x3e };
        
        lib.crypto_hash(out, msg);
        
        Assert.assertArrayEquals(pkExpected, out);
    }    

    @Test
    public void test_abc_Msg() {
        byte[] msg = new byte[] {0x61, 0x62, 0x63};  // "abc"
        byte[] out = new byte[SaltLib.crypto_hash_BYTES];
        byte[] pkExpected = new byte[] {
                    (byte)0xdd, (byte)0xaf, 0x35, (byte)0xa1, (byte)0x93, 0x61, 0x7a, (byte)0xba, (byte)0xcc,
                    0x41, 0x73, 0x49, (byte)0xae, 0x20, 0x41, 0x31,
                    0x12, (byte)0xe6, (byte)0xfa, 0x4e, (byte)0x89, (byte)0xa9, 0x7e, (byte)0xa2, 0x0a,
                    (byte)0x9e, (byte)0xee, (byte)0xe6, 0x4b, 0x55, (byte)0xd3, (byte)0x9a,
                    0x21, (byte)0x92, (byte)0x99, 0x2a, 0x27, 0x4f, (byte)0xc1, (byte)0xa8, 0x36,
                    (byte)0xba, 0x3c, 0x23, (byte)0xa3, (byte)0xfe, (byte)0xeb, (byte)0xbd,
                    0x45, 0x4d, 0x44, 0x23, 0x64, 0x3c, (byte)0xe8, (byte)0x0e, 0x2a, 
                    (byte)0x9a, (byte)0xc9, 0x4f, (byte)0xa5, 0x4c, (byte)0xa4, (byte)0x9f };
        
        lib.crypto_hash(out, msg);
        
        Assert.assertArrayEquals(pkExpected, out);
    }    

}