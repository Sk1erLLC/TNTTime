package club.sk1er.mods.tnttimer.tweaker;

import club.sk1er.mods.tnttimer.forge.FMLLoadingPlugin;
import gg.essential.loader.EssentialTweaker;

@SuppressWarnings("unused")
public class TNTTimerTweaker extends EssentialTweaker {
    public TNTTimerTweaker() {
        super(new String[]{FMLLoadingPlugin.class.getName()});
    }
}