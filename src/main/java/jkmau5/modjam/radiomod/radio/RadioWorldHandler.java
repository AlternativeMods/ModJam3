package jkmau5.modjam.radiomod.radio;

import cpw.mods.fml.common.FMLCommonHandler;
import jkmau5.modjam.radiomod.tile.TileEntityBroadcaster;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.storage.MapStorage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.WorldEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Lordmau5
 * Date: 13.12.13
 * Time: 17:08
 * You are allowed to change this code,
 * however, not to publish it without my permission.
 */
public class RadioWorldHandler {

    /**
     * A map from dimension id to a list of broadcasters
     */
    Map<Integer, List<TileEntityBroadcaster>> radioTiles = new HashMap<Integer, List<TileEntityBroadcaster>>();

    public RadioWorldHandler(){
        radioTiles.clear();
        MinecraftForge.EVENT_BUS.register(this);
    }

    public String getRadioName(TileEntityBroadcaster radio){
        return radio.getRadioName();
    }

    public List<TileEntityBroadcaster> getAvailableRadioList(int dimensionId, EntityPlayer player){
        //World world = player.worldObj.provider.dimensionId;
        List<TileEntityBroadcaster> tempRadios = radioTiles.get(dimensionId);
        if(tempRadios == null || tempRadios.isEmpty())
            return null;

        List<TileEntityBroadcaster> availableRadios = new ArrayList<TileEntityBroadcaster>();

        for(TileEntityBroadcaster availableRadio : tempRadios){
            if(availableRadio.getDistanceToMe() < 250)  //TODO: Change Probably?
                availableRadios.add(availableRadio);
        }
        return availableRadios;
    }

    public boolean addRadioTile(TileEntityBroadcaster radio){
        int dimensionId = radio.worldObj.provider.dimensionId;
        List<TileEntityBroadcaster> dimensionTiles = radioTiles.get(dimensionId);

        if(dimensionTiles == null)
            dimensionTiles = new ArrayList<TileEntityBroadcaster>();
        if(dimensionTiles.contains(radio))
            return false;
        dimensionTiles.add(radio);
        radioTiles.put(dimensionId, dimensionTiles);

        System.out.println("Added a radio tile in dimension " + dimensionId);

        return true;
    }

    public boolean removeRadioTile(TileEntityBroadcaster radio){
        int dimensionId = radio.worldObj.provider.dimensionId;
        List<TileEntityBroadcaster> dimensionTiles = radioTiles.get(dimensionId);

        if(dimensionTiles == null)
            return false;
        if(!dimensionTiles.contains(radio))
            return false;
        dimensionTiles.remove(radio);
        radioTiles.put(dimensionId, dimensionTiles);

        System.out.println("Removed a radio tile from dimension " + dimensionId);

        return true;
    }

    @ForgeSubscribe
    @SuppressWarnings("unused")
    public void onWorldLoad(WorldEvent.Load event){
        if(FMLCommonHandler.instance().getEffectiveSide().isClient()) return;
        MapStorage storage = event.world.mapStorage;
        RadioWorldData data = (RadioWorldData) storage.loadData(RadioWorldData.class, "RadioMod-RadioData-" + event.world.provider.dimensionId);
    }

    public void readFromNBT(NBTTagCompound tag, int dimension){

    }

    public void writeToNBT(NBTTagCompound tag, int dimension){

    }
}
