package xyz.wagyourtail.freecam.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import xyz.wagyourtail.freecam.Freecam;

@Mixin(InGameHud.class)
public class InGameHudMixin {
	
	@Inject(at = @At("HEAD"), method = "getCameraPlayer", cancellable = true)
	private void getCameraPlayer(CallbackInfoReturnable<PlayerEntity> info) {
		if (Freecam.isFreecam) {
			MinecraftClient mc = MinecraftClient.getInstance();
			info.setReturnValue((PlayerEntity) mc.player);
			info.cancel();
		}
	}
}