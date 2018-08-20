package com.estrelsteel.egj1.level.demos;

import com.estrelsteel.egj1.EGJ1;
import com.estrelsteel.egj1.actor.AntiPlayer;
import com.estrelsteel.egj1.actor.Block;
import com.estrelsteel.egj1.actor.Player;
import com.estrelsteel.egj1.level.GameLevel;
import com.estrelsteel.egj1.world.SplitWorld;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.velocity.Velocity;

public class TestLevel extends GameLevel {

	public TestLevel() {
		super(-100);
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
		w.getBlocks().add(new Block("block1", QuickRectangle.location(320, 320 - 64, 160, 64), 0));
		w.getBlocks().add(new Block("block2", QuickRectangle.location(320 + 128, 320 - 192 - 64 + 64, 160, 64), 0));
		w.getBlocks().add(new Block("floor1", QuickRectangle.location(0, 320, 320 - 128, 640), 2));
		w.getBlocks().add(new Block("floor2", QuickRectangle.location(320 - 128 + 160, 320, 640, 640), 2));
		w.getBlocks().add(new Block("floor3", QuickRectangle.location(320 - 128, 320 + 64, 160, 640), 2));
		
		w.getAntiBlocks().add(new Block("block3", QuickRectangle.location(320 - 128, 320, 160, 64), 1));
		w.getAntiBlocks().add(new Block("antifloor1", QuickRectangle.location(0, 0, 320, 320), 2));
		w.getAntiBlocks().add(new Block("antifloor2", QuickRectangle.location(320 + 160, 0, 640, 320), 2));
		w.getAntiBlocks().add(new Block("antifloor3", QuickRectangle.location(320, 0, 160, 320 - 64), 2));
		
		// side walls
		w.getBlocks().add(new Block(QuickRectangle.location(-64, 0, 64, EGJ1.HEIGHT), 2));
		w.getAntiBlocks().add(new Block(QuickRectangle.location(-64, 0, 64, EGJ1.HEIGHT), 2));
		w.getBlocks().add(new Block(QuickRectangle.location(EGJ1.WIDTH, 0, 64, EGJ1.HEIGHT), 2));
		w.getAntiBlocks().add(new Block(QuickRectangle.location(EGJ1.WIDTH, 0, 64, EGJ1.HEIGHT), 2));
		
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
