package jkmau5.modjam.radiomod.item;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import jkmau5.modjam.radiomod.gui.EnumGui;
import jkmau5.modjam.radiomod.gui.GuiOpener;
import jkmau5.modjam.radiomod.network.PacketMediaPlayerData;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import java.util.List;

public class ItemMediaPlayer extends Item {

    private Icon icon;

    public ItemMediaPlayer(int par1){
        super(par1);
        //setCreativeTab(RadioMod.tabRadioMod);
        this.setUnlocalizedName("radioMod.mediaPlayer");
    }

    @Override
    public void registerIcons(IconRegister register){
        this.icon = register.registerIcon("RadioMod:mediaplayer");
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
        if(tag.hasKey("station")){
            list.add("Radio station: " + tag.getString("station"));
        }else{
            list.add("Use right click to select a radio station");
        }
    }
}
