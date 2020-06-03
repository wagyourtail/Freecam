package xyz.wagyourtail.freecam.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import xyz.wagyourtail.freecam.Freecam;

@Mixin(Camera.class)
public class CameraMixin {
	@Shadow
	private Entity focusedEntity;
	
	@Shadow
	private boolean thirdPerson;
	
	@Overwrite
	public Entity getFocusedEntity() {
		MinecraftClient mc = MinecraftClient.getInstance();
		return Freecam.isFreecam ? (Entity) mc.player : focusedEntity;
	}
	
	@Overwrite
	public boolean isThirdPerson() {
		return thirdPerson || Freecam.isFreecam;
	}
}
