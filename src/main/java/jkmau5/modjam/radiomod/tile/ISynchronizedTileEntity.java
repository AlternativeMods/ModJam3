package jkmau5.modjam.radiomod.tile;

import io.netty.buffer.ByteBuf;

/**
 * No description given
 *
 * @author jk-5
 */
public interface ISynchronizedTileEntity {

    public void writeData(ByteBuf buffer);
    public void readData(ByteBuf buffer);
}
