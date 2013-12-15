package jkmau5.modjam.radiomod.tile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jkmau5.modjam.radiomod.radio.RadioNetwork;
import jkmau5.modjam.radiomod.radio.RadioNetworkHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * No description given
 *
 * @author jk-5
 */
public class TileEntityRadioNetwork extends TileEntity {

    public RadioNetwork network;

    @SideOnly(Side.CLIENT)
    public int getDistanceToPlayer(){
        return (int) Math.ceil(Minecraft.getMinecraft().thePlayer.getDistanceSq(this.xCoord, this.yCoord, this.zCoord));
    }

    @SideOnly(Side.CLIENT)
    public int getDistanceToCoords(int x, int y, int z){
        return (int) Math.ceil(this.getDistanceFrom(x, y, z));
    }

    @Override
    public void readFromNBT(NBTTagCompound tag){
        super.readFromNBT(tag);
        if(tag.hasKey("networkID")){
            this.network = RadioNetworkHandler.getNetworkFromID(tag.getInteger("networkID"));
            this.network.add(this);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag){
        super.writeToNBT(tag);
        if(this.network != null) tag.setInteger("networkID", this.network.getID());
    }

    public void linkToTile(TileEntityRadioNetwork newTile){
        if(this.network == null && newTile.network == null){
            RadioNetwork network = new RadioNetwork();
            network.grabNextID();
            network.add(this);
            network.add(newTile);
        }else if(this.network == null){
            newTile.network.add(this);
        }else if(newTile.network == null){
            this.network.add(newTile);
        }else{
            if(this.network.getNetworkSize() >= newTile.network.getNetworkSize()){
                //Move all the tiles in the other tile's network to my network
                for(int i = 0; i < newTile.network.getNetworkSize(); i++){
                    TileEntityRadioNetwork t = newTile.network.getNetworkTiles().get(i);
                    newTile.network.getNetworkTiles().remove(t);
                    this.network.add(t);
                    t.network = this.network;
                }
            }else{
                //Move all the tiles in this tile's network to the other tile's network
                for(int i = 0; i < this.network.getNetworkSize(); i++){
                    TileEntityRadioNetwork t = this.network.getNetworkTiles().get(i);
                    this.network.getNetworkTiles().remove(t);
                    newTile.network.add(t);
                    t.network = this.network;
                }
            }
        }
    }
}
