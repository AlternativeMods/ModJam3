package jkmau5.modjam.radiomod;

import jkmau5.modjam.radiomod.item.EnumIngredient;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;

/**
 * No description given
 *
 * @author jk-5
 */
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
        addShaped(new ItemStack(RadioMod.instance.blockCable, 4), "iii", "rdr", "iii", 'i', Item.ingotIron, 'r', Item.redstone, 'd', Item.diamond);
    }

    private static void addShaped(ItemStack output, Object... data){
        CraftingManager.getInstance().addRecipe(output, data);
    }

    private static void addSmelting(ItemStack input, ItemStack output, float exp){
        FurnaceRecipes.smelting().addSmelting(input.itemID, input.getItemDamage(), output, exp);
    }
}
