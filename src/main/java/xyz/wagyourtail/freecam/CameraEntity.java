package xyz.wagyourtail.freecam;

import com.mojang.authlib.GameProfile;

import merged.net.minecraft.client.MinecraftClient;
import merged.net.minecraft.client.input.Input;
import merged.net.minecraft.client.input.KeyboardInput;
import merged.net.minecraft.client.network.OtherClientPlayerEntity;
import merged.net.minecraft.client.world.ClientWorld;
import merged.net.minecraft.entity.Entity;
import merged.net.minecraft.entity.player.HungerManager;
import merged.net.minecraft.util.math.Vec3d;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import xyz.wagyourtail.multiversion.injected.split.annotations.*;

public class CameraEntity extends OtherClientPlayerEntity {
	public Input input;

	// add 3rd argument: merged.net.minecraft.network.encryption.PlayerPublicKey as null
	@Modify(versions = "1.19.1", ref = @Ref(value = "merged/net/minecraft/client/network/OtherClientPlayerEntity", member = "<init>", desc = "(Lmerged/net/minecraft/client/world/ClientWorld;Lcom/mojang/authlib/GameProfile;)V"))
	public static void modifySuper(MethodNode node, int index) {
		MethodInsnNode invokeSuper = (MethodInsnNode) node.instructions.get(index);
		// change desc
		invokeSuper.desc = "(Lmerged/net/minecraft/client/world/ClientWorld;Lcom/mojang/authlib/GameProfile;Lmerged/net/minecraft/network/encryption/PlayerPublicKey;)V";
		// add null before
		node.instructions.insertBefore(invokeSuper, new InsnNode(Opcodes.ACONST_NULL));
	}

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

	@Stub(versions = "1.20.2")
	public static void addEntity(ClientWorld world, int id, Entity entity) {
		world.addEntity(entity);
	}
	
	public void despawn() {
		MinecraftClient mc = MinecraftClient.getInstance();
		mc.world.removeEntity(this.getId());
	}

	@Stub(versions = {"1.17.1", "1.18.2", "1.19.1", "1.19.4", "1.20.1", "1.20.2"})
	public static void removeEntity(ClientWorld world, int id) {
		world.removeEntity(id, RemovalReason.DISCARDED);
	}

	@Stub(versions = {"1.19.1", "1.19.4", "1.20.1", "1.20.2"})
	public static void tick(Input input, boolean slowDown) {
		// adds settable slowDown multiplier to input.tick
		input.tick(slowDown, 0.3f);
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

	@Override
	@Versioned(versions = { "1.16.5" })
	public float getYaw() {
		return yaw;
	}
}