package jkmau5.modjam.radiomod.block;

import jkmau5.modjam.radiomod.client.ProxyClient;
import jkmau5.modjam.radiomod.tile.TileEntityCable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Author: Lordmau5
 * Date: 14.12.13
 * Time: 14:52
 * You are allowed to change this code,
 * however, not to publish it without my permission.
 */
public class BlockCable extends Block {

    public BlockCable(int par1) {
        super(par1, Material.iron);
    }

    public boolean hasTileEntity(int metadata) {
        return true;
    }

    public TileEntity createTileEntity(World world, int metadata) {
        return new TileEntityCable();
    }

    @Override
    public int getRenderType(){
        return ProxyClient.renderID_Cable;
    }

    public void onNeighborTileChange(World world, int x, int y, int z, int tileX, int tileY, int tileZ) {
        TileEntityCable cable = (TileEntityCable) world.getBlockTileEntity(x, y, z);
        if(cable == null)
            return;

        if(world.isRemote)
            cable.onNeighborTileChange();
    }
}