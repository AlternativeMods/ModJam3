package jkmau5.modjam.radiomod.tile;

import com.google.common.collect.Lists;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import jkmau5.modjam.radiomod.Constants;
import jkmau5.modjam.radiomod.gui.GuiPlaylist;
import jkmau5.modjam.radiomod.radio.IRadioCable;
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
public class TileEntityPlaylist extends TileEntityRadioNetwork implements IRadioCable {

    private int Length = 0;
    private String CableID = "";

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
        par1NBTTagCompound.setInteger("length", this.Length);
        par1NBTTagCompound.setString("ID", this.CableID);
    }

    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound){
        super.readFromNBT(par1NBTTagCompound);
        this.CableID = par1NBTTagCompound.getString("ID");
        this.Length = par1NBTTagCompound.getInteger("length");

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

    public void removeCable(int x, int y, int z){
        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS){
            if ((IRadioCable) worldObj.getBlockTileEntity(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ) instanceof TileEntityRadioNetwork){
                worldObj.markBlockForUpdate(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
            }
        }
    }

    public void updateCable(int x, int y, int z){
        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS){
            if (isValidTileAtPosition(this, this.getWorldObj(), x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ)){
                TileEntity tile = worldObj.getBlockTileEntity(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
                if ((tile instanceof TileEntityCable) &&
                        (((TileEntityCable) tile).getCableID() != "") &&
                        (((TileEntityCable) tile).getNetwork() == this.getNetwork())){
                    TileEntityCable cable = (TileEntityCable) tile;
                    if ((cable.getStepsToBroadcaster() != 0) && (cable.getStepsToBroadcaster() < this.Length))
                        this.Length = cable.getStepsToBroadcaster() + 1;
                }
                if ((tile instanceof TileEntityPlaylist) &&
                        (((TileEntityPlaylist) tile).getCableID() != "") &&
                        (((TileEntityPlaylist) tile).getNetwork() == this.getNetwork())){
                    TileEntityPlaylist playlist = (TileEntityPlaylist) tile;
                    if ((playlist.getStepsToBroadcaster() != 0) && (playlist.getStepsToBroadcaster() < this.Length))
                        this.Length = playlist.getStepsToBroadcaster() + 1;
                }
                if ((tile instanceof TileEntityAntenna) &&
                        (((TileEntityAntenna) tile).getCableID() != "") &&
                        (((TileEntityAntenna) tile).getNetwork() == this.getNetwork())){
                    TileEntityAntenna antenna = (TileEntityAntenna) tile;
                    if ((antenna.getStepsToBroadcaster() != 0) && (antenna.getStepsToBroadcaster() < this.Length))
                        this.Length = antenna.getStepsToBroadcaster();
                }
                if ((tile instanceof TileEntityBroadcaster) &&
                        (((TileEntityBroadcaster) tile).getNetwork() == this.getNetwork() || this.getCableID() == "")){
                    TileEntityBroadcaster broadcaster = (TileEntityBroadcaster) tile;
                    this.network = broadcaster.getNetwork();
                    this.Length = 1;
                }
            }
        }

        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS){
            if (isValidTileAtPosition(this, this.getWorldObj(), x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ)){
                TileEntity tile = worldObj.getBlockTileEntity(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
                if ((tile instanceof TileEntityCable) &&
                        ((((TileEntityCable) tile).getCableID() == "") ||
                                (((TileEntityCable) tile).getNetwork() == this.getNetwork()))){
                    TileEntityCable cable = (TileEntityCable) tile;
                    if ((cable.getStepsToBroadcaster() == 0 && this.CableID != "") || (cable.getStepsToBroadcaster() > this.Length))
                        cable.updateCable(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
                }
                if ((tile instanceof TileEntityPlaylist) &&
                        ((((TileEntityPlaylist) tile).getCableID() == "") ||
                                (((TileEntityPlaylist) tile).getNetwork() == this.getNetwork()))){
                    TileEntityPlaylist playlist = (TileEntityPlaylist) tile;
                    if ((playlist.getStepsToBroadcaster() == 0 && this.CableID != "") || (playlist.getStepsToBroadcaster() > this.Length))
                        playlist.updateCable(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
                }
                if ((tile instanceof TileEntityAntenna) &&
                        ((((TileEntityAntenna) tile).getCableID() == "") ||
                                (((TileEntityAntenna) tile).getNetwork() == this.getNetwork()))){
                    TileEntityAntenna antenna = (TileEntityAntenna) tile;
                    if ((antenna.getStepsToBroadcaster() == 0 && this.CableID != "") || (antenna.getStepsToBroadcaster() > this.Length))
                        antenna.updateCable(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
                }
            }
        }
        worldObj.markBlockForRenderUpdate(x, y, z);
    }

    public boolean isValidTileAtPosition(TileEntityPlaylist cable, World world, int x, int y, int z){
        TileEntity tile = world.getBlockTileEntity(x, y, z);
        if (tile == null)
            return false;
        if (tile instanceof TileEntityCable){
            if(((TileEntityCable) tile).getCableID() != cable.getCableID() && ((TileEntityCable) tile).getCableID() != "") return false;
            return true;}
        if (tile instanceof TileEntityPlaylist){
            if (((TileEntityPlaylist) tile).getCableID() != cable.getCableID() && ((TileEntityPlaylist) tile).getCableID() != "") return false;
            return true;}
        if (tile instanceof TileEntityAntenna){
            if(((TileEntityAntenna) tile).getCableID() != cable.getCableID() && ((TileEntityAntenna) tile).getCableID() != "") return false;
            return true;}
        if (tile instanceof TileEntityBroadcaster){
            TileEntityBroadcaster broadcaster = (TileEntityBroadcaster) tile;
            if (broadcaster.getRadioID() == cable.getCableID()) return true;
        }
        return false;
    }

    public String getCableID(){
        return this.CableID;
    }

    public int getStepsToBroadcaster(){
        return this.Length;
    }
}
