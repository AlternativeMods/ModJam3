package jkmau5.modjam.radiomod.tile;

import jkmau5.modjam.radiomod.radio.IRadioCable;
import jkmau5.modjam.radiomod.util.CableConnections;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

/**
 * Created by matthias on 1/7/14.
 *
 * The logic behind the cables
 */
public class TileEntityCable extends TileEntityRadioNetwork implements IRadioCable{

    private int CableID;
    private int Length;
    public final CableConnections connections = new CableConnections();

    @Override
    public void readFromNBT(NBTTagCompound tag){
        super.readFromNBT(tag);
        this.CableID = tag.getInteger("ID");
        this.Length = tag.getInteger("length");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag){
        super.writeToNBT(tag);
        tag.setInteger("length", this.Length);
        tag.setInteger("ID", this.CableID);
    }

    private void refreshConnections(){
        for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS){
            TileEntity tile = this.worldObj.getBlockTileEntity(this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ);

            boolean connect= isValidTileAtPosition(this,
                    this.worldObj,
                    this.xCoord + dir.offsetX,
                    this.yCoord + dir.offsetY,
                    this.zCoord + dir.offsetZ);
            this.connections.setConnected(dir, connect);
        }
    }

    @Override
    public void onNeighborBlockChange(){
        this.refreshConnections();
        this.updateCable(this.xCoord, this.yCoord, this.zCoord);
        super.onNeighborBlockChange();
    }

    @Override
    public void onBlockPlacedBy(EntityLivingBase ent, ItemStack is){
        this.refreshConnections();
        this.updateCable(this.xCoord, this.yCoord, this.zCoord);
        super.onBlockPlacedBy(ent, is);
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
                        (((TileEntityCable) tile).getCableID() != 0) &&
                        (((TileEntityCable) tile).getNetwork() == this.getNetwork())){
                    TileEntityCable cable = (TileEntityCable) tile;
                    if ((cable.getStepsToBroadcaster() != 0) && (cable.getStepsToBroadcaster() < this.Length))
                        this.Length = cable.getStepsToBroadcaster() + 1;
                }
                if ((tile instanceof TileEntityPlaylist) &&
                        (((TileEntityPlaylist) tile).getCableID() != 0) &&
                        (((TileEntityPlaylist) tile).getNetwork() == this.getNetwork())){
                    TileEntityPlaylist playlist = (TileEntityPlaylist) tile;
                    if ((playlist.getStepsToBroadcaster() != 0) && (playlist.getStepsToBroadcaster() < this.Length))
                        this.Length = playlist.getStepsToBroadcaster() + 1;
                }
                if ((tile instanceof TileEntityAntenna) &&
                        (((TileEntityAntenna) tile).getCableID() != 0) &&
                        (((TileEntityAntenna) tile).getNetwork() == this.getNetwork())){
                    TileEntityAntenna antenna = (TileEntityAntenna) tile;
                    if ((antenna.getStepsToBroadcaster() != 0) && (antenna.getStepsToBroadcaster() < this.Length))
                        this.Length = antenna.getStepsToBroadcaster();
                }
                if ((tile instanceof TileEntityBroadcaster) &&
                        (((TileEntityBroadcaster) tile).getNetwork() == this.getNetwork() || this.getNetwork().getID() == 0)){
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
                        ((((TileEntityCable) tile).getCableID() == 0) ||
                        (((TileEntityCable) tile).getNetwork() == this.getNetwork()))){
                    TileEntityCable cable = (TileEntityCable) tile;
                    if ((cable.getStepsToBroadcaster() == 0) || (cable.getStepsToBroadcaster() > this.Length))
                        cable.updateCable(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
                }
                if ((tile instanceof TileEntityPlaylist) &&
                        ((((TileEntityPlaylist) tile).getCableID() == 0) ||
                        (((TileEntityPlaylist) tile).getNetwork() == this.getNetwork()))){
                    TileEntityPlaylist playlist = (TileEntityPlaylist) tile;
                    if ((playlist.getStepsToBroadcaster() == 0) || (playlist.getStepsToBroadcaster() > this.Length))
                        playlist.updateCable(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
                }
                if ((tile instanceof TileEntityAntenna) &&
                        ((((TileEntityAntenna) tile).getCableID() == 0) ||
                        (((TileEntityAntenna) tile).getNetwork() == this.getNetwork()))){
                    TileEntityAntenna antenna = (TileEntityAntenna) tile;
                    if ((antenna.getStepsToBroadcaster() == 0) || (antenna.getStepsToBroadcaster() > this.Length))
                        antenna.updateCable(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
                }
            }
        }
        worldObj.markBlockForRenderUpdate(x, y, z);
    }

    public boolean isValidTileAtPosition(TileEntityCable cable, World world, int x, int y, int z){
        TileEntity tile = world.getBlockTileEntity(x, y, z);
        if (tile == null)
            return false;
        if (tile instanceof TileEntityCable){
            if(((TileEntityCable) tile).getCableID() != cable.getCableID() && ((TileEntityCable) tile).getCableID() != 0) return false;
            return true;}
        if (tile instanceof TileEntityPlaylist){
            if (((TileEntityPlaylist) tile).getCableID() != cable.getCableID() && ((TileEntityPlaylist) tile).getCableID() != 0) return false;
            return true;}
        if (tile instanceof TileEntityAntenna){
            if(((TileEntityAntenna) tile).getCableID() != cable.getCableID() && ((TileEntityAntenna) tile).getCableID() != 0) return false;
            return true;}
        if (tile instanceof TileEntityBroadcaster){
            TileEntityBroadcaster broadcaster = (TileEntityBroadcaster) tile;
            if (broadcaster.getNetwork().getID() == cable.getCableID()) return true;
        }
        return false;
    }

    public int getCableID(){
        return this.getNetwork().getID();
    }

    public int getStepsToBroadcaster(){
        return this.Length;
    }
}
