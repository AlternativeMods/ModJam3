package jkmau5.modjam.radiomod.network;

import com.google.common.collect.Lists;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import jkmau5.modjam.radiomod.RadioMod;
import jkmau5.modjam.radiomod.gui.GuiMediaPlayer;
import jkmau5.modjam.radiomod.tile.TileEntityRadio;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
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
        if(FMLCommonHandler.instance().getEffectiveSide().isServer()){
            List<String> radioList;
            if(this.isMediaPlayer){
                radioList = RadioMod.radioNetworkHandler.getAvailableRadioNames(this.player.worldObj, (int) this.player.posX, (int) this.player.posY, (int) this.player.posZ);
            }else{
                radioList = RadioMod.radioNetworkHandler.getAvailableRadioNames(this.tileEntity.worldObj, this.tileEntity.xCoord, this.tileEntity.yCoord, this.tileEntity.zCoord);
            }
            if(!radioList.isEmpty()){
                output.writeInt(radioList.size());
                for(String radio : radioList){
                    output.writeUTF(radio);
                }
            }else{
                output.writeInt(0);
            }
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
            int listSize = input.readInt();
            List<String> radios = Lists.newArrayList();
            for(int i = 0; i < listSize; i++){
                radios.add(input.readUTF());
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
