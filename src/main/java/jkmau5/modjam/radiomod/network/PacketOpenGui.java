package jkmau5.modjam.radiomod.network;

import jkmau5.modjam.radiomod.gui.EnumGui;
import jkmau5.modjam.radiomod.gui.GuiOpener;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * No description given
 *
 * @author jk-5
 */
public class PacketOpenGui extends PacketBase {

    private EnumGui gui;

    public PacketOpenGui(){}
    public PacketOpenGui(EnumGui gui){
        this.gui = gui;
    }

    @Override
    public void writePacket(DataOutput output) throws IOException{
        output.writeInt(this.gui.guiID);
    }

    @Override
    public void readPacket(DataInput input) throws IOException{
        int id = input.readInt();
        EnumGui gui = EnumGui.fromID(id);
        if(gui == null) return;
        this.gui = gui;
        GuiOpener.openGuiCallback(gui);
    }
}
