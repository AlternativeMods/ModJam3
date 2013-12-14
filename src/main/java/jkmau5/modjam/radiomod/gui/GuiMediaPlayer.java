package jkmau5.modjam.radiomod.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import jkmau5.modjam.radiomod.network.PacketRequestRadioNames;
import jkmau5.modjam.radiomod.tile.TileEntityRadio;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.List;

/**
 * Author: Lordmau5
 * Date: 14.12.13
 * Time: 11:41
 * You are allowed to change this code,
 * however, not to publish it without my permission.
 */
public class GuiMediaPlayer extends GuiScreen {

    private GuiButton connectButton;

    private static List<TileEntityRadio> availableRadios;

    private int xSize = 176;
    private int ySize = 166;
    private int scrollY = 0;
    private boolean isScrolling = false;
    private int mouseGrabY = 0;
    private static boolean isloading = false;

    public GuiMediaPlayer(){
        PacketDispatcher.sendPacketToServer(new PacketRequestRadioNames(Minecraft.getMinecraft().theWorld.provider.dimensionId).getPacket());
        isloading = true;
    }

    public static void updateRadioStations(List<TileEntityRadio> radios){
        availableRadios = radios;
        isloading = false;
        if(radios != null)
            System.out.println("Updated radio stations! New amount: " + radios.size());
    }

    public void initGui(){
        buttonList.add(connectButton = new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, "No radio selected"));

        //TODO: Add clickable-list of the available radios
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTick){
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        if(this.isScrolling){
            this.scrollY = mouseY - this.mouseGrabY;
        }
        if(availableRadios != null){
            this.scrollY = Math.min(this.scrollY, 0);
            this.scrollY = Math.max(this.scrollY, 47 - (availableRadios.size() * 10 + 2));
        }

        this.drawDefaultBackground();

        ScaledResolution res = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);

        this.ySize = 116;
        if(isloading){
            this.fontRenderer.drawString("Loading...", x, y, 0xFFCCCCCC);
        }else{
            if(availableRadios != null) {
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
                for(TileEntityRadio radio : availableRadios){
                    this.fontRenderer.drawString(radio.getRadioName(), x + 3, 3 + y + index * 10, 0xFFFFFFFF);
                    index++;
                }
                GL11.glDisable(GL11.GL_SCISSOR_TEST);
                GL11.glPopMatrix();
            }else{
                this.fontRenderer.drawString("No radios found", x, y, 0xFFFFFFFF);
            }
        }

        super.drawScreen(mouseX, mouseY, partialTick);
    }

    @Override
    protected void mouseClicked(int x, int y, int button){
        super.mouseClicked(x, y, button);
        if(button == 0){
            //TODO: check coords!
            this.mouseGrabY = y - this.scrollY;
            this.isScrolling = true;
        }
    }

    @Override
    protected void mouseMovedOrUp(int x, int y, int button){
        super.mouseMovedOrUp(x, y, button);
        if(button == 0){
            this.isScrolling = false;
        }
    }

    public boolean doesGuiPauseGame(){
        return false;
    }

    @Override
    public void handleMouseInput(){
        super.handleMouseInput();
        int scroll = Mouse.getEventDWheel();
        if(scroll < 0) scroll = -5;
        if(scroll > 0) scroll = 5;
        this.scrollY += scroll;
        if(availableRadios != null){
            this.scrollY = Math.min(this.scrollY, 0);
            this.scrollY = Math.max(this.scrollY, 47 - (availableRadios.size() * 10 + 2));
        }
    }
}
