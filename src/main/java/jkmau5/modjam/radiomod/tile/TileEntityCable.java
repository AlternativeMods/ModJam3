package jkmau5.modjam.radiomod.tile;

import jkmau5.modjam.radiomod.util.CableConnections;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

/**
 * Author: Lordmau5
 * Date: 14.12.13
 * Time: 14:53
 * You are allowed to change this code,
 * however, not to publish it without my permission.
 */
public class TileEntityCable extends TileEntity {

    private CableConnections connections;

    public TileEntityCable() {
        this.connections = new CableConnections();
    }

    public CableConnections getConnections() {
        return this.connections;
    }

    public void onNeighborTileChange() {
        for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            TileEntity tile = worldObj.getBlockTileEntity(this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ);

            boolean connect = false;
            if(tile != null && tile instanceof TileEntityCable)
                connect = true;

            connections.setConnected(dir, connect);
        }
    }
}