package jkmau5.modjam.radiomod.item;

import jkmau5.modjam.radiomod.RadioMod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * No description given
 *
 * @author jk-5
 */
public class ItemIngredient extends Item {

    public ItemIngredient(int id){
        super(id);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setUnlocalizedName("ingredient");
        this.setCreativeTab(RadioMod.tabRadioMod);
    }

    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack){
        return super.getUnlocalizedName(par1ItemStack) + "." + EnumIngredient.fromID(par1ItemStack.getItemDamage()).unlocalizedName;
    }

    @Override
    public void getSubItems(int id, CreativeTabs par2CreativeTabs, List list){
        for(EnumIngredient ingredient : EnumIngredient.values()){
            list.add(new ItemStack(id, 1, ingredient.subid));
        }
    }
}
