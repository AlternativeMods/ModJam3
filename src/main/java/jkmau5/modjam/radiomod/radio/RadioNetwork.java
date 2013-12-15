package jkmau5.modjam.radiomod.radio;

import com.google.common.collect.Lists;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import jkmau5.modjam.radiomod.tile.TileEntityBroadcaster;
import jkmau5.modjam.radiomod.tile.TileEntityRadioNetwork;

import java.util.List;

/**
 * Author: Lordmau5
 * Date: 14.12.13
 * Time: 15:41
 * You are allowed to change this code,
 * however, not to publish it without my permission.
 */
public class RadioNetwork {

    private List<TileEntityRadioNetwork> networkTiles = Lists.newArrayList();
    //private List<TileEntityAntenna> antennas = new ArrayList<TileEntityAntenna>(); //TODO: Add tile entity for the antenna
    private TileEntityBroadcaster broadcaster;

    public RadioNetwork(TileEntityRadioNetwork tile){
        this.networkTiles.clear();
        this.broadcaster = null;

        this.addNetworkTile(tile);
        if(tile instanceof IBroadcaster) this.broadcaster = (TileEntityBroadcaster) tile;
    }

    public void destroyNetwork(){
        this.networkTiles.clear();
        this.broadcaster = null;
    }

    public void remove(TileEntityRadioNetwork tile){
        if(tile instanceof ICable && this.networkTiles.contains(tile)){
            this.networkTiles.remove(tile);
            this.recalculateNetwork();
        }
    }

    public List<TileEntityRadioNetwork> getNetworkTiles(){
        return this.networkTiles;
    }

    public TileEntityBroadcaster getBroadcaster(){
        return this.broadcaster;
    }

    public boolean setBroadcaster(TileEntityBroadcaster broadcaster){
        if(broadcaster.getNetwork() != null) return false;
        if(this.broadcaster != null) this.networkTiles.remove(this.broadcaster);

        if(this.broadcaster != null && this.broadcaster == broadcaster) return true;
        if(this.broadcaster != null) return false;
        this.broadcaster = broadcaster;
        this.networkTiles.add(broadcaster);
        broadcaster.setNetwork(this);
        return true;
    }

    public void addNetworkTile(TileEntityRadioNetwork tile){
        if(this.networkTiles.contains(tile)) return;
        this.networkTiles.add(tile);
        tile.setNetwork(this);
    }

    public void recalculateNetwork(){
        if(this.broadcaster != null) this.broadcaster.destroyNetwork();
        this.broadcaster = null;
        for(int i = 0; i < this.networkTiles.size(); i++){
            this.networkTiles.get(i).initiateNetwork();
        }
    }

    public void mergeWithNetwork(RadioNetwork otherNetwork){
        if(otherNetwork == this) return;

        if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER){
            if(this.broadcaster != null) System.out.println("First: " + this.broadcaster.toString());
            if(otherNetwork.getBroadcaster() != null)
                System.out.println("Second: " + otherNetwork.getBroadcaster().toString());
        }

        for(TileEntityRadioNetwork tile : otherNetwork.getNetworkTiles()){
            addNetworkTile(tile);
        }

        //if(otherNetwork.getBroadcaster() != null)
        //    otherNetwork.getBroadcaster().setRadioNetwork(this);

        otherNetwork.destroyNetwork();
    }
}
