package jkmau5.modjam.radiomod.tile;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityAntenna extends TileEntityRadioNetwork implements ISynchronizedTileEntity {

    public float yaw = 0f;

    @Override
    public void writeData(ByteBuf buffer){
        buffer.writeFloat(this.yaw);
    }

    @Override
    public void readData(ByteBuf buffer){
        this.yaw = buffer.readFloat();
    }

    @Override
    public void func_145839_a(NBTTagCompound tag){
        super.func_145839_a(tag);
        this.yaw = tag.getFloat("yaw");
    }

    @Override
    public void func_145841_b(NBTTagCompound tag){
        super.func_145841_b(tag);
        tag.setFloat("yaw", this.yaw);
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox(){
        return AxisAlignedBB.getAABBPool().getAABB(this.field_145851_c - 1, this.field_145848_d, this.field_145849_e - 1, this.field_145851_c + 2, this.field_145848_d + 2, this.field_145849_e + 2);
    }
}
