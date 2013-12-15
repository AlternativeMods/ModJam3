package jkmau5.modjam.radiomod.network;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

/**
 * No description given
 *
 * @author jk-5
 */
public class PacketHandler implements IPacketHandler {

    @Override
    public void onPacketData(INetworkManager iNetworkManager, Packet250CustomPayload packet250CustomPayload, Player player){
        PacketBase.readPacket(packet250CustomPayload, (EntityPlayer) player);
    }
}
