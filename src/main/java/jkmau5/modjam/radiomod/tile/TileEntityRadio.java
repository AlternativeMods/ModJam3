package jkmau5.modjam.radiomod.tile;

import jkmau5.modjam.radiomod.RadioMod;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * Author: Lordmau5
 * Date: 13.12.13
 * Time: 16:05
 * You are allowed to change this code,
 * however, not to publish it without my permission.
 */
public class TileEntityRadio extends TileEntity {

    protected String radioName;
    private boolean isInitiated;

    public TileEntityRadio() {
        isInitiated = false;
        radioName = RadioMod.getUniqueRadioID();
    }

    @Override
    public void validate()
    {
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
        if(isInitiated) {
            isInitiated = false;
            if(!worldObj.isRemote)
                RadioMod.radioWorldHandler.removeRadioTile(this);
        }
        this.tileEntityInvalid = true;
    }

    @Override
    public void onChunkUnload()
    {
        if(isInitiated) {
            isInitiated = false;
            if(!worldObj.isRemote)
                RadioMod.radioWorldHandler.removeRadioTile(this);
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