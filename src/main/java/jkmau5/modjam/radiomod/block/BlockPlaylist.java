package jkmau5.modjam.radiomod.block;

import jkmau5.modjam.radiomod.RadioMod;
import jkmau5.modjam.radiomod.gui.EnumGui;
import jkmau5.modjam.radiomod.gui.GuiOpener;
import jkmau5.modjam.radiomod.tile.TileEntityPlaylist;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockPlaylist extends Block {

    private IIcon topIcon[];
    private IIcon sidesIcon;
    private IIcon bottomIcon;

    public BlockPlaylist(){
        super(Material.field_151573_f);
        this.func_149647_a(RadioMod.tabRadioMod);
        this.func_149663_c("radioMod.blockPlaylist");

        this.field_149756_F = 0.75;
    }

    @Override
    public boolean func_149686_d(){
        return false;
    }

    @Override
    public boolean func_149662_c(){
        return false;
    }

    @Override
    public void func_149689_a(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack){
        int meta = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
        world.setBlockMetadataWithNotify(x, y, z, meta, 2);

        TileEntity tile = world.func_147438_o(x, y, z);
        ((TileEntityPlaylist) tile).selectNetworkFromBroadcaster(world);
    }

    @Override
    public void func_149651_a(IIconRegister register){
        this.topIcon = new IIcon[4];
        this.topIcon[0] = register.registerIcon("RadioMod:playlist");
        this.topIcon[1] = register.registerIcon("RadioMod:playlist_90");
        this.topIcon[2] = register.registerIcon("RadioMod:playlist_180");
        this.topIcon[3] = register.registerIcon("RadioMod:playlist_270");
        this.sidesIcon = register.registerIcon("RadioMod:side_small");
        this.bottomIcon = register.registerIcon("RadioMod:side");
    }

    @Override
    public IIcon func_149691_a(int side, int meta){
        switch(side){
            case 0:
                return this.bottomIcon;
            case 1:
                return this.topIcon[meta];
            default:
                return this.sidesIcon;
        }
    }

    @Override
    public void func_149749_a(World world, int x, int y, int z, Block old, int oldMeta){
        super.func_149749_a(world, x, y, z, old, oldMeta);
        TileEntity tempTile = world.func_147438_o(x, y, z);
        if(tempTile == null || !(tempTile instanceof TileEntityPlaylist)){
            super.func_149749_a(world, x, y, z, old, oldMeta);
            return;
        }
        ((TileEntityPlaylist) tempTile).breakBlock();
    }

    @Override
    public boolean func_149727_a(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ){
        if(world.isRemote){
            return true;
        }

        TileEntity tempTile = world.func_147438_o(x, y, z);
        if(tempTile == null || !(tempTile instanceof TileEntityPlaylist)){
            return true;
        }
        TileEntityPlaylist playlist = (TileEntityPlaylist) tempTile;

        if(player.getHeldItem() == null){
            GuiOpener.openGuiOnClient(EnumGui.PLAYLIST_BLOCK, player, x, y, z);
        }else{
            if(!(player.getHeldItem().getItem() instanceof ItemRecord)){
                return true;
            }
            ItemRecord record = (ItemRecord) player.getHeldItem().getItem();

            if(playlist.addTitle(record.field_150929_a)){
                player.getHeldItem().stackSize--;
            }
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