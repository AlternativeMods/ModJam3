package jkmau5.modjam.radiomod.block;

import jkmau5.modjam.radiomod.RadioMod;
import jkmau5.modjam.radiomod.gui.EnumGui;
import jkmau5.modjam.radiomod.gui.GuiOpener;
import jkmau5.modjam.radiomod.tile.TileEntityPlaylist;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockPlaylist extends Block {

    private Icon topIcon[];
    private Icon sidesIcon;

    public BlockPlaylist(int par1){
        super(par1, Material.iron);
        this.setCreativeTab(RadioMod.tabRadioMod);
        this.setUnlocalizedName("radiomod.BlockPlaylist");

        this.maxY = 0.75;
    }

    @Override
    public boolean renderAsNormalBlock(){
        return false;
    }

    @Override
    public boolean isOpaqueCube(){
        return false;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack){
        int meta = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
        world.setBlockMetadataWithNotify(x, y, z, meta, 2);
    }

    @Override
    public void registerIcons(IconRegister register){
        this.topIcon = new Icon[4];
        this.topIcon[0] = register.registerIcon("RadioMod:playlist");
        this.topIcon[1] = register.registerIcon("RadioMod:playlist_90");
        this.topIcon[2] = register.registerIcon("RadioMod:playlist_180");
        this.topIcon[3] = register.registerIcon("RadioMod:playlist_270");
        this.sidesIcon = register.registerIcon("RadioMod:side_small");
    }

    @Override
    public Icon getIcon(int side, int meta){
        switch(side){
            case 1:
                return this.topIcon[meta];
            default:
                return this.sidesIcon;
        }
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