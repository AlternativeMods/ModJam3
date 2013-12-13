package jkmau5.modjam.radiomod.block;

import jkmau5.modjam.radiomod.RadioMod;
import jkmau5.modjam.radiomod.tile.TileEntityRadio;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Author: Lordmau5
 * Date: 13.12.13
 * Time: 16:02
 * You are allowed to change this code,
 * however, not to publish it without my permission.
 */
public class BlockRadio extends Block {

    public BlockRadio(int par1) {
        super(par1, Material.iron);
        setCreativeTab(RadioMod.tabRadioMod);
    }

    public boolean hasTileEntity(int metadata) {
        return true;
    }

    public TileEntity createTileEntity(World world, int metadata) {
        return new TileEntityRadio(world);
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if(world.isRemote)
            return false;

        TileEntity tempTile = world.getBlockTileEntity(x, y, z);
        if(tempTile != null)
            player.addChatMessage("Tile is there!");
        return false;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, int oldId, int oldMeta) {
        if(world.isRemote)
            return;

        TileEntity tempTile = world.getBlockTileEntity(x, y, z);
        if(tempTile == null || !(tempTile instanceof TileEntityRadio))
            return;

        RadioMod.radioWorldHandler.removeRadioTile((TileEntityRadio)tempTile);
    }
}