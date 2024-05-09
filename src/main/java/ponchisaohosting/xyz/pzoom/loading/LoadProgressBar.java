package ponchisaohosting.xyz.pzoom.loading;

import net.minecraft.client.gui.screen.SplashOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SplashOverlay.class)
public interface LoadProgressBar {
    @Accessor("progress")
    float getProgress();
}
