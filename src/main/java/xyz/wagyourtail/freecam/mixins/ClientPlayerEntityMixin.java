package xyz.wagyourtail.freecam.mixins;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;

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
	
	
	//redirect mouse movement look stuff to the freecam entity.
	@Override
	public void changeLookDirection(double dx, double dy) {
		if (this.equals(client.player) && Freecam.isFreecam && Freecam.fakePlayer != null) {
			Freecam.fakePlayer.changeLookDirection(dx, dy);
			Freecam.fakePlayer.setHeadYaw(Freecam.fakePlayer.yaw);
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
	public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
		super(world, profile);
		// TODO Auto-generated constructor stub
	}

}
