package club.sk1er.mods.tnttimer.tweaker;

import club.sk1er.mods.tnttimer.forge.FMLLoadingPlugin;
import gg.essential.loader.EssentialSetupTweaker;

@SuppressWarnings("unused")
public class TNTTimerTweaker extends EssentialSetupTweaker {
    public TNTTimerTweaker() {
        super(new String[]{FMLLoadingPlugin.class.getName()});
    }
}