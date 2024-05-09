package ponchisaohosting.xyz.pzoom.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Overlay;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.WeightedSoundSet;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.resource.Resource;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.glfw.GLFW;
import ponchisaohosting.xyz.pzoom.PZoom;
import ponchisaohosting.xyz.pzoom.loading.SoundLoader;

import java.util.Optional;
import java.util.Random;

public class PressToContinue extends Screen {

    private Screen parent;
    private static final String[] continueText = {"Presiona cualquier tecla para continuar", "Presiona cualquier tecla para continuar", "Presiona cualquier tecla para continuar"};

    private static final Identifier BACKGROUND_TEXTURE = new Identifier("ponchisaohosting_xyz_pzoom:mixin/title_screen/background.png");

    private static final int BLINK_INTERVAL = 1000; // Intervalo de parpadeo en milisegundos
    private long lastBlinkTime = 0; // Último momento en que el texto parpadeó

    private int messageIndex = 0;
    private static final long MESSAGE_CHANGE_INTERVAL = 1000L;

    private long lastMessageChangeTime = 0L;
    private int currentMessageIndex = 0;

    // Fade duration in milliseconds
    private static final int FADE_DURATION = 1200;
    private static final int FADE_IN_DURATION = FADE_DURATION / 2;
    private static final int FADE_OUT_DURATION = FADE_DURATION / 2;
    private long fadeInStartTime = -1;
    private long fadeOutStartTime = -1;
    private boolean fadeInComplete = false;
    private boolean fadeOutComplete = false;
    private boolean sounding = false;
    // Almacenar una referencia al sonido que se está reproduciendo
    private PositionedSoundInstance soundInstance;
    private SoundEvent press;

    public PressToContinue() {
        super(Text.of("Press to Continue"));
    }

    @Override
    protected void init() {
        super.init();

        Random rand = new Random();
        int rand_int1 = rand.nextInt(6); // Genera un número aleatorio entre 0 y 5

        switch (rand_int1) {
            case 1 -> press = SoundLoader.PRESS_BG_1;
            // case 2 -> press = SoundLoader.PRESS_BG_2;
            // case 3 -> press = SoundLoader.PRESS_BG_3;
            // case 4 -> press = SoundLoader.PRESS_BG_4;
            // case 5 -> press = SoundLoader.PRESS_BG_5;
        }
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
        MinecraftClient client = MinecraftClient.getInstance();
        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();

        if (!sounding) {
            SoundEvent pressBg1SoundEvent = SoundLoader.PRESS_BG_1;
            PositionedSoundInstance soundInstance = PositionedSoundInstance.master(pressBg1SoundEvent, 1.0f);
            MinecraftClient.getInstance().getSoundManager().play(soundInstance);
            sounding = true;
            PZoom.LOGGER.info("Ajustando sounding a true " + soundInstance + press);
        }

        // Verificar si el sonido está en reproducción
        if (soundInstance != null && !MinecraftClient.getInstance().getSoundManager().isPlaying(soundInstance)) {
            sounding = false;
            PZoom.LOGGER.info("Ajustando sounding a false");
        }

        // Renderizar la textura de fondo
        Identifier backgroundTextureId = BACKGROUND_TEXTURE;
        Optional<Resource> resourceOptional = client.getResourceManager().getResource(backgroundTextureId);

        if (resourceOptional.isPresent()) {
            Resource resource = resourceOptional.get();
            RenderSystem.setShaderTexture(0, backgroundTextureId);
            drawTexture(matrices, 0, 0, 0.0F, 0.0F, width, height, width, height);

            // Determinar si es hora de hacer parpadear el texto
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastMessageChangeTime >= MESSAGE_CHANGE_INTERVAL) {
                currentMessageIndex = (currentMessageIndex + 1) % continueText.length;
                lastMessageChangeTime = currentTime;
                fadeInStartTime = currentTime;
                fadeOutStartTime = fadeInStartTime + FADE_IN_DURATION;
            }
            String continuetext = continueText[currentMessageIndex];

            // Calculate alpha for fade-in effect
            int alpha = 255;
            if (!fadeInComplete && currentTime < fadeInStartTime + FADE_IN_DURATION) {
                alpha = (int) MathHelper.clamp((currentTime - fadeInStartTime) * 255.0 / FADE_IN_DURATION, 0, 255);
            } else {
                fadeInComplete = true;
            }

            // Calculate alpha for fade-out effect
            if (fadeInComplete && !fadeOutComplete && currentTime > fadeOutStartTime && currentTime < fadeOutStartTime + FADE_OUT_DURATION) {
                alpha = (int) MathHelper.clamp(255 - (currentTime - fadeOutStartTime) * 255.0 / FADE_OUT_DURATION, 0, 255);
            } else if (currentTime > fadeOutStartTime + FADE_OUT_DURATION) {
                fadeOutComplete = true;
            }

            // Renderizar texto en medio de la pantalla
            String username = client.getSession().getUsername();
            Text text = Text.of("¡Bienvenido " + username +  " a BLANCHINIILAND 3!");
            int textWidth = textRenderer.getWidth(text);
            int textX = (width - textWidth) / 2;
            int textY = height / 2;
            textRenderer.draw(matrices, text, textX, textY, 0xFFFFFF | (alpha << 24));

            // Renderizar texto de "Presiona cualquier tecla para continuar"
            Text pressText = Text.of(continuetext);
            int pressTextWidth = textRenderer.getWidth(pressText);
            int textHeight = textRenderer.fontHeight;
            int pressTextX = (width - pressTextWidth) / 2;
            int pressTextY = height - textHeight - 10;
            textRenderer.draw(matrices, pressText, pressTextX, pressTextY, 0xFFFFFF | (alpha << 24));
        }

        // Renderizar elementos en la pantalla
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        // Llama al método keyPressed de la superclase para manejar las entradas por teclado por defecto
        super.keyPressed(keyCode, scanCode, modifiers);

        MinecraftClient.getInstance().getSoundManager().stopAll();
        LoadingScreen loadingScreen = new LoadingScreen();
        MinecraftClient.getInstance().setScreen(loadingScreen);

        return true;
    }
}
