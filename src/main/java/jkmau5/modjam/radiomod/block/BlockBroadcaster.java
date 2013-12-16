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
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**
 * Author: Lordmau5
 * Date: 13.12.13
 * Time: 16:02
 * You are allowed to change this code,
 * however, not to publish it without my permission.
 */
public class BlockBroadcaster extends Block {

    private Icon topIcon[];
    private Icon bottomIcon;
    private Icon sidesIcon;

    public BlockBroadcaster(int par1){
        super(par1, Material.iron);
        this.setCreativeTab(RadioMod.tabRadioMod);
        this.setUnlocalizedName("radiomod.BlockBroadcaster");

        this.maxY = 0.75f;
    }

    @Override
    public boolean renderAsNormalBlock(){
        return false;
    }

    @Override
    public boolean isOpaqueCube(){
        return false;
    }

    public boolean hasTileEntity(int metadata){
        return true;
    }

    public TileEntity createTileEntity(World world, int metadata){
        return new TileEntityBroadcaster();
    }

    @Override
    public void registerIcons(IconRegister register){
        this.topIcon = new Icon[4];
        this.topIcon[0] = register.registerIcon("RadioMod:broadcaster");
        this.topIcon[1] = register.registerIcon("RadioMod:broadcaster_90");
        this.topIcon[2] = register.registerIcon("RadioMod:broadcaster_180");
        this.topIcon[3] = register.registerIcon("RadioMod:broadcaster_270");
        this.bottomIcon = register.registerIcon("RadioMod:side");
        this.sidesIcon = register.registerIcon("RadioMod:side_small");
    }

    @Override
    public Icon getIcon(int side, int meta){
        switch(side){
            case 1:
                return this.topIcon[meta];
            case 0:
                return this.bottomIcon;
            default:
                return this.sidesIcon;
        }
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack){
        int meta = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
        world.setBlockMetadataWithNotify(x, y, z, meta, 2);
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ){
        super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ); //TODO: remove


        if(world.isRemote) return true;

        TileEntity tempTile = world.getBlockTileEntity(x, y, z);
        if(tempTile == null || !(tempTile instanceof TileEntityBroadcaster))
            return false;

        TileEntityBroadcaster radio = (TileEntityBroadcaster) tempTile;
        PacketDispatcher.sendPacketToPlayer(new PacketUpdateRadioName(x, y, z, world.provider.dimensionId, radio.getRadioName()).getPacket(), (Player) player);

        GuiOpener.openGuiOnClient(EnumGui.BROADCASTER_BLOCK, player, x, y, z);
        return true;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, int oldId, int oldMeta){
        if(world.isRemote)
            return;

        TileEntity tempTile = world.getBlockTileEntity(x, y, z);
        if(tempTile == null || !(tempTile instanceof TileEntityBroadcaster))
            return;

        super.breakBlock(world, x, y, z, oldId, oldMeta);
    }
}
