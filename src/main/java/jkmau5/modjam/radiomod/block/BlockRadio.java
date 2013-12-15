package jkmau5.modjam.radiomod.block;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import jkmau5.modjam.radiomod.RadioMod;
import jkmau5.modjam.radiomod.gui.EnumGui;
import jkmau5.modjam.radiomod.gui.GuiOpener;
import jkmau5.modjam.radiomod.network.PacketMediaPlayerData;
import jkmau5.modjam.radiomod.tile.TileEntityRadio;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Author: Lordmau5
 * Date: 15.12.13
 * Time: 10:56
 * You are allowed to change this code,
 * however, not to publish it without my permission.
 */
public class BlockRadio extends Block {

    public BlockRadio(int par1) {
        super(par1, Material.iron);
        setCreativeTab(RadioMod.tabRadioMod);
        setUnlocalizedName("radiomod.BlockRadio");
    }

    public boolean hasTileEntity(int metadata) {
        return true;
    }

    public TileEntity createTileEntity(World world, int metadata) {
        return new TileEntityRadio();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par7, float par8, float par9) {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if(tileEntity == null || !(tileEntity instanceof TileEntityRadio))
            return true;

        if(!world.isRemote){
            NBTTagCompound tag = new NBTTagCompound();
            ((TileEntityRadio) tileEntity).writeGuiData(tag);
            PacketDispatcher.sendPacketToPlayer(new PacketMediaPlayerData(tag).getPacket(), (Player) player);
            GuiOpener.openGuiOnClient(EnumGui.RADIO_BLOCK, player, x, y, z);
        }

        return super.onBlockActivated(world, x, y, z, player, side, par7, par8, par9);
    }
}