package xyz.wagyourtail.freecam.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import xyz.wagyourtail.freecam.Freecam;

import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {
	
	// this fixes the "Attempting to attack an invalid entity" issue. include freecam entity so something like killaura can't target it.
	@Inject(at = @At("HEAD"), method = "attackEntity", cancellable = true)
	private void attackEntity(PlayerEntity player, Entity target, CallbackInfo info) {
		if (target.equals(player) || target.equals(Freecam.fakePlayer)) {
			info.cancel();
		}
	}
	
	
	// this fixes the "cannot interact with self" issue on some servers.
	@Inject(at = @At("HEAD"), method = "interactEntity", cancellable = true)
	private void interactEntity(PlayerEntity player, Entity target, Hand hand, CallbackInfoReturnable<ActionResult> info) {
	    if (target.equals(player)) {
	        info.setReturnValue(ActionResult.FAIL);
            info.cancel();
        }
	}
}
