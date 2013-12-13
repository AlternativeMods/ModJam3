package jkmau5.modjam.radiomod;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import jkmau5.modjam.radiomod.block.BlockRadio;
import jkmau5.modjam.radiomod.item.ItemMediaPlayer;
import jkmau5.modjam.radiomod.network.RadioWorldHandler;
import jkmau5.modjam.radiomod.server.ProxyCommon;
import jkmau5.modjam.radiomod.tile.TileEntityRadio;
import net.minecraft.creativetab.CreativeTabs;

@Mod(modid = RadioMod.MODID)
public class RadioMod {

    public static final String MODID = "RadioMod";
    public BlockRadio blockRadio;
    public ItemMediaPlayer itemMediaPlayer;

    public static RadioWorldHandler radioWorldHandler;

    public static final CreativeTabs tabRadioMod = new CreativeTabs("RadioMod");

    @SidedProxy(modId = RadioMod.MODID, clientSide = "jkmau5.modjam.radiomod.client.ProxyClient", serverSide = "jkmau5.modjam.radiomod.server.ProxyCommon")
    public static ProxyCommon proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        blockRadio = new BlockRadio(2500);
        GameRegistry.registerBlock(blockRadio, "BlockRadio");

        GameRegistry.registerTileEntity(TileEntityRadio.class, "TileRadio");

        //--------

        itemMediaPlayer = new ItemMediaPlayer(5000);
        GameRegistry.registerItem(itemMediaPlayer, "ItemMediaPlayer");

        //--------
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        LanguageRegistry.addName(blockRadio, "Radio Block");
        LanguageRegistry.addName(itemMediaPlayer, "Media Player");

        TickRegistry.registerTickHandler(new RadioTickHandler(), Side.SERVER);
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartedEvent event) {
        radioWorldHandler = new RadioWorldHandler();
    }
}
