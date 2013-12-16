package jkmau5.modjam.radiomod.network;

import jkmau5.modjam.radiomod.tile.TileEntityRadio;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * No description given
 *
 * @author jk-5
 */
public class PacketSelectRadio extends PacketBase {

    public String selectedName;
    public boolean isMediaPlayer;
    public TileEntityRadio tileEntity;

    public PacketSelectRadio(){
    }

    public PacketSelectRadio(String selected){
        this.selectedName = selected;
        this.isMediaPlayer = true;
    }

    public PacketSelectRadio(String selected, TileEntityRadio radio){
        this.selectedName = selected;
        this.tileEntity = radio;
        this.isMediaPlayer = false;
    }

    @Override
    public void writePacket(DataOutput output) throws IOException{
        output.writeBoolean(this.isMediaPlayer);
        output.writeUTF(this.selectedName);
        if(!this.isMediaPlayer){
            output.writeInt(this.tileEntity.worldObj.provider.dimensionId);
            output.writeInt(this.tileEntity.xCoord);
            output.writeInt(this.tileEntity.yCoord);
            output.writeInt(this.tileEntity.zCoord);
        }
    }

    @Override
    public void readPacket(DataInput input) throws IOException{
        this.isMediaPlayer = input.readBoolean();
        this.selectedName = input.readUTF();
        if(!this.isMediaPlayer){
            World world = DimensionManager.getWorld(input.readInt());
            TileEntity tile = world.getBlockTileEntity(input.readInt(), input.readInt(), input.readInt());
            if(tile == null || !(tile instanceof TileEntityRadio)) return;
            this.tileEntity = (TileEntityRadio) tile;
            this.tileEntity.setConnectedBroadcastStation(this.selectedName);
        }else{
            ItemStack stack = this.player.getCurrentEquippedItem();
            if(stack.getTagCompound() == null) stack.setTagCompound(new NBTTagCompound());
            NBTTagCompound tag = stack.getTagCompound();
            tag.setString("station", this.selectedName);
        }
    }
}
