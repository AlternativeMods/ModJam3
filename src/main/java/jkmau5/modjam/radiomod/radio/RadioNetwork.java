package jkmau5.modjam.radiomod.radio;

import com.google.common.collect.Lists;
import jkmau5.modjam.radiomod.Config;
import jkmau5.modjam.radiomod.tile.TileEntityRadioNetwork;

import java.util.List;

/**
 * This class represents a radio network connection between 2 or more TileEntities
 *
 * @author jk-5
 */
public class RadioNetwork {

    private static int nextID = Config.NEXT_NETWORK_ID;

    private static int getNextID(){
        int ret = nextID++;
        Config.updateNextID(ret);
        return ret;
    }

    private int ID = -1;
    private final List<TileEntityRadioNetwork> networkTiles = Lists.newArrayList();

    /**
     * This method adds an {@link jkmau5.modjam.radiomod.tile.TileEntityRadioNetwork} to the network.
     * It also sets the network field on the tileEntity
     *
     * @param tile The tile to add
     */
    public void add(TileEntityRadioNetwork tile){
        tile.network = this;
        this.networkTiles.add(tile);
    }

    /**
     * @return the number of network tiles connected to this network
     */
    public int getNetworkSize(){
        return this.networkTiles.size();
    }

    public List<TileEntityRadioNetwork> getNetworkTiles(){
        return this.networkTiles;
    }

    public int getID(){
        return this.ID;
    }

    void setID(int id){
        this.ID = id;
    }

    public void grabNextID(){
        this.setID(getNextID());
    }
}
