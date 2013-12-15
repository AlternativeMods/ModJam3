package jkmau5.modjam.radiomod.util;

import net.minecraftforge.common.ForgeDirection;

import java.util.BitSet;

/**
 * Author: Lordmau5
 * Date: 14.12.13
 * Time: 14:55
 * You are allowed to change this code,
 * however, not to publish it without my permission.
 */
public class CableConnections {

    private BitSet connections = new BitSet(ForgeDirection.values().length);
    private boolean shouldUpdate = false;

    public boolean isConnected(ForgeDirection dir) {
        return this.connections.get(dir.ordinal());
    }

    public boolean setConnected(ForgeDirection dir, boolean state) {
        if(this.connections.get(dir.ordinal()) != state) {
            this.connections.set(dir.ordinal(), state);
            this.shouldUpdate = true;
        }
        return this.connections.get(dir.ordinal());
    }
}