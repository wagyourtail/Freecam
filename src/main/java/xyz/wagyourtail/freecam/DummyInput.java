package xyz.wagyourtail.freecam;

import net.minecraft.client.input.Input;

public class DummyInput extends Input {
	public DummyInput() {
		this.movementSideways = 0.0F;
		this.movementForward = 0.0F;
		this.pressingBack = false;
		this.pressingForward = false;
		this.pressingLeft = false;
		this.pressingRight = false;
		this.jumping = false;
		this.sneaking = false;
	}
	
}
