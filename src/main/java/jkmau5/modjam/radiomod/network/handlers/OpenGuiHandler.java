package jkmau5.modjam.radiomod.network.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import jkmau5.modjam.radiomod.gui.RMGui;
import jkmau5.modjam.radiomod.network.RMPacket;
import jkmau5.modjam.radiomod.tile.IGuiTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

/**
 * No description given
 *
 * @author jk-5
 */
public class OpenGuiHandler extends SimpleChannelInboundHandler<RMPacket.GuiOpen> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RMPacket.GuiOpen msg) throws Exception{
        TileEntity tile = Minecraft.getMinecraft().theWorld.func_147438_o(msg.x, msg.y, msg.z);
        if(tile != null && tile instanceof IGuiTileEntity){
            RMGui gui = ((IGuiTileEntity) tile).getGui();
            if(gui == null) return;
            gui = gui.readGuiData(msg.data);
            if(gui == null) return;
            Minecraft.getMinecraft().func_147108_a(gui);
        }
    }
}
