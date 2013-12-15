package jkmau5.modjam.radiomod.network;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import cpw.mods.fml.common.network.PacketDispatcher;
import jkmau5.modjam.radiomod.Constants;
import jkmau5.modjam.radiomod.RMLogger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * No description given
 *
 * @author jk-5
 */
public abstract class PacketBase {

    private static final BiMap<Integer, Class<? extends PacketBase>> packets = HashBiMap.create();
    public EntityPlayer player;

    private static void registerPacket(int id, Class<? extends PacketBase> cl){
        packets.put(id, cl);
    }

    static {
        registerPacket(0, PacketOpenGui.class);
        registerPacket(1, PacketUpdateRadioName.class);
        registerPacket(2, PacketRequestRadioNames.class);
        registerPacket(3, PacketMediaPlayerData.class);
        registerPacket(4, PacketRemovePlaylistTitle.class);
    }

    public final Packet250CustomPayload getPacket(){
        Packet250CustomPayload ret = null;
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        try{
            output.writeByte(this.getID());
            this.writePacket(output);
            ret = PacketDispatcher.getPacket(Constants.MODID, output.toByteArray());
        }catch(IOException e){
            RMLogger.severe(e, "Error while writing packet data for packet " + this.getID());
        }
        return ret;
    }

    public static PacketBase readPacket(Packet250CustomPayload packet, EntityPlayer player){
        PacketBase ret = null;
        ByteArrayDataInput input = ByteStreams.newDataInput(packet.data);
        try{
            int packetid = input.readByte();
            PacketBase newPacket = packets.get(packetid).newInstance();
            if(newPacket != null){
                newPacket.player = player;
                newPacket.readPacket(input);
            }
            ret = newPacket;
        }catch(Exception e){
            RMLogger.severe(e, "Error while reading packet");
        }
        return ret;
    }

    public final int getID(){
        return packets.inverse().get(this.getClass());
    }

    public abstract void writePacket(DataOutput output) throws IOException;
    public abstract void readPacket(DataInput input) throws IOException;
}
