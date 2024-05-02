package ponchisaohosting.xyz.pzoom.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.resource.Resource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import ponchisaohosting.xyz.pzoom.PZoom;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomCreditsScreen extends Screen {

    private final Screen parent;
    private List<Text> credits;
    private int timeUntilClose = 100;
    private float timeElapsed;
    private int currentYPosition;
    private static final Identifier BACKGROUND_TEXTURE = new Identifier("ponchisaohosting_xyz_pzoom:mixin/title_screen/prueba.png");

    public CustomCreditsScreen(Screen parent) {
        super(Text.of("Créditos de los trabajadores"));
        this.parent = parent;
        this.credits = loadCredits();
    }

    private List<Text> loadCredits() {
        List<Text> loadedCredits = new ArrayList<>();
        if (this.client == null) {
            loadedCredits.add(Text.of("Error: MinecraftClient not initialized"));
            return loadedCredits;
        }
        try {
            Identifier resourceId = new Identifier("ponchisaohosting_xyz_pzoom:mixin/title_screen/custom_credits.txt");
            PZoom.LOGGER.info("Cargando créditos desde {}", resourceId);

            Optional<Resource> resourceOptional = this.client.getResourceManager().getResource(resourceId);
            if (resourceOptional.isPresent()) {
                Resource resource = resourceOptional.get();

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        loadedCredits.add(Text.of(line));
                    }
                }
            } else {
                loadedCredits.add(Text.of("Error: Resource not found"));
            }
        } catch (Exception e) {
            PZoom.LOGGER.error("Error cargando los créditos", e);
            loadedCredits.add(Text.of("Error cargando los créditos"));
        }
        return loadedCredits;
    }

    @Override
    protected void init() {
        super.init();
        this.currentYPosition = this.height;
        this.timeElapsed = 0;
        this.credits = loadCredits();
    }

    @Override
    public void close() {
        this.client.setScreen(this.parent);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        // Verificar si la textura se carga correctamente

        // La textura se cargó correctamente, renderizarla
        this.renderBackgroundTexture(matrices);

        // Renderizar elementos en la pantalla de créditos personalizados
        super.render(matrices, mouseX, mouseY, delta);

        // Desplazar gradualmente los créditos hacia arriba
        this.currentYPosition = (int) (this.height - this.timeElapsed * 4); // Reducir la velocidad de desplazamiento

        // Renderizar los créditos
        for (Text credit : this.credits) {
            drawCenteredText(matrices, this.textRenderer, credit, this.width / 2, this.currentYPosition, 0xFFFFFF);
            this.currentYPosition += 10; // Espacio entre líneas de crédito
        }

        // Actualizar el tiempo transcurrido
        this.timeElapsed += delta;

        // Si todos los créditos ya han pasado fuera de la pantalla, cerrar la pantalla y volver a la pantalla anterior
        if (this.currentYPosition < -10 && this.timeElapsed > 200) { // Tiempo de espera antes de volver a la pantalla anterior
            this.close();
        }
    }

    private void renderBackgroundTexture(MatrixStack matrices) {
        Optional<Resource> resourceOptional = this.client.getResourceManager().getResource(BACKGROUND_TEXTURE);
        if (resourceOptional.isPresent()) {
            Resource resource = resourceOptional.get();
            RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
            drawTexture(matrices, 0, 0, 0.0F, 0.0F, this.width, this.height, this.width, this.height);
        } else {
            // La textura no se pudo cargar, imprimir un mensaje de error
            PZoom.LOGGER.error("Error: No se pudo cargar la textura de fondo.");
        }
    }

}
