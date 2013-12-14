package jkmau5.modjam.radiomod.gui;

import jkmau5.modjam.radiomod.tile.TileEntityRadio;
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

    List<TileEntityRadio> availableRadios;

    public GuiMediaPlayer(List<TileEntityRadio> availableRadios) {
        this.availableRadios = availableRadios;
    }

    public void initGui() {
        buttonList.add(connectButton = new GuiButton(buttonList.size(), this.width / 2 - 100, this.height / 4 + 96 + 12, "No radio selected"));

        //TODO: Add clickable-list of the available radios
    }
}