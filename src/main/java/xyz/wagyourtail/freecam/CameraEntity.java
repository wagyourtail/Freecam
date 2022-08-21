package xyz.wagyourtail.freecam;

import com.mojang.authlib.GameProfile;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.util.math.Vec3d;

public class CameraEntity extends OtherClientPlayerEntity {
	public Input input;
	
	public CameraEntity(ClientWorld clientWorld, GameProfile gameProfile, HungerManager hunger) {
		super(clientWorld, gameProfile);
		this.hungerManager = hunger;
		MinecraftClient mc = MinecraftClient.getInstance();
		this.input = new KeyboardInput(mc.options);
		// TODO Auto-generated constructor stub
	}
	
	//make it not render because the constant 3rd person needed for rendering the actual mc.player
	//causes this to be rendered blocking the camera
	public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
		return false;
	}
	
	public boolean shouldRender(double distance) {
		return false;
	}
	
	public boolean shouldRenderName() {
		return false;
	}
	
	// spawn and despawn entity from the world.
	public void spawn() {
		MinecraftClient mc = MinecraftClient.getInstance();
		mc.world.addEntity(this.getId(), this);
	}
	
	public void despawn() {
		MinecraftClient mc = MinecraftClient.getInstance();
		mc.world.removeEntity(this.getId(), RemovalReason.DISCARDED);
	}
	
	
	public void tickMovement() {
		this.setVelocity(0, 0, 0);
		
		input.tick(false);
		
		float upDown = (this.input.sneaking ? -Freecam.speed : 0) + (this.input.jumping ? Freecam.speed : 0);
		
		Vec3d forward = new Vec3d(0, 0, Freecam.speed * 2.5).rotateY(-(float) Math.toRadians(this.headYaw));
		Vec3d strafe = forward.rotateY((float) Math.toRadians(90));
		Vec3d motion = this.getVelocity();
		
		motion = motion.add(0, 2 * upDown, 0);
		motion = motion.add(strafe.x * input.movementSideways, 0, strafe.z * input.movementSideways);
		motion = motion.add(forward.x * input.movementForward, 0, forward.z * input.movementForward);
		
		this.setPos(this.getX() + motion.x, this.getY() + motion.y, this.getZ() + motion.z);
	}
}