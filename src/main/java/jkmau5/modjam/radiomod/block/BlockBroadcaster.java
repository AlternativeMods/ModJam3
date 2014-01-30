package jkmau5.modjam.radiomod.block;

import jkmau5.modjam.radiomod.RadioMod;
import jkmau5.modjam.radiomod.tile.TileEntityBroadcaster;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockBroadcaster extends Block {

    private IIcon topIcon[];
    private IIcon bottomIcon;
    private IIcon sidesIcon;

    public BlockBroadcaster(){
        super(Material.field_151573_f);
        this.func_149647_a(RadioMod.tabRadioMod);
        this.func_149663_c("radioMod.blockBroadcaster");

        this.field_149756_F = 0.75f;
    }

    @Override
    public boolean func_149686_d(){
        return false;
    }

    @Override
    public boolean func_149662_c(){
        return false;
    }

    public boolean hasTileEntity(int metadata){
        return true;
    }

    public TileEntity createTileEntity(World world, int metadata){
        return new TileEntityBroadcaster();
    }

    @Override
    public void func_149651_a(IIconRegister register){
        this.topIcon = new IIcon[4];
        this.topIcon[0] = register.registerIcon("RadioMod:broadcaster");
        this.topIcon[1] = register.registerIcon("RadioMod:broadcaster_90");
        this.topIcon[2] = register.registerIcon("RadioMod:broadcaster_180");
        this.topIcon[3] = register.registerIcon("RadioMod:broadcaster_270");
        this.bottomIcon = register.registerIcon("RadioMod:side");
        this.sidesIcon = register.registerIcon("RadioMod:side_small");
    }

    @Override
    public IIcon func_149691_a(int side, int meta){
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
    public void func_149689_a(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack){
        int meta = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
        world.setBlockMetadataWithNotify(x, y, z, meta, 2);
    }

    public boolean func_149727_a(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ){
        super.func_149727_a(world, x, y, z, player, side, hitX, hitY, hitZ); //TODO: remove

        if(world.isRemote) return true;

        TileEntity tempTile = world.func_147438_o(x, y, z);
        if(tempTile == null || !(tempTile instanceof TileEntityBroadcaster)){
            return false;
        }

        TileEntityBroadcaster radio = (TileEntityBroadcaster) tempTile;
        //TODO: Rewrite GUI stuff
        //PacketDispatcher.sendPacketToPlayer(new PacketUpdateRadioName(x, y, z, world.provider.dimensionId, radio.getRadioName()).getPacket(), (Player) player);

        //GuiOpener.openGuiOnClient(EnumGui.BROADCASTER_BLOCK, player, x, y, z);
        return true;
    }

    @Override
    public void func_149749_a(World world, int x, int y, int z, Block oldBlock, int oldMeta){
        if(world.isRemote){
            return;
        }

        TileEntity tempTile = world.func_147438_o(x, y, z);
        if(tempTile == null || !(tempTile instanceof TileEntityBroadcaster)){
            return;
        }
        super.func_149749_a(world, x, y, z, oldBlock, oldMeta);
    }
}
