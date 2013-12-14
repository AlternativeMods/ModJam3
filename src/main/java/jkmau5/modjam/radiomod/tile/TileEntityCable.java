package jkmau5.modjam.radiomod.tile;

import jkmau5.modjam.radiomod.network.RadioNetwork;
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
    private RadioNetwork network;

    public TileEntityCable(){
        this.connections = new CableConnections();
        this.network = new RadioNetwork(this);
    }

    public void setNetwork(RadioNetwork network){
        this.network = network;
    }

    public CableConnections getConnections(){
        return this.connections;
    }

    public void onNeighborTileChange(){
        for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS){
            TileEntity tile = worldObj.getBlockTileEntity(this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ);

            boolean connect = false;
            if(isValidTile(tile))
                connect = true;

            connections.setConnected(dir, connect);
        }
    }

    private boolean isValidTile(TileEntity tile){
        if(tile == null)
            return false;
        if(tile instanceof TileEntityCable || tile instanceof TileEntityBroadcaster)
            return true;
        return false;
    }
}