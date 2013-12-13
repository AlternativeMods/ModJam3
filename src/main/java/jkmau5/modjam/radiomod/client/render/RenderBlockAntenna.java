package jkmau5.modjam.radiomod.client.render;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import jkmau5.modjam.radiomod.client.ProxyClient;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

/**
 * No description given
 *
 * @author jk-5
 */
public class RenderBlockAntenna implements ISimpleBlockRenderingHandler {

    @Override
    public void renderInventoryBlock(Block block, int i, int i2, RenderBlocks renderBlocks){

    }

    @Override
    public boolean renderWorldBlock(IBlockAccess iBlockAccess, int i, int i2, int i3, Block block, int i4, RenderBlocks renderBlocks){
        return false;
    }

    @Override
    public boolean shouldRender3DInInventory(){
        return false;
    }

    @Override
    public int getRenderId(){
        return ProxyClient.renderID_Antenna;
    }
}
