package ponchisaohosting.xyz.pzoom.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class PzoomMainMenu extends Screen {
    private static final Identifier CUSTOM_BACKGROUND_TEXTURE = new Identifier("ponchisaohosting_xyz_pzoom", "mixin/prueba.png");

    public PzoomMainMenu(Text title) {
        super(title);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        RenderSystem.setShaderTexture(0, CUSTOM_BACKGROUND_TEXTURE);
        drawTexture(matrices, 0, 0, width, height, 0, 0, 0, 0);
        super.render(matrices, mouseX, mouseY, delta);
    }
}
