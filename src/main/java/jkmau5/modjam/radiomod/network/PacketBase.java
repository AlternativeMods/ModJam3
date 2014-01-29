package jkmau5.modjam.radiomod.network;

import io.netty.buffer.ByteBuf;

public abstract class PacketBase {

    public abstract void encode(ByteBuf buffer);
    public abstract void decode(ByteBuf buffer);
}
