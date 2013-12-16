package jkmau5.modjam.radiomod.tile;

import jkmau5.modjam.radiomod.RadioMod;
import jkmau5.modjam.radiomod.radio.RadioNetwork;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * No description given
 *
 * @author jk-5
 */
public class TileEntityRadioNetwork extends TileEntity {

    public RadioNetwork network;

    public int getDistanceToCoords(int x, int y, int z){
        return (int) Math.ceil(this.getDistanceFrom(x, y, z));
    }

    @Override
    public void readFromNBT(NBTTagCompound tag){
        super.readFromNBT(tag);
        if(tag.hasKey("networkID")){
            this.network = RadioMod.radioNetworkHandler.getNetworkFromID(tag.getInteger("networkID"));
            this.network.add(this);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag){
        super.writeToNBT(tag);
        if(this.network != null){
            tag.setInteger("networkID", this.network.getID());
        }
    }

    public boolean linkToTile(TileEntityRadioNetwork newTile){
        if(this.network == null && newTile.network == null){
            RadioNetwork network = new RadioNetwork();
            network.grabNextID();
            network.add(this);
            boolean ret = network.add(newTile);
            if(ret){
                RadioMod.radioNetworkHandler.addNetwork(network);
            }
            return ret;
        }else if(this.network == null){
            return newTile.network.add(this);
        }else if(newTile.network == null){
            return this.network.add(newTile);
        }else{
            return false; //TODO: remove this?
            /*if(this.network.getNetworkSize() >= newTile.network.getNetworkSize()){
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
            }*/
        }
    }
}
