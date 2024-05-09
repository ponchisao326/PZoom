package ponchisaohosting.xyz.pzoom.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.mixin.networking.client.accessor.MinecraftClientAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Overlay;
import net.minecraft.client.gui.screen.SplashOverlay;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.resource.Resource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ponchisaohosting.xyz.pzoom.PZoom;
import ponchisaohosting.xyz.pzoom.gui.LoadingScreen;
import java.util.Random;

import java.util.Optional;

import static net.minecraft.client.gui.DrawableHelper.drawTexture;

@Mixin(SplashOverlay.class)
public class MojangStudiosMixin {
    Identifier BACKGROUND_TEXTURE = new Identifier("ponchisaohosting_xyz_pzoom:mixin/title_screen/background.png");
    Identifier INTRO_BACKGROUND_TEXTURE = new Identifier("ponchisaohosting_xyz_pzoom:mixin/title_screen/intro-background-6.png");
    private String[] MESSAGES = {"Cargando.", "Cargando..", "Cargando..."};
    private int messageIndex = 0;
    private static final long MESSAGE_CHANGE_INTERVAL = 1000L;

    private long lastMessageChangeTime = 0L;
    private int currentMessageIndex = 0;
    private int contador;

    // Fade duration in milliseconds
    private static final int FADE_DURATION = 1200;
    private static final int FADE_IN_DURATION = FADE_DURATION / 2;
    private static final int FADE_OUT_DURATION = FADE_DURATION / 2;
    private long fadeInStartTime = -1;
    private long fadeOutStartTime = -1;
    private boolean fadeInComplete = false;
    private boolean fadeOutComplete = false;

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void onRenderOverlay(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null) return;

        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();

        // Renderizar la textura de fondo
        Identifier backgroundTextureId = BACKGROUND_TEXTURE;
        Identifier introbackgroundTextureId = INTRO_BACKGROUND_TEXTURE;
        Optional<Resource> resourceOptional = client.getResourceManager().getResource(backgroundTextureId);
        Optional<Resource> introresourceOptional = client.getResourceManager().getResource(introbackgroundTextureId);

        if (resourceOptional.isPresent() && introresourceOptional.isPresent()) {
            Resource introResource = introresourceOptional.get();
            RenderSystem.setShaderTexture(0, introbackgroundTextureId);
            drawTexture(matrices, 0, 0, 0.0F, 0.0F, width, height, width, height);
            if (!client.getWindow().isFullscreen()) {client.getWindow().toggleFullscreen();}
            TextRenderer textRenderer = client.textRenderer;
            // Obtener el mensaje actual
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastMessageChangeTime >= MESSAGE_CHANGE_INTERVAL) {
                currentMessageIndex = (currentMessageIndex + 1) % MESSAGES.length;
                lastMessageChangeTime = currentTime;
                if (contador >= 9) {
                    MinecraftClient.getInstance().setOverlay((Overlay) null);
                }
                contador++;
                fadeInStartTime = currentTime;
                fadeOutStartTime = fadeInStartTime + FADE_IN_DURATION;
                // PZoom.LOGGER.info(String.valueOf(contador));
            }
            String message = MESSAGES[currentMessageIndex];
            if (contador > 4) {
                // La textura se cargó correctamente, renderizarla
                Resource resource = resourceOptional.get();
                RenderSystem.setShaderTexture(0, backgroundTextureId);
                drawTexture(matrices, 0, 0, 0.0F, 0.0F, width, height, width, height);

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

                Text loading = Text.of(message);
                int loadingTextWidth = textRenderer.getWidth(loading);
                int textHeight = textRenderer.fontHeight;
                int loadingtextX = (width - loadingTextWidth) / 2;
                int loadingtextY = height - textHeight - 10;
                textRenderer.draw(matrices, loading, loadingtextX, loadingtextY, 0xFFFFFF | (alpha << 24));

                // Renderizar texto en medio de la pantalla
                String username = client.getSession().getUsername();

                Text text = Text.of("¡Bienvenido " + username +  " a BLANCHINIILAND 3!");
                int textWidth = textRenderer.getWidth(text);
                int textX = (width - textWidth) / 2;
                int textY = height / 2;
                textRenderer.draw(matrices, text, textX, textY, 0xFFFFFF | (alpha << 24));
            }
        } else {
            // La textura no se pudo cargar, imprimir un mensaje de error
            PZoom.LOGGER.error("Error: No se pudo cargar la textura de fondo.");
        }

        // Cancelar la renderización original de la pantalla de carga
        ci.cancel();
    }
}