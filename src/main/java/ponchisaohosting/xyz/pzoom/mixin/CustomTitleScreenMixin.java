package ponchisaohosting.xyz.pzoom.mixin;

import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ponchisaohosting.xyz.pzoom.screen.TextureScreen;

@Mixin(TitleScreen.class)
public class CustomTitleScreenMixin {

    private static final Identifier CUSTOM_BACKGROUND_TEXTURE = new Identifier("ponchisaohosting_xyz_pzoom:textures/gui/title/background.png");


}
