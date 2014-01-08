package jkmau5.modjam.radiomod;

import jkmau5.modjam.radiomod.item.EnumIngredient;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;

public class Recipes {

    public static void init(){
        //Antenna
        addShaped(EnumIngredient.RAWANTENNAROD.getStack(1), "  i", " i ", "i  ", 'i', Item.ingotIron);
        addSmelting(EnumIngredient.RAWANTENNAROD.getStack(1), EnumIngredient.ANTENNAROD.getStack(1), 0f);
        addShaped(EnumIngredient.ANTENNAFOOT.getStack(1), "sis", "sis", "sis", 'i', Item.ingotIron, 's', Block.stone);
        addShaped(EnumIngredient.BIGANTENNAFOOT.getStack(1), "r", "r", "r", 'r', EnumIngredient.ANTENNAFOOT.getStack(1));
        addShaped(EnumIngredient.ANTENNAONFOOT.getStack(1), "prr", "f  ", "f  ", 'f', EnumIngredient.BIGANTENNAFOOT.getStack(1), 'p', Item.enderPearl, 'r', EnumIngredient.ANTENNAROD.getStack(1));
        addShaped(new ItemStack(RadioMod.instance.blockAntenna, 1), "rrr", "rar", "rrr", 'r', EnumIngredient.ANTENNAROD.getStack(1), 'a', EnumIngredient.ANTENNAONFOOT.getStack(1));

        //Cable
        addShaped(EnumIngredient.ENERGIZEDSTRING.getStack(1), " r ", "rsr", " r ", 'r', Item.redstone, 's', Item.silk);
        addShaped(new ItemStack(RadioMod.instance.blockEnergizedWool, 1), "sss", "sss", "sss", 's', EnumIngredient.ENERGIZEDSTRING.getStack(1));
        addSmelting(new ItemStack(RadioMod.instance.blockEnergizedWool, 1), EnumIngredient.ENERGIZEDINGOT.getStack(1), 0f);
        addShaped(new ItemStack(RadioMod.instance.blockCable), "iii", "eee", "iii", 'i', Item.ingotIron, 'e', EnumIngredient.ENERGIZEDINGOT);

        //Other
        addShaped(new ItemStack(RadioMod.instance.blockBroadcaster, 1), "iri", "rdr", "iri", 'd', Block.blockDiamond, 'i', Item.ingotIron, 'r', Item.redstone);
        addShaped(new ItemStack(RadioMod.instance.blockPlaylist, 1), "dgd", "cjc", "dgd", 'd', Item.diamond, 'g', Item.ingotGold, 'c', Block.chest, 'j', Block.jukebox);
        addShaped(new ItemStack(RadioMod.instance.blockRadio, 1), "iai", "iji", "iii", 'i', Item.ingotIron, 'a', EnumIngredient.ANTENNAROD.getStack(1), 'j', Block.jukebox);

        addShaped(new ItemStack(RadioMod.instance.itemLinkCard, 1), "ppp", "rir", "ppp", 'i', Item.ingotIron, 'p', Item.paper, 'r', Item.redstone);
    }

    private static void addShaped(ItemStack output, Object... data){
        CraftingManager.getInstance().addRecipe(output, data);
    }

    private static void addSmelting(ItemStack input, ItemStack output, float exp){
        FurnaceRecipes.smelting().addSmelting(input.itemID, input.getItemDamage(), output, exp);
    }
}
