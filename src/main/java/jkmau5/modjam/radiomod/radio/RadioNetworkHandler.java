package jkmau5.modjam.radiomod.radio;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * No description given
 *
 * @author jk-5
 */
public class RadioNetworkHandler {

    private static int nextID = 0;
    private static List<RadioNetwork> networks = Lists.newArrayList();

    public static void addNetwork(RadioNetwork network){
        if(network.getID() == -1){
            network.setID(nextID++);
        }
    }

    public static RadioNetwork getNetworkFromID(int id){
        for(RadioNetwork network : networks){
            if(network.getID() == id){
                return network;
            }
        }
        return null;
    }
}
