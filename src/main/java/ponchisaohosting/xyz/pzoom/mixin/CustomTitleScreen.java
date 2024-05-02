package ponchisaohosting.xyz.pzoom.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.gui.DrawableHelper;

import static net.minecraft.client.gui.DrawableHelper.drawTexture;


@Mixin(TitleScreen.class)
public class CustomTitleScreen {
    private static final int NUM_FRAMES = 91; // Número total de fotogramas del video
    private int currentFrame = 0;
    private final Identifier[] frameTextures = new Identifier[NUM_FRAMES]; // Array para almacenar las ubicaciones de los fotogramas

    private static final Identifier CUSTOM_BACKGROUND_TEXTURE =  new Identifier("ponchisaohosting_xyz_pzoom:mixin/title_screen/1.png");
    // @Inject(method = "render", at = @At("HEAD"))
    // private void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        // // Construir la ruta de la textura del fotograma actual
        // Identifier frameTexture = new Identifier("ponchisaohosting_xyz_pzoom:mixin/title_screen/" + (currentFrame + 1) + ".png");
//
        // // Renderizar el fotograma actual en la pantalla
        // RenderSystem.setShaderTexture(0, frameTexture);
        // // Utiliza las clases y métodos proporcionados por Fabric y Minecraft para renderizar el fotograma en la posición deseada en la pantalla
//
        // // Incrementar el índice del fotograma para el siguiente cuadro
        // currentFrame++;
        // System.out.println("Loading frame: " + currentFrame);
        // if (currentFrame >= NUM_FRAMES) {
        //     currentFrame = 0; // Reiniciar la reproducción cuando se alcanza el último fotograma
        // }

    //     MinecraftClient.getInstance().getTextureManager().bindTexture(CUSTOM_BACKGROUND_TEXTURE);
    //     int screenWidth = MinecraftClient.getInstance().getWindow().getScaledWidth();
    //     int screenHeight = MinecraftClient.getInstance().getWindow().getScaledHeight();
    //     drawTexture(matrices, 0, 0, screenWidth, screenHeight, 0, 0, screenWidth, screenHeight, screenWidth, screenHeight);
    // }

    // @Inject(at = @At("TAIL"), method = "render")
    // private void renderBackground(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
    //     // Lógica para dibujar tu propia imagen de fondo
    //     MinecraftClient.getInstance().getTextureManager().bindTexture(CUSTOM_BACKGROUND_TEXTURE);
    //     System.out.println("Dibujando textura: " + CUSTOM_BACKGROUND_TEXTURE);
    //     DrawableHelper.drawTexture(matrices, 0, 0, 0, 0, MinecraftClient.getInstance().getWindow().getScaledWidth(), MinecraftClient.getInstance().getWindow().getScaledHeight(), MinecraftClient.getInstance().getWindow().getScaledWidth(), MinecraftClient.getInstance().getWindow().getScaledHeight());
    //     System.out.println("Textura dibujada: " + CUSTOM_BACKGROUND_TEXTURE);
    // }
}
