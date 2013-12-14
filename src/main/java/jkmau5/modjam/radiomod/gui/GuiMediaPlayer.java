package jkmau5.modjam.radiomod.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import jkmau5.modjam.radiomod.network.PacketRequestRadioNames;
import jkmau5.modjam.radiomod.tile.TileEntityRadio;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
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
        this.drawDefaultBackground();

        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;

        if(isloading){
            this.fontRenderer.drawString("Loading...", x, y, 0xFFCCCCCC);
        }

        if(!isloading) {
            if(availableRadios != null) {
                GL11.glPushMatrix();
                int index = 0;
                for(TileEntityRadio radio : availableRadios){
                    this.fontRenderer.drawString(radio.getRadioName(), x, y + index * 10, 0xFFFFFFFF);
                    index++;
                }
                GL11.glPopMatrix();
            }
            else {
                GL11.glPushMatrix();
                this.fontRenderer.drawString("No radios found", x, y, 0xFFFFFFFF);
                GL11.glPopMatrix();
            }
        }

        super.drawScreen(mouseX, mouseY, partialTick);
    }

    public boolean doesGuiPauseGame(){
        return false;
    }
}
