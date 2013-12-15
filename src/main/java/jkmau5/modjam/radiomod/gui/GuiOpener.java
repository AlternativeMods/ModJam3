package jkmau5.modjam.radiomod.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import jkmau5.modjam.radiomod.network.PacketOpenGui;
import jkmau5.modjam.radiomod.tile.TileEntityBroadcaster;
import jkmau5.modjam.radiomod.tile.TileEntityRadio;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * No description given
 *
 * @author jk-5
 */
public class GuiOpener {

    public static void openGuiCallback(EnumGui gui){
        if(gui == EnumGui.MEDIA_PLAYER){
            Minecraft.getMinecraft().displayGuiScreen(new GuiMediaPlayer());
        }
    }

    public static void openGuiCallback(EnumGui gui, int x, int y, int z){
        if(gui == EnumGui.BROADCASTER_BLOCK){
            World world = Minecraft.getMinecraft().thePlayer.worldObj;
            TileEntity tempTile = world.getBlockTileEntity(x, y, z);
            if(tempTile == null || !(tempTile instanceof TileEntityBroadcaster))
                return;
            TileEntityBroadcaster radio = (TileEntityBroadcaster) tempTile;
            Minecraft.getMinecraft().displayGuiScreen(new GuiRadioScreen(x, y, z, radio.getRadioName()));
        }
        else if(gui == EnumGui.RADIO_BLOCK){
            World world = Minecraft.getMinecraft().theWorld;
            TileEntity tempTile = world.getBlockTileEntity(x, y, z);
            if(tempTile == null || !(tempTile instanceof TileEntityRadio))
                return;

            Minecraft.getMinecraft().displayGuiScreen(new GuiMediaPlayer((TileEntityRadio) tempTile));
        }
    }

    public static void openGuiOnClient(EnumGui gui, EntityPlayer player){
        PacketDispatcher.sendPacketToPlayer(new PacketOpenGui(gui).getPacket(), (Player) player);
    }

    public static void openGuiOnClient(EnumGui gui, EntityPlayer player, int x, int y, int z){
        PacketDispatcher.sendPacketToPlayer(new PacketOpenGui(gui, x, y, z).getPacket(), (Player) player);
    }
}
