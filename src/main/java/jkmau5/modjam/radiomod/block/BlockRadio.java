package jkmau5.modjam.radiomod.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jkmau5.modjam.radiomod.RadioMod;
import jkmau5.modjam.radiomod.tile.TileEntityRadio;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockRadio extends RMBlock {

    @SideOnly(Side.CLIENT) private IIcon top;
    @SideOnly(Side.CLIENT) private IIcon front;
    @SideOnly(Side.CLIENT) private IIcon bottom;

    public BlockRadio(){
        super("blockRadio", Material.field_151573_f);
        this.func_149647_a(RadioMod.tabRadioMod);
        this.func_149711_c(0.8f);
        this.func_149752_b(3f);
    }

    @Override
    public boolean hasTileEntity(int metadata){
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata){
        return new TileEntityRadio();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void func_149651_a(IIconRegister register){
        this.top = register.registerIcon("RadioMod:radio_top");
        this.front = register.registerIcon("RadioMod:radio_front");
        this.bottom = register.registerIcon("RadioMod:radio_bottom");
        this.field_149761_L = register.registerIcon("RadioMod:side");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon func_149691_a(int side, int meta){
        if(side == 1){
            return this.top;
        }else if(side == 0){
            return this.bottom;
        }else if(meta == 2 && side == 2){
            return this.front;
        }else if(meta == 3 && side == 5){
            return this.front;
        }else if(meta == 0 && side == 3){
            return this.front;
        }else if(meta == 1 && side == 4){
            return this.front;
        }else{
            return this.field_149761_L;
        }
    }

    @Override
    public void func_149689_a(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack){
        int meta = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
        world.setBlockMetadataWithNotify(x, y, z, meta, 2);
    }

    @Override
    public void func_149725_f(World world, int x, int y, int z, int par5){
        super.func_149725_f(world, x, y, z, par5);
        //TODO: Fix packets
        //PacketDispatcher.sendPacketToAllAround(x, y, z, 256, world.provider.dimensionId, new PacketPlaySound(null, x, y, z, true).getPacket());
    }
}
