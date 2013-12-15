package jkmau5.modjam.radiomod.tile;

import jkmau5.modjam.radiomod.RadioMod;
import jkmau5.modjam.radiomod.radio.IBroadcaster;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

/**
 * Author: Lordmau5
 * Date: 13.12.13
 * Time: 16:05
 * You are allowed to change this code,
 * however, not to publish it without my permission.
 */
public class TileEntityBroadcaster extends TileEntityRadioNetwork implements IBroadcaster {

    protected String radioName;
    private boolean radioInitiated;

    public TileEntityBroadcaster(){
        this.radioName = RadioMod.getUniqueRadioID();
        this.radioInitiated = false;
    }

    public boolean isConnectedToNetwork(){
        for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS){
            TileEntity tempTile = worldObj.getBlockTileEntity(this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ);
            if(tempTile != null && tempTile instanceof TileEntityCable && getNetwork() == ((TileEntityCable) tempTile).getNetwork())
                return true;
        }
        return false;
    }

    public void tryConnectToSurroundings(){
        for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS){
            TileEntity tempTile = worldObj.getBlockTileEntity(this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ);
            if(tempTile != null && tempTile instanceof TileEntityCable && ((TileEntityCable) tempTile).getNetwork() != null && ((TileEntityCable) tempTile).getNetwork().getBroadcaster() == null){
                ((TileEntityCable) tempTile).getNetwork().setBroadcaster(this);
                return;
            }
        }
    }

    public void updateEntity(){
        super.updateEntity();
        if(!this.radioInitiated){
            this.radioInitiated = true;
            RadioMod.radioWorldHandler.addRadioTile(this);
            this.tryConnectToSurroundings();
        }
    }

    @Override
    public void invalidate(){
        super.invalidate();
        if(this.radioInitiated){
            this.radioInitiated = false;
            if(worldObj != null && !worldObj.isRemote && RadioMod.radioWorldHandler != null){
                RadioMod.radioWorldHandler.removeRadioTile(this);
            }
        }
    }

    @Override
    public void onChunkUnload(){
        super.onChunkUnload();
        if(this.radioInitiated){
            this.radioInitiated = false;
            if(!worldObj.isRemote){
                RadioMod.radioWorldHandler.removeRadioTile(this);
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound){
        super.readFromNBT(tagCompound);
        this.radioName = tagCompound.getString("radioName");
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound){
        super.writeToNBT(tagCompound);
        tagCompound.setString("radioName", this.radioName);
    }

    public String getRadioName(){
        return radioName;
    }

    public void setRadioName(String radioName){
        this.radioName = radioName;
    }
}