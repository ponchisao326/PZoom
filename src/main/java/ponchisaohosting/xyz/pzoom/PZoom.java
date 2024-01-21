package ponchisaohosting.xyz.pzoom;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ponchisaohosting.xyz.pzoom.event.KeyInputHandler;
import ponchisaohosting.xyz.pzoom.gui.TitleScreen;


public class PZoom implements ModInitializer {
    public static final String MOD_ID = "pzoom";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("PZOOM: Initiating...");

    }
}
