package ponchisaohosting.xyz.pzoom.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String ZOOM_CATEGORY = "key.category.zoom";
    public static final String ZOOM_KEY = "key.ponchisaohosting.zoomkey";

    public static KeyBinding zoomKey;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (zoomKey.wasPressed()) {
                client.player.sendMessage(Text.of("I pressed a Key"));
            }
        });
    }

    public static void register() {
        zoomKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                ZOOM_KEY,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_C,
                    ZOOM_CATEGORY
        ));

        registerKeyInputs();
    }

}
