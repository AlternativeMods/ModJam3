package jkmau5.modjam.radiomod.tile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jkmau5.modjam.radiomod.radio.RadioNetwork;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

/**
 * No description given
 *
 * @author jk-5
 */
public class TileEntityRadioNetwork extends TileEntity {

    private RadioNetwork network;

    @SideOnly(Side.CLIENT)
    public int getDistanceToPlayer(){
        return (int) Math.ceil(Minecraft.getMinecraft().thePlayer.getDistanceSq(this.xCoord, this.yCoord, this.zCoord));
    }

    @SideOnly(Side.CLIENT)
    public int getDistanceToCoords(int x, int y, int z){
        return (int) Math.ceil(this.getDistanceFrom(x, y, z));
    }

    public void onNeighborBlockChange(){

    }

    public void onBlockPlacedBy(EntityLivingBase ent, ItemStack is){

    }

    public void linkTo(TileEntityRadioNetwork newTile){
        this.linkedTileEntity = newTile;
    }
}
