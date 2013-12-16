package jkmau5.modjam.radiomod.radio;

import com.google.common.collect.Lists;
import jkmau5.modjam.radiomod.Config;
import jkmau5.modjam.radiomod.Constants;
import jkmau5.modjam.radiomod.tile.TileEntityAntenna;
import jkmau5.modjam.radiomod.tile.TileEntityBroadcaster;
import jkmau5.modjam.radiomod.tile.TileEntityRadioNetwork;
import net.minecraft.world.World;

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
    private final List<IRadioListener> listeners = Lists.newArrayList();
    private TileEntityBroadcaster broadcaster;

    /**
     * This method adds an {@link jkmau5.modjam.radiomod.tile.TileEntityRadioNetwork} to the network.
     * It also sets the network field on the tileEntity
     *
     * @param tile The tile to add
     */
    public boolean add(TileEntityRadioNetwork tile){
        if(tile instanceof TileEntityBroadcaster && this.broadcaster != null){
            return false;
        }
        tile.network = this;
        this.networkTiles.add(tile);
        if(tile instanceof TileEntityBroadcaster && this.broadcaster == null){
            this.broadcaster = (TileEntityBroadcaster) tile;
        }
        return true;
    }

    public void addListener(IRadioListener listener){
        if(!this.listeners.contains(listener)){
            this.listeners.add(listener);
        }
    }

    public void removeListener(IRadioListener listener){
        if(this.listeners.contains(listener)){
            this.listeners.remove(listener);
        }
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

    public TileEntityBroadcaster getBroadcaster(){
        return this.broadcaster;
    }

    public int getMaxRangeForCoords(World world, int x, int y, int z){
        int range = 75 + (Math.max(y - 127, 1) / 3);
        if(world.isRaining()) range *= 0.8;
        if(world.isThundering()) range *= 0.7;
        return range * range;
    }

    public boolean areCoordsInRange(World world, int x, int y, int z){
        for(int i = 0; i < this.networkTiles.size(); i++){
            TileEntityRadioNetwork tile = this.networkTiles.get(i);
            if(world != tile.worldObj) continue;
            if(tile instanceof TileEntityAntenna){
                int dist = tile.getDistanceToCoords(x, y, z);
                int range = this.getMaxRangeForCoords(tile.worldObj, x, y, z);
                if(dist <= range) return true;
            }
        }
        return false;
    }

    public void playSound(String soundName){
        soundName = Constants.getNormalRecordTitle(soundName);
        if(soundName.equals("INVALID")) return;
        for(IRadioListener listener : this.listeners){
            listener.playSong(soundName);
        }
    }
}
