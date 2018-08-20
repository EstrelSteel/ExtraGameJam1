package com.estrelsteel.egj1.actor;

import java.util.ArrayList;

import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.image.Renderable;
import com.estrelsteel.engine2.shape.collide.RectangleCollideArea;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;

public class Button extends Actor {

	public static final long WAIT_TIME = 750;
	
	// if len = 0 then all blocks
	private int[] bblocks;
	private long lastPress;
	
	public Button(Rectangle loc, int r) {
		super("button " + r, loc);
		load(r);
	}
	
	public Button(String name, Rectangle loc, int r) {
		super(name, loc);
		load(r);
	}
	
	private void load(int r) {
		getAnimations().add(new Animation().load("ANI 0 BUTTON -1 false 60"));
		getAnimations().add(new Animation().load("ANI 1 PRESSED_BUTTON -1 false 61"));
		getAnimations().add(new Animation().load("ANI 10 ANTI_BUTTON -1 false 62"));
		getAnimations().add(new Animation().load("ANI 11 PRESSED_ANTI_BUTTON -1 false 63"));
		
		getCollision().setCollide(true);
		if(r == 0) {
			getCollision().setCollideArea(new RectangleCollideArea(QuickRectangle.location(
					getLocation().getX() + 1, getLocation().getY() + 1, getLocation().getWidth() - 2, getLocation().getHeight() - 2)));
		}
		
		setRunningAnimationNumber(r);
		bblocks = new int[0];
		lastPress = -1;
	}
	
	public void toggleBlocks(ArrayList<Renderable> blocks) {
		for(Renderable r : blocks) {
			if(r instanceof ButtonBlock) {
				if(bblocks.length == 0) {
					((ButtonBlock) r).toggleBlock();
				}
				else {
					for(int i = 0; i < bblocks.length; i++) {
						if(((ButtonBlock) r).getID() == bblocks[i]) {
							((ButtonBlock) r).toggleBlock();
						}
					}
				}
			}
		}
	}

	public int[] getButtonBlocks() {
		return bblocks;
	}

	public void setButtonBlocks(int[] bblocks) {
		this.bblocks = bblocks;
	}
	
	public void updatePressed() {
		if(System.currentTimeMillis() - lastPress <= WAIT_TIME) {
			if(getRunningAnimationNumber() == 0 || getRunningAnimationNumber() == 2) {
				setRunningAnimationNumber(getRunningAnimationNumber() + 1);
			}
		}
		else {
			if(getRunningAnimationNumber() == 1 || getRunningAnimationNumber() == 3) {
				setRunningAnimationNumber(getRunningAnimationNumber() - 1);
			}
		}
	}

	public long getLastPress() {
		return lastPress;
	}

	public void setLastPress(long lastPress) {
		this.lastPress = lastPress;
	}
}