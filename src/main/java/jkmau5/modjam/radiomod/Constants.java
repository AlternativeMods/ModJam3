package jkmau5.modjam.radiomod;

import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

/**
 * The strings in this file will be populated by gradle.
 *
 * @author jk-5
 */
public class Constants {

    public static final String MODID = "RadioMod";
    public static final String VERSION = "@VERSION@";

    public static List<String> musicTitles = new ArrayList<String>();
    public static void initiateTitles() {
        for(ItemStack itemStack : OreDictionary.getOres("record")) {
            ItemRecord record = (ItemRecord) itemStack.getItem();
            musicTitles.add(record.getRecordTitle());
            System.out.println(record.getRecordTitle());
        }
    }
}
