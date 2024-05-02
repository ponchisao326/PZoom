package ponchisaohosting.xyz.pzoom.gui;

import com.terraformersmc.modmenu.gui.widget.ModMenuButtonWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;


public class CustomMainMenu extends Screen {

    public ButtonWidget serverButton;
    public ButtonWidget button2;
    public ButtonWidget optionsButton;
    public ButtonWidget modsButton;

    private static final String SERVER_ADDRESS = "node-marb.ponchisaohosting.xyz:25565";
    private static final String CONNECT_BUTTON_TEXT = "BLANCHINIILAND 3";
    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 20;
    private static final Text MENU = Text.literal("Menu by PonchisaoHosting");

    public CustomMainMenu() {
        super(Text.of("Main Menu"));
    }

    @Override
    protected void init() {
        super.init();
        int offsetX = 0;
        int y = this.height / 2 - 30;

        ServerInfo info = new ServerInfo(I18n.translate("selectServer.defaultName"), SERVER_ADDRESS, false);
        info.setResourcePackPolicy(ServerInfo.ResourcePackPolicy.PROMPT);

        serverButton = new ButtonWidget.Builder(Text.of(CONNECT_BUTTON_TEXT), (buttonWidget) -> {
            ConnectScreen.connect(this, this.client, ServerAddress.parse(SERVER_ADDRESS), info);
            buttonWidget.playDownSound(this.client.getSoundManager());
        }).dimensions(this.width / 2 - 100, y, BUTTON_WIDTH, BUTTON_HEIGHT).build();

        button2 = ButtonWidget.builder(Text.literal("Exit"), button -> {
                    close();
                })
                .dimensions(width / 2 + 5, 20, 200, 20)
                .tooltip(Tooltip.of(Text.literal("Tooltip of button2")))
                .build();

        optionsButton = new ButtonWidget.Builder(Text.of("Options"), (buttonWidget) -> {
            MinecraftClient.getInstance().setScreen(new OptionsScreen(this, this.client.options));
        }).dimensions((int) (this.width / 2 - 100), this.height / 4 * 3, 100, 20).build();

        modsButton = new ModMenuButtonWidget(this.width / 2 - 100, this.height / 4 * 3 + 24, 200, 20, Text.of("Mods"), this);

        addDrawableChild(serverButton);
        addDrawableChild(button2);
        addDrawableChild(optionsButton);
        addDrawableChild(modsButton);

    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        // Renderizar elementos en la pantalla
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
    }

}
