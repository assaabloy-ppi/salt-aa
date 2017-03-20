package saltaa;

public class SaltTestData {    
    public static byte[] aSigSec = Hex.toBytes("55f4d1d198093c84de9ee9a6299e0f6891c2e1d0b369efb592a9e3f169fb0f795529ce8ccf68c0b8ac19d437ab0f5b32723782608e93c6264f184ba152c2357b");
    public static byte[] aSigPub = Hex.toBytes("5529ce8ccf68c0b8ac19d437ab0f5b32723782608e93c6264f184ba152c2357b");

    /**
     * aEnc, bEnc are taken from Bernstein's paper at
     * http://cr.yp.to/highspeed/naclcrypto-20090310.pdf.
     * Paper title: Cryptography in NaCl. Here called "Bernstein's paper".
     * They are encryption key pairs (not for signing).
     */
    public static byte[] aEncSec = new byte[] {
            0x77,0x07,0x6d,0x0a,0x73,0x18,(byte)0xa5,0x7d
            ,0x3c,0x16,(byte)0xc1,0x72,0x51,(byte)0xb2,0x66,0x45
            ,(byte)0xdf,0x4c,0x2f,(byte)0x87,(byte)0xeb,(byte)0xc0,(byte)0x99,0x2a
            ,(byte)0xb1,0x77,(byte)0xfb,(byte)0xa5,0x1d,(byte)0xb9,0x2c,0x2a
    };
    
    public static byte[] aEncPub = new byte[] {
            (byte)0x85,0x20,(byte)0xf0,0x09,(byte)0x89,0x30,(byte)0xa7,0x54
            ,0x74,(byte)0x8b,0x7d,(byte)0xdc,(byte)0xb4,0x3e,(byte)0xf7,0x5a
            ,0x0d,(byte)0xbf,0x3a,0x0d,0x26,0x38,0x1a,(byte)0xf4
            ,(byte)0xeb,(byte)0xa4,(byte)0xa9,(byte)0x8e,(byte)0xaa,(byte)0x9b,0x4e,0x6a
    };
}
