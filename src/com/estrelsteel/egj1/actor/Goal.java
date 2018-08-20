package com.estrelsteel.egj1.actor;

import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.shape.collide.RectangleCollideArea;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;

public class Goal extends Actor {

	public Goal(Rectangle loc, int r) {
		super("goal " + r, loc);
		load(r);
	}
	
	public Goal(String name, Rectangle loc, int r) {
		super(name, loc);
		load(r);
	}
	
	private void load(int r) {
		getAnimations().add(new Animation().load("ANI 0 GOAL 10 false 30 31 32 33 34 35"));
		getAnimations().add(new Animation().load("ANI 0 ANTI_GOAL 10 false 40 41 42 43 44 45"));
		
		getCollision().setCollide(true);
		getCollision().setCollideArea(new RectangleCollideArea(QuickRectangle.location(
				getLocation().getX() + getLocation().getWidth() / 4, getLocation().getY() + 1, getLocation().getWidth() / 2, getLocation().getHeight() - 2)));
		
		setRunningAnimationNumber(r);
	}
}