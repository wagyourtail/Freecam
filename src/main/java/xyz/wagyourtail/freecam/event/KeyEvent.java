package xyz.wagyourtail.freecam.event;

import merged.net.fabricmc.fabric.api.event.Event;
import merged.net.minecraft.util.ActionResult;
import merged.net.fabricmc.fabric.api.event.EventFactory;

//standard fabric event interface
public interface KeyEvent {
    Event EVENT = EventFactory.createArrayBacked(KeyEvent.class,
        (listeners) -> (KeyEvent) (window, key, scancode, action, mods) -> {
            for (KeyEvent event : (KeyEvent[]) listeners) {
                ActionResult result = event.interact(window, key, scancode, action, mods);
                if (result != ActionResult.PASS) {
                    return result;
                }
            }
            return ActionResult.PASS;
        });
    
    ActionResult interact(long window, int key, int scancode, int action, int mods);
}