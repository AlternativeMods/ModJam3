package jkmau5.modjam.radiomod.item;

import jkmau5.modjam.radiomod.RadioMod;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

import java.util.List;

/**
 * No description given
 *
 * @author jk-5
 */
public class ItemIngredient extends Item {

    private Icon antenna;
    private Icon antennarod;
    private Icon rawantennarod;
    private Icon bigantennafoot;
    private Icon smallantennafoot;

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
    public void registerIcons(IconRegister register){
        this.antenna = register.registerIcon("RadioMod:antenna");
        this.antennarod = register.registerIcon("RadioMod:antennarod");
        this.rawantennarod = register.registerIcon("RadioMod:rawantennarod");
        this.bigantennafoot = register.registerIcon("RadioMod:bigantennafoot");
        this.smallantennafoot = register.registerIcon("RadioMod:smallantennafoot");
    }

    @Override
    public Icon getIconFromDamage(int meta){
        switch(EnumIngredient.fromID(meta)){
            case ANTENNAONFOOT:
                return this.antenna;
            case ANTENNAROD:
                return this.antennarod;
            case RAWANTENNAROD:
                return this.rawantennarod;
            case BIGANTENNAFOOT:
                return this.bigantennafoot;
            case ANTENNAFOOT:
                return this.smallantennafoot;
            default:
                return super.getIconFromDamage(meta);
        }
    }

    @Override
    public void getSubItems(int id, CreativeTabs par2CreativeTabs, List list){
        for(EnumIngredient ingredient : EnumIngredient.values()){
            list.add(new ItemStack(id, 1, ingredient.subid));
        }
    }
}
