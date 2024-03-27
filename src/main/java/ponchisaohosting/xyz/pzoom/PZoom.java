package ponchisaohosting.xyz.pzoom;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.*;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import ponchisaohosting.xyz.pzoom.gui.PZoomResourcePackProvider;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public class PZoom implements ModInitializer {
    public static final String MOD_ID = "pzoom";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    private static final Identifier RESOURCE_PACK_ID = new Identifier(MOD_ID, "pzoom_resource_pack");

    @Override
    public void onInitialize() {
        LOGGER.info("PZOOM: Iniciando...");
        LOGGER.info("Autor: PonchisaoHosting (Ponchisao326)");

        // // Registra el proveedor de resource pack
        // ResourcePackManager resourcePackManager = MinecraftClient.getInstance().getResourcePackManager();
        // ResourcePackProvider provider = new PZoomResourcePackProvider();
        // resourcePackManager.registerProvider(provider);
        // resourcePackManager.addPackFinder(provider);
//
        // // Registra el reload listener para cargar texturas personalizadas
        // ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
        //     @Override
        //     public Identifier getFabricId() {
        //         return new Identifier(MOD_ID, "custom_textures");
        //     }
//
        //     @Override
        //     public void reload(ResourceManager manager) {
        //         // Lógica para cargar texturas personalizadas
        //         // Puedes utilizar manager.findResources() para obtener las texturas desde el resource pack
        //         // Luego, procesa esas texturas según tus necesidades
        //         // Recuerda cerrar los InputStreams después de procesar las texturas
        //     }
        // });
    }
}
