package jkmau5.modjam.radiomod.tile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jkmau5.modjam.radiomod.RadioMod;
import jkmau5.modjam.radiomod.network.RadioNetwork;
import net.minecraft.client.Minecraft;
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
public class TileEntityBroadcaster extends TileEntity {

    protected String radioName;
    private boolean isInitiated;
    private RadioNetwork radioNetwork;
    private boolean radioInitiated;

    public TileEntityBroadcaster(){
        this.isInitiated = false;
        this.radioName = RadioMod.getUniqueRadioID();
        this.radioInitiated = false;
    }

    public void setRadioNetwork(RadioNetwork radioNetwork) {
       this.radioNetwork = radioNetwork;
    }

    public RadioNetwork getRadioNetwork() {
        return this.radioNetwork;
    }

    @SideOnly(Side.CLIENT)
    public int getDistanceToMe() {
        return (int) Math.ceil(Minecraft.getMinecraft().thePlayer.getDistanceSq(this.xCoord, this.yCoord, this.zCoord));
    }

    @SideOnly(Side.CLIENT)
    public int getDistanceToMe(TileEntity tile){
        return (int) Math.ceil(tile.getDistanceFrom(this.xCoord, this.yCoord, this.zCoord));
    }

    public boolean isConnectedToNetwork() {
        for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            TileEntity tempTile = worldObj.getBlockTileEntity(this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ);
            if(tempTile != null && tempTile instanceof TileEntityCable && getRadioNetwork() == ((TileEntityCable)tempTile).getNetwork())
                return true;
        }
        return false;
    }

    public void tryConnectToSurroundings() {
        for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            TileEntity tempTile = worldObj.getBlockTileEntity(this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ);
            if(tempTile != null && tempTile instanceof TileEntityCable && ((TileEntityCable)tempTile).getNetwork().getBroadcaster() == null) {
                ((TileEntityCable)tempTile).getNetwork().setBroadcaster(this);
                return;
            }
        }
    }

    public void destroyNetwork() {
        this.radioNetwork = null;
        this.radioInitiated = false;
    }

    public void updateEntity() {
        if(!this.radioInitiated) {
            this.radioInitiated = true;
            tryConnectToSurroundings();
        }
    }

    @Override
    public void validate()
    {
        super.validate();
        if(!isInitiated) {
            isInitiated = true;
            if(!worldObj.isRemote)
                RadioMod.radioWorldHandler.addRadioTile(this);
        }
        this.tileEntityInvalid = false;
    }

    @Override
    public void invalidate()
    {
        super.invalidate();
        if(isInitiated) {
            isInitiated = false;
            if(!worldObj.isRemote)
                RadioMod.radioWorldHandler.removeRadioTile(this);
        }
        if(radioInitiated) {
            radioInitiated = false;
            getRadioNetwork().tryRemoveBroadcaster(this);
        }
        this.tileEntityInvalid = true;
    }

    @Override
    public void onChunkUnload()
    {
        super.onChunkUnload();
        if(isInitiated) {
            isInitiated = false;
            if(!worldObj.isRemote)
                RadioMod.radioWorldHandler.removeRadioTile(this);
        }
        if(radioInitiated) {
            radioInitiated = false;
            getRadioNetwork().tryRemoveBroadcaster(this);
        }
    }

    public String getRadioName() {
        return radioName;
    }

    public void setRadioName(String name) {
        radioName = name;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);


        setRadioName(tagCompound.getString("radioName"));
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);

        tagCompound.setString("radioName", getRadioName());
    }
}