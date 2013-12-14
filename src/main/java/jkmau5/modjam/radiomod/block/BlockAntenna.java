package jkmau5.modjam.radiomod.block;

import jkmau5.modjam.radiomod.tile.TileEntityAntenna;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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
    public int getRenderType(){
        return -1;
    }
}
