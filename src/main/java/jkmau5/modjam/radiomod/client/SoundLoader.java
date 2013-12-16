package jkmau5.modjam.radiomod.client;

import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class SoundLoader {

    @ForgeSubscribe
    public void loadSounds(SoundLoadEvent event){
        event.manager.addSound("RadioMod:noise.ogg");
    }
}
