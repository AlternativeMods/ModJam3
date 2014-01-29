package jkmau5.modjam.radiomod.network;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import jkmau5.modjam.radiomod.Constants;
import jkmau5.modjam.radiomod.tile.TileEntityPlaylist;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class PacketRemovePlaylistTitle extends PacketBase {

    public int dimensionId;
    public int x, y, z;
    public String title;

    public PacketRemovePlaylistTitle(){}
    public PacketRemovePlaylistTitle(int dimensionId, int x, int y, int z, String title){
        this.dimensionId = dimensionId;
        this.x = x;
        this.y = y;
        this.z = z;
        this.title = title;
    }

    @Override
    public void encode(ByteBuf buffer){
        if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT){
            buffer.writeInt(this.dimensionId);
            buffer.writeInt(this.x);
            buffer.writeInt(this.y);
            buffer.writeInt(this.z);
            ByteBufUtils.writeUTF8String(buffer, this.title);
        }
    }

    @Override
    public void decode(ByteBuf buffer){
        if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER){
            this.dimensionId = buffer.readInt();
            this.x = buffer.readInt();
            this.y = buffer.readInt();
            this.z = buffer.readInt();
            this.title = ByteBufUtils.readUTF8String(buffer);

            World world = DimensionManager.getWorld(dimensionId);
            if(world == null){
                return;
            }

            TileEntity tempTile = world.func_147438_o(x, y, z);
            if(tempTile == null || !(tempTile instanceof TileEntityPlaylist)){
                return;
            }

            TileEntityPlaylist playlist = (TileEntityPlaylist) tempTile;
            playlist.removeTitle(Constants.getNormalRecordTitle(title));
        }
    }
}