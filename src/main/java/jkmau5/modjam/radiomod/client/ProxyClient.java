package jkmau5.modjam.radiomod.client;

import cpw.mods.fml.client.registry.RenderingRegistry;
import jkmau5.modjam.radiomod.Constants;
import jkmau5.modjam.radiomod.client.render.RenderBlockAntenna;
import jkmau5.modjam.radiomod.server.ProxyCommon;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

/**
 * No description given
 *
 * @author jk-5
 */
public class ProxyClient extends ProxyCommon {

    public static final int renderID_Antenna = RenderingRegistry.getNextAvailableRenderId();
    public static IModelCustom blockAntenna;

    @Override
    public void init(){
        super.init();

        RenderingRegistry.registerBlockHandler(new RenderBlockAntenna());
        blockAntenna = AdvancedModelLoader.loadModel(Constants.MODID + ":/models/Antenna.tcn");
    }
}
