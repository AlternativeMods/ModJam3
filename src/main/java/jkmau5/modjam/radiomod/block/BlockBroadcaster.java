package jkmau5.modjam.radiomod.block;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import jkmau5.modjam.radiomod.RadioMod;
import jkmau5.modjam.radiomod.gui.EnumGui;
import jkmau5.modjam.radiomod.gui.GuiOpener;
import jkmau5.modjam.radiomod.network.PacketUpdateRadioName;
import jkmau5.modjam.radiomod.tile.TileEntityBroadcaster;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Author: Lordmau5
 * Date: 13.12.13
 * Time: 16:02
 * You are allowed to change this code,
 * however, not to publish it without my permission.
 */
public class BlockBroadcaster extends Block {

    public BlockBroadcaster(int par1){
        super(par1, Material.iron);
        setCreativeTab(RadioMod.tabRadioMod);
    }

    public boolean hasTileEntity(int metadata) {
        return true;
    }

    public TileEntity createTileEntity(World world, int metadata) {
        return new TileEntityBroadcaster();
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if(world.isRemote)
            return true;

        TileEntity tempTile = world.getBlockTileEntity(x, y, z);
        if(tempTile == null || !(tempTile instanceof TileEntityBroadcaster))
            return false;

        TileEntityBroadcaster radio = (TileEntityBroadcaster) tempTile;
        if(radio.getRadioNetwork() != null)
            player.addChatMessage(radio.getRadioNetwork().toString());
        PacketDispatcher.sendPacketToPlayer(new PacketUpdateRadioName(x, y, z, world.provider.dimensionId, radio.getRadioName()).getPacket(), (Player) player);

        GuiOpener.openGuiOnClient(EnumGui.RADIO_BLOCK, player, x, y, z);
        return true;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, int oldId, int oldMeta) {
        if(world.isRemote)
            return;

        TileEntity tempTile = world.getBlockTileEntity(x, y, z);
        if(tempTile == null || !(tempTile instanceof TileEntityBroadcaster))
            return;

        //RadioMod.radioWorldHandler.removeRadioTile((TileEntityRadio)tempTile);
        super.breakBlock(world, x, y, z, oldId, oldMeta);
    }
}
