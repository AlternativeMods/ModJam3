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

    public static Map<String, int[]> musicTitles = new HashMap<String, int[]>();
    public static Map<String, String> realMusicTitles = new HashMap<String, String>();
    public static Map<String, String> normalMusicTitles = new HashMap<String, String>();

    public static void initiateTitles(){
        for(ItemStack itemStack : OreDictionary.getOres("record")){
            ItemRecord record = (ItemRecord) itemStack.getItem();
            musicTitles.put(record.recordName, new int[]{itemStack.itemID, itemStack.getItemDamage()});
            realMusicTitles.put(record.recordName, record.getRecordTitle());
            normalMusicTitles.put(record.getRecordTitle(), record.recordName);
        }
    }

    public static String getRealRecordTitle(String recordName){
        if(realMusicTitles != null && !realMusicTitles.isEmpty() && realMusicTitles.containsKey(recordName))
            return realMusicTitles.get(recordName);
        return "INVALID";
    }

    public static String getNormalRecordTitle(String recordTitle){
        if(normalMusicTitles != null && !normalMusicTitles.isEmpty() && normalMusicTitles.containsKey(recordTitle))
            return normalMusicTitles.get(recordTitle);
        return "INVALID";
    }

    public static ItemStack buildRecordStack(String title){
        if(!musicTitles.containsKey(title))
            return null;

        int[] tempIds = musicTitles.get(title);
        return new ItemStack(tempIds[0], 1, tempIds[1]);
    }
}
