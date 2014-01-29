package jkmau5.modjam.radiomod.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import jkmau5.modjam.radiomod.gui.GuiMediaPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class PacketMediaPlayerData extends PacketBase {

    private NBTTagCompound tag;

    public PacketMediaPlayerData(){}
    public PacketMediaPlayerData(NBTTagCompound tag){
        this.tag = tag;
    }

    @Override
    public void encode(ByteBuf buffer){
        ByteBufUtils.writeTag(buffer, this.tag);
    }

    @Override
    public void decode(ByteBuf buffer){
        this.tag = ByteBufUtils.readTag(buffer);
        GuiMediaPlayer.guiData = this.tag;
        if(this.tag.hasKey("station")){
            GuiMediaPlayer.selectedName = this.tag.getString("station");
        }
    }
}
