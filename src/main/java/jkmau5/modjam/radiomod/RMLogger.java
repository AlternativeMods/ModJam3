package jkmau5.modjam.radiomod;

import cpw.mods.fml.common.FMLLog;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RMLogger {

    private static final Logger logger = Logger.getLogger("RadioMod");

    static{
        FMLLog.makeLog("RadioMod");
    }

    public static void log(Level level, String format, Object... data){
        logger.log(level, String.format(format, data));
    }

    public static void log(Level level, Throwable ex, String format, Object... data){
        logger.log(level, String.format(format, data), ex);
    }

    public static void severe(Throwable ex, String format, Object... data){
        log(Level.SEVERE, ex, format, data);
    }

    public static void severe(String format, Object... data){
        log(Level.SEVERE, format, data);
    }
}
