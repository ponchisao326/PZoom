package ponchisaohosting.xyz.pzoom.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.minecraft.client.MinecraftClient;
import ponchisaohosting.xyz.pzoom.event.KeyInputHandler;
import ponchisaohosting.xyz.pzoom.gui.CustomMainMenu;

@Environment(EnvType.CLIENT)
public class PZoomClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            // Establece la pantalla personalizada como la pantalla principal
            MinecraftClient.getInstance().setScreen(new CustomMainMenu());
        });
    }
}
