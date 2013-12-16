package jkmau5.modjam.radiomod.network;

import net.minecraft.client.Minecraft;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class PacketPlaySound extends PacketBase {

    private String name;
    private int x, y, z;
    private boolean stop;

    public PacketPlaySound(){
    }

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
    public void writePacket(DataOutput output) throws IOException{
        output.writeBoolean(this.stop);
        if(!this.stop) output.writeUTF(this.name);
        output.writeInt(this.x);
        output.writeInt(this.y);
        output.writeInt(this.z);
    }

    @Override
    public void readPacket(DataInput input) throws IOException{
        boolean stop = input.readBoolean();
        if(stop){
            Minecraft.getMinecraft().renderGlobal.playRecord(null, input.readInt(), input.readInt(), input.readInt());
        }else{
            Minecraft.getMinecraft().renderGlobal.playRecord(input.readUTF(), input.readInt(), input.readInt(), input.readInt());
        }
    }
}
