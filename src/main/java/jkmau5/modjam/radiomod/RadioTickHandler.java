package jkmau5.modjam.radiomod;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

import java.util.EnumSet;

public class RadioTickHandler implements ITickHandler {

    @Override
    public void tickStart(EnumSet<TickType> tickTypes, Object... objects){

    }

    @Override
    public void tickEnd(EnumSet<TickType> tickTypes, Object... objects){

    }

    @Override
    public EnumSet<TickType> ticks(){
        return EnumSet.of(TickType.SERVER);
    }

    @Override
    public String getLabel(){
        return "TickHandlerRadioMod";
    }
}