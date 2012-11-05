package com.marabout.lang;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A collection of runtime utilities.
 * 
 * @author <a href="mailto:gmarabout@gmail.com">Grégoire Marabout</a>
 */
public final class RuntimeUtils {

    private static Logger logger = Logger.getLogger(RuntimeUtils.class.getName());

    /**
     * Call <code>Runtime.getRuntime().exit(0)</code> in a timer thread.
     * The registered shutdown hooks are guaranteed to be called.
     *
     * @param milliseconds delay before shutdown.
     */
    public static void shutdown(final long milliseconds) {
        logger.log(Level.WARNING, "Shutdown in " + milliseconds + " ms!");
        new Timer(true).schedule(new TimerTask() {
            public void run() {
                Runtime.getRuntime().exit(0);
            }
        }, milliseconds);
    }
}
