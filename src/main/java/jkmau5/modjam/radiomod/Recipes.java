package jkmau5.modjam.radiomod;

import jkmau5.modjam.radiomod.item.EnumIngredient;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;

public class Recipes {

    public static void init(){
        //Antenna
        addShaped(EnumIngredient.RAWANTENNAROD.getStack(1), "  i", " i ", "i  ", 'i', Items.iron_ingot);
        addSmelting(EnumIngredient.RAWANTENNAROD.getStack(1), EnumIngredient.ANTENNAROD.getStack(1), 0f);
        addShaped(EnumIngredient.ANTENNAFOOT.getStack(1), "sis", "sis", "sis", 'i', Items.iron_ingot, 's', Blocks.stone);
        addShaped(EnumIngredient.BIGANTENNAFOOT.getStack(1), "r", "r", "r", 'r', EnumIngredient.ANTENNAFOOT.getStack(1));
        addShaped(EnumIngredient.ANTENNAONFOOT.getStack(1), "prr", "f  ", "f  ", 'f', EnumIngredient.BIGANTENNAFOOT.getStack(1), 'p', Items.ender_pearl, 'r', EnumIngredient.ANTENNAROD.getStack(1));
        addShaped(new ItemStack(RadioMod.instance.blockAntenna, 1), "rrr", "rar", "rrr", 'r', EnumIngredient.ANTENNAROD.getStack(1), 'a', EnumIngredient.ANTENNAONFOOT.getStack(1));

        addShaped(new ItemStack(RadioMod.instance.blockBroadcaster, 1), "iri", "rdr", "iri", 'd', Blocks.diamond_block, 'i', Items.iron_ingot, 'r', Items.redstone);
        addShaped(new ItemStack(RadioMod.instance.blockPlaylist, 1), "dgd", "cjc", "dgd", 'd', Items.diamond, 'g', Items.gold_ingot, 'c', Blocks.chest, 'j', Blocks.jukebox);
        addShaped(new ItemStack(RadioMod.instance.blockRadio, 1), "iai", "iji", "iii", 'i', Items.iron_ingot, 'a', EnumIngredient.ANTENNAROD.getStack(1), 'j', Blocks.jukebox);
        addShaped(new ItemStack(RadioMod.instance.itemLinkCard, 1), "ppp", "rir", "ppp", 'i', Items.iron_ingot, 'p', Items.paper, 'r', Items.redstone);
    }

    private static void addShaped(ItemStack output, Object... data){
        CraftingManager.getInstance().addRecipe(output, data);
    }

    private static void addSmelting(ItemStack input, ItemStack output, float exp){
        FurnaceRecipes.smelting().func_151394_a(input, output, exp);
    }
}
