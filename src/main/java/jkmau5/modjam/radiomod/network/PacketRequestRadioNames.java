package jkmau5.modjam.radiomod.network;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import jkmau5.modjam.radiomod.RadioMod;
import jkmau5.modjam.radiomod.tile.TileEntityRadio;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Author: Lordmau5
 * Date: 14.12.13
 * Time: 11:30
 * You are allowed to change this code,
 * however, not to publish it without my permission.
 */
public class PacketRequestRadioNames extends PacketBase {

    @Override
    public void writePacket(DataOutput output) throws IOException {
        if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
            NBTTagList list = new NBTTagList();
            for(TileEntityRadio temporaryRadio : RadioMod.radioWorldHandler.getAvailableRadioList(this.player)) {
                NBTTagCompound tempTag = new NBTTagCompound();
                temporaryRadio.writeToNBT(tempTag);
                list.appendTag(tempTag);
            }
            NBTTagCompound compoundTag = new NBTTagCompound();
            compoundTag.setTag("radios", list);
            output.write(CompressedStreamTools.compress(compoundTag));
        }
    }

    @Override
    public void readPacket(DataInput input) throws IOException {
        if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            NBTTagCompound compoundTag = CompressedStreamTools.decompress(input.readByte());
        }
        else {
            PacketDispatcher.sendPacketToPlayer(new PacketRequestRadioNames().getPacket(), (Player) this.player);
        }
    }
}