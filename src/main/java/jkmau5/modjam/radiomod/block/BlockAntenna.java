package jkmau5.modjam.radiomod.block;

import jkmau5.modjam.radiomod.RadioMod;
import jkmau5.modjam.radiomod.tile.TileEntityAntenna;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockAntenna extends Block {

    public BlockAntenna(int par1){
        super(par1, Material.iron);
        this.setHardness(1.25F);
        this.setResistance(7.0F);
        this.setUnlocalizedName("radioMod.blockAntenna");
        this.setCreativeTab(RadioMod.tabRadioMod);
        this.setLightOpacity(0);

        this.minX = 0.3;
        this.maxX = 0.8;
        this.minZ = 0.3;
        this.maxZ = 0.8;
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
    public boolean isOpaqueCube(){
        return false;
    }

    @Override
    public boolean renderAsNormalBlock(){
        return false;
    }

    @Override
    public boolean canBlockStay(World par1World, int x, int y, int z){
        Block below = Block.blocksList[par1World.getBlockId(x, y - 1, z)];
        Block above = Block.blocksList[par1World.getBlockId(x, y + 1, z)];
        return !par1World.isAirBlock(x, y - 1, z) && (below == this || below.isBlockSolidOnSide(par1World, x, y - 1, z, ForgeDirection.UP)) && (above == this || par1World.isAirBlock(x, y + 1, z));
    }

    @Override
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4){
        return par1World.isAirBlock(par2, par3, par4) && this.canBlockStay(par1World, par2, par3, par4);
    }

    @Override
    public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer par6EntityPlayer){
        super.onBlockHarvested(world, x, y, z, meta, par6EntityPlayer);
        if(meta == 0){
            world.setBlockToAir(x, y + 1, z);
        }else if(meta == 1){
            world.setBlockToAir(x, y - 1, z);
            if(!par6EntityPlayer.capabilities.isCreativeMode) this.dropBlockAsItem(world, x, y - 1, z, 0, 0);
        }
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int id){
        if(world.getBlockMetadata(x, y, z) == 0 && !this.canBlockStay(world, x, y, z)){
            if(world.getBlockMetadata(x, y, z) == 0) this.dropBlockAsItem(world, x, y, z, 0, 0);
            world.setBlockToAir(x, y, z);
            if(world.getBlockId(x, y + 1, z) == this.blockID && world.getBlockMetadata(x, y + 1, z) == 1){
                world.setBlockToAir(x, y + 1, z);
            }
        }
    }

    @Override
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack){
        super.onBlockPlacedBy(par1World, par2, par3, par4, par5EntityLivingBase, par6ItemStack);
        if(par1World.getBlockMetadata(par2, par3, par4) == 0){
            float newYaw = par5EntityLivingBase.rotationYaw - 90;
            if(newYaw < 0) newYaw += 360;
            if(newYaw >= 360) newYaw -= 360;
            ((TileEntityAntenna) par1World.getBlockTileEntity(par2, par3, par4)).yaw = newYaw;
        }
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z){
        super.onBlockAdded(world, x, y, z);
        if(world.getBlockMetadata(x, y, z) == 0 && world.isAirBlock(x, y + 1, z)){
            world.setBlock(x, y + 1, z, this.blockID, 1, 3);
        }
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z){
        int meta = world.getBlockMetadata(x, y, z);
        if(meta == 0){
            return AxisAlignedBB.getAABBPool().getAABB(x + this.minX, y, z + this.minZ, x + this.maxX, y + 2, z + this.maxZ);
        }else if(meta == 1){
            return AxisAlignedBB.getAABBPool().getAABB(x + this.minX, y - 1, z + this.minZ, x + this.maxX, y + 1, z + this.maxZ);
        }
        return super.getSelectedBoundingBoxFromPool(world, x, y, z);
    }

    @Override
    public int getRenderType(){
        return -1;
    }
}
