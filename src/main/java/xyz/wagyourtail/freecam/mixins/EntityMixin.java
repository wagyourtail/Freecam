package xyz.wagyourtail.freecam.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.Entity;
import xyz.wagyourtail.freecam.Freecam;

@Mixin(Entity.class)
public class EntityMixin {
    
    //make some further entities render while in freecam.
    
    @Inject(at = @At("HEAD"), cancellable = true, method = "shouldRender(D)Z")
    public void shouldRender(double distance, CallbackInfoReturnable<Boolean> info) {
        if (Freecam.isFreecam) {
            info.setReturnValue(true);
            info.cancel();
        }
    }
}
