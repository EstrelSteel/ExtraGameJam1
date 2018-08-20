package com.estrelsteel.egj1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.HashMap;

import com.estrelsteel.egj1.KeyHandler.KeyMap;
import com.estrelsteel.egj1.actor.AntiPlayer;
import com.estrelsteel.egj1.actor.Button;
import com.estrelsteel.egj1.actor.Player;
import com.estrelsteel.egj1.actor.PlayerType;
import com.estrelsteel.egj1.actor.Spike;
import com.estrelsteel.egj1.level.EmptyLevel;
import com.estrelsteel.egj1.level.GameLevel;
import com.estrelsteel.egj1.level.Level0;
import com.estrelsteel.egj1.level.Level1;
import com.estrelsteel.egj1.level.Level2;
import com.estrelsteel.egj1.level.Level3;
import com.estrelsteel.egj1.level.Level4;
import com.estrelsteel.egj1.level.Level5;
import com.estrelsteel.egj1.level.Level6;
import com.estrelsteel.egj1.level.Level7;
import com.estrelsteel.egj1.level.demos.EndCredit;
import com.estrelsteel.egj1.level.demos.TestLevel;
import com.estrelsteel.egj1.level.demos.TitleScreen;
import com.estrelsteel.egj1.world.SplitWorld;
import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.engine2.Launcher;
import com.estrelsteel.engine2.events.listener.RenderListener;
import com.estrelsteel.engine2.events.listener.StartListener;
import com.estrelsteel.engine2.events.listener.StopListener;
import com.estrelsteel.engine2.events.listener.TickListener;
import com.estrelsteel.engine2.file.GameFile;
import com.estrelsteel.engine2.image.Renderable;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;
import com.estrelsteel.engine2.velocity.Velocity;
import com.estrelsteel.engine2.window.WindowSettings;

public class EGJ1 implements StartListener, StopListener, TickListener, RenderListener {
	
	public static final double gravityForce = 1;
	public static final double jumpForce = 20;
	public static final double moveForce = 10;
	public static final boolean DEBUG = true;
	
	public static int WIDTH;
	public static int HEIGHT;
	
	private Launcher l;
	private KeyHandler k;
	
	private Player p;
	private AntiPlayer ap;
	private SplitWorld w;
	private HashMap<Integer, GameLevel> levels;
	private int level;
	private int maxLevel;
	
	private int deaths;
	private long startTime;
	private long endTime;
	
	public static void main(String[] args) {
		new EGJ1();
	}
	
	@SuppressWarnings("static-access")
	public EGJ1() {
		l = new Launcher();
		Rectangle size;
		if(System.getProperty("os.name").startsWith("Windows")) {
			size = QuickRectangle.location(0, 0, 630, 630);
		}
		else {
			size = QuickRectangle.location(0, 0, 640, 640);
		}
		k = new KeyHandler();
		l.getEngine().setWindowSettings(new WindowSettings(size, "Extra Game Jam 1 - EstrelSteel", "v1.0a", 0));
		
		l.getEngine().START_EVENT.addListener(this);
		l.getEngine().STOP_EVENT.addListener(this);
		l.getEngine().RENDER_EVENT.addListener(this);
		l.getEngine().TICK_EVENT.addListener(this);
		l.getEngine().addKeyListener(k);
		
		
		l.getEngine().development = true;
//		l.getEngine().devPath = System.getProperty("user.home") + "/Documents/usb/EGJ1/EGJ1";
		l.getEngine().devPath = GameFile.getCurrentPath();
		
		l.boot();
	}
	
