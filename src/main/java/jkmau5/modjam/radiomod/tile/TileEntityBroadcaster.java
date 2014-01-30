package jkmau5.modjam.radiomod.tile;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import jkmau5.modjam.radiomod.RadioMod;
import jkmau5.modjam.radiomod.gui.GuiBroadcaster;
import jkmau5.modjam.radiomod.gui.RMGui;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityBroadcaster extends TileEntityRadioNetwork implements IGuiTileEntity, IGuiReturnHandler {

    @Getter @Setter protected String radioName;

    public TileEntityBroadcaster(){
        this.radioName = RadioMod.getUniqueRadioID();
    }

    @Override
    public void func_145839_a(NBTTagCompound tag){
        super.func_145839_a(tag);
        this.radioName = tag.getString("radioName");
    }

    @Override
    public void func_145841_b(NBTTagCompound tag){
        super.func_145841_b(tag);
        tag.setString("radioName", this.radioName);
    }

    @Override
    public boolean canPlayerOpenGui(EntityPlayer player){
        return true;
    }

    @Override
    public void writeGuiData(ByteBuf buffer){
        ByteBufUtils.writeUTF8String(buffer, this.radioName);
    }

    @Override
    public void readGuiReturnData(ByteBuf buffer){
        this.radioName = ByteBufUtils.readUTF8String(buffer);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public RMGui getGui(){
        return new GuiBroadcaster(this);
    }
}
