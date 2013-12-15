package jkmau5.modjam.radiomod.gui;

import jkmau5.modjam.radiomod.Constants;
import jkmau5.modjam.radiomod.tile.TileEntityPlaylist;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Lordmau5
 * Date: 15.12.13
 * Time: 17:15
 * You are allowed to change this code,
 * however, not to publish it without my permission.
 */
public class GuiPlaylist extends GuiScreen {

    private static int actualHeight = 0;
    private int xSize = 176;
    private int ySize = 166;
    private int scrollY = 0;
    private boolean isScrolling = false;
    private int mouseGrabY = 0;
    private String selectedIndex = "";
    private static List<String> titleList = new ArrayList<String>();
    private static Map<String, Integer> titleCoordinates = new HashMap<String, Integer>();

    public static TileEntityPlaylist playlist;

    public GuiPlaylist(TileEntityPlaylist playlist) {
        this.playlist = playlist;

        titleList = new ArrayList<String>();
        titleCoordinates = new HashMap<String, Integer>();
    }

    @Override
    public void initGui() {
        super.initGui();

        this.actualHeight = height;
        initTitles();
    }

    public static void initTitles() {
        if(playlist.getTitles() != null && !playlist.getTitles().isEmpty()) {
            List<String> titles = playlist.getTitles();
            for(int i=0; i<titles.size(); i++) {
                String realTitle = Constants.getRealRecordTitle(titles.get(i));

                int fromPos = 2 + ((actualHeight - 116) / 2) + (i * 10);
                titleList.add(realTitle);
                titleCoordinates.put(realTitle, fromPos);
            }
        }
    }

    public static void updateTitles() {
        titleList = new ArrayList<String>();
        titleCoordinates = new HashMap<String, Integer>();

        initTitles();
    }

    public String getIndexId(int yClick) {
        for(String title : titleList) {
            int titlePos = titleCoordinates.get(title);

            if(yClick >= titlePos && yClick < titlePos + 10)
                return title;
        }
        return "";
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTick){
        int x = (this.width - this.xSize) / 2;
        int y = (this.actualHeight - this.ySize) / 2;
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
        if(playlist != null && playlist.getTitles() != null && !playlist.getTitles().isEmpty()) {
            GL11.glPushMatrix();
            int yS = (this.actualHeight / 5);
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
                String realRecord = Constants.getRealRecordTitle(title);
                if(selectedIndex == realRecord) {
                    Gui.drawRect(x + 2, y + 2 + index * 10, x + this.xSize - 2, y + 2 + (index + 1) * 10, 0xFF00AA00);
                    Gui.drawRect(x + this.xSize - 16, y + 3 + index * 10, x + this.xSize - 6, y + 1 + (index + 1) * 10, 0xFFDD0000);
                }
                this.fontRenderer.drawString(realRecord, x + 3, 3 + y + index * 10, 0xFFFFFFFF);
                index++;
            }
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
            GL11.glPopMatrix();
        }else{
            this.fontRenderer.drawString("No songs available", x, y, 0xFFFFFFFF);
        }

        super.drawScreen(mouseX, mouseY, partialTick);
    }

    private void removeIfClickingRemove(int xMouse, int yMouse) {

    }

    @Override
    protected void mouseClicked(int x, int y, int button){
        super.mouseClicked(x, y, button);
        if(button == 0){
            //TODO: check coords!
            int realX = (this.width - this.xSize) / 2;
            int realY = (this.actualHeight - this.ySize) / 2;
            int yS = (this.actualHeight / 5);
            if(x > realX && x < realX + this.xSize) {
                if(y > realY && y < realY + yS) {
                    selectedIndex = getIndexId(y - scrollY);

                    removeIfClickingRemove(x, y - scrollY);
                }
            }
        }
    }

    @Override
    public void handleMouseInput(){
        super.handleMouseInput();
        int scroll = Mouse.getEventDWheel();
        if(scroll < 0) scroll = -5;
        if(scroll > 0) scroll = 5;
        this.scrollY += scroll;
        if(playlist != null && playlist.getTitles() != null && !playlist.getTitles().isEmpty()){
            this.scrollY = Math.min(this.scrollY, 0);
            this.scrollY = Math.max(this.scrollY, 47 - (playlist.getTitles().size() * 10 + 2));
        }
    }
}