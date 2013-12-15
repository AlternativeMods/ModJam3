package jkmau5.modjam.radiomod.tile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jkmau5.modjam.radiomod.radio.RadioNetwork;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

/**
 * No description given
 *
 * @author jk-5
 */
public class TileEntityRadioNetwork extends TileEntity {

    private RadioNetwork network;
    private boolean networkInitiated = false;

    public void initiateNetwork(){
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
    }

    @Override
    public void validate(){
        super.validate();
        this.networkInitiated = false;
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
}
