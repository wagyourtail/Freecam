package xyz.wagyourtail.freecam;

import com.mojang.authlib.GameProfile;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.HungerManager;

public class CameraEntity extends OtherClientPlayerEntity {
	
	public CameraEntity(ClientWorld clientWorld, GameProfile gameProfile, HungerManager hunger) {
		super(clientWorld, gameProfile);
		this.hungerManager = hunger;
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
	
	
	// spawn and despawn entity from the world.
	public void spawn() {
		MinecraftClient mc = MinecraftClient.getInstance();
		mc.world.addEntity(this.getEntityId(), this);
	}
	
	public void despawn() {
		MinecraftClient mc = MinecraftClient.getInstance();
		mc.world.removeEntity(this.getEntityId());
	}
	
}