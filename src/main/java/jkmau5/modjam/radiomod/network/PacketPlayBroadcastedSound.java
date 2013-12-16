package jkmau5.modjam.radiomod.network;

import jkmau5.modjam.radiomod.RadioMod;
import jkmau5.modjam.radiomod.radio.RadioNetwork;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * No description given
 *
 * @author jk-5
 */
public class PacketPlayBroadcastedSound extends PacketBase {

    public String soundName;
    public int networkID;

    public PacketPlayBroadcastedSound(){
    }

    public PacketPlayBroadcastedSound(String soundName, int networkID){
        this.soundName = soundName;
        this.networkID = networkID;
    }

    @Override
    public void writePacket(DataOutput output) throws IOException{
        output.writeInt(this.networkID);
        output.writeUTF(this.soundName);
    }

    @Override
    public void readPacket(DataInput input) throws IOException{
        this.networkID = input.readInt();
        this.soundName = input.readUTF();
        RadioNetwork network = RadioMod.radioNetworkHandler.getNetworkFromID(this.networkID);
        if(network == null) return;
        network.playSound(this.soundName);
    }
}
