package jkmau5.modjam.radiomod.gui;

import jkmau5.modjam.radiomod.tile.TileEntityPlaylist;
import net.minecraft.client.gui.GuiScreen;

/**
 * Author: Lordmau5
 * Date: 15.12.13
 * Time: 17:15
 * You are allowed to change this code,
 * however, not to publish it without my permission.
 */
public class GuiPlaylist extends GuiScreen {

    TileEntityPlaylist playlist;

    public GuiPlaylist(TileEntityPlaylist playlist) {
        this.playlist = playlist;
    }
}