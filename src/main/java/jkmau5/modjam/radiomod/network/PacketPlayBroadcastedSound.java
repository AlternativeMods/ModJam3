package jkmau5.modjam.radiomod.network;

import jkmau5.modjam.radiomod.Constants;
import jkmau5.modjam.radiomod.tile.TileEntityRadioNetwork;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.DimensionManager;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * No description given
 *
 * @author jk-5
 */
public class PacketPlayBroadcastedSound extends PacketBase {

    public String soundName;
    public int x, y, z, dimid;

    public PacketPlayBroadcastedSound(){
    }

    public PacketPlayBroadcastedSound(String soundName, int x, int y, int z, int dimid){
        this.soundName = soundName;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void writePacket(DataOutput output) throws IOException{
        output.writeInt(this.x);
        output.writeInt(this.y);
        output.writeInt(this.z);
        output.writeInt(this.dimid);
        output.writeUTF(Constants.getNormalRecordTitle(this.soundName));
    }

    @Override
    public void readPacket(DataInput input) throws IOException{
        this.x = input.readInt();
        this.y = input.readInt();
        this.z = input.readInt();
        this.dimid = input.readInt();
        this.soundName = input.readUTF();
        TileEntity tile = DimensionManager.getWorld(this.dimid).getBlockTileEntity(x, y, z);
        if(tile == null || !(tile instanceof TileEntityRadioNetwork)) return;
        ((TileEntityRadioNetwork) tile).network.playSound(this.soundName);
    }
}
