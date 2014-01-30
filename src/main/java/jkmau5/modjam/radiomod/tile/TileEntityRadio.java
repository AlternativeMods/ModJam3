package jkmau5.modjam.radiomod.tile;

import cpw.mods.fml.common.FMLCommonHandler;
import jkmau5.modjam.radiomod.RadioMod;
import jkmau5.modjam.radiomod.radio.IRadioListener;
import jkmau5.modjam.radiomod.radio.RadioNetwork;
import net.minecraft.nbt.NBTTagCompound;
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
    public void func_145845_h(){
        super.func_145845_h();
        if(!this.isListener && this.connectedBroadcastStation != null && !this.connectedBroadcastStation.isEmpty()){
            RadioNetwork network = RadioMod.radioNetworkHandler.getNetworkFromName(this.connectedBroadcastStation);
            if(network == null) return;
            network.addListener(this);
            this.isListener = true;
        }
    }

    @Override
    public void func_145843_s(){
        super.func_145843_s();
        if(this.isListener && this.connectedBroadcastStation != null && !this.connectedBroadcastStation.isEmpty()){
            RadioNetwork network = RadioMod.radioNetworkHandler.getNetworkFromName(this.connectedBroadcastStation);
            if(network == null) return;
            network.removeListener(this);
            this.isListener = false;
        }
    }

    @Override
    public void onChunkUnload(){
        super.onChunkUnload();
        if(this.isListener && this.connectedBroadcastStation != null && !this.connectedBroadcastStation.isEmpty()){
            RadioNetwork network = RadioMod.radioNetworkHandler.getNetworkFromName(this.connectedBroadcastStation);
            if(network == null) return;
            network.removeListener(this);
            this.isListener = false;
        }
    }

    @Override
    public void func_145839_a(NBTTagCompound tag){
        super.func_145839_a(tag);

        setConnectedBroadcastStation(tag.getString("connectedBroadcastStation"));
    }

    @Override
    public void func_145841_b(NBTTagCompound tag){
        super.func_145841_b(tag);

        tag.setString("connectedBroadcastStation", getConnectedBroadcastStation());
    }

    /*@Override
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
    }*/

    @Override
    public void playSong(String name){
        //PacketDispatcher.sendPacketToAllAround(this.xCoord, this.yCoord, this.zCoord, 256, this.worldObj.provider.dimensionId, new PacketPlaySound(name, this.xCoord, this.yCoord, this.zCoord).getPacket());
    }

    @Override
    public void playOutOfRange(){
        this.func_145831_w().playSoundEffect(this.field_145851_c, this.field_145848_d, this.field_145849_e, "RadioMod:noise", 1, 1);
    }
}
