package jkmau5.modjam.radiomod.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import jkmau5.modjam.radiomod.RadioMod;
import jkmau5.modjam.radiomod.network.PacketUpdateRadioName;
import jkmau5.modjam.radiomod.tile.TileEntityRadio;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Author: Lordmau5
 * Date: 13.12.13
 * Time: 16:29
 * You are allowed to change this code,
 * however, not to publish it without my permission.
 */
public class GuiRadioScreen extends GuiScreen {

    int x, y, z;
    public GuiRadioScreen(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    GuiButton nameButton;
    GuiTextField radioNameField;

    public void initGui() {
        buttonList.add(nameButton = new GuiButton(buttonList.size(), this.width / 2 - 100, this.height / 4 + 96 + 12, "Set Radio Name"));

        radioNameField = new GuiTextField(this.fontRenderer, this.width / 2 - 150, 60, 300, 20);
        radioNameField.setMaxStringLength(26);
        radioNameField.setFocused(true);
        initiateRadioNameField();
    }

    protected void initiateRadioNameField() {
        World world = mc.theWorld;
        TileEntity tempTile = world.getBlockTileEntity(x, y, z);
        if(tempTile == null || !(tempTile instanceof TileEntityRadio))
            return;
        radioNameField.setText(((TileEntityRadio)tempTile).getRadioName());
    }

    protected void actionPerformed(GuiButton button) {
        if(button == nameButton) {
            if(radioNameField.getText().equals(""))
                radioNameField.setText(RadioMod.getUniqueRadioID());
            PacketDispatcher.sendPacketToServer(new PacketUpdateRadioName(x, y, z, mc.thePlayer.worldObj.provider.dimensionId, radioNameField.getText()).getPacket());
            hideScreen();
        }
    }

    protected void hideScreen() {
        this.mc.displayGuiScreen(null);
    }

    protected void keyTyped(char paramChar, int paramInt)
    {
        radioNameField.textboxKeyTyped(paramChar, paramInt);
        if(radioNameField.getText().equals(""))
            nameButton.displayString = "Random Radio Name";
        else
            nameButton.displayString = "Set Radio Name";

        if (paramInt == 28 || paramInt == 156)
            actionPerformed(nameButton);
        else if (paramInt == 1)
            hideScreen();
    }

    public void drawScreen(int x, int y, float floatVar)
    {
        this.radioNameField.drawTextBox();

        super.drawScreen(x, y, floatVar);
    }
}