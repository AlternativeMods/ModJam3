package jkmau5.modjam.radiomod.block;

import jkmau5.modjam.radiomod.RadioMod;
import jkmau5.modjam.radiomod.tile.TileEntityAntenna;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockAntenna extends Block {

    public BlockAntenna(){
        super(Material.field_151573_f);
        this.func_149711_c(1.25F);
        this.func_149752_b(7.0F);
        this.func_149663_c("radioMod.blockAntenna");
        this.func_149647_a(RadioMod.tabRadioMod);
        this.func_149713_g(0);

        this.field_149759_B = 0.3;
        this.field_149755_E = 0.8;
        this.field_149754_D = 0.3;
        this.field_149757_G = 0.8;
    }

    @Override
    public boolean hasTileEntity(int metadata){
        return metadata == 0;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata){
        if(metadata == 1) return null;
        return new TileEntityAntenna();
    }

    @Override
    public boolean func_149662_c(){
        return false;
    }

    @Override
    public boolean func_149686_d(){
        return false;
    }

    @Override
    public boolean func_149718_j(World world, int x, int y, int z){
        Block below = world.func_147439_a(x, y - 1, z);
        Block above = world.func_147439_a(x, y + 1, z);
        return !world.func_147437_c(x, y - 1, z) && (below == this || below.isSideSolid(world, x, y - 1, z, ForgeDirection.UP)) && (above == this || world.func_147437_c(x, y + 1, z));
    }

    @Override
    public boolean func_149742_c(World world, int x, int y, int z){
        return world.func_147437_c(x, y, z) && this.func_149718_j(world, x, y, z);
    }

    @Override
    public void func_149681_a(World world, int x, int y, int z, int meta, EntityPlayer par6EntityPlayer){
        super.func_149681_a(world, x, y, z, meta, par6EntityPlayer);
        if(meta == 0){
            world.func_147468_f(x, y + 1, z);
        }else if(meta == 1){
            world.func_147468_f(x, y - 1, z);
            if(!par6EntityPlayer.capabilities.isCreativeMode) this.func_149697_b(world, x, y - 1, z, 0, 0);
        }
    }

    @Override
    public void func_149695_a(World world, int x, int y, int z, Block block){
        if(world.getBlockMetadata(x, y, z) == 0 && !this.func_149718_j(world, x, y, z)){
            if(world.getBlockMetadata(x, y, z) == 0) this.func_149697_b(world, x, y, z, 0, 0);
            world.func_147468_f(x, y, z);
            if(world.func_147439_a(x, y + 1, z) == this && world.getBlockMetadata(x, y + 1, z) == 1){
                world.func_147468_f(x, y + 1, z);
            }
        }
    }

    @Override
    public void func_149689_a(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack){
        super.func_149689_a(par1World, par2, par3, par4, par5EntityLivingBase, par6ItemStack);
        if(par1World.getBlockMetadata(par2, par3, par4) == 0){
            float newYaw = par5EntityLivingBase.rotationYaw - 90;
            if(newYaw < 0) newYaw += 360;
            if(newYaw >= 360) newYaw -= 360;
            ((TileEntityAntenna) par1World.func_147438_o(par2, par3, par4)).yaw = newYaw;
        }
    }

    @Override
    public void func_149726_b(World world, int x, int y, int z){
        super.func_149726_b(world, x, y, z);
        if(world.getBlockMetadata(x, y, z) == 0 && world.func_147437_c(x, y + 1, z)){
            world.func_147465_d(x, y + 1, z, this, 1, 3);
        }
    }

    //FIXME: This method seems to have disappeared from the 1.7 srg list
    /*@Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z){
        int meta = world.getBlockMetadata(x, y, z);
        if(meta == 0){
            return AxisAlignedBB.getAABBPool().getAABB(x + this.field_149759_B, y, z + this.field_149754_D, x + this.field_149755_E, y + 2, z + this.field_149757_G);
        }else if(meta == 1){
            return AxisAlignedBB.getAABBPool().getAABB(x + this.field_149759_B, y - 1, z + this.field_149754_D, x + this.field_149755_E, y + 1, z + this.field_149757_G);
        }
        return super.getSelectedBoundingBoxFromPool(world, x, y, z);
    }*/

    @Override
    public int func_149645_b(){
        return -1;
    }
}
