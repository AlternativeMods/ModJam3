package jkmau5.modjam.radiomod.block;

import jkmau5.modjam.radiomod.Constants;
import jkmau5.modjam.radiomod.RadioMod;
import jkmau5.modjam.radiomod.client.ProxyClient;
import jkmau5.modjam.radiomod.radio.IRadioCable;
import jkmau5.modjam.radiomod.tile.TileEntityAntenna;
import jkmau5.modjam.radiomod.tile.TileEntityBroadcaster;
import jkmau5.modjam.radiomod.tile.TileEntityCable;
import jkmau5.modjam.radiomod.tile.TileEntityPlaylist;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

import java.util.List;

/**
 * Created by matthias on 1/7/14.
 *
 * The cables used to create networks
 */
public class BlockCable extends Block{

    private Icon icon;

    public BlockCable(int par1){
        super(par1, Material.iron);
        this.setCreativeTab(RadioMod.tabRadioMod);
        this.setUnlocalizedName("radioMod.blockCable");
    }

    public void registerIcons(IconRegister par1IconRegister){
        this.blockIcon = par1IconRegister.registerIcon("RadioMod:energizedcable");
    }

    public boolean hasTileEntity(int metadata){
        return true;
    }

    public TileEntity createTileEntity(World world, int metadata){
        return new TileEntityCable();
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase ent, ItemStack is){
        TileEntity tile = world.getBlockTileEntity(x,y,z);
        ((IRadioCable) tile).updateCable(x, y, z);
        ((TileEntityCable) tile).refreshConnections();
        super.onBlockPlacedBy(world, x, y, z, ent, is);
    }

    public int getRenderType(){
        return ProxyClient.renderID_Cable;
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ){
        return false;
    }

    public void breakBlock(World world, int x, int y, int z, int oldId, int oldMeta){
        TileEntity tempTile = world.getBlockTileEntity(x, y, z);
        if(tempTile == null || !(tempTile instanceof TileEntityCable)){
            super.breakBlock(world, x, y, z, oldId, oldMeta);
            return;
        }

        TileEntityCable cable = (TileEntityCable) tempTile;
        cable.removeCable(x, y, z);

        super.breakBlock(world, x, y, z, oldId, oldMeta);
    }

    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z){
        this.setCableBoundingBox(world, x, y, z);
    }

    public void setCableBoundingBox(IBlockAccess world, int x, int y, int z){
        TileEntity tile = world.getBlockTileEntity(x, y, z);
        if(tile == null || !(tile instanceof TileEntityCable)) return;
        TileEntityCable cable = (TileEntityCable) tile;

        float minX = 0.375F;
        float minY = 0.375F;
        float minZ = 0.375F;

        float maxX = 0.625F;
        float maxY = 0.625F;
        float maxZ = 0.625F;
        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS){
            if (isValidTileAtPosition(cable, cable.getWorldObj(), x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ)){
                if (dir == ForgeDirection.UP)
                    maxY = 1F;
                else if (dir == ForgeDirection.DOWN)
                    minY = 0F;
                else if(dir == ForgeDirection.NORTH)
                    minZ = 0F;
                else if(dir == ForgeDirection.SOUTH)
                    maxZ = 1F;
                else if(dir == ForgeDirection.EAST)
                    maxX = 1F;
                else if(dir == ForgeDirection.WEST)
                    minX = 0F;
            }
        }

        this.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axisAlignedBB, List list, Entity entity){
        setCableBoundingBox(world, x, y, z);
        AxisAlignedBB aabb = super.getCollisionBoundingBoxFromPool(world, x, y, z);
        if ((aabb != null) && (axisAlignedBB.intersectsWith(aabb))) {
            list.add(aabb);
        }
    }

    public boolean isValidTileAtPosition(TileEntityCable cable, IBlockAccess world, int x, int y, int z){
        TileEntity tile = world.getBlockTileEntity(x, y, z);
        if (tile == null)
            return false;
        if (tile instanceof IRadioCable){
            if(((IRadioCable) tile).getCableID() != cable.getCableID() && ((TileEntityCable) tile).getCableID() != "") return false;
            return true;}
        if (tile instanceof TileEntityBroadcaster){
            TileEntityBroadcaster broadcaster = (TileEntityBroadcaster) tile;
            if (broadcaster.getRadioID() == cable.getCableID() || cable.getCableID() == "") return true;
        }
        return false;
    }

    public boolean renderAsNormalBlock(){
        return false;
    }

    public boolean isBlockNormalCube(World world, int x, int y, int z){
        return false;
    }

    public boolean isOpaqueCube(){
        return false;
    }
}
