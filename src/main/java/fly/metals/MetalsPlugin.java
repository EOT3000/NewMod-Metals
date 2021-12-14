package fly.metals;

import fly.metals.setup.MetalsAddonSetup;
import fly.newmod.NewMod;

public class MetalsPlugin extends NewMod.ModExtension {
    @Override
    public void load() {
        MetalsAddonSetup.init();
    }
}
