package jkmau5.modjam.radiomod.network;

import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.EnumMap;

/**
 * No description given
 *
 * @author jk-5
 */
public class NetworkHandler {

    private static EnumMap<Side, FMLEmbeddedChannel> channels;

    public static void registerChannels(Side side){
        channels = NetworkRegistry.INSTANCE.newChannel("RadioMod", new PacketCodec());

    }

    @SideOnly(Side.CLIENT)
    private static void registerClientHandlers(){

    }
}
