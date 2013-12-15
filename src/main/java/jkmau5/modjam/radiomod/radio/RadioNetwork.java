package jkmau5.modjam.radiomod.radio;

import com.google.common.collect.Lists;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import jkmau5.modjam.radiomod.tile.TileEntityBroadcaster;
import jkmau5.modjam.radiomod.tile.TileEntityCable;
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

    private List<ICable> cables = Lists.newArrayList();
    //private List<TileEntityAntenna> antennas = new ArrayList<TileEntityAntenna>(); //TODO: Add tile entity for the antenna
    private TileEntityBroadcaster broadcaster;

    public RadioNetwork(TileEntityRadioNetwork tile){
        this.cables.clear();
        this.broadcaster = null;

        if(tile instanceof ICable) this.addCable((ICable) tile);
        if(tile instanceof IBroadcaster) this.broadcaster = (TileEntityBroadcaster) tile;
    }

    public void destroyNetwork(){
        this.cables.clear();
        this.broadcaster = null;
    }

    public void remove(TileEntityRadioNetwork tile){

    }

    public List<ICable> getCables(){
        return this.cables;
    }

    public TileEntityBroadcaster getBroadcaster() {
        return this.broadcaster;
    }

    public boolean setBroadcaster(TileEntityBroadcaster broadcaster){
        //System.out.println("1");
        if(broadcaster.getNetwork() != null) return false;
        //System.out.println("2");
        if(this.broadcaster != null && this.broadcaster == broadcaster) return true;
        //System.out.println("3");
        if(this.broadcaster != null) return false;
        //System.out.println("4");
        this.broadcaster = broadcaster;
        broadcaster.setNetwork(this);
        return true;
    }

    public boolean tryRemoveBroadcaster(TileEntityBroadcaster radio){
        /*if(radio != null && !radio.isConnectedToNetwork()) {
            this.broadcaster.destroyNetwork();
            this.broadcaster = null;
            return true;
        }*/
        return false;
    }

    public void addCable(ICable cable){
        if(this.cables.contains(cable)) return;
        this.cables.add(cable);
        cable.setNetwork(this);
    }

    public void removeCable(TileEntityCable cable) {
        if(!this.cables.contains(cable))
            return;
        this.cables.remove(cable);

        recalculateNetwork(this);
    }

    public void recalculateNetwork(RadioNetwork network) {
        for(ICable cable : network.getCables()){
            cable.initiateNetwork();
        }
        if(this.broadcaster != null) this.broadcaster.destroyNetwork();
        this.broadcaster = null;
    }

    public void mergeWithNetwork(RadioNetwork otherNetwork) {
        if(otherNetwork == this) return;

        if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
            if(this.broadcaster != null) System.out.println("First: " + this.broadcaster.toString());
            if(otherNetwork.getBroadcaster() != null)
                System.out.println("Second: " + otherNetwork.getBroadcaster().toString());
        }

        for(ICable cable : otherNetwork.getCables()) addCable(cable);

        //if(otherNetwork.getBroadcaster() != null)
        //    otherNetwork.getBroadcaster().setRadioNetwork(this);

        otherNetwork.destroyNetwork();
    }
}