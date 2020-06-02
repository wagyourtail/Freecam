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
	
	public void spawn() {
		MinecraftClient mc = MinecraftClient.getInstance();
		mc.world.addEntity(this.getEntityId(), this);
	}
	
	public void despawn() {
		MinecraftClient mc = MinecraftClient.getInstance();
		mc.world.removeEntity(this.getEntityId());
	}
	
}