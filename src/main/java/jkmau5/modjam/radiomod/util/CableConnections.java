package jkmau5.modjam.radiomod.util;

import net.minecraftforge.common.ForgeDirection;

import java.util.BitSet;

/**
 * Created by matthias on 1/7/14.
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
