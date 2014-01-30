package jkmau5.modjam.radiomod.tile;

import cpw.mods.fml.common.FMLCommonHandler;
import jkmau5.modjam.radiomod.RadioMod;
import jkmau5.modjam.radiomod.radio.RadioNetwork;
import net.minecraft.nbt.NBTTagCompound;

/**
 * No description given
 *
 * @author jk-5
 */
public class TileEntityRadioNetwork extends RMTileEntity {

    public RadioNetwork network;

    public int getDistanceToCoords(int x, int y, int z){
        return (int) Math.ceil(this.func_145835_a(x, y, z));
    }

    @Override
    public void func_145839_a(NBTTagCompound tag){
        super.func_145839_a(tag);
        if(FMLCommonHandler.instance().getEffectiveSide().isServer() && tag.hasKey("networkID")){
            this.network = RadioMod.radioNetworkHandler.getNetworkFromID(tag.getInteger("networkID"));
            this.network.add(this);
        }
    }

    @Override
    public void func_145841_b(NBTTagCompound tag){
        super.func_145841_b(tag);
        if(FMLCommonHandler.instance().getEffectiveSide().isServer() && this.network != null){
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
        }
        return false;
    }
}
