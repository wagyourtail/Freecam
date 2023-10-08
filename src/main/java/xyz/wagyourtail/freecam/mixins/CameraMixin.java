package xyz.wagyourtail.freecam.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;

import merged.net.minecraft.client.MinecraftClient;
import merged.net.minecraft.client.render.Camera;
import merged.net.minecraft.entity.Entity;
import xyz.wagyourtail.freecam.Freecam;

@Mixin(Camera.class)
public class CameraMixin {
    
	// makes mc.player render while in freecam.
    
    
	@Inject(at = @At("HEAD"), cancellable = true, method ="getFocusedEntity")
	public void getFocusedEntity(CallbackInfoReturnable<Entity> info) {
        MinecraftClient mc = MinecraftClient.getInstance();
	    if (Freecam.isFreecam) {
	        info.setReturnValue(mc.player);
	        info.cancel();
	    }
	}
	
	@Inject(at = @At("HEAD"), cancellable = true, method= "isThirdPerson")
	public void isThirdPerson(CallbackInfoReturnable<Boolean> info) {
		if (Freecam.isFreecam) {
		    info.setReturnValue(true);
		    info.cancel();
		}
	}
}
