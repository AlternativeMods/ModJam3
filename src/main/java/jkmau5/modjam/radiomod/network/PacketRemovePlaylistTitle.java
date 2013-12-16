package jkmau5.modjam.radiomod.network;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import jkmau5.modjam.radiomod.Constants;
import jkmau5.modjam.radiomod.tile.TileEntityPlaylist;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class PacketRemovePlaylistTitle extends PacketBase {

    int dimensionId;
    int x, y, z;
    String title;

    public PacketRemovePlaylistTitle(){
    }

    public PacketRemovePlaylistTitle(int dimensionId, int x, int y, int z, String title){
        this.dimensionId = dimensionId;
        this.x = x;
        this.y = y;
        this.z = z;

        this.title = title;
    }

    @Override
    public void writePacket(DataOutput output) throws IOException{
        if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT){
            output.writeInt(dimensionId);
            output.writeInt(x);
            output.writeInt(y);
            output.writeInt(z);

            output.writeUTF(title);
        }
    }

    @Override
    public void readPacket(DataInput input) throws IOException{
        if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER){
            this.dimensionId = input.readInt();
            this.x = input.readInt();
            this.y = input.readInt();
            this.z = input.readInt();
            this.title = input.readUTF();

            World world = DimensionManager.getWorld(dimensionId);
            if(world == null)
                return;

            TileEntity tempTile = world.getBlockTileEntity(x, y, z);
            if(tempTile == null || !(tempTile instanceof TileEntityPlaylist))
                return;

            TileEntityPlaylist playlist = (TileEntityPlaylist) tempTile;
            playlist.removeTitle(Constants.getNormalRecordTitle(title));
        }
    }
}