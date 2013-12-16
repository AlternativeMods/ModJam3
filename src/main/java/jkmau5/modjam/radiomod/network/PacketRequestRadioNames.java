package jkmau5.modjam.radiomod.network;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import jkmau5.modjam.radiomod.RadioMod;
import jkmau5.modjam.radiomod.gui.GuiMediaPlayer;
import jkmau5.modjam.radiomod.tile.TileEntityBroadcaster;
import jkmau5.modjam.radiomod.tile.TileEntityRadio;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

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

    public PacketRequestRadioNames(int dimensionId, EntityPlayer player){
        this.dimensionId = dimensionId;
        this.isMediaPlayer = true;
        this.player = player;
    }
    public PacketRequestRadioNames(int dimensionId, TileEntityRadio tileEntity){
        this.dimensionId = dimensionId;
        this.isMediaPlayer = false;
        this.tileEntity = tileEntity;
    }

    @Override
    public void writePacket(DataOutput output) throws IOException{
        if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER){
            boolean listEmpty = true;
            NBTTagList list = new NBTTagList();
            List<TileEntityBroadcaster> radioList;
            if(this.isMediaPlayer){
                radioList = RadioMod.radioWorldHandler.getAvailableRadioList(dimensionId, this.player);
            }else{
                radioList = RadioMod.radioWorldHandler.getAvailableRadioList(dimensionId, this.tileEntity);
            }
            if(radioList != null && !radioList.isEmpty()){
                listEmpty = false;
                for(TileEntityBroadcaster temporaryRadio : radioList){
                    NBTTagCompound tempTag = new NBTTagCompound();
                    temporaryRadio.writeToNBT(tempTag);
                    list.appendTag(tempTag);
                }
            }
            output.writeBoolean(listEmpty);
            NBTTagCompound compoundTag = new NBTTagCompound();
            compoundTag.setTag("radios", list);
            CompressedStreamTools.write(compoundTag, output);
        }else{
            output.writeBoolean(this.isMediaPlayer);
            if(!this.isMediaPlayer){
                output.writeInt(this.tileEntity.worldObj.provider.dimensionId);
                output.writeInt(this.tileEntity.xCoord);
                output.writeInt(this.tileEntity.yCoord);
                output.writeInt(this.tileEntity.zCoord);
            }else{
                output.writeInt(this.player.worldObj.provider.dimensionId);
            }
        }
    }

    @Override
    public void readPacket(DataInput input) throws IOException{
        if(FMLCommonHandler.instance().getEffectiveSide().isClient()){
            boolean listEmpty = input.readBoolean();
            if(listEmpty){
                GuiMediaPlayer.updateRadioStations(null);
                return;
            }

            NBTTagCompound compoundTag = CompressedStreamTools.read(input);
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
            this.isMediaPlayer = input.readBoolean();
            this.dimensionId = input.readInt();
            if(isMediaPlayer){
                PacketDispatcher.sendPacketToPlayer(new PacketRequestRadioNames(dimensionId, this.player).getPacket(), (Player) this.player);
            }else{
                World world = DimensionManager.getWorld(this.dimensionId);
                if(world == null) return;
                int x = input.readInt();
                int y = input.readInt();
                int z = input.readInt();
                TileEntityRadio radio = (TileEntityRadio) world.getBlockTileEntity(x, y, z);
                if(radio == null) return;
                PacketDispatcher.sendPacketToPlayer(new PacketRequestRadioNames(dimensionId, radio).getPacket(), (Player) this.player);
            }
        }
    }
}
