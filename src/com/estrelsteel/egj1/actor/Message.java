package com.estrelsteel.egj1.actor;

import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;

public class Message extends Actor {

	public Message(Rectangle loc, int r) {
		super("Message " + r, loc);
		load(r);
	}
	
	public Message(String name, Rectangle loc, int r) {
		super(name, loc);
		load(r);
	}
	
	private void load(int r) {
		getAnimations().add(new Animation().load("ANI 0 ARROW_KEYS -1 false 100"));
		getAnimations().add(new Animation().load("ANI 1 TITLE -1 false 200"));
		getAnimations().add(new Animation().load("ANI 2 ENDCREDIT -1 false 201"));
		
		setRunningAnimationNumber(r);
	}
}