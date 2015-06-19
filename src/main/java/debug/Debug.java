package debug;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Debug class.
 */
public class Debug {

    /**
     * Debug flag.
     */
    private static final boolean DEBUG = true;

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(Debug.class.getName());

    /**
     * Print debug information to console if the debug flag is set to true.
     *
     * @param logContent String to be logged.
     */
    public static void debug(String logContent) {
        if (Debug.DEBUG) {
            LOGGER.log(Level.FINE, logContent);
        }
    }

}
