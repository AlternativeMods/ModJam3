package jkmau5.modjam.radiomod.network;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import jkmau5.modjam.radiomod.tile.TileEntityRadio;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Author: Lordmau5
 * Date: 13.12.13
 * Time: 18:35
 * You are allowed to change this code,
 * however, not to publish it without my permission.
 */
public class PacketUpdateRadioName extends PacketBase {

    private int x, y, z, dimId;
    private String radioName;

    public PacketUpdateRadioName(){}
    public PacketUpdateRadioName(int x, int y, int z, int dimId, String radioName) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.dimId = dimId;
        this.radioName = radioName;
    }

    @Override
    public void writePacket(DataOutput output) throws IOException {
        output.writeInt(this.x);
        output.writeInt(this.y);
        output.writeInt(this.z);
        output.writeInt(this.dimId);
        output.writeUTF(this.radioName);
    }

    @Override
    public void readPacket(DataInput input) throws IOException {
        this.x = input.readInt();
        this.y = input.readInt();
        this.z = input.readInt();
        this.dimId = input.readInt();
        this.radioName = input.readUTF();

        Side effectiveSide = FMLCommonHandler.instance().getEffectiveSide();
        World world = DimensionManager.getWorld(dimId);
        if(effectiveSide == Side.CLIENT)
            world = Minecraft.getMinecraft().theWorld;

        TileEntity tempTile = world.getBlockTileEntity(x, y, z);
        if(tempTile == null || !(tempTile instanceof TileEntityRadio))
            return;
        TileEntityRadio radio = (TileEntityRadio) tempTile;

        radio.setRadioName(radioName);

        //if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
        //    PacketDispatcher.sendPacketToAllPlayers(new PacketUpdateRadioName(x, y, z, dimId, radioName).getPacket()); // TODO: Less packet sending at this one...
    }
}