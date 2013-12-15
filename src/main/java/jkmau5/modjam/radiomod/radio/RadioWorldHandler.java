package jkmau5.modjam.radiomod.radio;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import cpw.mods.fml.common.FMLCommonHandler;
import jkmau5.modjam.radiomod.tile.TileEntityBroadcaster;
import jkmau5.modjam.radiomod.tile.TileEntityRadio;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.storage.MapStorage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.WorldEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    private Multimap<Integer, TileEntityBroadcaster> radioTiles = ArrayListMultimap.create();
    private Multimap<Integer, String> radioNames = ArrayListMultimap.create();

    public RadioWorldHandler(){
        radioTiles.clear();
        MinecraftForge.EVENT_BUS.register(this);
    }

    public List<TileEntityBroadcaster> getAvailableRadioList(int dimensionId, EntityPlayer player){
        Collection<TileEntityBroadcaster> tempRadios = radioTiles.get(dimensionId);
        if(tempRadios == null || tempRadios.isEmpty())
            return null;

        List<TileEntityBroadcaster> availableRadios = new ArrayList<TileEntityBroadcaster>();

        for(TileEntityBroadcaster availableRadio : tempRadios){
            if(availableRadio.getDistanceToMe() < 250)  //TODO: Change Probably?
                availableRadios.add(availableRadio);
        }
        return availableRadios;
    }

    public List<TileEntityBroadcaster> getAvailableRadioList(int dimensionId, TileEntityRadio tile){
        if(tile == null)
            return null;

        List<TileEntityBroadcaster> tempRadios = radioTiles.get(dimensionId);
        if(tempRadios == null || tempRadios.isEmpty())
            return null;

        List<TileEntityBroadcaster> availableRadios = new ArrayList<TileEntityBroadcaster>();

        for(TileEntityBroadcaster availableRadio : tempRadios){
            if(availableRadio.getDistanceToMe(tile.xCoord, tile.yCoord, tile.zCoord) < 250)  //TODO: Change Probably?
                availableRadios.add(availableRadio);
        }
        return availableRadios;
    }

    public boolean addRadioTile(TileEntityBroadcaster radio){
        int dimid = radio.worldObj.provider.dimensionId;
        this.radioTiles.put(dimid, radio);
        System.out.println("Added a radio tile in dimension " + dimid);
        return true;
    }

    public boolean removeRadioTile(TileEntityBroadcaster radio){
        int dimid = radio.worldObj.provider.dimensionId;
        this.radioTiles.remove(dimid, radio);
        System.out.println("Removed a radio tile from dimension " + dimid);
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
        NBTTagList list = tag.getTagList("RadioList");
        for(int i = 0; i < list.tagCount(); i++){
            NBTTagCompound b = (NBTTagCompound) list.tagAt(i);
            this.radioNames.put(dimension, b.getString("name"));
        }
    }

    public void writeToNBT(NBTTagCompound tag, int dimension){
        Collection<TileEntityBroadcaster> broadcasters = this.radioTiles.get(dimension);
        NBTTagList list = new NBTTagList();
        for(TileEntityBroadcaster broadcaster : broadcasters){
            NBTTagCompound b = new NBTTagCompound();
            b.setString("name", broadcaster.getRadioName());
            list.appendTag(b);
        }
        tag.setTag("RadioList", list);
    }
}
