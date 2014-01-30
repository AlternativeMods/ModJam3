package jkmau5.modjam.radiomod.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import jkmau5.modjam.radiomod.Constants;
import jkmau5.modjam.radiomod.RadioMod;
import jkmau5.modjam.radiomod.client.render.ItemRendererAntenna;
import jkmau5.modjam.radiomod.client.render.TileEntityAntennaRenderer;
import jkmau5.modjam.radiomod.server.ProxyCommon;
import jkmau5.modjam.radiomod.tile.TileEntityAntenna;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class ProxyClient extends ProxyCommon {

    public static IModelCustom modelAntenna;

    public static TileEntityAntennaRenderer antennaRenderer;

    @Override
    public void init(){
        super.init();

        modelAntenna = AdvancedModelLoader.loadModel(new ResourceLocation("radiomod", "/models/Antenna.tcn"));

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAntenna.class, antennaRenderer = new TileEntityAntennaRenderer());

        MinecraftForgeClient.registerItemRenderer(Item.func_150898_a(RadioMod.instance.blockAntenna), new ItemRendererAntenna());

        Constants.initiateTitles();
    }
}
