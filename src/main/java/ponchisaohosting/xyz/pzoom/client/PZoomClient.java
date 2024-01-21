package ponchisaohosting.xyz.pzoom.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import ponchisaohosting.xyz.pzoom.event.KeyInputHandler;
import ponchisaohosting.xyz.pzoom.gui.TitleScreen;


@Environment(EnvType.CLIENT)
public class PZoomClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        KeyInputHandler.register();


    }
}
