package jkmau5.modjam.radiomod.radio;

import jkmau5.modjam.radiomod.RadioMod;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldSavedData;

/**
 * No description given
 *
 * @author jk-5
 */
public class RadioWorldData extends WorldSavedData {

    public int dimension;

    public RadioWorldData(String id){
        super(id);
        this.dimension = Integer.parseInt(id.split("-")[2]);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound){
        RadioMod.radioWorldHandler.readFromNBT(nbttagcompound, this.dimension);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound){
        RadioMod.radioWorldHandler.writeToNBT(nbttagcompound, this.dimension);
    }
}
