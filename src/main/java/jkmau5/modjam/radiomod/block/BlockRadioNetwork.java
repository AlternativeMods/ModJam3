package jkmau5.modjam.radiomod.block;

import jkmau5.modjam.radiomod.tile.TileEntityRadioNetwork;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
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
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ){
        TileEntityRadioNetwork tile = (TileEntityRadioNetwork) world.getBlockTileEntity(x, y, z);
        if(tile == null) return super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
        if(!world.isRemote) player.addChatMessage(tile.network.toString());
        return super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
    }
}
