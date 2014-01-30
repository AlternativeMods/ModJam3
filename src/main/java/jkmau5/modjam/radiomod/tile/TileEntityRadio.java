package jkmau5.modjam.radiomod.tile;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import jkmau5.modjam.radiomod.RadioMod;
import jkmau5.modjam.radiomod.gui.GuiRadio;
import jkmau5.modjam.radiomod.gui.RMGui;
import jkmau5.modjam.radiomod.radio.IRadioListener;
import jkmau5.modjam.radiomod.radio.RadioNetwork;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

public class TileEntityRadio extends RMTileEntity implements IRadioListener, IGuiTileEntity, IGuiReturnHandler {

    @Getter @Setter private String connectedBroadcastStation;
    private boolean isListener = false;

    public TileEntityRadio(){
        this.setConnectedBroadcastStation("Not connected...");
    }

    @Override
    public void readGuiReturnData(ByteBuf buffer){
        boolean selected = buffer.readBoolean();
        if(selected){
            String selectedName = ByteBufUtils.readUTF8String(buffer);
            this.setConnectedBroadcastStation(selectedName);
        }else{
            this.setConnectedBroadcastStation(null);
        }
    }

    @Override
    public void writeGuiData(ByteBuf buffer){
        buffer.writeBoolean(this.connectedBroadcastStation != null);
        if(this.connectedBroadcastStation != null){
            ByteBufUtils.writeUTF8String(buffer, this.connectedBroadcastStation);
        }
        List<String> available = this.getAvailableRadios();
        ByteBufUtils.writeVarInt(buffer, available.size(), 4);
        for(int i = 0; i < available.size(); i++){
            ByteBufUtils.writeUTF8String(buffer, available.get(i));
        }
    }

    @Override
    public boolean canPlayerOpenGui(EntityPlayer player){
        return true;
    }

    @Override
    public RMGui getGui(){
        return new GuiRadio(this);
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

    @Override
    public void playSong(String name){
        //PacketDispatcher.sendPacketToAllAround(this.xCoord, this.yCoord, this.zCoord, 256, this.worldObj.provider.dimensionId, new PacketPlaySound(name, this.xCoord, this.yCoord, this.zCoord).getPacket());
    }

    @Override
    public void playOutOfRange(){
        this.func_145831_w().playSoundEffect(this.field_145851_c, this.field_145848_d, this.field_145849_e, "RadioMod:noise", 1, 1);
    }

    private List<String> getAvailableRadios(){
        return RadioMod.radioNetworkHandler.getAvailableRadioNames(this.func_145831_w(), this.field_145851_c, this.field_145848_d, this.field_145849_e);
    }
}
