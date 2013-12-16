package jkmau5.modjam.radiomod.client;

import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

/**
 * No description given
 *
 * @author jk-5
 */
public class SoundLoader {

    @ForgeSubscribe
    public void loadSounds(SoundLoadEvent event){
        event.manager.addSound("RadioMod:noise.ogg");
    }
}
