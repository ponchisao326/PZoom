package ponchisaohosting.xyz.pzoom;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ponchisaohosting.xyz.pzoom.event.KeyInputHandler;

public class PZoom implements ModInitializer {
    public static final String MOD_ID = "pzoom";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("PZOOM: Initiating...");
    }
}
