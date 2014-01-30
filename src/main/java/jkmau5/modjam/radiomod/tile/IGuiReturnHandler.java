package jkmau5.modjam.radiomod.tile;

import io.netty.buffer.ByteBuf;

/**
 * No description given
 *
 * @author jk-5
 */
public interface IGuiReturnHandler {
    public void readGuiReturnData(ByteBuf buffer);
}
