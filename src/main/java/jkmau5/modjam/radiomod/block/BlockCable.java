package jkmau5.modjam.radiomod.block;

import jkmau5.modjam.radiomod.Constants;
import jkmau5.modjam.radiomod.RadioMod;
import jkmau5.modjam.radiomod.client.ProxyClient;
import jkmau5.modjam.radiomod.tile.TileEntityCable;
import jkmau5.modjam.radiomod.tile.TileEntityRadio;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

/**
 * Author: Lordmau5
 * Date: 14.12.13
 * Time: 14:52
 * You are allowed to change this code,
 * however, not to publish it without my permission.
 */
public class BlockCable extends Block {

    public BlockCable(int par1) {
        super(par1, Material.iron);
        setCreativeTab(RadioMod.tabRadioMod);
        setUnlocalizedName("blockCable");
    }

    public void registerIcons(IconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon(Constants.MODID.toLowerCase() + ":" + getUnlocalizedName().substring(5));
    }

    public boolean hasTileEntity(int metadata) {
        return true;
    }

    public TileEntity createTileEntity(World world, int metadata) {
        return new TileEntityCable();
    }

    @Override
    public int getRenderType(){
        return ProxyClient.renderID_Cable;
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if(world.isRemote)
            return false;

        TileEntity tempTile = world.getBlockTileEntity(x, y, z);
        if(tempTile == null || !(tempTile instanceof TileEntityCable))
            return false;

        TileEntityCable cable = (TileEntityCable) tempTile;
        player.addChatMessage(cable.getNetwork().toString());

        return true;
    }

    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase ent, ItemStack is) {
        super.onBlockPlacedBy(world, x, y, z, ent, is);

        TileEntityCable cable = (TileEntityCable) world.getBlockTileEntity(x, y, z);
        if(cable == null)
            return;

        cable.onNeighborTileChange();
        cable.tryMergeWithNeighbors();
    }

    public void onNeighborTileChange(World world, int x, int y, int z, int tileX, int tileY, int tileZ) {
        TileEntityCable cable = (TileEntityCable) world.getBlockTileEntity(x, y, z);
        if(cable == null)
            return;

        if(world.isRemote)
            cable.onNeighborTileChange();
    }

    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        setCableBoundingBox(world, x, y, z);
    }

    public void setCableBoundingBox(IBlockAccess world, int x, int y, int z) {
        float minX = 0.4F;
        float minY = 0.4F;
        float minZ = 0.4F;

        float maxX = 0.6F;
        float maxY = 0.6F;
        float maxZ = 0.6F;

        if(isValidTileAtPosition(world, x - 1, y, z))
            minX = 0F;
        if(isValidTileAtPosition(world, x + 1, y, z))
            maxX = 1F;

        if(isValidTileAtPosition(world, x, y - 1, z))
            minY = 0F;
        if(isValidTileAtPosition(world, x, y + 1, z))
            maxY = 1F;

        if(isValidTileAtPosition(world, x, y, z - 1))
            minZ = 0F;
        if(isValidTileAtPosition(world, x, y, z + 1))
            maxZ = 1F;

        this.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axisAlignedBB, List list, Entity entity) {
        setCableBoundingBox(world, x, y, z);
        AxisAlignedBB aabb = super.getCollisionBoundingBoxFromPool(world, x, y, z);
        if ((aabb != null) && (axisAlignedBB.intersectsWith(aabb))) {
            list.add(aabb);
        }
    }

    public boolean isValidTileAtPosition(IBlockAccess world, int x, int y, int z) {
        TileEntity tile = world.getBlockTileEntity(x, y, z);
        if(tile == null)
            return false;
        if(tile instanceof TileEntityCable || tile instanceof TileEntityRadio)
            return true;
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public boolean isBlockNormalCube(World world, int x, int y, int z) {
        return false;
    }

    public boolean isOpaqueCube() {
        return false;
    }
}