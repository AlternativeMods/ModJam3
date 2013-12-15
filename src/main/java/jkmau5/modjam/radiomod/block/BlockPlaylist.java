package jkmau5.modjam.radiomod.block;

import jkmau5.modjam.radiomod.RadioMod;
import jkmau5.modjam.radiomod.gui.EnumGui;
import jkmau5.modjam.radiomod.gui.GuiOpener;
import jkmau5.modjam.radiomod.tile.TileEntityPlaylist;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemRecord;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Author: Lordmau5
 * Date: 15.12.13
 * Time: 16:35
 * You are allowed to change this code,
 * however, not to publish it without my permission.
 */
public class BlockPlaylist extends BlockRadioNetwork {

    public BlockPlaylist(int par1){
        super(par1, Material.iron);
        setCreativeTab(RadioMod.tabRadioMod);
        setUnlocalizedName("radiomod.BlockPlaylist");
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, int oldId, int oldMeta){
        super.breakBlock(world, x, y, z, oldId, oldMeta);
        TileEntity tempTile = world.getBlockTileEntity(x, y, z);
        if(tempTile == null || !(tempTile instanceof TileEntityPlaylist)){
            super.breakBlock(world, x, y, z, oldId, oldMeta);
            return;
        }
        ((TileEntityPlaylist) tempTile).breakBlock();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ){
        if(world.isRemote)
            return true;

        TileEntity tempTile = world.getBlockTileEntity(x, y, z);
        if(tempTile == null || !(tempTile instanceof TileEntityPlaylist))
            return true;
        TileEntityPlaylist playlist = (TileEntityPlaylist) tempTile;

        if(player.getHeldItem() == null){
            GuiOpener.openGuiOnClient(EnumGui.PLAYLIST_BLOCK, player, x, y, z);
        }else{
            if(!(player.getHeldItem().getItem() instanceof ItemRecord))
                return true;
            ItemRecord record = (ItemRecord) player.getHeldItem().getItem();

            if(playlist.addTitle(record.recordName))
                player.getHeldItem().stackSize--;
        }

        return true;
    }

    @Override
    public boolean hasTileEntity(int metadata){
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata){
        return new TileEntityPlaylist();
    }
}