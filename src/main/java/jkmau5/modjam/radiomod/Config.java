package jkmau5.modjam.radiomod;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

import java.io.File;

/**
 * No description given
 *
 * @author jk-5
 */
public class Config {

    public static Configuration config;
    private static Property idProp;

    public static int NEXT_NETWORK_ID = 0;

    public static void load(File file){
        config = new Configuration(file);
        config.load();

        idProp = config.get("doNotChangeMe", "youWillBreakStuff", 0, "DO NOT CHANGE THIS VALUE");
        NEXT_NETWORK_ID = idProp.getInt(0);

        if(config.hasChanged()) config.save();
    }

    public static void updateNextID(int id){
        idProp.set(id);
        config.save();
    }
}
