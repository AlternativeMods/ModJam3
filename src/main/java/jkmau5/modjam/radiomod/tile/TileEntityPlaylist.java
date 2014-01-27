package jkmau5.modjam.radiomod.tile;

import com.google.common.collect.Lists;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import jkmau5.modjam.radiomod.Constants;
import jkmau5.modjam.radiomod.gui.GuiPlaylist;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Lordmau5
 * Date: 15.12.13
 * Time: 16:36
 * You are allowed to change this code,
 * however, not to publish it without my permission.
 */
public class TileEntityPlaylist extends TileEntityRadioNetwork {

    private List<String> titles = Lists.newArrayList();

    public List<String> getTitles(){
        return titles;
    }

    public boolean addTitle(String title){
        if(titles.contains(title)) return false;
        this.titles.add(title);
        if(worldObj != null && !worldObj.isRemote){
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
        return true;
    }

    public boolean removeTitle(String title){
        if(titles.contains(title)){
            titles.remove(title);
            dropRecordItemInWorld(worldObj, title);
            if(worldObj != null && !worldObj.isRemote){
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            }
            return true;
        }
        return false;
    }

    public void breakBlock(){
        for(String title : this.titles){
            dropRecordItemInWorld(this.worldObj, title);
        }
    }

    public void dropRecordItemInWorld(World world, String title){
        if(world.isRemote) return;

        double d0 = world.rand.nextFloat() * 0.7F + 1.0F - 0.7F * 0.5D;
        double d1 = world.rand.nextFloat() * 0.7F + 1.0F - 0.7F * 0.5D;
        double d2 = world.rand.nextFloat() * 0.7F + 1.0F - 0.7F * 0.5D;
        EntityItem entityitem = new EntityItem(world, xCoord + d0, yCoord + d1, zCoord + d2, Constants.buildRecordStack(title));
        entityitem.delayBeforeCanPickup = 10;
        world.spawnEntityInWorld(entityitem);
    }

    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound){
        super.writeToNBT(par1NBTTagCompound);

        NBTTagList tagList = new NBTTagList();
        for(String title : this.titles){
            NBTTagCompound tag = new NBTTagCompound();
            tag.setString("title", title);
            tagList.appendTag(tag);
        }
        par1NBTTagCompound.setTag("titles", tagList);
    }

    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound){
        super.readFromNBT(par1NBTTagCompound);

        titles = new ArrayList<String>();
        NBTTagList tagList = par1NBTTagCompound.getTagList("titles");
        for(int i = 0; i < tagList.tagCount(); i++){
            NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
            addTitle(tag.getString("title"));
        }

        if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT){
            if(GuiPlaylist.playlist == this){
                GuiPlaylist.updateTitles();
            }
        }
    }

    public void selectNetworkFromBroadcaster(World world) {
        for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS){
            TileEntity tile = world.getBlockTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
            if (tile instanceof TileEntityBroadcaster) this.network = ((TileEntityBroadcaster) tile).network;
        }
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
}
