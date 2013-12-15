package jkmau5.modjam.radiomod.block;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import jkmau5.modjam.radiomod.RadioMod;
import jkmau5.modjam.radiomod.gui.EnumGui;
import jkmau5.modjam.radiomod.gui.GuiOpener;
import jkmau5.modjam.radiomod.network.PacketMediaPlayerData;
import jkmau5.modjam.radiomod.tile.TileEntityRadio;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**
 * Author: Lordmau5
 * Date: 15.12.13
 * Time: 10:56
 * You are allowed to change this code,
 * however, not to publish it without my permission.
 */
public class BlockRadio extends Block {

    private Icon top;
    private Icon front;
    private Icon bottom;

    public BlockRadio(int par1) {
        super(par1, Material.iron);
        setCreativeTab(RadioMod.tabRadioMod);
        setUnlocalizedName("radiomod.BlockRadio");
    }

    public boolean hasTileEntity(int metadata) {
        return true;
    }

    public TileEntity createTileEntity(World world, int metadata) {
        return new TileEntityRadio();
    }

    @Override
    public void registerIcons(IconRegister register){
        this.top = register.registerIcon("RadioMod:radio_top");
        this.front = register.registerIcon("RadioMod:radio_front");
        this.bottom = register.registerIcon("RadioMod:radio_bottom");
    }

    @Override
    public Icon getIcon(int side, int meta){
        if(side == 1){
            return this.top;
        }else if(side == 0){
            return this.bottom;
        }else if(meta == 2 && side == 2){
            return this.front;
        }else if(meta == 3 && side == 5){
            return this.front;
        }else if(meta == 0 && side == 3){
            return this.front;
        }else if(meta == 1 && side == 4){
            return this.front;
        }else{
            return this.blockIcon;
        }
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack){
        int meta = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
        world.setBlockMetadataWithNotify(x, y, z, meta, 2);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par7, float par8, float par9) {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if(tileEntity == null || !(tileEntity instanceof TileEntityRadio))
            return true;

        if(!world.isRemote){
            NBTTagCompound tag = new NBTTagCompound();
            ((TileEntityRadio) tileEntity).writeGuiData(tag);
            PacketDispatcher.sendPacketToPlayer(new PacketMediaPlayerData(tag).getPacket(), (Player) player);
            GuiOpener.openGuiOnClient(EnumGui.RADIO_BLOCK, player, x, y, z);
        }

        return true;
    }
}
