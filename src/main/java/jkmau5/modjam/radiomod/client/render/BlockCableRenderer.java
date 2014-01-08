package jkmau5.modjam.radiomod.client.render;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import jkmau5.modjam.radiomod.client.ProxyClient;
import jkmau5.modjam.radiomod.tile.TileEntityAntenna;
import jkmau5.modjam.radiomod.tile.TileEntityBroadcaster;
import jkmau5.modjam.radiomod.tile.TileEntityCable;
import jkmau5.modjam.radiomod.tile.TileEntityPlaylist;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

/**
 * Created by matthias on 1/7/14.
 *
 * The old cable renderer, updated to work with the current layout
 */
public class BlockCableRenderer implements ISimpleBlockRenderingHandler {

    @Override
    public void renderInventoryBlock(Block block, int i, int i2, RenderBlocks renderBlocks) {

    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer){
        float min = 0.375F;
        float max = 0.625F;
        renderer.setRenderBounds(min, min, min, max, max, max);
        renderer.renderAllFaces = true;
        renderer.renderStandardBlock(block, x, y, z);

        TileEntity tile = world.getBlockTileEntity(x, y, z);
        if(!isValid(tile))
            return false;

        TileEntityCable cable = (TileEntityCable) tile;
        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS){
            if (isValidTileAtPosition(cable, cable.getWorldObj(), x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ)){
                if (dir == ForgeDirection.UP)
                    renderer.setRenderBounds(min, max, min, max, 1, max);
                else if (dir == ForgeDirection.DOWN)
                    renderer.setRenderBounds(min, 0, min, max, min, max);
                else if(dir == ForgeDirection.NORTH)
                    renderer.setRenderBounds(min, min, 0, max, max, min);
                else if(dir == ForgeDirection.SOUTH)
                    renderer.setRenderBounds(min, min, max, max, max, 1);
                else if(dir == ForgeDirection.EAST)
                    renderer.setRenderBounds(max, min, min, 1, max, max);
                else if(dir == ForgeDirection.WEST)
                    renderer.setRenderBounds(0, min, min, min, max, max);

                renderer.renderStandardBlock(block, x, y, z);
            }
        }

        return true;
    }

    public boolean isValidTileAtPosition(TileEntityCable cable, World world, int x, int y, int z){
        TileEntity tile = world.getBlockTileEntity(x, y, z);
        if (tile == null)
            return false;
        if (tile instanceof TileEntityCable){
            if(((TileEntityCable) tile).getCableID() != cable.getCableID() && ((TileEntityCable) tile).getCableID() != 0) return false;
            return true;}
        if (tile instanceof TileEntityPlaylist){
            if (((TileEntityPlaylist) tile).getCableID() != cable.getCableID() && ((TileEntityPlaylist) tile).getCableID() != 0) return false;
            return true;}
        if (tile instanceof TileEntityAntenna){
            if(((TileEntityAntenna) tile).getCableID() != cable.getCableID() && ((TileEntityAntenna) tile).getCableID() != 0) return false;
            return true;}
        if (tile instanceof TileEntityBroadcaster){
            TileEntityBroadcaster broadcaster = (TileEntityBroadcaster) tile;
            if (broadcaster.getNetwork().getID() == cable.getCableID()) return true;
        }
        return false;
    }

    private boolean isValid(TileEntity tile) {
        return tile != null && tile instanceof TileEntityCable;
    }

    @Override
    public boolean shouldRender3DInInventory(){
        return false;
    }

    @Override
    public int getRenderId(){
        return ProxyClient.renderID_Cable;
    }
}
