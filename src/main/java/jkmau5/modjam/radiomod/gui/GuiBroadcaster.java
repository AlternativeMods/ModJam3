package jkmau5.modjam.radiomod.gui;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import jkmau5.modjam.radiomod.RadioMod;
import jkmau5.modjam.radiomod.tile.IGuiTileEntity;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

public class GuiBroadcaster extends RMGui {

    private GuiButton nameButton;
    private GuiTextField radioNameField;

    public GuiBroadcaster(IGuiTileEntity tileEntity){
        super(tileEntity);
    }

    @Override
    public RMGui readGuiData(ByteBuf buffer){
        this.radioNameField.func_146180_a(ByteBufUtils.readUTF8String(buffer));
        return this;
    }

    @Override
    protected void writeGuiData(ByteBuf buffer){
        ByteBufUtils.writeUTF8String(buffer, this.radioNameField.func_146179_b());
    }

    public void initGui(){
        Keyboard.enableRepeatEvents(true);

        int xSize = 176;
        int ySize = 166;

        int x = (this.field_146294_l - xSize) / 2;
        int y = (this.field_146295_m - ySize) / 2;

        this.addButton(this.nameButton = new GuiButton(0, x, y / 2 + 96 + 12, xSize, 20, "Set Radio Name"));

        this.radioNameField = new GuiTextField(this.field_146289_q, x, y, xSize, 20);
        this.radioNameField.func_146203_f(20);
        this.radioNameField.func_146195_b(true);
        this.radioNameField.func_146180_a("Loading...");
    }

    @Override
    public void func_146281_b(){
        Keyboard.enableRepeatEvents(false);
    }

    protected void func_146284_a(GuiButton button){
        switch(button.field_146127_k){
            case 0:
                if(this.radioNameField.func_146179_b().isEmpty()){
                    this.radioNameField.func_146180_a(RadioMod.getUniqueRadioID());
                }
                this.sendGuiData();
                this.field_146297_k.func_147108_a(null);
                break;
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTick){
        this.func_146276_q_();
        this.radioNameField.func_146194_f();
        super.drawScreen(mouseX, mouseY, partialTick);
    }

    protected void keyTyped(char paramChar, int paramInt){
        this.radioNameField.func_146201_a(paramChar, paramInt);
        if(radioNameField.func_146179_b().equals("")){
            this.nameButton.field_146126_j = "Random Radio Name";
        }else{
            this.nameButton.field_146126_j = "Set Radio Name";
        }

        if(paramInt == Keyboard.KEY_RETURN || paramInt == Keyboard.KEY_NUMPADENTER){
            this.func_146284_a(this.nameButton);
        }else if(paramInt == Keyboard.KEY_ESCAPE){
            this.field_146297_k.func_147108_a(null);
        }
    }
}
