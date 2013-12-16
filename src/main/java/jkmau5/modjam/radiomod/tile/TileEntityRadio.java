package jkmau5.modjam.radiomod.tile;

import cpw.mods.fml.common.FMLCommonHandler;
import jkmau5.modjam.radiomod.RadioMod;
import jkmau5.modjam.radiomod.radio.IRadioListener;
import jkmau5.modjam.radiomod.radio.RadioNetwork;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class TileEntityRadio extends TileEntity implements IRadioListener {

    private String connectedBroadcastStation;
    private boolean isListener = false;

    public TileEntityRadio(){
        setConnectedBroadcastStation("Not connected...");
    }

    public String getConnectedBroadcastStation(){
        return this.connectedBroadcastStation;
    }

    public void setConnectedBroadcastStation(String connectedBroadcastStation){
        this.connectedBroadcastStation = connectedBroadcastStation;
    }

    @Override
    public boolean canUpdate(){
        return FMLCommonHandler.instance().getEffectiveSide().isServer();
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!this.isListener && this.connectedBroadcastStation != null){
            RadioNetwork network = RadioMod.radioNetworkHandler.getNetworkFromName(this.connectedBroadcastStation);
            network.addListener(this);
            this.isListener = true;
        }
    }

    @Override
    public void invalidate(){
        super.invalidate();
        if(this.isListener && this.connectedBroadcastStation != null){
            RadioNetwork network = RadioMod.radioNetworkHandler.getNetworkFromName(this.connectedBroadcastStation);
            network.removeListener(this);
            this.isListener = false;
        }
    }

    @Override
    public void onChunkUnload(){
        super.onChunkUnload();
        if(this.isListener && this.connectedBroadcastStation != null){
            RadioNetwork network = RadioMod.radioNetworkHandler.getNetworkFromName(this.connectedBroadcastStation);
            network.removeListener(this);
            this.isListener = false;
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound){
        super.readFromNBT(tagCompound);

        setConnectedBroadcastStation(tagCompound.getString("connectedBroadcastStation"));
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound){
        super.writeToNBT(tagCompound);

        tagCompound.setString("connectedBroadcastStation", getConnectedBroadcastStation());
    }

    @Override
    public Packet getDescriptionPacket(){
        NBTTagCompound tag = new NBTTagCompound();
        this.writeToNBT(tag);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 0, tag);
    }

    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt){
        readFromNBT(pkt.data);
    }

    public void writeGuiData(NBTTagCompound tag){
        tag.setString("station", this.connectedBroadcastStation);
    }

    @Override
    public void playSong(String name){
        this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, name, 1, 1);
    }

    @Override
    public void playOutOfRange(){
        this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "RadioMod:noise", 1, 1);
    }
}
