package jkmau5.modjam.radiomod.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;

public class PacketPlaySound extends RMPacket {

    private String name;
    private int x, y, z;
    private boolean stop;

    public PacketPlaySound(){}
    public PacketPlaySound(String name, int x, int y, int z){
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.stop = false;
    }

    public PacketPlaySound(String name, int x, int y, int z, boolean stop){
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.stop = stop;
    }

    @Override
    public void encode(ByteBuf buffer){
        buffer.writeBoolean(this.stop);
        if(!this.stop) ByteBufUtils.writeUTF8String(buffer, this.name);
        buffer.writeInt(this.x);
        buffer.writeInt(this.y);
        buffer.writeInt(this.z);
    }

    @Override
    public void decode(ByteBuf buffer){
        boolean stop = buffer.readBoolean();
        if(stop){
            Minecraft.getMinecraft().renderGlobal.playRecord(null, buffer.readInt(), buffer.readInt(), buffer.readInt());
        }else{
            Minecraft.getMinecraft().renderGlobal.playRecord(ByteBufUtils.readUTF8String(buffer), buffer.readInt(), buffer.readInt(), buffer.readInt());
        }
    }
}
