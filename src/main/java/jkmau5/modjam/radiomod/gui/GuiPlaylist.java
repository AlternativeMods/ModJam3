package jkmau5.modjam.radiomod.gui;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import jkmau5.modjam.radiomod.Constants;
import jkmau5.modjam.radiomod.tile.TileEntityPlaylist;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.List;
import java.util.Map;

public class GuiPlaylist extends RMGui {

    private int actualHeight = 0;
    private int xSize = 176;
    private int ySize = 166;
    private int scrollY = 0;
    private String selectedIndex = "";
    private Map<String, Integer> titleCoordinates = Maps.newHashMap();

    public final TileEntityPlaylist tile;
    private List<String> titleList = Lists.newArrayList();
    private short operation = -1; //-1 = nothing. 0 = play song. 1 = remove from list

    public GuiPlaylist(TileEntityPlaylist tile){
        super(tile);
        this.tile = tile;
    }

    @Override
    public RMGui readGuiData(ByteBuf buffer){
        int size = ByteBufUtils.readVarInt(buffer, 4);
        this.titleList = Lists.newArrayListWithExpectedSize(size);
        for(int i = 0; i < size; i++){
            this.titleList.add(ByteBufUtils.readUTF8String(buffer));
        }
        return this;
    }

    @Override
    protected void writeGuiData(ByteBuf buffer){
        buffer.writeByte(this.operation);
        ByteBufUtils.writeUTF8String(buffer, this.selectedIndex);
        this.operation = -1;
    }

    @Override
    public void initGui(){
        super.initGui();

        this.addButton(new GuiButton(0, this.field_146294_l / 2, this.field_146295_m / 8, 100, 20, "Play"));

        this.actualHeight = this.field_146295_m;
        this.initTitles();
    }

    @Override
    protected void func_146284_a(GuiButton button){
        if(button.field_146127_k == 0 && !this.selectedIndex.isEmpty()){
            this.operation = 0;
            this.sendGuiData();
        }
    }

    public void initTitles(){
        if(tile.getTitles() != null && !tile.getTitles().isEmpty()){
            List<String> titles = tile.getTitles();
            for(int i = 0; i < titles.size(); i++){
                String realTitle = Constants.getRealRecordTitle(titles.get(i));

                int fromPos = 2 + ((actualHeight - 116) / 2) + (i * 10);
                titleList.add(realTitle);
                titleCoordinates.put(realTitle, fromPos);
            }
        }
    }

    public String getIndexId(int yClick){
        for(String title : titleList){
            int titlePos = titleCoordinates.get(title);

            if(yClick >= titlePos && yClick < titlePos + 10){
                return title;
            }
        }
        return "";
    }

    @Override
    public boolean doesGuiPauseGame(){
        return false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTick){
        int x = (this.field_146294_l - this.xSize) / 2;
        int y = (actualHeight - this.ySize) / 2;
        if(tile != null && tile.getTitles() != null){
            this.scrollY = Math.min(this.scrollY, 0);
            this.scrollY = Math.max(this.scrollY, 47 - (tile.getTitles().size() * 10 + 2));
        }

        this.func_146276_q_();

        ScaledResolution res = new ScaledResolution(this.field_146297_k.gameSettings, this.field_146297_k.displayWidth, this.field_146297_k.displayHeight);

        this.ySize = 116;
        if(tile != null && tile.getTitles() != null && !tile.getTitles().isEmpty()){
            GL11.glPushMatrix();
            int yS = (actualHeight / 5);
            Gui.drawRect(x - 1, y - 1, x + this.xSize + 1, y + yS + 1, 0xFFAAAAAA);
            Gui.drawRect(x, y, x + this.xSize, y + yS, 0xFF000000);

            GL11.glScissor(x * res.getScaleFactor(), this.field_146297_k.displayHeight - yS * res.getScaleFactor() - y * res.getScaleFactor(), (this.xSize - 5) * res.getScaleFactor(), yS * res.getScaleFactor());
            GL11.glTranslated(0, this.scrollY, 0);
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            int index = 0;
            for(String title : tile.getTitles()){
                String realRecord = Constants.getRealRecordTitle(title);
                if(selectedIndex.equals(realRecord)){
                    Gui.drawRect(x + 2, y + 2 + index * 10, x + this.xSize - 2, y + 2 + (index + 1) * 10, 0xFF00AA00);
                    Gui.drawRect(x + this.xSize - 16, y + 3 + index * 10, x + this.xSize - 6, y + 1 + (index + 1) * 10, 0xFFDD0000);
                }
                this.field_146289_q.drawString(realRecord, x + 3, 3 + y + index * 10, 0xFFFFFFFF);
                index++;
            }
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
            GL11.glPopMatrix();
        }else{
            this.field_146289_q.drawString("No songs available", x, y, 0xFFFFFFFF);
        }

        super.drawScreen(mouseX, mouseY, partialTick);
    }

    private int getYStartForTitle(){
        if(!titleCoordinates.containsKey(selectedIndex))
            return -1;

        return titleCoordinates.get(selectedIndex);
    }

    private void removeIfClickingRemove(int xMouse, int yMouse){
        if(selectedIndex.isEmpty()) return;
        int realX = (this.field_146294_l - this.xSize) / 2;
        if(xMouse >= realX + this.xSize - 16 && xMouse < realX + this.xSize - 6){
            int y = getYStartForTitle();
            if(yMouse >= y + 2 && yMouse <= y + 8){
                this.operation = 1;
                this.sendGuiData();
            }
        }
    }

    @Override
    protected void mouseClicked(int x, int y, int button){
        super.mouseClicked(x, y, button);
        if(button == 0){
            int realX = (this.field_146294_l - this.xSize) / 2;
            int realY = (actualHeight - this.ySize) / 2;
            int yS = actualHeight / 5;
            if(x > realX && x < realX + this.xSize && y > realY && y < realY + yS){
                removeIfClickingRemove(x, y - scrollY);
                selectedIndex = getIndexId(y - scrollY);
            }
        }
    }

    @Override
    public void func_146274_d(){
        super.func_146274_d();
        int scroll = Mouse.getEventDWheel();
        if(scroll < 0) scroll = -5;
        if(scroll > 0) scroll = 5;
        this.scrollY += scroll;
        if(tile != null && tile.getTitles() != null && !tile.getTitles().isEmpty()){
            this.scrollY = Math.min(this.scrollY, 0);
            this.scrollY = Math.max(this.scrollY, 47 - (tile.getTitles().size() * 10 + 2));
        }
    }
}