package jkmau5.modjam.radiomod.radio;

import com.google.common.collect.Lists;
import net.minecraft.world.World;

import java.util.List;

/**
 * No description given
 *
 * @author jk-5
 */
public class RadioNetworkHandler {

    private List<RadioNetwork> networks = Lists.newArrayList();

    public List<String> getAvailableRadioNames(World world, int x, int y, int z){
        List<String> ret = Lists.newArrayList();
        for(RadioNetwork network : this.networks){
            if(network.areCoordsInRange(world, x, y, z) && network.getBroadcaster() != null){
                ret.add(network.getBroadcaster().getRadioName());
            }
        }
        return ret;
    }

    public RadioNetwork getNetworkFromID(int id){
        for(RadioNetwork network : networks){
            if(network.getID() == id){
                return network;
            }
        }
        RadioNetwork network = new RadioNetwork();
        network.setID(id);
        networks.add(network);
        return network;
    }

    public List<RadioNetwork> getNetworks(){
        return networks;
    }

    public void addNetwork(RadioNetwork network){
        this.networks.add(network);
    }

    public RadioNetwork getNetworkFromName(String name){
        for(RadioNetwork network : networks){
            if(network.getBroadcaster() != null && network.getBroadcaster().getRadioName().equals(name)){
                return network;
            }
        }
        return null;
    }
}
