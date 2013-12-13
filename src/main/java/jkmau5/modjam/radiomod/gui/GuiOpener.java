package jkmau5.modjam.radiomod.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import jkmau5.modjam.radiomod.network.PacketOpenGui;
import net.minecraft.entity.player.EntityPlayer;

/**
 * No description given
 *
 * @author jk-5
 */
public class GuiOpener {

    public static void openGuiCallback(EnumGui gui){

    }

    public static void openGuiOnClient(EnumGui gui, EntityPlayer player){
        PacketDispatcher.sendPacketToPlayer(new PacketOpenGui(gui).getPacket(), (Player) player);
    }
}
