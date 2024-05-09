package ponchisaohosting.xyz.pzoom.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import ponchisaohosting.xyz.pzoom.event.KeyInputHandler;
import ponchisaohosting.xyz.pzoom.gui.CustomMainMenu;
import ponchisaohosting.xyz.pzoom.gui.LoadingScreen;
import ponchisaohosting.xyz.pzoom.gui.PressToContinue;

@Environment(EnvType.CLIENT)
public class PZoomClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            // Mostrar la pantalla de carga antes de cargar la pantalla principal del juego
            PressToContinue pressToContinueScreen = new PressToContinue();
            MinecraftClient.getInstance().setScreen(pressToContinueScreen);
        });
    }
}