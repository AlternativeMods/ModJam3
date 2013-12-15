package jkmau5.modjam.radiomod.tile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jkmau5.modjam.radiomod.radio.RadioNetwork;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

/**
 * No description given
 *
 * @author jk-5
 */
public class TileEntityRadioNetwork extends TileEntity {

    private RadioNetwork network;
    private boolean networkInitiated = false;
    private boolean merged = false;

    public void initiateNetwork(){
        if(this.networkInitiated) this.destroyNetwork();
        this.network = new RadioNetwork(this);
        this.networkInitiated = true;
    }

    public void destroyNetwork(){
        if(!this.networkInitiated) return;
        this.network.remove(this);
        this.network = null;
        this.networkInitiated = false;
    }

    @Override
    public void updateEntity(){
        super.updateEntity();

        if(!this.networkInitiated){
            this.initiateNetwork();
        }
        if(!this.merged){
            this.tryMergeNeighborNetworks();
            this.merged = true;
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

    @Override
    public void validate(){
        super.validate();
        this.initiateNetwork();
    }

    @Override
    public void invalidate(){
        super.invalidate();
        this.destroyNetwork();
    }

    @Override
    public void onChunkUnload(){
        super.onChunkUnload();
        this.destroyNetwork();
    }

    public void setNetwork(RadioNetwork network){
        this.network = network;
    }

    public boolean isNetworkInitiated(){
        return networkInitiated;
    }

    public RadioNetwork getNetwork(){
        return this.network;
    }

    @SideOnly(Side.CLIENT)
    public int getDistanceToPlayer(){
        return (int) Math.ceil(Minecraft.getMinecraft().thePlayer.getDistanceSq(this.xCoord, this.yCoord, this.zCoord));
    }

    @SideOnly(Side.CLIENT)
    public int getDistanceToCoords(int x, int y, int z){
        return (int) Math.ceil(this.getDistanceFrom(x, y, z));
    }

    public void onNeighborBlockChange(){
        for(int i = 0; i < this.network.getNetworkTiles().size(); i++){
            TileEntityRadioNetwork tile = this.network.getNetworkTiles().get(i);
            tile.initiateNetwork();
            tile.tryMergeNeighborNetworks();
        }
        //this.tryMergeNeighborNetworks();
    }

    public void onBlockPlacedBy(EntityLivingBase ent, ItemStack is){

    }
}
