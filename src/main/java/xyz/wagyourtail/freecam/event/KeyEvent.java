package xyz.wagyourtail.freecam.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;


//standard fabric event interface
public interface KeyEvent {
    Event<KeyEvent> EVENT = EventFactory.createArrayBacked(KeyEvent.class, 
        (listeners) -> (window, key, scancode, action, mods) -> {
            for (KeyEvent event : listeners) {
                ActionResult result = event.interact(window, key, scancode, action, mods);
                if (result != ActionResult.PASS) {
                    return result;
                }
            }
            return ActionResult.PASS;
        });
    
    ActionResult interact(long window, int key, int scancode, int action, int mods);
}