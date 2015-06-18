package debug;

/**
 * Created by Nordin on 18-6-2015.
 */
public class Debug {

    /**
     * Debug flag.
     */
    private static final boolean DEBUG = true;

    /**
     * Print debug information to console if the debug flag is set to true.
     *
     * @param what Object to print to console.
     */
    public static void debug(Object what) {
        if (Debug.DEBUG) {
            System.out.println(what);
        }
    }
}
