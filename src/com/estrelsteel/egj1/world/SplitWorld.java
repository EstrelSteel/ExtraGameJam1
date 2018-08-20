package com.estrelsteel.egj1.world;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.estrelsteel.egj1.EGJ1;
import com.estrelsteel.egj1.actor.AntiPlayer;
import com.estrelsteel.egj1.actor.Goal;
import com.estrelsteel.egj1.actor.Player;
import com.estrelsteel.egj1.actor.PlayerType;
import com.estrelsteel.engine2.grid.PixelGrid;
import com.estrelsteel.engine2.image.Renderable;
import com.estrelsteel.engine2.point.AbstractedPoint;
import com.estrelsteel.engine2.point.Point2;
import com.estrelsteel.engine2.point.PointMaths;
import com.estrelsteel.engine2.world.World;

public class SplitWorld extends World {

	private boolean camFollow;
	private ArrayList<PlayerType> players;
	private ArrayList<Renderable> blocks;
	private ArrayList<Renderable> antiblocks;
	private ArrayList<Renderable> goals;
	
	public SplitWorld() {
		super(new PixelGrid());
		players = new ArrayList<PlayerType>();
		blocks = new ArrayList<Renderable>();
		antiblocks = new ArrayList<Renderable>();
		goals = new ArrayList<Renderable>();
		
		camFollow = true;
	}
	
	public Renderable checkCollide(PlayerType p) {
		if(p instanceof Player) {
			return checkCollide(blocks, p.getCollision().getCollideArea(), p);
		}
		else if(p instanceof AntiPlayer) {
			return checkCollide(antiblocks, p.getCollision().getCollideArea(), p);
		}
		return null;
	}
	
	public Renderable checkGoal(PlayerType p) {
		return checkCollide(goals, p.getCollision().getCollideArea(), p);
	}
	
	@Override
	public Graphics2D renderWorld(Graphics2D ctx) {
		ctx = super.renderWorld(ctx);
		for(Renderable b : blocks) {
			ctx = b.render(ctx, this);
		}
		for(Renderable b : antiblocks) {
			ctx = b.render(ctx, this);
		}
		for(Renderable g : goals) {
			((Goal) g).getRunningAnimation().run();
			ctx = g.render(ctx, this);
		}
		for(PlayerType p : players) {
			ctx = p.render(ctx, this);
		}
		return ctx;
	}
	
	public void updateMainCamera(PlayerType p1, PlayerType p2, int width, int height) {
		AbstractedPoint cp1 = PointMaths.getCentre(p1.getLocation());
		AbstractedPoint cp2 = PointMaths.getCentre(p2.getLocation());
		AbstractedPoint mid = PointMaths.getMidpoint(cp1, cp2);
		mid.setY(EGJ1.HEIGHT / 2);
		getMainCamera().setLocation(new Point2(mid.getX() - width / 2, mid.getY() - height / 2, getGrid()));
	}
	
	public ArrayList<PlayerType> getPlayers() {
		return players;
	}
	
	public ArrayList<Renderable> getBlocks() {
		return blocks;
	}
	
	public ArrayList<Renderable> getAntiBlocks() {
		return antiblocks;
	}

	public void setPlayers(ArrayList<PlayerType> players) {
		this.players = players;
	}
	
	public void setBlocks(ArrayList<Renderable> blocks) {
		this.blocks = blocks;
	}
	
	public void setAntiBlocks(ArrayList<Renderable> antiblocks) {
		this.antiblocks = antiblocks;
	}

	public boolean doesCamFollow() {
		return camFollow;
	}

	public void setCamFollow(boolean camFollow) {
		this.camFollow = camFollow;
	}

	public ArrayList<Renderable> getGoals() {
		return goals;
	}

	public void setGoals(ArrayList<Renderable> goals) {
		this.goals = goals;
	}
}
