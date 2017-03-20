package saltaa;

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
    
    public static SaltLib getLib(LibType type) {
        switch (type) {
        case BEST:
            return getLib(LibType.JAVA);   // for now, this returns the JAVA implementation
        case JAVA:
            return new JavaSaltLib();
        case NATIVE:
            throw new Error("NATIVE lib not implemented yet");
        default:
            return getLib(LibType.JAVA);
        }
    }
}
