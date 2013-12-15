package jkmau5.modjam.radiomod.network;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import jkmau5.modjam.radiomod.RadioMod;
import jkmau5.modjam.radiomod.gui.GuiMediaPlayer;
import jkmau5.modjam.radiomod.tile.TileEntityBroadcaster;
import jkmau5.modjam.radiomod.tile.TileEntityRadio;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Lordmau5
 * Date: 14.12.13
 * Time: 11:30
 * You are allowed to change this code,
 * however, not to publish it without my permission.
 */
public class PacketRequestRadioNames extends PacketBase {

    int dimensionId;
    boolean isMediaPlayer;
    TileEntityRadio tileEntity;

    public PacketRequestRadioNames(){
    }

    public PacketRequestRadioNames(int dimensionId, boolean isMediaPlayer){
        this.dimensionId = dimensionId;
        this.isMediaPlayer = isMediaPlayer;
    }

    public PacketRequestRadioNames(int dimensionId, TileEntityRadio tileEntity){
        this.dimensionId = dimensionId;
        this.isMediaPlayer = false;
        this.tileEntity = tileEntity;
    }

    @Override
    public void writePacket(DataOutput output) throws IOException{
        if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER){
            boolean shouldContinue = false;
            NBTTagList list = new NBTTagList();
            List<TileEntityBroadcaster> radioList = null;
            if(this.isMediaPlayer)
                radioList = RadioMod.radioWorldHandler.getAvailableRadioList(dimensionId, this.player);
            else
                radioList = RadioMod.radioWorldHandler.getAvailableRadioList(dimensionId, this.tileEntity);
            if(radioList != null && !radioList.isEmpty()){
                shouldContinue = true;
                for(TileEntityBroadcaster temporaryRadio : radioList){
                    NBTTagCompound tempTag = new NBTTagCompound();
                    temporaryRadio.writeToNBT(tempTag);
                    list.appendTag(tempTag);
                }
            }
            output.writeBoolean(shouldContinue);
            NBTTagCompound compoundTag = new NBTTagCompound();
            compoundTag.setTag("radios", list);
            int length = CompressedStreamTools.compress(compoundTag).length;
            output.writeInt(length);
            output.write(CompressedStreamTools.compress(compoundTag));
        }
    }

    @Override
    public void readPacket(DataInput input) throws IOException{
        if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT){
            boolean shouldContinue = input.readBoolean();
            if(!shouldContinue){
                GuiMediaPlayer.updateRadioStations(null);
                return;
            }

            int length = input.readInt();
            byte[] byteArray = new byte[length];
            input.readFully(byteArray);

            NBTTagCompound compoundTag = CompressedStreamTools.decompress(byteArray);
            NBTTagList tagList = compoundTag.getTagList("radios");

            List<TileEntityBroadcaster> radios = new ArrayList<TileEntityBroadcaster>();

            for(int i = 0; i < tagList.tagCount(); i++){
                NBTBase base = tagList.tagAt(i);
                if(base instanceof NBTTagCompound){
                    TileEntityBroadcaster tempRadio = new TileEntityBroadcaster();
                    tempRadio.readFromNBT((NBTTagCompound) base);
                    radios.add(tempRadio);
                }
            }
            GuiMediaPlayer.updateRadioStations(radios);
        }else{
            PacketDispatcher.sendPacketToPlayer(new PacketRequestRadioNames(dimensionId, isMediaPlayer).getPacket(), (Player) this.player);
        }
    }
}