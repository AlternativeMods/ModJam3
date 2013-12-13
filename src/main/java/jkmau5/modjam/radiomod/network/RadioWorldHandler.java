package jkmau5.modjam.radiomod.network;

import jkmau5.modjam.radiomod.tile.TileEntityRadio;
import net.minecraft.world.World;

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

    Map<Integer, List<TileEntityRadio>> radioTiles = new HashMap<Integer, List<TileEntityRadio>>();

    public RadioWorldHandler() {
        radioTiles.clear();
    }

    public String getRadioName(TileEntityRadio radio) {
        return radio.getRadioName();
    }

    public boolean addRadioTile(TileEntityRadio radio, World world) {
        int dimensionId = world.provider.dimensionId;
        List<TileEntityRadio> dimensionTiles = radioTiles.get(dimensionId);

        if(dimensionTiles == null)
            dimensionTiles = new ArrayList<TileEntityRadio>();
        if(dimensionTiles.contains(radio))
            return false;
        dimensionTiles.add(radio);
        radioTiles.put(dimensionId, dimensionTiles);

        System.out.println("Added a radio tile in dimension " + dimensionId);

        return true;
    }

    public boolean removeRadioTile(TileEntityRadio radio) {
        int dimensionId = radio.worldObj.provider.dimensionId;
        List<TileEntityRadio> dimensionTiles = radioTiles.get(dimensionId);

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