package jkmau5.modjam.radiomod;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import jkmau5.modjam.radiomod.block.BlockRadio;
import jkmau5.modjam.radiomod.item.ItemMediaPlayer;
import jkmau5.modjam.radiomod.tile.TileEntityRadio;
import net.minecraft.creativetab.CreativeTabs;

@Mod(modid = RadioMod.MODID)
public class RadioMod {

    public static final String MODID = "RadioMod";
    public BlockRadio blockRadio;
    public ItemMediaPlayer itemMediaPlayer;

    public CreativeTabs tabRadioMod = new CreativeTabs("RadioMod");

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        blockRadio = new BlockRadio(2500);
        GameRegistry.registerBlock(blockRadio, "BlockRadio");

        GameRegistry.registerTileEntity(TileEntityRadio.class, "TileRadio");

        //--------

        itemMediaPlayer = new ItemMediaPlayer(5000);
        GameRegistry.registerItem(itemMediaPlayer, "ItemMediaPlayer");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        LanguageRegistry.addName(blockRadio, "Radio Block");
        LanguageRegistry.addName(itemMediaPlayer, "Media Player");
        LanguageRegistry.addName("itemGroup.RadioMod", "Radio Mod");

        TickRegistry.registerTickHandler(new RadioTickHandler(), Side.SERVER);
    }
}
