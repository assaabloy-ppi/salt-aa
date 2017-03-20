package saltaa;

/**
 * Thrown to indicate that a signature was not valid.
 * 
 * @author Frans Lundberg
 */
public class BadSignature extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public BadSignature() {}
}
