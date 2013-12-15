package jkmau5.modjam.radiomod.block;

import jkmau5.modjam.radiomod.tile.TileEntityRadioNetwork;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * No description given
 *
 * @author jk-5
 */
public abstract class BlockRadioNetwork extends Block {

    protected BlockRadioNetwork(int id, Material material){
        super(id, material);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int neighborID){
        super.onNeighborBlockChange(world, x, y, z, neighborID);
        TileEntityRadioNetwork cable = (TileEntityRadioNetwork) world.getBlockTileEntity(x, y, z);
        if(cable == null) return;
        cable.onNeighborBlockChange();
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase ent, ItemStack is){
        super.onBlockPlacedBy(world, x, y, z, ent, is);
        TileEntityRadioNetwork cable = (TileEntityRadioNetwork) world.getBlockTileEntity(x, y, z);
        if(cable == null) return;
        cable.onBlockPlacedBy(ent, is);
    }
}
