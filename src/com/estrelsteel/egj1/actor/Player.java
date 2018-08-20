package com.estrelsteel.egj1.actor;

import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.image.Renderable;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;

public class Player extends PlayerType {

	public Player(Rectangle loc) {
		super("Player", loc);
		
		getAnimations().add(new Animation().load("ANI 0 PLAYER -1 false 0"));
	}
	
//	@Override
//	public void updateCollision() {
//		getCollision().setCollideArea(new RectangleCollideArea(QuickRectangle.location(getLocation().getX() + 1,
//				getLocation().getY(), getLocation().getWidth() - 1, getLocation().getHeight())));
//		
//	}

//	@Override
//	public void correctForFloor(int floor) {
//		if(false && getLocation().getY() + getLocation().getHeight() > floor) {
//			getVelocity().setVelocityY(0);
//			setLocation(QuickRectangle.location(getLocation().getX(), floor - getLocation().getHeight(), getLocation().getWidth(), getLocation().getHeight()));
//			setGrounded(true);
//		}
//	}

	@Override
	public void collideNS(Renderable r) {
		if(getVelocity().getVelocityY() > 0) { // UP
			setLocation(QuickRectangle.location(getLocation().getX(), r.getLocation().getY() - getLocation().getHeight(),
					getLocation().getWidth(), getLocation().getHeight()));
			setGrounded(true);
		}
		else { // DOWN
			setLocation(QuickRectangle.location(getLocation().getX(), r.getLocation().getY() + r.getLocation().getHeight(),
					getLocation().getWidth(), getLocation().getHeight()));
		}
		updateCollision();
		getVelocity().setVelocityY(0);
	}

	@Override
	public void collideEW(Renderable r) {
//		System.out.println(getVelocity().getVelocityX() + "\t" + getVelocity().getVelocityY());
//		System.out.println("collide ew.");
		if(getVelocity().getVelocityX() < 0) { // LEFT
			setLocation(QuickRectangle.location(r.getLocation().getX() + r.getLocation().getWidth(), getLocation().getY(),
					getLocation().getWidth(), getLocation().getHeight()));
		}
		else { // RIGHT
			setLocation(QuickRectangle.location(r.getLocation().getX() - getLocation().getWidth(), getLocation().getY(),
					getLocation().getWidth(), getLocation().getHeight()));
		}
		updateCollision();
		getVelocity().setVelocityX(0);
	}

}
