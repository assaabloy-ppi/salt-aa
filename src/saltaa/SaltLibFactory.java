package saltaa;

import java.util.Arrays;
import java.util.List;

/**
 * Creates SaltLib instances.
 * 
 * @author Frans Lundberg
 */
public class SaltLibFactory {

    public enum LibType { 
        JAVA, NATIVE, BEST
    }
    
    public static SaltLib getLib() {
        return getLib(LibType.BEST);
    }
    
    /**
     * Returns a list of all SaltLib implementations.
     */
    public static List<SaltLib> getAllLibs() {
        return Arrays.asList((SaltLib) new JavaSaltLib(), (SaltLib) new NativeSaltLib());
    }
    
    public static SaltLib getLib(LibType type) {
        switch (type) {
        case BEST:
            return getLib(LibType.JAVA);   // for now, this returns the JAVA implementation
        case JAVA:
            return new JavaSaltLib();
        case NATIVE:
            return new NativeSaltLib();
        default:
            return getLib(LibType.JAVA);
        }
    }
}
