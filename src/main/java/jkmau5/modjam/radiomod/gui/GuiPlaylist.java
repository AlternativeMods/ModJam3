package jkmau5.modjam.radiomod.gui;

import jkmau5.modjam.radiomod.tile.TileEntityPlaylist;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

/**
 * Author: Lordmau5
 * Date: 15.12.13
 * Time: 17:15
 * You are allowed to change this code,
 * however, not to publish it without my permission.
 */
public class GuiPlaylist extends GuiScreen {

    private int xSize = 176;
    private int ySize = 166;
    private int scrollY = 0;
    private boolean isScrolling = false;
    private int mouseGrabY = 0;

    TileEntityPlaylist playlist;

    public GuiPlaylist(TileEntityPlaylist playlist) {
        this.playlist = playlist;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTick){
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        if(this.isScrolling){
            this.scrollY = mouseY - this.mouseGrabY;
        }
        if(playlist != null && playlist.getTitles() != null){
            this.scrollY = Math.min(this.scrollY, 0);
            this.scrollY = Math.max(this.scrollY, 47 - (playlist.getTitles().size() * 10 + 2));
        }

        this.drawDefaultBackground();

        ScaledResolution res = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);

        this.ySize = 116;
        if(playlist != null && playlist.getTitles() != null) {
            GL11.glPushMatrix();
            int yS = (this.height / 5);
            Gui.drawRect(x - 1, y - 1, x + this.xSize + 1, y + yS + 1, 0xFFAAAAAA);
            Gui.drawRect(x, y, x + this.xSize, y + yS, 0xFF000000);

            /*int barHeight = (availableRadios.size() - 5) / yS;
            //int barY = yS / (this.scrollY == 0 ? 1 : this.scrollY);
            int barY = this.scrollY / yS;
            Gui.drawRect(x + (this.xSize - 5), y * barY, x + this.xSize, barHeight, 0xFFFFFFFF);*/

            GL11.glScissor(x * res.getScaleFactor(), this.mc.displayHeight - yS * res.getScaleFactor() - y * res.getScaleFactor(), (this.xSize - 5) * res.getScaleFactor(), yS * res.getScaleFactor());
            GL11.glTranslated(0, this.scrollY, 0);
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            int index = 0;
            for(String title : playlist.getTitles()){
                this.fontRenderer.drawString(title, x + 3, 3 + y + index * 10, 0xFFFFFFFF);
                index++;
            }
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
            GL11.glPopMatrix();
        }else{
            this.fontRenderer.drawString("No radios found", x, y, 0xFFFFFFFF);
        }

        super.drawScreen(mouseX, mouseY, partialTick);
    }
}