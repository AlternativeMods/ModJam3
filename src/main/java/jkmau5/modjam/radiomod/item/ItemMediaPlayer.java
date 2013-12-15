package jkmau5.modjam.radiomod.item;

import jkmau5.modjam.radiomod.RadioMod;
import jkmau5.modjam.radiomod.gui.EnumGui;
import jkmau5.modjam.radiomod.gui.GuiOpener;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Author: Lordmau5
 * Date: 13.12.13
 * Time: 16:31
 * You are allowed to change this code,
 * however, not to publish it without my permission.
 */
public class ItemMediaPlayer extends Item {

    public ItemMediaPlayer(int par1) {
        super(par1);
        setCreativeTab(RadioMod.tabRadioMod);
    }

    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        GuiOpener.openGuiOnClient(EnumGui.MEDIA_PLAYER, player);
        return itemStack;
    }
}