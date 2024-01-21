package ponchisaohosting.xyz.pzoom.client;

import net.fabricmc.api.ClientModInitializer;
import ponchisaohosting.xyz.pzoom.event.KeyInputHandler;

public class PZoomClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();
    }
}
