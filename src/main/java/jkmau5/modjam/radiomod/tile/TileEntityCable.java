package jkmau5.modjam.radiomod.tile;

import jkmau5.modjam.radiomod.radio.IRadioCable;
import jkmau5.modjam.radiomod.util.CableConnections;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import sun.org.mozilla.javascript.internal.IRFactory;

/**
 * Created by matthias on 1/7/14.
 *
 * The logic behind the cables
 */
public class TileEntityCable extends TileEntityRadioNetwork implements IRadioCable{

    private String CableID = "";
    private int Length = 0;
    public final CableConnections connections = new CableConnections();

    @Override
    public void readFromNBT(NBTTagCompound tag){
        super.readFromNBT(tag);
        this.CableID = tag.getString("ID");
        this.Length = tag.getInteger("length");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag){
        super.writeToNBT(tag);
        tag.setInteger("length", this.Length);
        tag.setString("ID", this.CableID);
    }

    public void refreshConnections(){
        for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS){
            boolean connect= isValidTileAtPosition(this,
                    this.worldObj,
                    this.xCoord + dir.offsetX,
                    this.yCoord + dir.offsetY,
                    this.zCoord + dir.offsetZ);
            this.connections.setConnected(dir, connect);
        }
    }

    public void removeCable(int x, int y, int z){
        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS){
            if (worldObj.getBlockTileEntity(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ) instanceof IRadioCable){
                worldObj.markBlockForUpdate(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
            }
        }
    }

    public void updateCable(int x, int y, int z){
        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS){
            if (isValidTileAtPosition(this, this.getWorldObj(), x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ)){
                TileEntity tile = worldObj.getBlockTileEntity(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
                if (tile instanceof IRadioCable){
                    IRadioCable cable = (IRadioCable) tile;
                    if (cable.getCableID() == this.CableID && cable.getStepsToBroadcaster() < this.Length - 1){
                        this.Length = cable.getStepsToBroadcaster() + 1;
                    }
                    if (this.CableID == "" && cable.getCableID() != ""){
                        this.CableID = cable.getCableID();
                    }
                }
                if (tile instanceof TileEntityBroadcaster){
                    TileEntityBroadcaster broadcaster = (TileEntityBroadcaster) tile;
                    if (broadcaster.getRadioID() == this.CableID){
                        this.Length = 1;
                    }
                }
            }
        }

        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS){
            if (isValidTileAtPosition(this, this.getWorldObj(), x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ)){
                TileEntity tile = worldObj.getBlockTileEntity(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
                if (tile instanceof IRadioCable){
                    IRadioCable cable = (IRadioCable) tile;
                    if (cable.getCableID() == "" || cable.getCableID() == this.CableID){
                        if (cable.getStepsToBroadcaster() > this.Length + 1 || cable.getStepsToBroadcaster() == 0 || cable.getCableID() == ""){
                            if (this.CableID != "") updateCable(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
                        }
                    }
                }
            }
        }
        worldObj.markBlockForRenderUpdate(x, y, z);
    }

    public boolean isValidTileAtPosition(TileEntityCable cable, World world, int x, int y, int z){
        TileEntity tile = world.getBlockTileEntity(x, y, z);
        if (tile == null)
            return false;
        if (tile instanceof IRadioCable){
            if(((IRadioCable) tile).getCableID() != cable.getCableID() && ((TileEntityCable) tile).getCableID() != "") return false;
            return true;}
        if (tile instanceof TileEntityBroadcaster){
            TileEntityBroadcaster broadcaster = (TileEntityBroadcaster) tile;
            if (broadcaster.getRadioID() == cable.getCableID() || this.CableID == "") return true;
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
