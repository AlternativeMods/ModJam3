package jkmau5.modjam.radiomod.network;

import jkmau5.modjam.radiomod.tile.TileEntityBroadcaster;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

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

    Map<Integer, List<TileEntityBroadcaster>> radioTiles = new HashMap<Integer, List<TileEntityBroadcaster>>();

    public RadioWorldHandler(){
        radioTiles.clear();
    }

    public String getRadioName(TileEntityBroadcaster radio){
        return radio.getRadioName();
    }

    public List<TileEntityBroadcaster> getAvailableRadioList(int dimensionId, EntityPlayer player){
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

    public List<TileEntityBroadcaster> getAvailableRadioList(int dimensionId, TileEntity tile){
        List<TileEntityBroadcaster> tempRadios = radioTiles.get(dimensionId);
        if(tempRadios == null || tempRadios.isEmpty())
            return null;

        List<TileEntityBroadcaster> availableRadios = new ArrayList<TileEntityBroadcaster>();

        for(TileEntityBroadcaster availableRadio : tempRadios){
            if(availableRadio.getDistanceToMe(tile) < 250)  //TODO: Change Probably?
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
}