	public Renderable canMoveDirection(PlayerType p, Direction d) {
		PlayerType t = null;
		if(p instanceof Player) {
//			System.out.println(p.getVelocity().getVelocityX() + "\t" + p.getVelocity().getVelocityY());
			t = new Player(QuickRectangle.location(p.getLocation().getX(), p.getLocation().getY(),
					p.getLocation().getWidth(), p.getLocation().getHeight()));
		}
		else if(p instanceof AntiPlayer) {
			t = new AntiPlayer(QuickRectangle.location(p.getLocation().getX(), p.getLocation().getY(),
					p.getLocation().getWidth(), p.getLocation().getHeight()));
		}
		t.setVelocity(new Velocity(0.0));
		t.getVelocity().setVelocityX(p.getVelocity().getVelocityX());
		t.getVelocity().setVelocityY(p.getVelocity().getVelocityY());
		Renderable r = null;
		if(d == Direction.UP || d == Direction.DOWN || d == Direction.NS) {
			t.getVelocity().setVelocityX(0);
			t.applyVelocity();
			t.updateCollision();
			r = w.checkCollide(t);
			return r;
		}
		else if(d == Direction.LEFT || d == Direction.RIGHT || d == Direction.EW) {
			t.getVelocity().setVelocityY(0);
			t.applyVelocity();
			t.updateCollision();
			r = w.checkCollide(t);
			return r;
		}
		return r;
	}
	
	public void gotoLevel(int l) {
		if(l > maxLevel) {
			l = -3;
		}
		level = l;
		w = levels.get(level).loadLevel(p, ap);
	}

	@Override
	public Graphics2D render(Graphics2D ctx) {
		ctx.setColor(Color.WHITE);
		ctx.fillRect(0, 0, WIDTH, HEIGHT / 2);
		ctx.setColor(Color.BLACK);
		ctx.fillRect(0, HEIGHT / 2, WIDTH, HEIGHT / 2);
		if(w.doesCamFollow()) {
			w.updateMainCamera(p, ap, WIDTH, HEIGHT);
		}
		
		w.renderWorld(ctx);
		if(level == -3) {
			if(endTime < 0) endTime = System.currentTimeMillis();
			ctx.setFont(new Font("Menlo", Font.BOLD, 32));
			ctx.setColor(Color.WHITE);
			ctx.drawString("Deaths: " + deaths, 240, 320 + 160);
			ctx.drawString("Time: " + ((endTime - startTime) / 1000) + " seconds", 160, 320 + 200);
		}
		return ctx;
	}
	
