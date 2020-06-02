package xyz.wagyourtail.freecam.mixins;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.mojang.authlib.GameProfile;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import xyz.wagyourtail.freecam.Freecam;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {

	@Shadow
	@Final
	protected MinecraftClient client;
	
	// this allows for the player to move from baritone.
	@Overwrite
	public boolean isCamera() {
		return this.client.getCameraEntity() == this || Freecam.isFreecam;
	}
	
	// IGNORE
	public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
		super(world, profile);
		// TODO Auto-generated constructor stub
	}

}
