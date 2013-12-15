package jkmau5.modjam.radiomod.block;

import jkmau5.modjam.radiomod.tile.TileEntityPlaylist;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Author: Lordmau5
 * Date: 15.12.13
 * Time: 16:35
 * You are allowed to change this code,
 * however, not to publish it without my permission.
 */
public class BlockPlaylist extends Block {

    public BlockPlaylist(int par1) {
        super(par1, Material.iron);
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TileEntityPlaylist();
    }
}