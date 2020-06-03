package xyz.wagyourtail.freecam.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import net.minecraft.client.render.WorldRenderer;
import xyz.wagyourtail.freecam.Freecam;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
	
	//cave culling fix
	@ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;setupTerrain(Lnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/Frustum;ZIZ)V"), index = 4)
	public boolean isSpectator(boolean spectator) {
		return spectator || Freecam.isFreecam;
	}
}
