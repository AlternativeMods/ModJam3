package jkmau5.modjam.radiomod.tile;

import jkmau5.modjam.radiomod.RadioMod;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Author: Lordmau5
 * Date: 13.12.13
 * Time: 16:05
 * You are allowed to change this code,
 * however, not to publish it without my permission.
 */
public class TileEntityRadio extends TileEntity {

    protected String radioName;

    public TileEntityRadio() {}

    public TileEntityRadio(World world) {
        if(!world.isRemote)
            RadioMod.radioWorldHandler.addRadioTile(this, world);
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