package com.estrelsteel.egj1.level;

import com.estrelsteel.egj1.EGJ1;
import com.estrelsteel.egj1.actor.AntiPlayer;
import com.estrelsteel.egj1.actor.Block;
import com.estrelsteel.egj1.actor.Button;
import com.estrelsteel.egj1.actor.ButtonBlock;
import com.estrelsteel.egj1.actor.Goal;
import com.estrelsteel.egj1.actor.Player;
import com.estrelsteel.egj1.world.SplitWorld;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.velocity.Velocity;

public class Level7 extends GameLevel {

	public Level7() {
		super(7);
	}

	@Override
	public SplitWorld loadLevel(Player p, AntiPlayer ap) {
		SplitWorld w = new SplitWorld();
		w.setCamFollow(true);
		
		respawnPlayer(p);
		respawnAntiPlayer(ap);
		
		w.getPlayers().add(p);
		w.getPlayers().add(ap);
		
		//	integer at end: 0 = black, 1 = white, 2 = invisible
		w.getBlocks().add(new Block(QuickRectangle.location(0, EGJ1.HEIGHT / 2, EGJ1.WIDTH * 2, EGJ1.HEIGHT / 2), 2));
		w.getAntiBlocks().add(new Block(QuickRectangle.location(0, 0, EGJ1.WIDTH * 2, EGJ1.HEIGHT / 2), 2));
		
		// side walls
		w.getBlocks().add(new Block(QuickRectangle.location(-EGJ1.WIDTH, -EGJ1.HEIGHT, EGJ1.WIDTH, EGJ1.HEIGHT * 3), 0));
		w.getAntiBlocks().add(new Block(QuickRectangle.location(-EGJ1.WIDTH, EGJ1.HEIGHT / 2, EGJ1.WIDTH, EGJ1.HEIGHT * 3), 1));
		w.getBlocks().add(new Block(QuickRectangle.location(EGJ1.WIDTH * 2, -EGJ1.HEIGHT, EGJ1.WIDTH, EGJ1.HEIGHT * 3), 0));
		w.getAntiBlocks().add(new Block(QuickRectangle.location(EGJ1.WIDTH * 2, EGJ1.HEIGHT / 2, EGJ1.WIDTH, EGJ1.HEIGHT * 3), 1));
		
		w.getGoals().add(new Goal(QuickRectangle.location(EGJ1.WIDTH * 2 - 64, 320 - 256 - 64, 64, 64), 0));
		w.getGoals().add(new Goal(QuickRectangle.location(EGJ1.WIDTH * 2 - 64, 320 + 256, 64, 64), 1));
		
		w.getBlocks().add(new Block(QuickRectangle.location(480, 320 - 128, 160, 64), 0));
		w.getAntiBlocks().add(new Block(QuickRectangle.location(480, 320 + 64, 160, 64), 1));
		w.getBlocks().add(new ButtonBlock(false, QuickRectangle.location(680, 320 - 256, 240, 64), 1, 0));
		w.getAntiBlocks().add(new ButtonBlock(false, QuickRectangle.location(680, 320 + 256 - 64, 240, 64), 0, 1));
		w.getBlocks().add(new Block(QuickRectangle.location(EGJ1.WIDTH * 2 - 160, 320 - 256, 160, 64), 0));
		w.getAntiBlocks().add(new Block(QuickRectangle.location(EGJ1.WIDTH * 2 - 160, 320 + 256 - 64, 160, 64), 1));
		
		Button b1 = new Button(QuickRectangle.location(528, 384 + 64, 64, 64), 2);
		b1.setButtonBlocks(new int[1]);
		b1.getButtonBlocks()[0] = 0;
		w.getObjects().add(b1);
		Button b2 = new Button(QuickRectangle.location(528, 320 - 128 - 64, 64, 64), 0);
		b2.setButtonBlocks(new int[1]);
		b2.getButtonBlocks()[0] = 1;
		w.getObjects().add(b2);
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
