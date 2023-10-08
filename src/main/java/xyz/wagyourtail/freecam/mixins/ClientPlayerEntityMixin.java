package xyz.wagyourtail.freecam.mixins;

import merged.net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;

import com.mojang.authlib.GameProfile;

import merged.net.minecraft.client.MinecraftClient;
import merged.net.minecraft.client.network.AbstractClientPlayerEntity;
import merged.net.minecraft.client.network.ClientPlayerEntity;
import merged.net.minecraft.client.world.ClientWorld;
import xyz.wagyourtail.freecam.Freecam;
import xyz.wagyourtail.multiversion.injected.split.annotations.Versioned;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {

	
	@Shadow
	@Final
	protected MinecraftClient client;
	
	//redirect mouse movement look stuff to the freecam entity.
	@Override
	public void changeLookDirection(double dx, double dy) {
		if (this.equals(client.player) && Freecam.isFreecam && Freecam.fakePlayer != null) {
			Freecam.fakePlayer.changeLookDirection(dx, dy);
			Freecam.fakePlayer.setHeadYaw(Freecam.fakePlayer.getYaw());
		} else {
			super.changeLookDirection(dx, dy);
		}
	}

	// this allows for the player to move from baritone.
	@Inject(at = @At("HEAD"), cancellable = true, method = "isCamera")
	public void isCamera(CallbackInfoReturnable<Boolean> info) {
		if (Freecam.isFreecam) {
		    info.setReturnValue(true);
		    info.cancel();
		}
	}
	
	// IGNORE
	@Versioned(versions = {})
	public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
		super(world, profile);
		// TODO Auto-generated constructor stub
	}

}
