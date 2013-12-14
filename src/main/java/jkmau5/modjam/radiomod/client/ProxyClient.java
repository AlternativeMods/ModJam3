package jkmau5.modjam.radiomod.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import jkmau5.modjam.radiomod.Constants;
import jkmau5.modjam.radiomod.client.render.BlockCableRenderer;
import jkmau5.modjam.radiomod.client.render.TileEntityAntennaRenderer;
import jkmau5.modjam.radiomod.server.ProxyCommon;
import jkmau5.modjam.radiomod.tile.TileEntityAntenna;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

/**
 * No description given
 *
 * @author jk-5
 */
public class ProxyClient extends ProxyCommon {

    public static final int renderID_Cable = RenderingRegistry.getNextAvailableRenderId();
    public static IModelCustom modelAntenna;

    @Override
    public void init(){
        super.init();

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAntenna.class, new TileEntityAntennaRenderer());
        RenderingRegistry.registerBlockHandler(new BlockCableRenderer());
        modelAntenna = AdvancedModelLoader.loadModel(Constants.MODID.toLowerCase() + ":models/Antenna.tcn");
    }
}
