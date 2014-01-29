package jkmau5.modjam.radiomod.network;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import jkmau5.modjam.radiomod.tile.TileEntityBroadcaster;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class PacketUpdateRadioName extends PacketBase {

    private int x, y, z, dimId;
    private String radioName;

    public PacketUpdateRadioName(){
    }

    public PacketUpdateRadioName(int x, int y, int z, int dimId, String radioName){
        this.x = x;
        this.y = y;
        this.z = z;
        this.dimId = dimId;
        this.radioName = radioName;
    }

    @Override
    public void encode(ByteBuf buffer){
        buffer.writeInt(this.x);
        buffer.writeInt(this.y);
        buffer.writeInt(this.z);
        buffer.writeInt(this.dimId);
        ByteBufUtils.writeUTF8String(buffer, this.radioName);
    }

    @Override
    public void decode(ByteBuf buffer){
        this.x = buffer.readInt();
        this.y = buffer.readInt();
        this.z = buffer.readInt();
        this.dimId = buffer.readInt();
        this.radioName = ByteBufUtils.readUTF8String(buffer);

        Side effectiveSide = FMLCommonHandler.instance().getEffectiveSide();
        World world = DimensionManager.getWorld(dimId);
        if(effectiveSide == Side.CLIENT){
            world = Minecraft.getMinecraft().theWorld;
        }

        TileEntity tile = world.func_147438_o(x, y, z);
        if(tile == null || !(tile instanceof TileEntityBroadcaster)){
            return;
        }
        TileEntityBroadcaster radio = (TileEntityBroadcaster) tile;

        radio.setRadioName(radioName);
    }
}
