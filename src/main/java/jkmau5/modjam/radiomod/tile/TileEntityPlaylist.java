package jkmau5.modjam.radiomod.tile;

import jkmau5.modjam.radiomod.Constants;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.world.World;

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
    private List<String> titles = new ArrayList<String>();

    public List<String> getTitles() {
        return titles;
    }

    public boolean addTitle(String title) {
        if(titles.contains(title))
            return false;
        titles.add(title);
        if(!worldObj.isRemote)
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        return true;
    }

    public boolean removeTitle(String title) {
        if(titles.contains(title))
            titles.remove(title);
        dropRecordItemInWorld(worldObj, title);
        if(!worldObj.isRemote)
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        return true;
    }

    public void breakBlock(World world) {
        for(String title : this.titles)
            dropRecordItemInWorld(world, title);
    }

    public void dropRecordItemInWorld(World world, String title) {
        if(world.isRemote)
            return;

        double d0 = world.rand.nextFloat() * 0.7F + 1.0F - 0.7F * 0.5D;
        double d1 = world.rand.nextFloat() * 0.7F + 1.0F - 0.7F * 0.5D;
        double d2 = world.rand.nextFloat() * 0.7F + 1.0F - 0.7F * 0.5D;
        EntityItem entityitem = new EntityItem(world, xCoord + d0, yCoord + d1, zCoord + d2, Constants.buildRecordStack(title));
        entityitem.delayBeforeCanPickup = 10;
        world.spawnEntityInWorld(entityitem);
    }

    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeToNBT(par1NBTTagCompound);

        NBTTagList tagList = new NBTTagList();
        for(String title : this.titles) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setString("title", title);
            tagList.appendTag(tag);
        }
        par1NBTTagCompound.setTag("titles", tagList);
    }

    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readFromNBT(par1NBTTagCompound);

        NBTTagList tagList = par1NBTTagCompound.getTagList("titles");
        for(int i=0; i<tagList.tagCount(); i++) {
            NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
            addTitle(tag.getString("title"));
        }
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tag = new NBTTagCompound();
        this.writeToNBT(tag);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 0, tag);
    }

    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
        readFromNBT(pkt.data);
    }
}