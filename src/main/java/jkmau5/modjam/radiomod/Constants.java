package jkmau5.modjam.radiomod;

import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * The strings in this file will be populated by gradle.
 *
 * @author jk-5
 */
public class Constants {

    public static final String MODID = "RadioMod";
    public static final String VERSION = "@VERSION@";

    public static Map<String, ItemStack> musicTitles = new HashMap<String, ItemStack>();
    public static void initiateTitles() {
        for(ItemStack itemStack : OreDictionary.getOres("record")) {
            ItemRecord record = (ItemRecord) itemStack.getItem();
            musicTitles.put(record.recordName, itemStack);
        }
    }

    public static ItemStack buildRecordStack(String title) {
        if(!musicTitles.containsKey(title))
            return null;

        return musicTitles.get(title);
    }
}
