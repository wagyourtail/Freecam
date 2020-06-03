package xyz.wagyourtail.freecam.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import xyz.wagyourtail.freecam.Freecam;

import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {
	@Inject(at = @At("HEAD"), method = "attackEntity", cancellable = true)
	private void attackEntity(PlayerEntity player, Entity target, CallbackInfo info) {
		if (target.equals(player) || target.equals(Freecam.fakePlayer)) {
			info.cancel();
		}
	}
}
