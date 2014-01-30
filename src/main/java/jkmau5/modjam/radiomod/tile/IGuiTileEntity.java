package jkmau5.modjam.radiomod.tile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import jkmau5.modjam.radiomod.gui.RMGui;
import net.minecraft.entity.player.EntityPlayer;

/**
 * No description given
 *
 * @author jk-5
 */
public interface IGuiTileEntity {

    public boolean canPlayerOpenGui(EntityPlayer player);
    public void writeGuiData(ByteBuf buffer);

    @SideOnly(Side.CLIENT)
    public RMGui getGui();
}
