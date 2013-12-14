package jkmau5.modjam.radiomod.network;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import jkmau5.modjam.radiomod.RadioMod;
import jkmau5.modjam.radiomod.gui.GuiMediaPlayer;
import jkmau5.modjam.radiomod.tile.TileEntityRadio;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Lordmau5
 * Date: 14.12.13
 * Time: 11:30
 * You are allowed to change this code,
 * however, not to publish it without my permission.
 */
public class PacketRequestRadioNames extends PacketBase {

    int dimensionId;

    public PacketRequestRadioNames() {}
    public PacketRequestRadioNames(int dimensionId) {
        this.dimensionId = dimensionId;
    }

    @Override
    public void writePacket(DataOutput output) throws IOException {
        if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
            boolean shouldContinue = false;
            NBTTagList list = new NBTTagList();
            List<TileEntityRadio> radioList = RadioMod.radioWorldHandler.getAvailableRadioList(dimensionId, this.player);
            if(radioList != null && !radioList.isEmpty()) {
                shouldContinue = true;
                for(TileEntityRadio temporaryRadio : radioList) {
                    NBTTagCompound tempTag = new NBTTagCompound();
                    temporaryRadio.writeToNBT(tempTag);
                    list.appendTag(tempTag);
                }
            }
            output.writeBoolean(shouldContinue);
            NBTTagCompound compoundTag = new NBTTagCompound();
            compoundTag.setTag("radios", list);
            int length = CompressedStreamTools.compress(compoundTag).length;
            output.writeInt(length);
            output.write(CompressedStreamTools.compress(compoundTag));
        }
    }

    @Override
    public void readPacket(DataInput input) throws IOException {
        if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            boolean shouldContinue = input.readBoolean();
            if(!shouldContinue) {
                GuiMediaPlayer.updateRadioStations(null);
                return;
            }

            int length = input.readInt();
            byte[] byteArray = new byte[length];
            input.readFully(byteArray);

            NBTTagCompound compoundTag = CompressedStreamTools.decompress(byteArray);
            NBTTagList tagList = compoundTag.getTagList("radios");

            List<TileEntityRadio> radios = new ArrayList<TileEntityRadio>();

            for(int i=0; i<tagList.tagCount(); i++) {
                NBTBase base = tagList.tagAt(i);
                if(base instanceof NBTTagCompound) {
                    TileEntityRadio tempRadio = new TileEntityRadio();
                    tempRadio.readFromNBT((NBTTagCompound) base);
                    radios.add(tempRadio);
                }
            }
            GuiMediaPlayer.updateRadioStations(radios);
        }
        else {
            PacketDispatcher.sendPacketToPlayer(new PacketRequestRadioNames(dimensionId).getPacket(), (Player) this.player);
        }
    }
}