package ponchisaohosting.xyz.pzoom.mixin;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import ponchisaohosting.xyz.pzoom.PZoom;

@Mixin(PlayerEntity.class)
public class onDeathScreen {
    @Inject(method="onDeath", at=@At("TAIL"))
    private void died(DamageSource damageSource, CallbackInfo info){
        PZoom.LOGGER.info(damageSource.getName() + "Death Mixin");

        PlayerEntity player = (PlayerEntity) (Object) this;
        sendDeathMessageToAllPlayers(player);
    }

    private void sendDeathMessageToAllPlayers(PlayerEntity player) {
        MinecraftServer server = player.getServer();
        if (server != null) {
            server.getPlayerManager().getPlayerList().forEach(serverPlayer -> {
                serverPlayer.sendMessage(Text.literal(player.getName().toString() + " has perished!"), false);
            });
        }
    }
}