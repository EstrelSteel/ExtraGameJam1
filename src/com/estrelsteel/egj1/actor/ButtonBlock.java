package com.estrelsteel.egj1.actor;

import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.shape.collide.RectangleCollideArea;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;

public class ButtonBlock extends Actor {

	private boolean onByDefault;
	private int id;
	private int on;
	
	public ButtonBlock(boolean onByDefault, Rectangle loc, int r, int id) {
		super("button_block " + r, loc);
		load(r, onByDefault, id);
	}
	
	public ButtonBlock(String name, boolean onByDefault, Rectangle loc, int r, int id) {
		super(name, loc);
		load(r, onByDefault, id);
	}
	
	private void load(int r, boolean onByDefault, int id) {
		getAnimations().add(new Animation().load("ANI 0 BUTTON_BLOCK -1 false 65"));
		getAnimations().add(new Animation().load("ANI 1 ANTI_BUTTON_BLOCK -1 false 66"));
		getAnimations().add(new Animation().load("ANI 2 INVISIBLE -1 false 20"));
		
		getCollision().setCollide(onByDefault);
		getCollision().setCollideArea(new RectangleCollideArea(QuickRectangle.location(
				getLocation().getX() + 1, getLocation().getY() + 1, getLocation().getWidth() - 2, getLocation().getHeight() - 2)));
		
		setRunningAnimationNumber(r);
		this.onByDefault = onByDefault;
		this.id = id;
		this.on = r;
		if(!onByDefault) setRunningAnimationNumber(2);
	}
	
	public boolean toggleBlock() {
		if(getRunningAnimationNumber() == 2) {
			setRunningAnimationNumber(on);
			getCollision().setCollide(true);
			return true;
		}
		setRunningAnimationNumber(2);
		getCollision().setCollide(false);
		return false;
	}

	public boolean isOnByDefault() {
		return onByDefault;
	}

	public void setOnByDefault(boolean onByDefault) {
		this.onByDefault = onByDefault;
	}

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}
}