package ponchisaohosting.xyz.pzoom.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.resource.Resource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class LoadingScreen extends Screen {
    private final Identifier[] backgroundTextures = new Identifier[192];
    private Screen parent;
    private int currentFrame = 0;
    private long lastFrameTime = 0;
    private static final long FRAME_DURATION_MS = 30; // Duración en milisegundos de cada fotograma

    public LoadingScreen() {
        super(Text.of("Loading..."));

        // Inicializar los identificadores de textura
        for (int i = 0; i < backgroundTextures.length; i++) {
            backgroundTextures[i] = new Identifier("ponchisaohosting_xyz_pzoom:mixin/intro/" + (i + 1) + ".png");
        }
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void close() {
        if (this.parent != null) {
            client.setScreen(parent);
        } else {
            // Si no hay una pantalla padre, se comporta como la pantalla principal y sale del juego
            this.client.openPauseMenu(false);
        }
    }
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        // Renderizar la textura de fondo solo si no hemos mostrado todas las imágenes una vez
        if (currentFrame < backgroundTextures.length) {
            Identifier backgroundTextureId = backgroundTextures[currentFrame];
            Optional<Resource> resourceOptional = this.client.getResourceManager().getResource(backgroundTextureId);

            if (resourceOptional.isPresent()) {
                // La textura se cargó correctamente, renderizarla
                Resource resource = resourceOptional.get();
                RenderSystem.setShaderTexture(0, backgroundTextureId);
                drawTexture(matrices, 0, 0, 0.0F, 0.0F, this.width, this.height, this.width, this.height);
            } else {
                // La textura no se pudo cargar, imprimir un mensaje de error
                System.out.println("Error: No se pudo cargar la textura de fondo.");
            }
        } else {
            this.close();
            // Después de cargar los recursos necesarios, establecer la pantalla principal del juego
            Screen mainScreen = new CustomMainMenu();
            MinecraftClient.getInstance().setScreen(mainScreen);
        }

        // Renderizar elementos en la pantalla
        super.render(matrices, mouseX, mouseY, delta);

        // Actualizar el fotograma actual basado en el tiempo
        long currentTime = System.currentTimeMillis();
        if (currentFrame < backgroundTextures.length && currentTime - lastFrameTime > FRAME_DURATION_MS) {
            currentFrame++;
            lastFrameTime = currentTime;
        }
    }

}
