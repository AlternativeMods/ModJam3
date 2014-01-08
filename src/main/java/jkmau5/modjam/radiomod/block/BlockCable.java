package jkmau5.modjam.radiomod.block;

import jkmau5.modjam.radiomod.Constants;
import jkmau5.modjam.radiomod.RadioMod;
import jkmau5.modjam.radiomod.client.ProxyClient;
import jkmau5.modjam.radiomod.tile.TileEntityAntenna;
import jkmau5.modjam.radiomod.tile.TileEntityBroadcaster;
import jkmau5.modjam.radiomod.tile.TileEntityCable;
import jkmau5.modjam.radiomod.tile.TileEntityPlaylist;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by matthias on 1/7/14.
 */
public class BlockCable extends Block{

    private Icon icon;

    public BlockCable(int par1){
        super(par1, Material.iron);
        this.setCreativeTab(RadioMod.tabRadioMod);
        this.setUnlocalizedName("radioMod.blockCable");
    }

    public void registerIcons(IconRegister par1IconRegister){
        this.blockIcon = par1IconRegister.registerIcon(Constants.MODID.toLowerCase() + ":" + getUnlocalizedName().substring(14));
    }

    public boolean hasTileEntity(int metadata){
        return true;
    }

    public TileEntity createTileEntity(World world, int metadata){
        return new TileEntityCable();
    }

    public int getRenderType(){
        return ProxyClient.renderID_Cable;
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ){
        if (world.isRemote) return true;

        TileEntity tempTile = world.getBlockTileEntity(x, y, z);
        if(tempTile == null || !(tempTile instanceof TileEntityCable)) return false;

        TileEntityCable cable = (TileEntityCable) tempTile;
        player.addChatMessage(cable.getNetwork().toString());

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

        if(isValidTileAtPosition(cable, world, x - 1, y, z)) minX = 0F;
        if(isValidTileAtPosition(cable, world, x + 1, y, z)) maxX = 1F;
        if(isValidTileAtPosition(cable, world, x, y - 1, z)) minY = 0F;
        if(isValidTileAtPosition(cable, world, x, y + 1, z)) minY = 1F;
        if(isValidTileAtPosition(cable, world, x, y, z - 1)) minZ = 0F;
        if(isValidTileAtPosition(cable, world, x, y, z + 1)) minZ = 1F;

        this.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axisAlignedBB, List list, Entity entity){
        setCableBoundingBox(world, x, y, z);
        AxisAlignedBB aabb = super.getCollisionBoundingBoxFromPool(world, x, y, z);
        if ((aabb != null) && (axisAlignedBB.intersectsWith(aabb))) {
            list.add(aabb);
        }
    }

    public boolean isValidTileAtPosition(TileEntityCable cable, IBlockAccess world, int x, int y, int z) {
        TileEntity tile = world.getBlockTileEntity(x, y, z);
        if (tile == null)
            return false;
        if (tile instanceof TileEntityCable)
            return true;
        if (tile instanceof TileEntityPlaylist)
            return true;
        if (tile instanceof TileEntityAntenna)
            return true;
        if (tile instanceof TileEntityBroadcaster){
            TileEntityBroadcaster broadcaster = (TileEntityBroadcaster) tile;
            if (broadcaster.getNetwork() == cable.getNetwork()) return true;
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
