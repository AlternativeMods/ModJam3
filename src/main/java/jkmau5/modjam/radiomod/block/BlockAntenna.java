package jkmau5.modjam.radiomod.block;

import jkmau5.modjam.radiomod.RadioMod;
import jkmau5.modjam.radiomod.tile.TileEntityAntenna;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * No description given
 *
 * @author jk-5
 */
public class BlockAntenna extends Block {

    public BlockAntenna(int par1){
        super(par1, Material.iron);

        this.setUnlocalizedName("radioMod.blockAntenna");
        this.setCreativeTab(RadioMod.tabRadioMod);
        //this.maxY = 2;
    }

    @Override
    public boolean hasTileEntity(int metadata){
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata){
        return new TileEntityAntenna();
    }

    @Override
    public boolean isOpaqueCube(){
        return false;
    }

    @Override
    public boolean canBlockStay(World par1World, int par2, int par3, int par4){
        return !par1World.isAirBlock(par2, par3 - 1, par4);
    }

    @Override
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack){
        super.onBlockPlacedBy(par1World, par2, par3, par4, par5EntityLivingBase, par6ItemStack);
        float newYaw = par5EntityLivingBase.rotationYawHead - 90;
        if(newYaw < 0) newYaw += 360;
        if(newYaw >= 360) newYaw -= 360;
        ((TileEntityAntenna) par1World.getBlockTileEntity(par2, par3, par4)).yaw = newYaw;
    }

    @Override
    public int getRenderType(){
        return -1;
    }
}
