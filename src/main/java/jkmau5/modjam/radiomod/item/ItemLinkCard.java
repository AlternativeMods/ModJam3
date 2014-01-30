package jkmau5.modjam.radiomod.item;

import jkmau5.modjam.radiomod.RadioMod;
import jkmau5.modjam.radiomod.tile.TileEntityRadioNetwork;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

public class ItemLinkCard extends Item {

    private IIcon inactive;
    private IIcon active;

    public ItemLinkCard(){
        super();
        this.setMaxStackSize(1);
        this.setCreativeTab(RadioMod.tabRadioMod);
        this.setUnlocalizedName("radioMod.linkCard");
    }

    @Override
    public void registerIcons(IIconRegister register){
        this.inactive = register.registerIcon("RadioMod:linkcard_inactive");
        this.active = register.registerIcon("RadioMod:linkcard_active");
    }

    @Override
    public IIcon getIconIndex(ItemStack stack){
        NBTTagCompound tag = stack.getTagCompound();
        if(tag == null){
            stack.setTagCompound(new NBTTagCompound());
            stack.getTagCompound().setBoolean("linked", false);
        }
        tag = stack.getTagCompound();
        boolean linked = tag.getBoolean("linked");
        if(linked){
            return this.active;
        }else{
            return this.inactive;
        }
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ){
        if(world.isRemote){
            if(world.func_147439_a(x, y, z) == RadioMod.instance.blockAntenna){
                player.swingItem();
            }
            return false;
        }
        if(world.func_147439_a(x, y, z) == RadioMod.instance.blockAntenna && world.getBlockMetadata(x, y, z) == 1){
            y--;
        }
        TileEntity tile = world.func_147438_o(x, y, z);
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
            TileEntity newTile = world.func_147438_o(tag.getInteger("linkedX"), tag.getInteger("linkedY"), tag.getInteger("linkedZ"));
            if(newTile == null || !(newTile instanceof TileEntityRadioNetwork)){
                player.func_146105_b(new ChatComponentTranslation("radioMod.linkCard.invalid"));
            }else{
                if(radioTile.linkToTile((TileEntityRadioNetwork) newTile)){
                    player.destroyCurrentEquippedItem();
                }
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
