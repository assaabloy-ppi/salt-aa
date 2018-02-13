package saltaa.nperf;

import java.io.PrintStream;
import saltaa.SaltLib;
import saltaa.SaltLibFactory;
import saltaa.SaltTestData;
import saltaa.SaltLibFactory.LibType;

/**
 * Quick hack to test encrypt/decrypt performance. 
 * What bandwidth can we achieve? Enough for video, for example?
 * 
 * @author Frans Lundberg
 */
public class EncryptPerformance {
    private PrintStream out = System.out;
    private SaltLib lib;
    
    public static void main(String[] args) {
        new EncryptPerformance().go(args);
    }
    
    private void go(String[] args) {
        lib = SaltLibFactory.getLib(LibType.NATIVE);
        out.println("======== " + EncryptPerformance.class.getName() + " ========");
        out.println("SaltLib: " + lib.getName() + "\n");
        
        int messageSize = 1000*1000;
        byte[] m = new byte[messageSize];      // cleartext message
        byte[] c = new byte[m.length + 100];   // encrypted message
        byte[] k1 = new byte[SaltLib.crypto_box_BEFORENMBYTES];
        byte[] k2 = new byte[SaltLib.crypto_box_BEFORENMBYTES];
        byte[] nonce = new byte[SaltLib.crypto_box_NONCEBYTES];
        
        // Alice (a) computes:
        lib.crypto_box_beforenm(k1, SaltTestData.bEncPub, SaltTestData.aEncSec);
        
        // Bob (b) computes:
        lib.crypto_box_beforenm(k2, SaltTestData.aEncPub, SaltTestData.bEncSec);
        
        int n = 1000;
        long t0 = System.nanoTime();
        
        
        for (int i = 0; i < n; i++) {
            lib.crypto_box_afternm(c, m, nonce, k1);
            nonce[0] = (byte) (nonce[0] + 1);
        }
        
        long t1 = System.nanoTime();
        double time = 1e-9 * (t1 - t0);
        double mb = n * messageSize / 1000000.0;
        double mbPerSec1 = mb / time;
        
        System.out.println("Time: " + time);
        System.out.println("MB (1e6 bytes): " + mb);
        System.out.println("MB per sec: " + mbPerSec1);
    }
}

/*

Typical output, Frans's work laptop, 2018-01-02.
(Intel Core i5-4310U CPU @ 2.00GHz × 4). 64-bit Ubuntu.

    ======== saltaa.nperf.EncryptPerformance ========
    SaltLib: libsodium-jni
    
    Time: 2.078559717
    MB (1e6 bytes): 1000.0
    MB per sec: 481.1

That is roughly: 4000 Mbit/s. Typical video stream range from 0.5 to 5 mbit.
Skype video is 0.5 Mbit/s. HD Skype calls: 1.5 Mbit.

*/
