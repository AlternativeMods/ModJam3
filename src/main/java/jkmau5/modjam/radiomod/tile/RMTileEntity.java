package jkmau5.modjam.radiomod.tile;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import jkmau5.modjam.radiomod.network.NetworkHandler;
import jkmau5.modjam.radiomod.network.RMPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;

/**
 * No description given
 *
 * @author jk-5
 */
public abstract class RMTileEntity extends TileEntity {

    @Override
    public Packet func_145844_m(){
        if(!(this instanceof ISynchronizedTileEntity)) return null;
        ByteBuf buffer = Unpooled.buffer();
        ((ISynchronizedTileEntity) this).writeData(buffer);
        if(buffer.readableBytes() == 0){
            buffer.release();
            return null;
        }
        return NetworkHandler.getProxyPacket(new RMPacket.TileEntityData(this.field_145851_c, this.field_145848_d, this.field_145849_e, buffer));
    }

    public final void sendDataToPlayer(EntityPlayerMP player){
        player.playerNetServerHandler.func_147359_a(this.func_145844_m());
    }

    public boolean onBlockActivated(EntityPlayer entity, int side, float hitX, float hitY, float hitZ){
        if(this instanceof IGuiTileEntity){
            IGuiTileEntity tile = (IGuiTileEntity) this;
            if(this.field_145850_b.isRemote) return true;
            else{
                if(tile.canPlayerOpenGui(entity)){
                    ByteBuf data = Unpooled.buffer();
                    tile.writeGuiData(data);
                    NetworkHandler.sendPacketToPlayer(new RMPacket.GuiOpen(this.field_145851_c, this.field_145848_d, this.field_145849_e, data), entity);
                }
                return true;
            }
        }
        return false;
    }
}
