package jkmau5.modjam.radiomod.item;

import jkmau5.modjam.radiomod.RadioMod;
import jkmau5.modjam.radiomod.tile.TileEntityRadioNetwork;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.world.World;

import java.util.List;

/**
 * No description given
 *
 * @author jk-5
 */
public class ItemLinkCard extends Item {

    public ItemLinkCard(int id){
        super(id);
        this.setMaxStackSize(1);
        this.setCreativeTab(RadioMod.tabRadioMod);
        this.setUnlocalizedName("radioMod.linkCard");
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ){
        if(world.isRemote) return false;
        TileEntity tile = world.getBlockTileEntity(x, y, z);
        if(tile == null || !(tile instanceof TileEntityRadioNetwork)) return true;
        TileEntityRadioNetwork radioTile = (TileEntityRadioNetwork) tile;
        NBTTagCompound tag = stack.getTagCompound();
        if(tag == null){
            stack.setTagCompound(new NBTTagCompound());
            stack.getTagCompound().setBoolean("linked", false);
        }
        tag = stack.getTagCompound();
        boolean linked = tag.getBoolean("linked");
        if(linked){
            TileEntity newTile = world.getBlockTileEntity(tag.getInteger("linkedX"), tag.getInteger("linkedY"), tag.getInteger("linkedZ"));
            if(newTile == null || !(newTile instanceof TileEntityRadioNetwork)){
                player.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("radioMod.linkCard.invalid"));
            }else{
                radioTile.linkToTile((TileEntityRadioNetwork) newTile);
            }
        }else{
            tag.setBoolean("linked", true);
            tag.setInteger("linkedX", x);
            tag.setInteger("linkedY", y);
            tag.setInteger("linkedZ", z);
        }
        return true;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advancedTooltips){
        NBTTagCompound tag = stack.getTagCompound();
        if(tag == null){
            stack.setTagCompound(new NBTTagCompound());
            stack.getTagCompound().setBoolean("linked", false);
        }
        tag = stack.getTagCompound();
        boolean linked = tag.getBoolean("linked");
        if(linked){
            list.add("Linked to: " + tag.getInteger("linkedX") + ", " + tag.getInteger("linkedY") + ", " + tag.getInteger("linkedZ"));
        }else{
            list.add("Not linked");
        }
    }
}
