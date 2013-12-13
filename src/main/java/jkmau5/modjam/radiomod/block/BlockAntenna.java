package jkmau5.modjam.radiomod.block;

import jkmau5.modjam.radiomod.client.ProxyClient;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * No description given
 *
 * @author jk-5
 */
public class BlockAntenna extends Block {

    public BlockAntenna(int par1){
        super(par1, Material.iron);

        this.setUnlocalizedName("radioMod.blockAntenna");
    }

    @Override
    public int getRenderType(){
        return ProxyClient.renderID_Antenna;
    }
}
