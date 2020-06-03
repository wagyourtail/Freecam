package xyz.wagyourtail.freecam.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.Keyboard;
import net.minecraft.util.ActionResult;

import xyz.wagyourtail.freecam.event.KeyEvent;

@Mixin(Keyboard.class)
class KeyboardMixin {
    
	// key event mixin
    @Inject(at = @At("HEAD"), method = "onKey", cancellable = true)
    private void onKeyMixin(long window, int key, int scancode, int i, int j, final CallbackInfo info) {
        ActionResult result = KeyEvent.EVENT.invoker().interact(window, key, scancode, i, j);
        if (result != ActionResult.PASS) {
            info.cancel();
        }
    }
}