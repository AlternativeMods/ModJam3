package jkmau5.modjam.radiomod.network;

import cpw.mods.fml.common.network.FMLIndexedMessageToMessageCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * No description given
 *
 * @author jk-5
 */
public class PacketCodec extends FMLIndexedMessageToMessageCodec<PacketBase> {

    public PacketCodec(){
        this.addDiscriminator(0, PacketOpenGui.class);
        this.addDiscriminator(1, PacketUpdateRadioName.class);
        //this.addDiscriminator(2, PacketRequestRadioNames.class);
        this.addDiscriminator(3, PacketMediaPlayerData.class);
        this.addDiscriminator(4, PacketRemovePlaylistTitle.class);
        //this.addDiscriminator(5, PacketSelectRadio.class);
        this.addDiscriminator(6, PacketPlayBroadcastedSound.class);
        this.addDiscriminator(7, PacketPlaySound.class);
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, PacketBase msg, ByteBuf target) throws Exception{
        msg.encode(target);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf source, PacketBase msg){
        msg.decode(source);
    }
}
