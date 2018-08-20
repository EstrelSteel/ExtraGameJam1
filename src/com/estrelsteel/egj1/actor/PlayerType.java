package com.estrelsteel.egj1.actor;

import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.image.Renderable;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;
import com.estrelsteel.engine2.velocity.Velocity;

public abstract class PlayerType extends Actor {

	private Velocity gravity;
	private Velocity velocity;
	private boolean grounded;
	private boolean finished;
	
	public PlayerType(String name, Rectangle loc) {
		super(name, loc);
		this.velocity = new Velocity(0.0);
	}
	
//	public abstract void correctForFloor(int floor);
	public abstract void collideNS(Renderable r);
	public abstract void collideEW(Renderable r);
	
	public Velocity getGravity() {
		return gravity;
	}
	
	public Velocity getVelocity() {
		return velocity;
	}
	
	public boolean isGrounded() {
		return grounded;
	}
	
//	@Override
//	public void updateCollision() {
//		getCollision().setCollideArea(new RectangleCollideArea(QuickRectangle.location(getLocation().getX() + 1,
//				getLocation().getY(), getLocation().getWidth() - 2, getLocation().getHeight())));
//		
//	}
	
	public void applyGravity() {
		velocity.applyVelocity(gravity);
	}
	
	public void applyVelocity() {
		setLocation(QuickRectangle.translate(velocity.getVelocityX(), velocity.getVelocityY(), getLocation()));
	}
	
	public void applyAntiVelocity() {
		setLocation(QuickRectangle.translate(-velocity.getVelocityX(), -velocity.getVelocityY(), getLocation()));
	}
	
	public PlayerType setGravity(Velocity gravity) {
		this.gravity = gravity;
		return this;
	}
	
	public PlayerType setVelocity(Velocity velocity) {
		this.velocity = velocity;
		return this;
	}

	public PlayerType setGrounded(boolean grounded) {
		this.grounded = grounded;
		return this;
	}

	public boolean hasFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}
}
