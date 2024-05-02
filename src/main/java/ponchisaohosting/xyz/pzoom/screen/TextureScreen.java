package ponchisaohosting.xyz.pzoom.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class TextureScreen extends Screen {
    private static final Identifier CUSTOM_BACKGROUND_TEXTURE = new Identifier("ponchisaohosting_xyz_pzoom:mixin/title_screen/prueba.png");
    private boolean textureRendered = false;
    public ButtonWidget button1;
    public ButtonWidget button2;


    public TextureScreen(Text title) {
        super(title);
    }

    @Override
    protected void init() {
        super.init();
        button1 = ButtonWidget.builder(Text.literal("Button 1"), button -> {
                    System.out.println("You clicked button1!");
                })
                .dimensions(width / 2 - 205, 20, 200, 20)
                .tooltip(Tooltip.of(Text.literal("Tooltip of button1")))
                .build();
        button2 = ButtonWidget.builder(Text.literal("Exit"), button -> {
                    close();
                })
                .dimensions(width / 2 + 5, 20, 200, 20)
                .tooltip(Tooltip.of(Text.literal("Tooltip of button2")))
                .build();

        addDrawableChild(button1);
        addDrawableChild(button2);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (!textureRendered) {
            this.renderBackground(matrices);

            // Obtener el ancho y alto de la pantalla
            int screenWidth = MinecraftClient.getInstance().getWindow().getWidth();
            int screenHeight = MinecraftClient.getInstance().getWindow().getHeight();

            // Renderiza la textura CUSTOM_BACKGROUND_TEXTURE en la pantalla
            MinecraftClient.getInstance().getTextureManager().bindTexture(CUSTOM_BACKGROUND_TEXTURE);
            System.out.println("Textura cargada correctamente: " + CUSTOM_BACKGROUND_TEXTURE);
            drawTexture(matrices, 0, 0, screenWidth, screenHeight, 0.0f, 0.0f, 1, 1, 1920, 1080);


            super.render(matrices, mouseX, mouseY, delta);
            textureRendered = true;
        }
    }
}
