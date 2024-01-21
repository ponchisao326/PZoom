package ponchisaohosting.xyz.pzoom.mixin;

import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {

    // Variables para la conexión directa al servidor
    private static final String SERVER_ADDRESS = "node-marb.ponchisaohosting.xyz:25565";
    private static final String SERVER_NAME = "Blanchiniiland 3";
    private static final String CONNECT_BUTTON_TEXT = "Conectar";
    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 20;

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
        }).dimensions(this.width / 2 - 100 + offsetX, y, BUTTON_WIDTH, BUTTON_HEIGHT).build();

        this.addDrawableChild(button);
    }
}
