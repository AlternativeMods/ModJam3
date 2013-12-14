package jkmau5.modjam.radiomod.client;

import cpw.mods.fml.client.registry.RenderingRegistry;
import jkmau5.modjam.radiomod.client.render.BlockCableRenderer;
import jkmau5.modjam.radiomod.server.ProxyCommon;
import net.minecraftforge.client.model.IModelCustom;

/**
 * No description given
 *
 * @author jk-5
 */
public class ProxyClient extends ProxyCommon {

    public static final int renderID_Antenna = RenderingRegistry.getNextAvailableRenderId();
    public static final int renderID_Cable = RenderingRegistry.getNextAvailableRenderId();
    public static IModelCustom modelAntenna;

    @Override
    public void init(){
        super.init();

        //RenderingRegistry.registerBlockHandler(new RenderBlockAntenna());
        RenderingRegistry.registerBlockHandler(new BlockCableRenderer());
        //modelAntenna = AdvancedModelLoader.loadModel("assets/" + Constants.MODID.toLowerCase() + "/models/Antenna.tcn");
    }
}
