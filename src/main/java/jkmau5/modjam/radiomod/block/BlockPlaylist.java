package jkmau5.modjam.radiomod.block;

import jkmau5.modjam.radiomod.RadioMod;
import jkmau5.modjam.radiomod.tile.TileEntityPlaylist;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Author: Lordmau5
 * Date: 15.12.13
 * Time: 16:35
 * You are allowed to change this code,
 * however, not to publish it without my permission.
 */
public class BlockPlaylist extends Block {

    public BlockPlaylist(int par1) {
        super(par1, Material.iron);
        setCreativeTab(RadioMod.tabRadioMod);
        setUnlocalizedName("radiomod.BlockPlaylist");
    }

    @Override
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
        if(!par1World.isRemote)
            return false;

        SoundManager sndMng = Minecraft.getMinecraft().sndManager;
        String name = sndMng.soundPoolStreaming.getRandomSound().getSoundName();
        System.out.println(name);
        sndMng.stopAllSounds();
        sndMng.playStreaming(name.replace(".ogg", ""), par2, par3, par4);

        return super.onBlockActivated(par1World, par2, par3, par4, par5EntityPlayer, par6, par7, par8, par9);
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TileEntityPlaylist();
    }
}