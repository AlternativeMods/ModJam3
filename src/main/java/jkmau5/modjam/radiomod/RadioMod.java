package jkmau5.modjam.radiomod;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import jkmau5.modjam.radiomod.block.BlockAntenna;
import jkmau5.modjam.radiomod.block.BlockBroadcaster;
import jkmau5.modjam.radiomod.block.BlockCable;
import jkmau5.modjam.radiomod.item.ItemIngredient;
import jkmau5.modjam.radiomod.item.ItemMediaPlayer;
import jkmau5.modjam.radiomod.network.PacketHandler;
import jkmau5.modjam.radiomod.network.RadioWorldHandler;
import jkmau5.modjam.radiomod.server.ProxyCommon;
import jkmau5.modjam.radiomod.tile.TileEntityAntenna;
import jkmau5.modjam.radiomod.tile.TileEntityBroadcaster;
import jkmau5.modjam.radiomod.tile.TileEntityCable;
import net.minecraft.creativetab.CreativeTabs;

import java.util.Random;

@Mod(modid = Constants.MODID)
@NetworkMod(clientSideRequired = true, channels = {Constants.MODID}, packetHandler = PacketHandler.class)
public class RadioMod {

    public BlockBroadcaster blockBroadcaster;
    public BlockAntenna blockAntenna;
    public BlockCable blockCable;
    public ItemMediaPlayer itemMediaPlayer;
    public ItemIngredient itemIngredient;

    @Mod.Instance(Constants.MODID)
    public static RadioMod instance;

    public static RadioWorldHandler radioWorldHandler;

    public static final CreativeTabs tabRadioMod = new CreativeTabs("RadioMod");

    @SidedProxy(modId = Constants.MODID, clientSide = "jkmau5.modjam.radiomod.client.ProxyClient", serverSide = "jkmau5.modjam.radiomod.server.ProxyCommon")
    public static ProxyCommon proxy;

    public static String getUniqueRadioID(){
        Random random = new Random();
        return "Radio-" + (random.nextInt(8999999) + 1000000);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        blockBroadcaster = new BlockBroadcaster(2500);
        blockAntenna = new BlockAntenna(2501);
        blockCable = new BlockCable(2502);
        GameRegistry.registerBlock(blockBroadcaster, "BlockBroadcaster");
        GameRegistry.registerBlock(blockAntenna, "BlockAntenna");
        GameRegistry.registerBlock(blockCable, "BlockCable");

        GameRegistry.registerTileEntity(TileEntityBroadcaster.class, "TileBroadcaster");
        GameRegistry.registerTileEntity(TileEntityCable.class, "TileCable");
        GameRegistry.registerTileEntity(TileEntityAntenna.class, "TileAntenna");

        proxy.preInit();

        itemMediaPlayer = new ItemMediaPlayer(5000);
        itemIngredient = new ItemIngredient(5001);
        GameRegistry.registerItem(itemMediaPlayer, "ItemMediaPlayer");
        GameRegistry.registerItem(itemIngredient, "ItemIngredient");

        Recipes.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        LanguageRegistry.addName(blockBroadcaster, "Radio Block");
        LanguageRegistry.addName(itemMediaPlayer, "Media Player");

        proxy.init();

        TickRegistry.registerTickHandler(new RadioTickHandler(), Side.SERVER); //TODO: remove?
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartedEvent event){
        radioWorldHandler = new RadioWorldHandler();
    }

    @Mod.EventHandler
    public void serverStopping(FMLServerStoppedEvent event){
        radioWorldHandler = null;
    }
}
