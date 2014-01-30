package jkmau5.modjam.radiomod;

import com.google.common.collect.Maps;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Map;

/**
 * The strings in this file will be populated by gradle.
 *
 * @author jk-5
 */
public class Constants {

    public static final String MODID = "RadioMod";

    public static Map<String, ItemStack> musicTitles = Maps.newHashMap();
    public static Map<String, String> realMusicTitles = Maps.newHashMap();
    public static Map<String, String> normalMusicTitles = Maps.newHashMap();

    public static void initiateTitles(){
        for(ItemStack itemStack : OreDictionary.getOres("record")){
            if(itemStack.getItem() instanceof  ItemRecord){
                ItemRecord record = (ItemRecord) itemStack.getItem();
                musicTitles.put(record.field_150929_a, itemStack);
                realMusicTitles.put(record.field_150929_a, record.func_150927_i());
                normalMusicTitles.put(record.getItemStackDisplayName(itemStack), record.field_150929_a);
            }
        }
    }

    public static String getRealRecordTitle(String recordName){
        if(realMusicTitles.containsKey(recordName)){
            return realMusicTitles.get(recordName);
        }
        return "INVALID";
    }

    public static String getNormalRecordTitle(String recordTitle){
        if(normalMusicTitles.containsKey(recordTitle)){
            return normalMusicTitles.get(recordTitle);
        }
        return "INVALID";
    }

    public static ItemStack buildRecordStack(String title){
        return musicTitles.get(title).copy();
    }
}
