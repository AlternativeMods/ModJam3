package jkmau5.modjam.radiomod.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import jkmau5.modjam.radiomod.network.PacketOpenGui;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * No description given
 *
 * @author jk-5
 */
public class GuiOpener {

    public static void openGuiCallback(EnumGui gui){

    }

    public static void openGuiCallback(EnumGui gui, int x, int y, int z){
        if(gui == EnumGui.RADIO_BLOCK) {
            World world = Minecraft.getMinecraft().thePlayer.worldObj;

            Minecraft.getMinecraft().displayGuiScreen(new GuiRadioScreen(x, y, z));
        }
    }

    public static void openGuiOnClient(EnumGui gui, EntityPlayer player){
        PacketDispatcher.sendPacketToPlayer(new PacketOpenGui(gui).getPacket(), (Player) player);
    }

    public static void openGuiOnClient(EnumGui gui, EntityPlayer player, int x, int y, int z){
        PacketDispatcher.sendPacketToPlayer(new PacketOpenGui(gui, x, y, z).getPacket(), (Player) player);
    }
}
