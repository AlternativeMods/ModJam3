package jkmau5.modjam.radiomod.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import jkmau5.modjam.radiomod.RadioMod;
import jkmau5.modjam.radiomod.network.PacketUpdateRadioName;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

/**
 * Author: Lordmau5
 * Date: 13.12.13
 * Time: 16:29
 * You are allowed to change this code,
 * however, not to publish it without my permission.
 */
public class GuiBroadcaster extends GuiScreen {

    private int tileX, tileY, tileZ;
    private String radioName;
    private GuiButton nameButton;
    private GuiTextField radioNameField;

    private int xSize = 176;
    private int ySize = 166;

    public GuiBroadcaster(int tileX, int tileY, int tileZ, String radioName){
        this.tileX = tileX;
        this.tileY = tileY;
        this.tileZ = tileZ;
        this.radioName = radioName;
    }

    public void initGui(){
        Keyboard.enableRepeatEvents(true);

        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;

        this.buttonList.add(nameButton = new GuiButton(0, x, y / 2 + 96 + 12, this.xSize, 20, "Set Radio Name"));

        this.radioNameField = new GuiTextField(this.fontRenderer, x, y, this.xSize, 20);
        this.radioNameField.setMaxStringLength(20);
        this.radioNameField.setFocused(true);
        this.radioNameField.setText(radioName);
    }

    @Override
    public void onGuiClosed(){
        Keyboard.enableRepeatEvents(false);
    }

    protected void actionPerformed(GuiButton button){
        switch(button.id){
            case 0:
                if(this.radioNameField.getText().isEmpty()){
                    this.radioNameField.setText(RadioMod.getUniqueRadioID());
                }
                PacketDispatcher.sendPacketToServer(new PacketUpdateRadioName(this.tileX, this.tileY, this.tileZ, this.mc.thePlayer.worldObj.provider.dimensionId, this.radioNameField.getText()).getPacket());
                this.mc.displayGuiScreen(null);
                break;
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTick){
        this.drawDefaultBackground();
        this.radioNameField.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTick);
    }

    protected void keyTyped(char paramChar, int paramInt){
        this.radioNameField.textboxKeyTyped(paramChar, paramInt);
        if(radioNameField.getText().equals("")){
            this.nameButton.displayString = "Random Radio Name";
        }else{
            this.nameButton.displayString = "Set Radio Name";
        }

        if(paramInt == Keyboard.KEY_RETURN || paramInt == Keyboard.KEY_NUMPADENTER){
            actionPerformed(this.nameButton);
        }else if(paramInt == Keyboard.KEY_ESCAPE){
            this.mc.displayGuiScreen(null);
        }
    }

    public boolean doesGuiPauseGame() {
        return false;
    }
}
