package jkmau5.modjam.radiomod.network;

import io.netty.buffer.ByteBuf;
import jkmau5.modjam.radiomod.gui.EnumGui;
import jkmau5.modjam.radiomod.gui.GuiOpener;

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
    public void encode(ByteBuf buffer){
        buffer.writeInt(this.gui.guiID);
        buffer.writeBoolean(this.coordsSet);
        if(this.coordsSet){
            buffer.writeInt(this.x);
            buffer.writeInt(this.y);
            buffer.writeInt(this.z);
        }
    }

    @Override
    public void decode(ByteBuf buffer){
        int id = buffer.readInt();
        this.coordsSet = buffer.readBoolean();
        if(this.coordsSet){
            this.x = buffer.readInt();
            this.y = buffer.readInt();
            this.z = buffer.readInt();
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
