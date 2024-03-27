package ponchisaohosting.xyz.pzoom.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import ponchisaohosting.xyz.pzoom.PZoom;
import ponchisaohosting.xyz.pzoom.event.KeyInputHandler;
// import ponchisaohosting.xyz.pzoom.gui.PZoomResourcePackProvider;

@Environment(EnvType.CLIENT)
public class PZoomClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();

        // // Load the resource pack
        // ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
        //     PZoomResourcePackProvider.loadResourcePack();
        // });
    }
}
