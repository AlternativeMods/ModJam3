package jkmau5.modjam.radiomod.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import jkmau5.modjam.radiomod.Constants;
import jkmau5.modjam.radiomod.tile.TileEntityRadioNetwork;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.DimensionManager;

/**
 * No description given
 *
 * @author jk-5
 */
public class PacketPlayBroadcastedSound extends PacketBase {

    public String soundName;
    public int x, y, z, dimid;

    public PacketPlayBroadcastedSound(){}
    public PacketPlayBroadcastedSound(String soundName, int x, int y, int z, int dimid){
        this.soundName = soundName;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void encode(ByteBuf buffer){
        buffer.writeInt(this.x);
        buffer.writeInt(this.y);
        buffer.writeInt(this.z);
        buffer.writeInt(this.dimid);
        ByteBufUtils.writeUTF8String(buffer, Constants.getNormalRecordTitle(this.soundName));
    }

    @Override
    public void decode(ByteBuf buffer){
        this.x = buffer.readInt();
        this.y = buffer.readInt();
        this.z = buffer.readInt();
        this.dimid = buffer.readInt();
        this.soundName = ByteBufUtils.readUTF8String(buffer);
        TileEntity tile = DimensionManager.getWorld(this.dimid).func_147438_o(x, y, z);
        if(tile == null || !(tile instanceof TileEntityRadioNetwork)) return;
        ((TileEntityRadioNetwork) tile).network.playSound(this.soundName);
    }
}
