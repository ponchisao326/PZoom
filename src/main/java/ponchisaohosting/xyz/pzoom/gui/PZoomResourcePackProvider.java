// package ponchisaohosting.xyz.pzoom.gui;
//
// import net.minecraft.client.MinecraftClient;
// import net.minecraft.resource.ResourcePackManager;
// import net.minecraft.resource.ResourcePackProfile;
// import net.minecraft.resource.ResourcePackProvider;
// import net.minecraft.util.Identifier;
// import ponchisaohosting.xyz.pzoom.PZoom;
//
// import java.util.Collections;
// import java.util.List;
//
// public class PZoomResourcePackProvider implements ResourcePackProvider {
//     private static final Identifier RESOURCE_PACK_ID = new Identifier(PZoom.MOD_ID, "pzoom_resource_pack");
//
//     public static void register() {
//         ResourcePackManager resourcePackManager = MinecraftClient.getInstance().getResourcePackManager();
//         resourcePackManager.registerProvider(new PZoomResourcePackProvider());
//     }
//
//     public static void loadResourcePack() {
//         MinecraftClient.getInstance().getResourcePackManager().scanPacks();
//     }
//
//     @Override
//     public <T extends ResourcePackProfile> List<T> createResourcePackProfiles(ResourcePackManager manager, ResourcePackProfile.Factory<T> factory) {
//         T profile = factory.create(RESOURCE_PACK_ID, "PZoom Resource Pack", () -> RESOURCE_PACK_ID.toString());
//         return Collections.singletonList(profile);
//     }
// }
//