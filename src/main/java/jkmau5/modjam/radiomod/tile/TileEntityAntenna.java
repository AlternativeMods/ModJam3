package jkmau5.modjam.radiomod.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

/**
 * No description given
 *
 * @author jk-5
 */
public class TileEntityAntenna extends TileEntity {

    public float yaw = 0f;

    @Override
    public Packet getDescriptionPacket(){
        NBTTagCompound tag = new NBTTagCompound();
        this.writeToNBT(tag);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 0, tag);
    }

    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt){
        this.readFromNBT(pkt.data);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag){
        super.readFromNBT(tag);
        this.yaw = tag.getFloat("yaw");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag){
        super.writeToNBT(tag);
        tag.setFloat("yaw", this.yaw);
    }
}
