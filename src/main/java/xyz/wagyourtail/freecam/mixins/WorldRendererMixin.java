package xyz.wagyourtail.freecam.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import merged.net.minecraft.client.render.WorldRenderer;
import xyz.wagyourtail.freecam.Freecam;
import xyz.wagyourtail.multiversion.injected.split.annotations.Remove;
import xyz.wagyourtail.multiversion.injected.split.annotations.Versioned;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    
    //cave culling fix
    @Versioned(versions = {"1.17.1", "1.16.5"})
    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;setupTerrain(Lnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/Frustum;ZIZ)V"), index = 4)
    public boolean isSpectator_v1_17_1(boolean spectator) {
        return spectator || Freecam.isFreecam;
    }

    @Remove(versions = {"1.17.1", "1.16.5"})
    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;setupTerrain(Lnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/Frustum;ZZ)V"), index = 3)
    public boolean isSpectator_v1_18_2(boolean spectator) {
        return spectator || Freecam.isFreecam;
    }
}
