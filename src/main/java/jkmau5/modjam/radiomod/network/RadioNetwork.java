package jkmau5.modjam.radiomod.network;

import jkmau5.modjam.radiomod.tile.TileEntityBroadcaster;
import jkmau5.modjam.radiomod.tile.TileEntityCable;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Lordmau5
 * Date: 14.12.13
 * Time: 15:41
 * You are allowed to change this code,
 * however, not to publish it without my permission.
 */
public class RadioNetwork {
    private List<TileEntityCable> cables = new ArrayList<TileEntityCable>();
    //private List<TileEntityAntenna> antennas = new ArrayList<TileEntityAntenna>(); //TODO: Add tile entity for the antenna
    private TileEntityBroadcaster broadcaster;

    public RadioNetwork(TileEntityCable mainCable) {
        this.cables.clear();
        this.broadcaster = null;

        addCable(mainCable);
    }

    public RadioNetwork(TileEntityBroadcaster broadcaster) {
        this.cables.clear();
        this.broadcaster = broadcaster;
    }

    public void destroyNetwork() {
        this.cables = null;
        this.broadcaster = null;
    }

    public List<TileEntityCable> getCables() {
        return this.cables;
    }

    public TileEntityBroadcaster getBroadcaster() {
        return this.broadcaster;
    }

    public boolean setBroadcaster(TileEntityBroadcaster broadcaster){
        if(this.broadcaster != null && this.broadcaster == broadcaster)
            return true;
        if(this.broadcaster != null && this.broadcaster != broadcaster)
            return false;
        if(this.broadcaster == null)
            this.broadcaster = broadcaster;
        broadcaster.setRadioNetwork(this);
        return true;
    }

    public boolean tryRemoveBroadcaster(TileEntityBroadcaster radio){
        if(!radio.isConnectedToNetwork()) {
            this.broadcaster.destroyNetwork();
            this.broadcaster = null;
            return true;
        }
        return false;
    }

    public void addCable(TileEntityCable cable) {
        if(this.cables.contains(cable))
            return;
        this.cables.add(cable);
        cable.setNetwork(this);
    }

    public void removeCable(TileEntityCable cable) {
        if(!this.cables.contains(cable))
            return;
        this.cables.remove(cable);
        cable.setNetwork(null);

        recalculateNetwork(this);
    }

    public void recalculateNetwork(RadioNetwork network) {
        if(this.broadcaster != null)
            this.broadcaster.setRadioNetwork(null);

        for(TileEntityCable cable : network.getCables()) {
            cable.initiateNetwork();
        }
    }

    public void mergeWithNetwork(RadioNetwork otherNetwork) {
        if(otherNetwork == this)
            return;

        for(TileEntityCable cable : otherNetwork.getCables())
            addCable(cable);

        if(otherNetwork.getBroadcaster() != null)
            setBroadcaster(otherNetwork.getBroadcaster());

        otherNetwork.destroyNetwork();
    }
}