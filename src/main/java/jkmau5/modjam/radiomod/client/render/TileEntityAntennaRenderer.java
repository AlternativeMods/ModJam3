package jkmau5.modjam.radiomod.client.render;

import jkmau5.modjam.radiomod.client.ProxyClient;
import jkmau5.modjam.radiomod.tile.TileEntityAntenna;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TileEntityAntennaRenderer extends TileEntitySpecialRenderer {

    private final ResourceLocation texture = new ResourceLocation("radiomod", "models/Antenna.png");

    @Override
    public void func_147500_a(TileEntity tileentity, double x, double y, double z, float f){
        float yaw = ((TileEntityAntenna) tileentity).yaw;
        this.doRender(yaw, x, y, z);
    }

    public void doRender(float yaw, double x, double y, double z){
        GL11.glPushMatrix();
        this.func_147499_a(this.texture);
        GL11.glTranslated(x + 0.5d, y - 0.3d, z + 0.5d);
        GL11.glRotatef(180, 1, 0, 0);
        GL11.glRotatef(yaw, 0, 1, 0);
        GL11.glScalef(0.07f, 0.07f, 0.07f);
        ProxyClient.modelAntenna.renderAll();
        GL11.glPopMatrix();
    }
}
