package jkmau5.modjam.radiomod.tile;

import com.google.common.collect.Lists;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import jkmau5.modjam.radiomod.Constants;
import jkmau5.modjam.radiomod.gui.GuiPlaylist;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

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
        if(this.func_145831_w() != null && !this.func_145831_w().isRemote){
            this.func_145831_w().func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
        }
        return true;
    }

    public boolean removeTitle(String title){
        if(titles.contains(title)){
            titles.remove(title);
            dropRecordItemInWorld(this.func_145831_w(), title);
            if(this.func_145831_w() != null && !this.func_145831_w().isRemote){
                this.func_145831_w().func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
            }
            return true;
        }
        return false;
    }

    public void breakBlock(){
        for(String title : this.titles){
            dropRecordItemInWorld(this.func_145831_w(), title);
        }
    }

    public void dropRecordItemInWorld(World world, String title){
        if(world.isRemote) return;

        double d0 = world.rand.nextFloat() * 0.7F + 1.0F - 0.7F * 0.5D;
        double d1 = world.rand.nextFloat() * 0.7F + 1.0F - 0.7F * 0.5D;
        double d2 = world.rand.nextFloat() * 0.7F + 1.0F - 0.7F * 0.5D;
        EntityItem entityitem = new EntityItem(world, this.field_145851_c + d0, this.field_145848_d + d1, this.field_145849_e + d2, Constants.buildRecordStack(title));
        entityitem.field_145804_b = 10;
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
        NBTTagList tagList = par1NBTTagCompound.func_150295_c("titles", 10 /*NBTTagCompound*/);
        for(int i = 0; i < tagList.tagCount(); i++){
            NBTTagCompound tag = tagList.func_150305_b(i);
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
            TileEntity tile = world.func_147438_o(this.field_145851_c + dir.offsetX, this.field_145848_d + dir.offsetY, this.field_145849_e + dir.offsetZ);
            if (tile instanceof TileEntityBroadcaster) this.network = ((TileEntityBroadcaster) tile).network;
        }
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
}
