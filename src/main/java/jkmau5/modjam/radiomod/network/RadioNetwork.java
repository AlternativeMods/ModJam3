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
    private TileEntityBroadcaster radio;

    public RadioNetwork(TileEntityCable mainCable) {
        this.cables.clear();
        this.radio = null;

        addCable(mainCable);
    }

    public void destroyNetwork() {
        this.cables = null;
        this.radio = null;
    }

    public List<TileEntityCable> getCables() {
        return this.cables;
    }

    public boolean setRadio(TileEntityBroadcaster radio){
        if(this.radio != null && this.radio == radio)
            return true;
        if(this.radio != null && this.radio != radio)
            return false;
        if(this.radio == null)
            this.radio = radio;
        return true;
    }

    public boolean tryRemoveRadio(TileEntityBroadcaster radio){
        if(!radio.isConnectedToNetwork()) {
            this.radio = null;
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
        for(TileEntityCable cable : network.getCables())
            cable.initiateNetwork();
    }

    public void mergeWithNetwork(RadioNetwork otherNetwork) {
        if(otherNetwork == this)
            return;

        for(TileEntityCable cable : otherNetwork.getCables())
            addCable(cable);

        otherNetwork.destroyNetwork();
    }
}