	@Override
	public void tick() {
		p.updateCollision();
		ap.updateCollision();
		
		Renderable r = null;
		boolean alldone = true;
		for(PlayerType pt : w.getPlayers()) {
			if(Math.abs(pt.getLocation().getY()) > 10000) {
				// Player out of bounds, reset them.
				levels.get(level).respawn(pt);
			}
			r = canMoveDirection(pt, Direction.NS);
			if(r != null) {
				pt.collideNS(r);
			}
			r = canMoveDirection(pt, Direction.EW);
			if(r != null) {
				pt.collideEW(r);
			}
			
			r = w.checkCollide(pt.getCollision().getCollideArea(), pt);
			if(r != null) {
				if(r instanceof Spike) {
					levels.get(level).respawn(pt);
					deaths++;
				}
				if(r instanceof Button) {
					if(System.currentTimeMillis() - ((Button) r).getLastPress() > Button.WAIT_TIME) {
						((Button) r).toggleBlocks(w.getBlocks());
						((Button) r).toggleBlocks(w.getAntiBlocks());
					}
					((Button) r).setLastPress(System.currentTimeMillis());
				}
			}
			
			r = w.checkGoal(pt);
			if(r != null) {
				pt.getVelocity().setVelocityX((r.getLocation().getX() + r.getLocation().getWidth() / 2) - (pt.getLocation().getX() + pt.getLocation().getWidth() / 2));
				pt.setFinished(true);
			}
			else {
				alldone = false;
			}
		}
		if(alldone) {
			gotoLevel(level + 1);
		}
		p.applyVelocity();
		ap.applyVelocity();
		
		p.applyGravity();
		ap.applyGravity();
		
		if(level == -3) {
			
		}
		
		if(level == -2 && k.isKeyDown(KeyMap.ANYKEY)) {
			gotoLevel(0);
		}
		
		if(k.isKeyDown(KeyMap.UP) || k.isKeyDown(KeyMap.DOWN)) {
			if(p.isGrounded() && !p.hasFinished()) {
				p.getVelocity().setVelocityY(p.getVelocity().getVelocityY() - jumpForce);
				p.setGrounded(false);
			}
			if(ap.isGrounded() && !ap.hasFinished()) {
				ap.getVelocity().setVelocityY(ap.getVelocity().getVelocityY() + jumpForce);
				ap.setGrounded(false);
			}
		}
		if(k.isKeyDown(KeyMap.LEFT)) {
			if(!p.hasFinished()) p.getVelocity().setVelocityX(-moveForce);
			if(!ap.hasFinished()) ap.getVelocity().setVelocityX(-moveForce);
		}
		else if(k.isKeyDown(KeyMap.RIGHT)) {
			p.getVelocity().setVelocityX(moveForce);
			ap.getVelocity().setVelocityX(moveForce);
		}
		else {
			if(p.getVelocity().getVelocityX() < 0 && !p.hasFinished()) {
				p.getVelocity().setVelocityX(p.getVelocity().getVelocityX() + 1);
			}
			if(ap.getVelocity().getVelocityX() < 0 && !ap.hasFinished()) {
				ap.getVelocity().setVelocityX(ap.getVelocity().getVelocityX() + 1);
			}
			if(p.getVelocity().getVelocityX() > 0 && !p.hasFinished()) {
				p.getVelocity().setVelocityX(p.getVelocity().getVelocityX() - 1);
			}
			if(ap.getVelocity().getVelocityX() > 0 && !ap.hasFinished()) {
				ap.getVelocity().setVelocityX(ap.getVelocity().getVelocityX() - 1);
			}
		}
		
		for(Renderable o : w.getObjects()) {
			if(o instanceof Button) {
				((Button) o).updatePressed();
			}
		}
	}

	@Override
	public void stop() {
		if(endTime < 0) endTime = System.currentTimeMillis();
		System.out.println("###");
		System.out.println("Deaths: " + deaths);
		System.out.println("Start Time(ms): " + startTime);
		System.out.println("End Time(ms): " + endTime);
		System.out.println("Game Time(ms): " + (endTime - startTime));
		System.out.println("Play Time(ms): " + (System.currentTimeMillis() - startTime));
		System.out.println("###");
		System.out.println("Thank you for playing :-D");
	}

	@Override
	public void start() {
		GameFile res = new GameFile(GameFile.getDataPath() + "/res/res.txt");
//		GameFile sfx = new GameFile(GameFile.getDataPath() + "/res/sounds.txt");
		try {
			res.setLines(res.readFile());
//			sfx.setLines(sfx.readFile());
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		Engine2.GLOBAL_RESOURCE_REFERENCE.load(res, 0);
//		new SFX().load(sfx, 0);

		WIDTH = (int) l.getEngine().getWindowSettings().getRectangle().getWidth();
		HEIGHT = (int) l.getEngine().getWindowSettings().getRectangle().getHeight();
		
		maxLevel = 7;
		levels = new HashMap<Integer, GameLevel>();
		levels.put(-100, new TestLevel());
		levels.put(-3, new EndCredit());
		levels.put(-2, new TitleScreen());
		levels.put(-1, new EmptyLevel());
		levels.put(0, new Level0());
		levels.put(1, new Level1());
		levels.put(2, new Level2());
		levels.put(3, new Level3());
		levels.put(3, new Level3());
		levels.put(4, new Level4());
		levels.put(5, new Level5());
		levels.put(6, new Level6());
		levels.put(7, new Level7());
		
		p = new Player(QuickRectangle.location(0, 0, 32, 32));
		ap = new AntiPlayer(QuickRectangle.location(0, HEIGHT - 32, 32, 32));
		
		gotoLevel(-2);
		startTime = System.currentTimeMillis();
		endTime = -1;
		deaths = 0;
	}
}
