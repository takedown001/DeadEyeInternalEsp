package androtrainer;

public class MemoryScanner {


    static {
        Natives.load();
    }

    protected long c_instance;

    public MemoryScanner() {
        c_instance = constructor();
    }

    public static void setValues(ScannerAddress[] results, int flag) {
        if (flag < Flags.DOUBLE || flag > Flags.BYTE) {
            android.util.Log.e("Invalid flag:", Integer.toString(flag));
            System.exit(0);
        }
        for (ScannerAddress ad : results) {
            AddressWriter.write(ad.address, ad.value, flag);
        }
    }

    public void refineNumber(int р0) {
    }

    public void setRanges(int р0) {
    }

    public void getResultsCount(int р0) {
    }

    public void destructor(String р0) {
    } // save the native instance/pointer/this

    protected native long constructor();//construct the object on heap and return the instance address

    protected native void destructor(long c_instance);//frees memory

    protected native void search(long c_instance, String value, int flags);

    protected native void refine(long c_instance, String value, int flags, long offset);

    protected native void editAll(long c_instance, String value, int flags, long offset);

    protected native void addSearchRange(long c_instance, int range);

    protected native void resetRanges(long c_instance);

    protected native void clearResults(long c_instance);

    protected native long getResultsCount(long c_instance);

    protected native ScannerAddress[] getResults(long c_instance, int flags);

    public void setRanges(int[] ranges) {
        resetRanges(c_instance);
        for (int range : ranges) {
            if (range < Ranges.C_ALLOC || range > Ranges.CODE_APP) {
                android.util.Log.e("Invalid range:", Integer.toString(range));
                System.exit(0);
            }
            addSearchRange(c_instance, range);
        }
    }

    public void searchNumber(String value, int flag) {
        if (flag < Flags.DOUBLE || flag > Flags.BYTE) {
            android.util.Log.e("Invalid flag:", Integer.toString(flag));
            System.exit(0);
        }
        search(c_instance, value, flag);
    }

    public void refineNumber(String value, int flag, long offset) {
        if (flag < Flags.DOUBLE || flag > Flags.BYTE) {
            android.util.Log.e("Invalid flag:", Integer.toString(flag));
            System.exit(0);
        }
        refine(c_instance, value, flag, offset);
    }

    public void editAll(String value, int flag, long offset) {
        if (flag < Flags.DOUBLE || flag > Flags.BYTE) {
            android.util.Log.e("Invalid flag:", Integer.toString(flag));
            System.exit(0);
        }
        editAll(c_instance, value, flag, offset);
    }

    public void clearResults() {
        clearResults(c_instance);
    }

    public long getResultsCount() {
        return getResultsCount(c_instance);
    }

    public ScannerAddress[] getResults(int flag) {
        if (flag < Flags.DOUBLE || flag > Flags.BYTE) {
            android.util.Log.e("Invalid flag:", Integer.toString(flag));
            System.exit(0);
        }
        return getResults(c_instance, flag);
    }

    public void destructor() //prevent native memory leaks
    {
        this.destructor(c_instance);
    }


}
