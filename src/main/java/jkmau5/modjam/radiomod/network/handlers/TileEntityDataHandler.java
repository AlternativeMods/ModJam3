package jkmau5.modjam.radiomod.network.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import jkmau5.modjam.radiomod.network.RMPacket;
import jkmau5.modjam.radiomod.tile.ISynchronizedTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

/**
 * No description given
 *
 * @author jk-5
 */
public class TileEntityDataHandler extends SimpleChannelInboundHandler<RMPacket.TileEntityData> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RMPacket.TileEntityData msg) throws Exception{
        TileEntity tile = Minecraft.getMinecraft().theWorld.func_147438_o(msg.x, msg.y, msg.z);
        if(tile != null && tile instanceof ISynchronizedTileEntity){
            ((ISynchronizedTileEntity) tile).readData(msg.data);
        }
    }
}
