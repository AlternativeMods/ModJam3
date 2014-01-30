package jkmau5.modjam.radiomod.item;

import jkmau5.modjam.radiomod.RadioMod;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class ItemIngredient extends Item {

    private IIcon antenna;
    private IIcon antennarod;
    private IIcon rawantennarod;
    private IIcon bigantennafoot;
    private IIcon smallantennafoot;

    public ItemIngredient(){
        super();
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setUnlocalizedName("radioMod.ingredient");
        this.setCreativeTab(RadioMod.tabRadioMod);
    }

    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack){
        return super.getUnlocalizedName(par1ItemStack) + "." + EnumIngredient.fromID(par1ItemStack.getItemDamage()).unlocalizedName;
    }

    @Override
    public void registerIcons(IIconRegister register){
        this.antenna = register.registerIcon("RadioMod:antenna");
        this.antennarod = register.registerIcon("RadioMod:antennarod");
        this.rawantennarod = register.registerIcon("RadioMod:rawantennarod");
        this.bigantennafoot = register.registerIcon("RadioMod:bigantennafoot");
        this.smallantennafoot = register.registerIcon("RadioMod:smallantennafoot");
    }

    @Override
    public IIcon getIconFromDamage(int meta){
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
    public void func_150895_a(Item item, CreativeTabs par2CreativeTabs, List list){
        for(EnumIngredient ingredient : EnumIngredient.values()){
            list.add(new ItemStack(item, 1, ingredient.subid));
        }
    }
}
