package jkmau5.modjam.radiomod.network.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import jkmau5.modjam.radiomod.network.NetworkHandler;
import jkmau5.modjam.radiomod.network.RMPacket;
import jkmau5.modjam.radiomod.tile.IGuiReturnHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;

/**
 * No description given
 *
 * @author jk-5
 */
public class GuiReturnDataHandler extends SimpleChannelInboundHandler<RMPacket.GuiReturnDataPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RMPacket.GuiReturnDataPacket msg) throws Exception{
        EntityPlayerMP player = NetworkHandler.getPlayer(ctx);
        TileEntity tile = player.worldObj.func_147438_o(msg.x, msg.y, msg.z);
        if(tile != null && tile instanceof IGuiReturnHandler){
            ((IGuiReturnHandler) tile).readGuiReturnData(msg.data);
        }
    }
}
