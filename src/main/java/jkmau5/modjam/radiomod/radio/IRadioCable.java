package jkmau5.modjam.radiomod.radio;

import java.util.HashMap;

/**
 * Created by matthias on 1/7/14.
 */
public interface IRadioCable {
    void removeCable(int x, int y, int z);
    void updateCable(int x, int y, int z);
    int getStepsToBroadcaster();
    int getCableID();
}
