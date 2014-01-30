package jkmau5.modjam.radiomod;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;
import jkmau5.modjam.radiomod.block.BlockAntenna;
import jkmau5.modjam.radiomod.block.BlockBroadcaster;
import jkmau5.modjam.radiomod.block.BlockPlaylist;
import jkmau5.modjam.radiomod.block.BlockRadio;
import jkmau5.modjam.radiomod.item.ItemIngredient;
import jkmau5.modjam.radiomod.item.ItemLinkCard;
import jkmau5.modjam.radiomod.item.ItemMediaPlayer;
import jkmau5.modjam.radiomod.network.NetworkHandler;
import jkmau5.modjam.radiomod.radio.RadioNetworkHandler;
import jkmau5.modjam.radiomod.server.ProxyCommon;
import jkmau5.modjam.radiomod.tile.TileEntityAntenna;
import jkmau5.modjam.radiomod.tile.TileEntityBroadcaster;
import jkmau5.modjam.radiomod.tile.TileEntityPlaylist;
import jkmau5.modjam.radiomod.tile.TileEntityRadio;
import jkmau5.modjam.radiomod.world.gen.structure.VillageStudioHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import java.util.Random;

@Mod(modid = Constants.MODID, name = Constants.MODID, version = "0.3-SNAPSHOT")
public class RadioMod {

    public BlockBroadcaster blockBroadcaster;
    public BlockAntenna blockAntenna;
    public ItemMediaPlayer itemMediaPlayer;
    public ItemIngredient itemIngredient;
    public ItemLinkCard itemLinkCard;
    public BlockRadio blockRadio;
    public BlockPlaylist blockPlaylist;

    @Mod.Instance(Constants.MODID)
    public static RadioMod instance;

    public static RadioNetworkHandler radioNetworkHandler;

    public static final CreativeTabs tabRadioMod = new CreativeTabs("RadioMod") {
        @Override
        public Item getTabIconItem(){
            return Item.func_150898_a(RadioMod.instance.blockAntenna);
        }
    };

    @SidedProxy(modId = Constants.MODID, clientSide = "jkmau5.modjam.radiomod.client.ProxyClient", serverSide = "jkmau5.modjam.radiomod.server.ProxyCommon")
    public static ProxyCommon proxy;

    public static String getUniqueRadioID(){
        Random random = new Random();
        return "Radio-" + (random.nextInt(8999999) + 1000000);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        Config.load(event.getSuggestedConfigurationFile());

        NetworkHandler.registerChannels(event.getSide());

        blockBroadcaster = new BlockBroadcaster();
        blockAntenna = new BlockAntenna();
        blockRadio = new BlockRadio();
        blockPlaylist = new BlockPlaylist();
        GameRegistry.registerBlock(blockBroadcaster, "BlockBroadcaster");
        GameRegistry.registerBlock(blockAntenna, "BlockAntenna");
        GameRegistry.registerBlock(blockRadio, "BlockRadio");
        GameRegistry.registerBlock(blockPlaylist, "BlockPlaylist");

        GameRegistry.registerTileEntity(TileEntityBroadcaster.class, "TileBroadcaster");
        GameRegistry.registerTileEntity(TileEntityAntenna.class, "TileAntenna");
        GameRegistry.registerTileEntity(TileEntityRadio.class, "TileRadio");
        GameRegistry.registerTileEntity(TileEntityPlaylist.class, "TilePlaylist");

        proxy.preInit();

        VillagerRegistry.instance().registerVillageCreationHandler(new VillageStudioHandler());

        try{
            //MapGenStructureIO.func_143031_a(ComponentVillageStudio.class, "RadioMod:VillageStudioStructure");
        }catch(Throwable e){
            RMLogger.error(e, "There was a problem while registering the VillageStudio component");
        }

        itemMediaPlayer = new ItemMediaPlayer();
        itemIngredient = new ItemIngredient();
        itemLinkCard = new ItemLinkCard();
        GameRegistry.registerItem(itemMediaPlayer, "ItemMediaPlayer");
        GameRegistry.registerItem(itemIngredient, "ItemIngredient");
        GameRegistry.registerItem(itemLinkCard, "ItemLinkCard");

        Recipes.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        proxy.init();
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerAboutToStartEvent event){
        radioNetworkHandler = new RadioNetworkHandler();
    }

    @Mod.EventHandler
    public void serverStopping(FMLServerStoppedEvent event){
        radioNetworkHandler = null;
    }
}
