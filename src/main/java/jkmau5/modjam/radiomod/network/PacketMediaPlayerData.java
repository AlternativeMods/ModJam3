package jkmau5.modjam.radiomod.network;

import jkmau5.modjam.radiomod.gui.GuiMediaPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * No description given
 *
 * @author jk-5
 */
public class PacketMediaPlayerData extends PacketBase {

    private NBTTagCompound tag;

    public PacketMediaPlayerData(){
    }

    public PacketMediaPlayerData(NBTTagCompound tag){
        this.tag = tag;
    }

    @Override
    public void writePacket(DataOutput output) throws IOException{
        CompressedStreamTools.write(this.tag, output);
    }

    @Override
    public void readPacket(DataInput input) throws IOException{
        this.tag = CompressedStreamTools.read(input);
        GuiMediaPlayer.guiData = this.tag;
        if(this.tag.hasKey("station")){
            GuiMediaPlayer.selectedName = this.tag.getString("station");
        }
    }
}
