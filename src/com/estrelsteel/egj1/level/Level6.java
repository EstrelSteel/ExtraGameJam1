package com.estrelsteel.egj1.level;

import com.estrelsteel.egj1.EGJ1;
import com.estrelsteel.egj1.actor.AntiPlayer;
import com.estrelsteel.egj1.actor.Block;
import com.estrelsteel.egj1.actor.Goal;
import com.estrelsteel.egj1.actor.Player;
import com.estrelsteel.egj1.actor.Spike;
import com.estrelsteel.egj1.world.SplitWorld;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.velocity.Velocity;

public class Level6 extends GameLevel {

	public Level6() {
		super(6);
	}

	@Override
	public SplitWorld loadLevel(Player p, AntiPlayer ap) {
		SplitWorld w = new SplitWorld();
		w.setCamFollow(false);
		
		respawnPlayer(p);
		respawnAntiPlayer(ap);
		
		w.getPlayers().add(p);
		w.getPlayers().add(ap);
		
		//	integer at end: 0 = black, 1 = white, 2 = invisible
		w.getBlocks().add(new Block(QuickRectangle.location(0, EGJ1.HEIGHT / 2, EGJ1.WIDTH, EGJ1.HEIGHT / 2), 2));
		w.getAntiBlocks().add(new Block(QuickRectangle.location(0, 0, EGJ1.WIDTH, EGJ1.HEIGHT / 2), 2));
		
		// side walls
		w.getBlocks().add(new Block(QuickRectangle.location(-64, -EGJ1.HEIGHT, 64, EGJ1.HEIGHT * 3), 2));
		w.getAntiBlocks().add(new Block(QuickRectangle.location(-64, -EGJ1.HEIGHT, 64, EGJ1.HEIGHT * 3), 2));
		w.getBlocks().add(new Block(QuickRectangle.location(EGJ1.WIDTH, -EGJ1.HEIGHT, 64, EGJ1.HEIGHT * 3), 2));
		w.getAntiBlocks().add(new Block(QuickRectangle.location(EGJ1.WIDTH, -EGJ1.HEIGHT, 64, EGJ1.HEIGHT * 3), 2));
		
		w.getGoals().add(new Goal(QuickRectangle.location(576, 320 - 64, 64, 64), 0));
		w.getGoals().add(new Goal(QuickRectangle.location(576, 320, 64, 64), 1));
		
//		w.getObjects().add(new Message(QuickRectangle.location(64, 64, 512, 64), 0));
		w.getObjects().add(new Spike(QuickRectangle.location(160, 320 - 32, 32, 32), 0));
		w.getObjects().add(new Spike(QuickRectangle.location(160 + 32, 320 - 32, 32, 32), 0));
		
		w.getObjects().add(new Spike(QuickRectangle.location(320, 320, 32, 32), 1));
		w.getObjects().add(new Spike(QuickRectangle.location(320 + 32, 320, 32, 32), 1));
		w.getObjects().add(new Spike(QuickRectangle.location(320 + 64, 320, 32, 32), 1));
		w.getObjects().add(new Spike(QuickRectangle.location(320 + 96, 320, 32, 32), 1));
		w.getObjects().add(new Spike(QuickRectangle.location(320 + 128, 320, 32, 32), 1));
		w.getBlocks().add(new Block(QuickRectangle.location(54, 0, 64, 96), 2));
		w.getAntiBlocks().add(new Block(QuickRectangle.location(54, EGJ1.HEIGHT - 96, 64, 96), 2));
		
		return w;
	}

	@Override
	public void respawnPlayer(Player p) {
		p.setLocation(QuickRectangle.location(0, 0, 32, 32));
		p.updateCollision();
		
		p.setFinished(false);
		p.setGrounded(false);
		
		Velocity pg = new Velocity(0.0).setVelocityY(-EGJ1.gravityForce);
		p.setGravity(pg);
		p.setVelocity(new Velocity(0));
	}

	@Override
	public void respawnAntiPlayer(AntiPlayer ap) {
		ap.setLocation(QuickRectangle.location(0, EGJ1.HEIGHT - 32, 32, 32));
		ap.updateCollision();
		
		ap.setFinished(false);
		ap.setGrounded(false);
		
		Velocity apg = new Velocity(0.0).setVelocityY(EGJ1.gravityForce);
		ap.setGravity(apg);
		ap.setVelocity(new Velocity(0));
	}

}
