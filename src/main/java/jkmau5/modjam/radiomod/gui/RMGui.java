package jkmau5.modjam.radiomod.gui;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import jkmau5.modjam.radiomod.network.NetworkHandler;
import jkmau5.modjam.radiomod.network.RMPacket;
import jkmau5.modjam.radiomod.tile.IGuiTileEntity;
import lombok.RequiredArgsConstructor;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.tileentity.TileEntity;

/**
 * No description given
 *
 * @author jk-5
 */
@RequiredArgsConstructor
public abstract class RMGui extends GuiScreen {

    private final IGuiTileEntity tileEntity;

    public final void sendGuiData(){
        TileEntity tile = (TileEntity) tileEntity;
        ByteBuf data = Unpooled.buffer();
        this.writeGuiData(data);
        NetworkHandler.sendPacketToServer(new RMPacket.GuiReturnDataPacket(tile.field_145851_c, tile.field_145848_d, tile.field_145849_e, data));
    }

    @SuppressWarnings("unchecked")
    protected final void addButton(GuiButton button){
        this.field_146292_n.add(button);
    }

    @SuppressWarnings("unchecked")
    protected final void addLabel(GuiLabel label){
        this.field_146293_o.add(label);
    }

    public RMGui readGuiData(ByteBuf buffer){
        return this;
    }

    protected void writeGuiData(ByteBuf buffer){

    }
}
