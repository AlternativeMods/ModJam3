package jkmau5.modjam.radiomod.client.render;

import jkmau5.modjam.radiomod.client.ProxyClient;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

/**
 * No description given
 *
 * @author jk-5
 */
public class TileEntityAntennaRenderer extends TileEntitySpecialRenderer {

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float f){
        GL11.glPushMatrix();

        ProxyClient.modelAntenna.renderAll();
        GL11.glPopMatrix();
    }
}
