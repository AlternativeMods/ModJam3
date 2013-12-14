package jkmau5.modjam.radiomod.item;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * No description given
 *
 * @author jk-5
 */
public enum EnumIngredient {
    ANTENNAROD(0);

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

    public static EnumIngredient fromID(int id){
        return BY_ID.get(id);
    }
}
