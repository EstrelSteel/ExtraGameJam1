package com.estrelsteel.egj1.actor;

import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.image.Renderable;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;

public class AntiPlayer extends PlayerType {

	public AntiPlayer(Rectangle loc) {
		super("AntiPlayer", loc);
		
		getAnimations().add(new Animation().load("ANI 0 PLAYER -1 false 10"));
	}
	
//	@Override
//	public void correctForFloor(int floor) {
//		if(false && getLocation().getY() <= floor) {
//			getVelocity().setVelocityY(0);
//			setLocation(QuickRectangle.location(getLocation().getX(), floor, getLocation().getWidth(), getLocation().getHeight()));
//			setGrounded(true);
//		}
//	}

	@Override
	public void collideNS(Renderable r) {
		if(getVelocity().getVelocityY() > 0) { // UP
			setLocation(QuickRectangle.location(getLocation().getX(), r.getLocation().getY() - getLocation().getHeight(),
					getLocation().getWidth(), getLocation().getHeight()));
		}
		else { // DOWN
			setLocation(QuickRectangle.location(getLocation().getX(), r.getLocation().getY() + r.getLocation().getHeight(),
					getLocation().getWidth(), getLocation().getHeight()));
			setGrounded(true);
		}
		getVelocity().setVelocityY(0);
	}

	@Override
	public void collideEW(Renderable r) {
		if(getVelocity().getVelocityX() < 0) { // LEFT
			setLocation(QuickRectangle.location(r.getLocation().getX() + r.getLocation().getWidth(), getLocation().getY(),
					getLocation().getWidth(), getLocation().getHeight()));
		}
		else { // RIGHT
			setLocation(QuickRectangle.location(r.getLocation().getX() - getLocation().getWidth(), getLocation().getY(),
					getLocation().getWidth(), getLocation().getHeight()));
		}
		getVelocity().setVelocityX(0);
	}

}
