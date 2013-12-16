package jkmau5.modjam.radiomod.item;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import jkmau5.modjam.radiomod.RadioMod;
import jkmau5.modjam.radiomod.gui.EnumGui;
import jkmau5.modjam.radiomod.gui.GuiOpener;
import jkmau5.modjam.radiomod.network.PacketMediaPlayerData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.List;

/**
 * Author: Lordmau5
 * Date: 13.12.13
 * Time: 16:31
 * You are allowed to change this code,
 * however, not to publish it without my permission.
 */
public class ItemMediaPlayer extends Item {

    public ItemMediaPlayer(int par1){
        super(par1);
        setCreativeTab(RadioMod.tabRadioMod);
        this.setTextureName("RadioMod:mediaplayer");
    }

    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player){
        if(itemStack.getTagCompound() == null) itemStack.setTagCompound(new NBTTagCompound());
        PacketDispatcher.sendPacketToPlayer(new PacketMediaPlayerData(itemStack.getTagCompound()).getPacket(), (Player) player);
        GuiOpener.openGuiOnClient(EnumGui.MEDIA_PLAYER, player);
        return itemStack;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advancedToolTips){
        if(stack.getTagCompound() == null) stack.setTagCompound(new NBTTagCompound());
        NBTTagCompound tag = stack.getTagCompound();
        String name = tag.getString("station");
        if(name != null){
            list.add("Radio station: " + name);
        }
    }
}
