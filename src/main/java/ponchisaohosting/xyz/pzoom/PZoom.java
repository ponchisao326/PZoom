package ponchisaohosting.xyz.pzoom;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.InputSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

public class PZoom implements ModInitializer {
    public static final String MOD_ID = "pzoom";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    private InputStream smallIconStream;
    private InputStream bigIconStream;

    @Override
    public void onInitialize() {
        LOGGER.info("PZOOM: Iniciando...");
        LOGGER.info("Autor: PonchisaoHosting (Ponchisao326)");

        ClientTickEvents.END_CLIENT_TICK.register((client) -> {
            if (MinecraftClient.getInstance().getWindow() != null) {
                try {
                    smallIconStream = getClass().getResourceAsStream("/assets/icons/small-icon.png");
                    bigIconStream = getClass().getResourceAsStream("/assets/icons/big-icon.png");

                    if (smallIconStream != null && bigIconStream != null) {
                        MinecraftClient.getInstance().getWindow().setIcon(asInputSupplier(smallIconStream), asInputSupplier(bigIconStream));
                        // Desregistrando el evento para evitar que se llame más de una vez
                        ClientTickEvents.END_CLIENT_TICK.register((client2) -> {});
                    } else {
                        LOGGER.error("Los flujos de entrada de los iconos son nulos. No se puede establecer el ícono.");
                    }
                } catch (Exception e) {
                    LOGGER.error("Error al cargar y establecer el icono:", e);
                }
            }
        });

    }


    public static InputSupplier<InputStream> asInputSupplier(final InputStream inputStream) {
        return () -> {;
            return inputStream;
        };
    }
}
