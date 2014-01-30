package jkmau5.modjam.radiomod.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityAntenna extends TileEntityRadioNetwork {

    public float yaw = 0f;

    /*@Override
    public Packet getDescriptionPacket(){
        NBTTagCompound tag = new NBTTagCompound();
        this.writeToNBT(tag);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 0, tag);
    }

    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt){
        this.readFromNBT(pkt.data);
    }*/

    //TODO: Write some TileEntity packet stuff

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

    @Override
    public AxisAlignedBB getRenderBoundingBox(){
        return AxisAlignedBB.getAABBPool().getAABB(this.field_145851_c - 1, this.field_145848_d, this.field_145849_e - 1, this.field_145851_c + 2, this.field_145848_d + 2, this.field_145849_e + 2);
    }
}
