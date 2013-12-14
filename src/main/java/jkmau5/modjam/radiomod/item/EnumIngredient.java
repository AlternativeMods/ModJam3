package jkmau5.modjam.radiomod.item;

import com.google.common.collect.Maps;
import jkmau5.modjam.radiomod.RadioMod;
import net.minecraft.item.ItemStack;

import java.util.Map;

/**
 * No description given
 *
 * @author jk-5
 */
public enum EnumIngredient {
    RAWANTENNAROD(0),
    ANTENNAROD(1),
    ANTENNAFOOT(2),
    BIGANTENNAFOOT(3),
    ANTENNAONFOOT(4);

    public final int subid;
    public final String unlocalizedName;

    private static final Map<Integer, EnumIngredient> BY_ID = Maps.newHashMap();

    static{
        for(EnumIngredient ingredient : EnumIngredient.values()){
            BY_ID.put(ingredient.subid, ingredient);
        }
    }

    private EnumIngredient(int subid){
        this.subid = subid;
        this.unlocalizedName = this.name().toLowerCase();
    }

    public ItemStack getStack(int size){
        return new ItemStack(RadioMod.instance.itemIngredient, size, this.subid);
    }

    public static EnumIngredient fromID(int id){
        return BY_ID.get(id);
    }
}
