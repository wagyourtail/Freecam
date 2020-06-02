package xyz.wagyourtail.freecam;

import org.lwjgl.glfw.GLFW;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import xyz.wagyourtail.freecam.event.KeyEvent;

public class Freecam implements ClientModInitializer {
	public static final String MOD_ID = "freecam";
	private static FabricKeyBinding keyBinding;
	private static CameraEntity fakePlayer;
	private static float upV;
	private static float forwardV;
	private static float sideV;
	public static boolean isFreecam = false;
	
	
	@Override
	public void onInitializeClient() {
		// TODO Auto-generated method stub
		
		keyBinding = FabricKeyBinding.Builder.create(new Identifier("freecam", "toggle"), InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_U, "Freecam").build();
        KeyBindingRegistry.INSTANCE.addCategory("Freecam");
        KeyBindingRegistry.INSTANCE.register(keyBinding);
        
        ClientTickCallback.EVENT.register(e -> {
        	if (fakePlayer != null) {
        		MinecraftClient mc = MinecraftClient.getInstance();
        		
        		fakePlayer.setHealth(mc.player.getHealth());
        		//figure out how to un-tie this from the actual player
        		fakePlayer.setYaw(mc.player.yaw);
        		fakePlayer.setHeadYaw(mc.player.headYaw);
        		fakePlayer.pitch = mc.player.pitch;
        		
        		
        		

        		//this is stupid do this different, see comment about movement below.
        		float speed = .25F;
        		
        		fakePlayer.setVelocity(0, 0, 0);
        		
        		
        		Vec3d forward = new Vec3d(0, 0, speed * 2.5).rotateY(-(float) Math.toRadians(fakePlayer.headYaw));
        		Vec3d strafe = forward.rotateY((float) Math.toRadians(90));
        		Vec3d motion = fakePlayer.getVelocity();
        		motion = motion.add(0, 1.5 * speed * upV, 0);
        		motion = motion.add(strafe.x * sideV, 0, strafe.z * sideV);
        		motion = motion.add(forward.x * forwardV, 0, forward.z * forwardV);
        		
        		fakePlayer.setPos(fakePlayer.getX() + motion.x, fakePlayer.getY() + motion.y, fakePlayer.getZ() + motion.z);
        	}
        });
        
        KeyEvent.EVENT.register((window, key, scancode, action, mods) -> {
            MinecraftClient mc = MinecraftClient.getInstance();
            
            if (keyBinding.matchesKey(key, scancode) && action == 1) {
            	isFreecam = !isFreecam; 
            	if (isFreecam && mc.player != null) {
            		//doing hunger like this (setting same hunger manager) might not be the best way...
        			fakePlayer = new CameraEntity(mc.world, mc.player.getGameProfile(), mc.player.getHungerManager());
            		fakePlayer.copyPositionAndRotation(mc.player);
            		fakePlayer.setHeadYaw(mc.player.headYaw);
        			fakePlayer.spawn();
            		mc.setCameraEntity(fakePlayer);
            	} else {
            		mc.setCameraEntity(mc.player);
            		if (fakePlayer != null) fakePlayer.despawn();
            		fakePlayer = null;
            	}
            	return ActionResult.PASS;
            }
            
            //this is stupid do this different.
            // game input for the mc.player is overridden by Baritone [here](https://github.com/cabaletta/baritone/blob/master/src/main/java/baritone/utils/InputOverrideHandler.java#L114). Freecam should leave this as-is when Baritone is pathing. When Baritone is not pathing, it needs to override mc.player.movementInput itself to something that ignores all input. 
            if (isFreecam) {            	
	            if (mc.options.keyForward.matchesKey(key, scancode)) {
	            	if (action > 0) forwardV = 1.0F;
	            	else forwardV = 0;
	            	return ActionResult.FAIL;
	            } else if (mc.options.keyBack.matchesKey(key, scancode)) {
	            	if (action > 0) forwardV = -1.0F;
	            	else forwardV = 0;
	            	return ActionResult.FAIL;
	            } else if (mc.options.keyLeft.matchesKey(key, scancode)) {
	            	if (action > 0) sideV = 1.0F;
	            	else sideV = 0;
	            	return ActionResult.FAIL;
	            } else if (mc.options.keyRight.matchesKey(key, scancode)) {
	            	if (action > 0) sideV = -1.0F;
	            	else sideV = 0;
	            	return ActionResult.FAIL;
	            } else if ((mc.options.keyJump.matchesKey(key, scancode))) {
	            	if (action > 0) upV = 1.0F;
	            	else upV = 0;
	            	return ActionResult.FAIL;
	            } else if (mc.options.keySneak.matchesKey(key, scancode)) {
	            	if (action > 0) upV = -1.0F;
	            	else upV = 0;
	            	return ActionResult.FAIL;
	            }
            }
            
			return ActionResult.PASS;
        });
	}

	
}
