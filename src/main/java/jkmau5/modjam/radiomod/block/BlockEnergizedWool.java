package jkmau5.modjam.radiomod.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jkmau5.modjam.radiomod.RadioMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPoweredOre;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;

/**
 * Created by matthias on 1/8/14.
 *
 * The energized wool block used to smelt energized ingots
 */
public class BlockEnergizedWool extends Block {

    @SideOnly(Side.CLIENT)
    private Icon icon;

    public BlockEnergizedWool(int par1){
        super(par1, Material.iron);
        this.setCreativeTab(RadioMod.tabRadioMod);
        this.setUnlocalizedName("radioMod.blockEnergizedWool");
        this.setHardness(1f);
        this.setResistance(3f);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister register){
        this.icon = register.registerIcon("RadioMod:energizedwool");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta){
        return this.icon;
    }
}
