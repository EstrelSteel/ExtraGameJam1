package com.estrelsteel.egj1.actor;

import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.shape.collide.RectangleCollideArea;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;

public class Spike extends Actor {

	public Spike(Rectangle loc, int r) {
		super("spikes " + r, loc);
		load(r);
	}
	
	public Spike(String name, Rectangle loc, int r) {
		super(name, loc);
		load(r);
	}
	
	private void load(int r) {
		getAnimations().add(new Animation().load("ANI 0 SPIKE -1 false 50"));
		getAnimations().add(new Animation().load("ANI 1 ANTI_SPIKE -1 false 51"));
		
		getCollision().setCollide(true);
		if(r == 0) {
			getCollision().setCollideArea(new RectangleCollideArea(QuickRectangle.location(
					getLocation().getX() + 1, getLocation().getY() + 1, getLocation().getWidth() - 2, getLocation().getHeight())));
		}
		
		setRunningAnimationNumber(r);
	}
}