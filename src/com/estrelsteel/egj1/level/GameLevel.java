package com.estrelsteel.egj1.level;

import com.estrelsteel.egj1.actor.AntiPlayer;
import com.estrelsteel.egj1.actor.Player;
import com.estrelsteel.egj1.actor.PlayerType;
import com.estrelsteel.egj1.world.SplitWorld;

public abstract class GameLevel {
	
	private int id;
	
	public GameLevel(int id) {
		this.id = id;
	}
	
	public int getLevelId() {
		return id;
	}
	
	public abstract SplitWorld loadLevel(Player p, AntiPlayer ap);
	
	public void respawn(PlayerType p) {
		if(p instanceof Player) respawnPlayer((Player) p);
		else if(p instanceof AntiPlayer) respawnAntiPlayer((AntiPlayer) p);
		else System.err.println("unknown player");
	}
	
	public abstract void respawnPlayer(Player p);
	public abstract void respawnAntiPlayer(AntiPlayer ap);
}
