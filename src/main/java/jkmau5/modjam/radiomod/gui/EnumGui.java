package jkmau5.modjam.radiomod.gui;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * No description given
 *
 * @author jk-5
 */
public enum EnumGui {
    RADIO_BLOCK(0);

    public final int guiID;
    private static final Map<Integer, EnumGui> BY_ID = Maps.newHashMap();

    private EnumGui(int id){
        this.guiID = id;
    }

    public static EnumGui fromID(int id){
        return BY_ID.get(id);
    }
}
