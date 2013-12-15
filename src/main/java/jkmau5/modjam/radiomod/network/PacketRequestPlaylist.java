package jkmau5.modjam.radiomod.network;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import jkmau5.modjam.radiomod.tile.TileEntityPlaylist;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Author: Lordmau5
 * Date: 15.12.13
 * Time: 17:17
 * You are allowed to change this code,
 * however, not to publish it without my permission.
 */
public class PacketRequestPlaylist extends PacketBase {

    TileEntityPlaylist playlist;

    public PacketRequestPlaylist() {}
    public PacketRequestPlaylist(TileEntityPlaylist playlist) {
        this.playlist = playlist;
    }

    @Override
    public void writePacket(DataOutput output) throws IOException {
        if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {

        }else{
            output.writeInt(this.playlist.xCoord);
            output.writeInt(this.playlist.yCoord);
            output.writeInt(this.playlist.zCoord);
        }
    }

    @Override
    public void readPacket(DataInput input) throws IOException {
        if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {

        }else{

        }
    }
}