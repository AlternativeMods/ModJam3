package jkmau5.modjam.radiomod.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import jkmau5.modjam.radiomod.network.PacketRequestRadioNames;
import jkmau5.modjam.radiomod.tile.TileEntityRadio;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.util.List;

/**
 * Author: Lordmau5
 * Date: 14.12.13
 * Time: 11:41
 * You are allowed to change this code,
 * however, not to publish it without my permission.
 */
public class GuiMediaPlayer extends GuiScreen {
    GuiButton connectButton;

    private static List<TileEntityRadio> availableRadios;

    public GuiMediaPlayer() {
        PacketDispatcher.sendPacketToServer(new PacketRequestRadioNames(Minecraft.getMinecraft().theWorld.provider.dimensionId).getPacket());
    }

    public static void updateRadioStations(List<TileEntityRadio> radios) {
        availableRadios = radios;
        System.out.println("Updated radio stations! New amount: " + radios.size());
    }

    public void initGui() {
        buttonList.add(connectButton = new GuiButton(buttonList.size(), this.width / 2 - 100, this.height / 4 + 96 + 12, "No radio selected"));

        //TODO: Add clickable-list of the available radios
    }

    public boolean doesGuiPauseGame() {
        return false;
    }
}