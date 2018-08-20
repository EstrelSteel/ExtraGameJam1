package com.estrelsteel.egj1.actor;

import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.shape.collide.RectangleCollideArea;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;

public class Block extends Actor {

	public Block(Rectangle loc, int r) {
		super("block " + r, loc);
		load(r);
	}
	
	public Block(String name, Rectangle loc, int r) {
		super(name, loc);
		load(r);
	}
	
	private void load(int r) {
		getAnimations().add(new Animation().load("ANI 0 BLOCK -1 false 0"));
		getAnimations().add(new Animation().load("ANI 1 ANTI_BLOCK -1 false 10"));
		getAnimations().add(new Animation().load("ANI 2 INVISIBLE_BLOCK -1 false 20"));
		
		getCollision().setCollide(true);
		getCollision().setCollideArea(new RectangleCollideArea(QuickRectangle.location(
				getLocation().getX() + 1, getLocation().getY() + 1, getLocation().getWidth() - 2, getLocation().getHeight() - 2)));
		
		setRunningAnimationNumber(r);
	}
}