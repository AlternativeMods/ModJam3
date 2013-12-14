package jkmau5.modjam.radiomod.client.render;

import jkmau5.modjam.radiomod.client.ProxyClient;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

/**
 * No description given
 *
 * @author jk-5
 */
public class ItemRendererAntenna implements IItemRenderer {

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type){
        return item != null && type != ItemRenderType.FIRST_PERSON_MAP;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper){
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data){
        GL11.glPushMatrix();
        switch(type){
            case ENTITY:
                GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
                ProxyClient.antennaRenderer.doRender(180, 0, 0, 0);
                break;
            case INVENTORY:
                GL11.glScalef(0.65f, 0.65f, 0.65f);
                GL11.glTranslatef(-0.25f, -0.8f, 0);
                ProxyClient.antennaRenderer.doRender(180, 0, 0, 0);
                break;
            case EQUIPPED:
                GL11.glRotatef(67, 0, 0, 1);
                GL11.glRotatef(-37, 1, 0, 0);
                GL11.glTranslatef(0.35f, -1.8f, -0.10f);
                ProxyClient.antennaRenderer.doRender(0, 0, 0, 0);
                break;
            case EQUIPPED_FIRST_PERSON:
                GL11.glRotatef(10, 1, 0, 0.4f);
                GL11.glScalef(0.65f, 0.65f, 0.65f);
                GL11.glTranslatef(-0.4f, 0.50f, 0.35f);
                ProxyClient.antennaRenderer.doRender(140, 0, 0, 0);
                break;
        }
        GL11.glPopMatrix();
    }
}
