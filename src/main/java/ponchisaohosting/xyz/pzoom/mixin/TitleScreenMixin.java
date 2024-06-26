package ponchisaohosting.xyz.pzoom.mixin;

import com.google.common.util.concurrent.Runnables;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.CreditsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.PressableTextWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.gui.Element;
import net.minecraft.client.util.math.MatrixStack;
import ponchisaohosting.xyz.pzoom.screen.TextureScreen;


import java.lang.invoke.LambdaMetafactory;
import java.util.List;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {

    // Variables para la conexión directa al servidor
    private static final String SERVER_ADDRESS = "node-marb.ponchisaohosting.xyz:25565";
    private static final String CONNECT_BUTTON_TEXT = "BLANCHINIILAND 3";
    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 20;
    private static final Text MENU = Text.literal("Menu by PonchisaoHosting");
    private boolean modsButtonAdjusted = false;

    protected TitleScreenMixin(Text title) {
        super(title);
    }


    @Inject(method = "initWidgetsNormal", at = @At(value = "TAIL"))
    private void addCustomButton(int y, int spacingY, CallbackInfo ci) {
        int offsetX = 0;

        ServerInfo info = new ServerInfo(I18n.translate("selectServer.defaultName"), SERVER_ADDRESS, false);
        info.setResourcePackPolicy(ServerInfo.ResourcePackPolicy.PROMPT);

        var button = new ButtonWidget.Builder(Text.of(CONNECT_BUTTON_TEXT), (buttonWidget) -> {
            ConnectScreen.connect(this, this.client, ServerAddress.parse(SERVER_ADDRESS), info);
            buttonWidget.playDownSound(this.client.getSoundManager());
        }).dimensions(this.width / 2 - 100, y, BUTTON_WIDTH, BUTTON_HEIGHT).build();

        this.addDrawableChild(button);

        int i = this.textRenderer.getWidth(MENU);
        int j = this.width - i - 2;

        this.addDrawableChild(new TextWidget(j, this.height - 20, i, 10, MENU, this.textRenderer));
    }

    @Inject(method = "initWidgetsNormal", at = @At(value = "TAIL"))
    private void addCustomButtonTexture(int y, int spacingY, CallbackInfo ci) {
        int offsetX = 0;

        var button = new ButtonWidget.Builder(Text.of("Mostrar Textura"), (buttonWidget) -> {
            MinecraftClient.getInstance().setScreen(new TextureScreen(Text.of("Textura")));
            buttonWidget.playDownSound(this.client.getSoundManager());
        }).dimensions(this.width / 2 - 100, y + spacingY * 3, 200, 20).build();

        this.addDrawableChild(button);

        int i = this.textRenderer.getWidth(MENU);
        int j = this.width - i - 2;

        this.addDrawableChild(new TextWidget(j, this.height - 20, i, 10, MENU, this.textRenderer));
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void initInject(CallbackInfo ci) {
        List<? extends Element> elements = ((TitleScreen) (Object) this).children();

        for (Element element : elements) {
            if (element instanceof ButtonWidget) {
                ButtonWidget button = (ButtonWidget) element;

                String buttonText = button.getMessage().getString();
                if (//buttonText.equalsIgnoreCase("singleplayer") ||
                        buttonText.equalsIgnoreCase("multiplayer") ||
                        buttonText.equalsIgnoreCase("minecraft realms") ||
                        buttonText.equalsIgnoreCase("accessibility") ||
                        buttonText.equalsIgnoreCase("language") ||
                        buttonText.equalsIgnoreCase("Copyright Mojang AB. Do not distribute!")) {
                    // Oculta los botones de singleplayer, multiplayer, realms, accesibility, language & Copyright
                    button.visible = false;
                }
            }
        }
    }

    @Inject(method = "render", at = @At("HEAD"))
    private void renderInject(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        // Verifica si ya se ha ajustado la posición del botón "mods"
        if (!modsButtonAdjusted) {
            List<? extends Element> elements2 = ((TitleScreen) (Object) this).children();

            for (Element element2 : elements2) {
                if (element2 instanceof ButtonWidget) {
                    ButtonWidget button2 = (ButtonWidget) element2;

                    String buttonText2 = button2.getMessage().getString();

                    if (buttonText2.equalsIgnoreCase("mods")) {
                        final int modButtonX = button2.getX();
                        final int modButtonY = button2.getY();
                        final int modButtonFinal = modButtonY + 50;

                        button2.setPos(modButtonX, modButtonFinal);
                        modsButtonAdjusted = true;
                        break;

                    }
                }
            }
        }
    }
}
