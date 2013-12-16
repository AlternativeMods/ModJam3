package jkmau5.modjam.radiomod.network;

import jkmau5.modjam.radiomod.gui.EnumGui;
import jkmau5.modjam.radiomod.gui.GuiOpener;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class PacketOpenGui extends PacketBase {

    private EnumGui gui;
    private int x, y, z;
    private boolean coordsSet = false;

    public PacketOpenGui(){
    }

    public PacketOpenGui(EnumGui gui){
        this.gui = gui;
    }

    public PacketOpenGui(EnumGui gui, int x, int y, int z){
        this.gui = gui;
        this.x = x;
        this.y = y;
        this.z = z;
        this.coordsSet = true;
    }

    @Override
    public void writePacket(DataOutput output) throws IOException{
        output.writeInt(this.gui.guiID);
        output.writeBoolean(this.coordsSet);
        if(this.coordsSet){
            output.writeInt(this.x);
            output.writeInt(this.y);
            output.writeInt(this.z);
        }
    }

    @Override
    public void readPacket(DataInput input) throws IOException{
        int id = input.readInt();
        this.coordsSet = input.readBoolean();
        if(this.coordsSet){
            this.x = input.readInt();
            this.y = input.readInt();
            this.z = input.readInt();
            EnumGui gui = EnumGui.fromID(id);
            if(gui == null) return;
            this.gui = gui;
            GuiOpener.openGuiCallback(gui, x, y, z);
        }else{
            EnumGui gui = EnumGui.fromID(id);
            if(gui == null) return;
            this.gui = gui;
            GuiOpener.openGuiCallback(gui);
        }
    }
}
