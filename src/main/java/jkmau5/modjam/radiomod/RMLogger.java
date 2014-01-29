package jkmau5.modjam.radiomod;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RMLogger {

    private static final Logger logger = LogManager.getLogger("RadioMod");

    public static void log(Level level, String format, Object... data){
        logger.log(level, String.format(format, data));
    }

    public static void log(Level level, Throwable ex, String format, Object... data){
        logger.log(level, String.format(format, data), ex);
    }

    public static void error(Throwable ex, String format, Object... data){
        log(Level.ERROR, ex, format, data);
    }

    public static void error(String format, Object... data){
        log(Level.ERROR, format, data);
    }
}
