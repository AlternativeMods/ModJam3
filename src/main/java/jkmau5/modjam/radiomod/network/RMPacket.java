package jkmau5.modjam.radiomod.network;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public abstract class RMPacket {

    public abstract void encode(ByteBuf buffer);
    public abstract void decode(ByteBuf buffer);

    @NoArgsConstructor
    @AllArgsConstructor
    public static class TileEntityData extends RMPacket {

        public int x, y, z;
        public ByteBuf data;

        @Override
        public void encode(ByteBuf buffer){
            buffer.writeInt(this.x);
            buffer.writeInt(this.y);
            buffer.writeInt(this.z);
            buffer.writeBytes(this.data);
        }

        @Override
        public void decode(ByteBuf buffer){
            this.x = buffer.readInt();
            this.y = buffer.readInt();
            this.z = buffer.readInt();
            this.data = buffer.slice();
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    public static class GuiReturnDataPacket extends RMPacket {

        public int x, y, z;
        public ByteBuf data;

        @Override
        public void encode(ByteBuf buffer){
            buffer.writeInt(this.x);
            buffer.writeInt(this.y);
            buffer.writeInt(this.z);
            buffer.writeBytes(this.data);
        }

        @Override
        public void decode(ByteBuf buffer){
            this.x = buffer.readInt();
            this.y = buffer.readInt();
            this.z = buffer.readInt();
            this.data = buffer.slice();
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    public static class GuiOpen extends RMPacket {

        public int x, y, z;
        public ByteBuf data;

        @Override
        public void encode(ByteBuf buffer){
            buffer.writeInt(this.x);
            buffer.writeInt(this.y);
            buffer.writeInt(this.z);
            buffer.writeBytes(this.data);
        }

        @Override
        public void decode(ByteBuf buffer){
            this.x = buffer.readInt();
            this.y = buffer.readInt();
            this.z = buffer.readInt();
            this.data = buffer.slice();
        }
    }
}
