package jkmau5.modjam.radiomod.client.render;

import jkmau5.modjam.radiomod.RadioMod;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

/**
 * Created by matthias on 1/7/14.
 */
public class ItemRendererCable implements IItemRenderer {
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
        GL11.glScalef(1.2f, 1.2f, 1.2f);
        switch(type){
            case ENTITY:
                GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
                break;
            case INVENTORY:
                GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
                break;
            case EQUIPPED:
                GL11.glTranslatef(0.1f, 0.5f, 0.35f);
                break;
            case EQUIPPED_FIRST_PERSON:
                GL11.glTranslatef(-0.5f, 0.55f, 0.05f);
                break;
            }
        this.doRenderCable((RenderBlocks) data[0]);
        GL11.glPopMatrix();
        }

    public void doRenderCable(RenderBlocks renderBlocks){
        float min = 0.4F;
        float max = 0.6F;
        Tessellator tessellator = Tessellator.instance;
        Block block = RadioMod.instance.blockCable;
        Icon icon = block.getIcon(0, 0);
        renderBlocks.setRenderBounds(min, 0, min, max, 1, max);
        renderBlocks.renderAllFaces = true;
        tessellator.startDrawingQuads();
        tessellator.setNormal(0, -1, 0);
        renderBlocks.renderFaceYNeg(block, 0, 0, 0, icon);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0, 1, 0);
        renderBlocks.renderFaceYPos(block, 0, 0, 0, icon);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0, 0, -1);
        renderBlocks.renderFaceZNeg(block, 0, 0, 0, icon);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0, 0, 1);
        renderBlocks.renderFaceZPos(block, 0, 0, 0, icon);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1, 0, 0);
        renderBlocks.renderFaceXNeg(block, 0, 0, 0, icon);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1, 0, 0);
        renderBlocks.renderFaceXPos(block, 0, 0, 0, icon);
        tessellator.draw();
    }
}
