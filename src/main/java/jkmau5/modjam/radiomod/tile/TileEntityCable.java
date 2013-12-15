package jkmau5.modjam.radiomod.tile;

import jkmau5.modjam.radiomod.radio.ICable;
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
public class TileEntityCable extends TileEntityRadioNetwork implements ICable {

    public final CableConnections connections = new CableConnections();
    private boolean initiated = false;

    public void updateEntity(){
        super.updateEntity();
        if(!this.initiated){
            this.initiated = true;
            tryMergeNeighborNetworks();
        }
    }

    public void tryMergeNeighborNetworks(){
        for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS){
            TileEntity tile = this.worldObj.getBlockTileEntity(this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ);
            if(tile == null) continue;
            if(tile instanceof TileEntityCable && ((TileEntityCable) tile).getNetwork() != null){
                ((TileEntityCable) tile).getNetwork().mergeWithNetwork(getNetwork());
            }else if(tile instanceof TileEntityBroadcaster && ((TileEntityBroadcaster) tile).getNetwork() == null){
                getNetwork().setBroadcaster((TileEntityBroadcaster) tile);
            }
        }
    }

    private boolean isValidTile(TileEntity tile){
        if(tile == null || getNetwork() == null) return false;
        if(tile instanceof TileEntityBroadcaster){
            TileEntityBroadcaster broadcaster = (TileEntityBroadcaster) tile;
            if(getNetwork().getBroadcaster() == broadcaster)
                return true;
        }
        return tile instanceof TileEntityRadioNetwork;
    }

    public void refreshConnections(){
        for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS){
            TileEntity tile = this.worldObj.getBlockTileEntity(this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ);

            boolean connect = isValidTile(tile);

            this.connections.setConnected(dir, connect);

            if(tile instanceof TileEntityBroadcaster){
                if(getNetwork() != null && getNetwork().setBroadcaster((TileEntityBroadcaster) tile)){
                    worldObj.markBlockForRenderUpdate(this.xCoord, this.yCoord, this.zCoord);
                }
            }
        }
    }
}