package jkmau5.modjam.radiomod;

import net.minecraftforge.common.Configuration;

import java.io.File;

/**
 * No description given
 *
 * @author jk-5
 */
public class Config {

    public static Configuration config;

    public static int NEXT_NETWORK_ID = 0;

    public static void load(File file){
        Configuration config = new Configuration(file);
        config.load();

        NEXT_NETWORK_ID = config.get("doNotChangeMe", "youWillBreakStuff", 0, "DO NOT CHANGE THIS VALUE").getInt(0);

        if(config.hasChanged()) config.save();
    }

    public static void updateNextID(int id){
        config.getCategory("doNotChangeMe").get("youWillBreakStuff").set(id);
        config.save();
    }
}
