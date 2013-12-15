package jkmau5.modjam.radiomod.tile;

import cpw.mods.fml.common.FMLCommonHandler;
import jkmau5.modjam.radiomod.RadioMod;
import jkmau5.modjam.radiomod.radio.IBroadcaster;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;

/**
 * Author: Lordmau5
 * Date: 13.12.13
 * Time: 16:05
 * You are allowed to change this code,
 * however, not to publish it without my permission.
 */
public class TileEntityBroadcaster extends TileEntityRadioNetwork implements IBroadcaster {

    protected String radioName;
    private boolean radioInitiated;

    public TileEntityBroadcaster(){
        this.radioName = RadioMod.getUniqueRadioID();
        this.radioInitiated = false;
    }

    @Override
    public boolean canUpdate(){
        return FMLCommonHandler.instance().getEffectiveSide().isServer();
    }

    public void updateEntity(){
        super.updateEntity();
        if(!this.radioInitiated){
            this.radioInitiated = true;
            RadioMod.radioWorldHandler.addRadioTile(this);
        }
    }

    @Override
    public void invalidate(){
        super.invalidate();
        if(this.radioInitiated){
            this.radioInitiated = false;
            if(worldObj != null && !worldObj.isRemote && RadioMod.radioWorldHandler != null){
                RadioMod.radioWorldHandler.removeRadioTile(this);
            }
        }
    }

    @Override
    public void onChunkUnload(){
        super.onChunkUnload();
        if(this.radioInitiated){
            this.radioInitiated = false;
            if(!worldObj.isRemote){
                RadioMod.radioWorldHandler.removeRadioTile(this);
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tag){
        super.readFromNBT(tag);
        this.radioName = tag.getString("radioName");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag){
        super.writeToNBT(tag);
        tag.setString("radioName", this.radioName);
    }

    @Override
    public Packet getDescriptionPacket(){
        NBTTagCompound tag = new NBTTagCompound();
        this.writeToNBT(tag);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 0, tag);
    }

    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt){
        readFromNBT(pkt.data);
    }

    public String getRadioName(){
        return radioName;
    }

    public void setRadioName(String radioName){
        this.radioName = radioName;
    }
}
