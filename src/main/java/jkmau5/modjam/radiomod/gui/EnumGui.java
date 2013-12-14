package jkmau5.modjam.radiomod.gui;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * No description given
 *
 * @author jk-5
 */
public enum EnumGui {
    RADIO_BLOCK(0),
    MEDIA_PLAYER(1);

    public final int guiID;
    private static final Map<Integer, EnumGui> BY_ID = Maps.newHashMap();

    private EnumGui(int id){
        this.guiID = id;
    }

    public static EnumGui fromID(int id){
        return BY_ID.get(id);
    }

    static {
        for(EnumGui gui : values()){
            BY_ID.put(gui.guiID, gui);
        }
    }
}
