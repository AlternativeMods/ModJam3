package jkmau5.modjam.radiomod.tile;

import jkmau5.modjam.radiomod.RadioMod;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityBroadcaster extends TileEntityRadioNetwork {

    protected String radioName;

    public TileEntityBroadcaster(){
        this.radioName = RadioMod.getUniqueRadioID();
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

    /*@Override
    public Packet getDescriptionPacket(){
        NBTTagCompound tag = new NBTTagCompound();
        this.writeToNBT(tag);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 0, tag);
    }

    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt){
        readFromNBT(pkt.data);
    }*/

    public String getRadioName(){
        return radioName;
    }

    public void setRadioName(String radioName){
        this.radioName = radioName;
    }
}
