package jkmau5.modjam.radiomod.gui;

import com.google.common.collect.Lists;
import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import jkmau5.modjam.radiomod.tile.IGuiTileEntity;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class GuiRadio extends RMGui {

    private GuiButton connectButton;

    private List<String> availableRadios;

    private int xSize = 176;
    private int ySize = 166;
    private int scrollY = 0;
    private boolean isloading = true;

    public String selectedName = null;

    public GuiRadio(IGuiTileEntity tileEntity){
        super(tileEntity);
    }

    @Override
    public RMGui readGuiData(ByteBuf buffer){
        if(buffer.readBoolean()){
            this.selectedName = ByteBufUtils.readUTF8String(buffer);
        }else{
            this.selectedName = null;
        }
        int size = ByteBufUtils.readVarInt(buffer, 4);
        this.availableRadios = Lists.newArrayListWithExpectedSize(size);
        for(int i = 0; i < size; i++){
            this.availableRadios.add(ByteBufUtils.readUTF8String(buffer));
        }
        this.isloading = false;
        return this;
    }

    @Override
    protected void writeGuiData(ByteBuf buffer){
        if(this.selectedName == null){
            buffer.writeBoolean(false);
        }else{
            buffer.writeBoolean(true);
            ByteBufUtils.writeUTF8String(buffer, this.selectedName);
        }
    }

    public void initGui(){
        this.addButton(this.connectButton = new GuiButton(0, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 96 + 12, "No radio selected"));
        this.onSelectionChanged();
    }

    @Override
    protected void func_146284_a(GuiButton button){
        if(button.field_146127_k == 0){
            this.sendGuiData();
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTick){
        int x = (this.field_146294_l - this.xSize) / 2;
        int y = (this.field_146295_m - this.ySize) / 2;
        if(availableRadios != null){
            this.scrollY = Math.min(this.scrollY, 0);
            this.scrollY = Math.max(this.scrollY, 47 - (availableRadios.size() * 10 + 2));
        }

        this.func_146276_q_();

        ScaledResolution res = new ScaledResolution(this.field_146297_k.gameSettings, this.field_146297_k.displayWidth, this.field_146297_k.displayHeight);

        this.ySize = 116;
        if(isloading){
            this.field_146289_q.drawString("Loading...", x, y, 0xFFCCCCCC);
        }else{
            if(availableRadios != null){
                GL11.glPushMatrix();
                int yS = (this.field_146295_m / 5);
                Gui.drawRect(x - 1, y - 1, x + this.xSize + 1, y + yS + 1, 0xFFAAAAAA);
                Gui.drawRect(x, y, x + this.xSize, y + yS, 0xFF000000);

                GL11.glScissor(x * res.getScaleFactor(), this.field_146297_k.displayHeight - yS * res.getScaleFactor() - y * res.getScaleFactor(), (this.xSize - 5) * res.getScaleFactor(), yS * res.getScaleFactor());
                GL11.glTranslated(0, this.scrollY, 0);
                GL11.glEnable(GL11.GL_SCISSOR_TEST);
                int index = 0;
                for(String radio : availableRadios){
                    if(radio.equals(selectedName)){
                        Gui.drawRect(x + 2, y + 2 + index * 10, x + this.xSize - 2, y + 2 + (index + 1) * 10, 0xFF00AA00);
                    }
                    this.field_146289_q.drawString(radio, x + 3, 3 + y + index * 10, 0xFFFFFFFF);
                    index++;
                }
                GL11.glDisable(GL11.GL_SCISSOR_TEST);
                GL11.glPopMatrix();
            }else{
                this.field_146289_q.drawString("No radios found", x, y, 0xFFFFFFFF);
            }
        }

        super.drawScreen(mouseX, mouseY, partialTick);
    }

    @Override
    protected void mouseClicked(int x, int y, int button){
        super.mouseClicked(x, y, button);
        if(button == 0){
            int realX = (this.field_146294_l - this.xSize) / 2;
            int realY = (this.field_146295_m - this.ySize) / 2;
            int yS = this.field_146295_m / 5;
            if(x > realX && x < realX + this.xSize && y > realY && y < realY + yS){
                selectedName = this.getNameFromY(y - this.scrollY);
                this.onSelectionChanged();
            }
        }
    }

    public String getNameFromY(int y){
        int i = 0;
        for(String name : availableRadios){
            int pos = 2 + ((this.field_146295_m - 116) / 2) + (i * 10);
            if(y >= pos && y < pos + 10) return name;
            i++;
        }
        return null;
    }

    public void onSelectionChanged(){
        if(selectedName == null){
            this.connectButton.field_146126_j = "No radio selected";
        }else{
            this.connectButton.field_146126_j = "Listen to this radio";
        }
    }

    @Override
    public boolean doesGuiPauseGame(){
        return false;
    }

    @Override
    public void func_146274_d(){
        super.func_146274_d();
        int scroll = Mouse.getEventDWheel();
        if(scroll < 0) scroll = -5;
        if(scroll > 0) scroll = 5;
        this.scrollY += scroll;
        if(availableRadios != null){
            this.scrollY = Math.min(this.scrollY, 0);
            this.scrollY = Math.max(this.scrollY, 47 - (availableRadios.size() * 10 + 2));
        }
    }
}
