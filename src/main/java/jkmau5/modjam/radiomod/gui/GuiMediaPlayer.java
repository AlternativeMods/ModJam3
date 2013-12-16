package jkmau5.modjam.radiomod.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import jkmau5.modjam.radiomod.network.PacketRequestRadioNames;
import jkmau5.modjam.radiomod.tile.TileEntityRadio;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.nbt.NBTTagCompound;
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

    private static List<String> availableRadios;
    public static NBTTagCompound guiData;

    private int xSize = 176;
    private int ySize = 166;
    private int scrollY = 0;
    private static boolean isloading = false;

    private String selectedName = null;

    public GuiMediaPlayer(){
        PacketDispatcher.sendPacketToServer(new PacketRequestRadioNames(Minecraft.getMinecraft().theWorld.provider.dimensionId, Minecraft.getMinecraft().thePlayer).getPacket());
        isloading = true;
    }

    public GuiMediaPlayer(TileEntityRadio tileEntity){
        PacketDispatcher.sendPacketToServer(new PacketRequestRadioNames(Minecraft.getMinecraft().theWorld.provider.dimensionId, tileEntity).getPacket());
        isloading = true;
    }

    public static void updateRadioStations(List<String> radios){
        availableRadios = radios;
        isloading = false;
    }

    public void initGui(){
        buttonList.add(connectButton = new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, "No radio selected"));
        this.onSelectionChanged();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTick){
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
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
            if(availableRadios != null){
                GL11.glPushMatrix();
                int yS = (this.height / 5);
                Gui.drawRect(x - 1, y - 1, x + this.xSize + 1, y + yS + 1, 0xFFAAAAAA);
                Gui.drawRect(x, y, x + this.xSize, y + yS, 0xFF000000);

                GL11.glScissor(x * res.getScaleFactor(), this.mc.displayHeight - yS * res.getScaleFactor() - y * res.getScaleFactor(), (this.xSize - 5) * res.getScaleFactor(), yS * res.getScaleFactor());
                GL11.glTranslated(0, this.scrollY, 0);
                GL11.glEnable(GL11.GL_SCISSOR_TEST);
                int index = 0;
                for(String radio : availableRadios){
                    if(radio.equals(this.selectedName)){
                        Gui.drawRect(x + 2, y + 2 + index * 10, x + this.xSize - 2, y + 2 + (index + 1) * 10, 0xFF00AA00);
                    }
                    this.fontRenderer.drawString(radio, x + 3, 3 + y + index * 10, 0xFFFFFFFF);
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
            int realX = (this.width - this.xSize) / 2;
            int realY = (this.height - this.ySize) / 2;
            int yS = this.height / 5;
            if(x > realX && x < realX + this.xSize && y > realY && y < realY + yS){
                this.selectedName = this.getNameFromY(y - this.scrollY);
                this.onSelectionChanged();
            }
        }
    }

    public String getNameFromY(int y){
        int i = 0;
        for(String name : availableRadios){
            int pos = 2 + ((this.height - 116) / 2) + (i * 10);
            if(y >= pos && y < pos + 10) return name;
            i++;
        }
        return null;
    }

    public void onSelectionChanged(){
        if(this.selectedName == null){
            this.connectButton.displayString = "No radio selected";
        }else{
            this.connectButton.displayString = "Listen to this radio";
        }
    }

    @Override
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
