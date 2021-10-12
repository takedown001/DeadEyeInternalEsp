package androtrainer;

public class AddressWriter {
    static {
        Natives.load();
    }

    protected native static boolean writeC(long address, String value, int flag);

    public static boolean write(long address, String value, int flag) {
        if (flag < Flags.DOUBLE || flag > Flags.BYTE) {
            android.util.Log.e("Invalid flag:", Integer.toString(flag));
            System.exit(0);
        }
        return writeC(address, value, flag);
    }
}
