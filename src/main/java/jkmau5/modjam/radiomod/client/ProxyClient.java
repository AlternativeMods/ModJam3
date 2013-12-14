package jkmau5.modjam.radiomod.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import jkmau5.modjam.radiomod.Constants;
import jkmau5.modjam.radiomod.RadioMod;
import jkmau5.modjam.radiomod.client.render.BlockCableRenderer;
import jkmau5.modjam.radiomod.client.render.ItemRendererAntenna;
import jkmau5.modjam.radiomod.client.render.TileEntityAntennaRenderer;
import jkmau5.modjam.radiomod.server.ProxyCommon;
import jkmau5.modjam.radiomod.tile.TileEntityAntenna;
import net.minecraftforge.client.MinecraftForgeClient;
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

    public static TileEntityAntennaRenderer antennaRenderer;

    @Override
    public void init(){
        super.init();

        modelAntenna = AdvancedModelLoader.loadModel("/assets/" + Constants.MODID.toLowerCase() + "/models/Antenna.tcn");

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAntenna.class, antennaRenderer = new TileEntityAntennaRenderer());

        RenderingRegistry.registerBlockHandler(new BlockCableRenderer());

        MinecraftForgeClient.registerItemRenderer(RadioMod.instance.blockAntenna.blockID, new ItemRendererAntenna());
    }
}
