package saltaa;

public class NativeSaltLib implements SaltLib {

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void crypto_sign_keypair_not_random(byte[] pk, byte[] sk) {
        // TODO Auto-generated method stub

    }

    @Override
    public void crypto_sign(byte[] sm, byte[] m, byte[] sk) {
        // TODO Auto-generated method stub

    }

    @Override
    public void crypto_sign_open(byte[] m, byte[] sm, byte[] pk) {
        // TODO Auto-generated method stub

    }
}
