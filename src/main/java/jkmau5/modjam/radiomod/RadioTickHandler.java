package jkmau5.modjam.radiomod;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

import java.util.EnumSet;

/**
 * Author: Lordmau5
 * Date: 13.12.13
 * Time: 16:33
 * You are allowed to change this code,
 * however, not to publish it without my permission.
 */
public class RadioTickHandler implements ITickHandler {

    @Override
    public void tickStart(EnumSet<TickType> tickTypes, Object... objects) {

    }

    @Override
    public void tickEnd(EnumSet<TickType> tickTypes, Object... objects) {

    }

    @Override
    public EnumSet<TickType> ticks() {
        return EnumSet.of(TickType.SERVER);
    }

    @Override
    public String getLabel() {
        return null;
    }
}