package states;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import entities.Entity;
import entities.Player;
import entities.PowerUp;
import game.Screen;
import managers.EnemyManager;
import managers.IntroManager;
import managers.PowerUpManager;
import save.SaveScore;

public class BattleState extends BasicGameState implements KeyListener {
	
	private LinkedList<Entity> enemies;
	private LinkedList<Entity> projectiles;
	private LinkedList<Entity> enemyProjectiles;
	private LinkedList<PowerUp> powerUps;
	private Player player;
	
	private EnemyManager enemyManager;
	private PowerUpManager powerUpManager;
	private IntroManager introManager;
	
	private boolean isPaused;
	private boolean gameOver;
	private boolean musicOn;
	private boolean isIntro;
	
	private Image backGround;
	private Image topBackGround;
	private Image life;
	
	private Music music;
	
	private int scX, scY;
	private int bgX, bgY;
	
	private SaveScore scoreSaver;
	
	
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
		backGround = new Image("res/Background/starBackground.png");
		topBackGround = backGround;
		life = new Image("res/life.png");
		isPaused = false;
		gameOver = false;
		isIntro = true;
		music = new Music("res/sounds/8bit.wav");
		setMusicOn(true);
		
		
		player = new Player(Screen.SCREEN_W/2, Screen.SCREEN_H/2);
		player.setX(Screen.SCREEN_W/2 - (player.getSprite().getWidth()/2));
		player.setY(Screen.SCREEN_H/2 - (player.getSprite().getHeight()/2));
		projectiles = new LinkedList<Entity>();
		enemies = new LinkedList<Entity>();
		enemyProjectiles = new LinkedList<Entity>();
		powerUps = new LinkedList<PowerUp>();
		enemyManager = new EnemyManager();
		powerUpManager = new PowerUpManager();
		introManager = new IntroManager();
		setScX(0);
		scY = 0;
		bgX = 0;
		bgY = 0;
		
	}
	
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException{
		this.init(gc, sbg);
		music.loop(1, (float).1);
	}
	
	public void leave(GameContainer gc, StateBasedGame sbg){
		music.stop();
		
		try {
			setScoreSaver(new SaveScore(enemyManager.getScore()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		drawBG();
		g.setColor(Color.red);
		g.drawString("SCORE:" + enemyManager.getScore(), 860, 0);
		
		
		if(!isIntro){
			player.draw(g);
		}
		
		if(isIntro){
			try {
				introManager.render(g);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		for(int i = 0; i < player.getLives(); i++){
			g.drawImage(life, 825 + (i * 35), 25);
		}
		
		for(int i = 0; i < powerUps.size(); i++){
			powerUps.get(i).draw(g);
		}
			
		for(int i =0; i < projectiles.size(); i++){
			projectiles.get(i).draw(g);
		}
			
		for(int i =0; i < enemyProjectiles.size(); i++){
			enemyProjectiles.get(i).draw(g);
		}
			
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).draw(g);
				
		}
		
		if(isPaused){
			try {
				introManager.playInstructions(g);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			g.drawString("GAME PAUSED", Screen.SCREEN_W/2, Screen.SCREEN_H/2);
		}
		
		//uiHandler.draw();
		
		if(gameOver){
			g.setColor(Color.red);
			g.drawString("GAME OVER", Screen.SCREEN_W/2, Screen.SCREEN_H/2);
			g.drawString("Your score is " + enemyManager.getScore(), Screen.SCREEN_W/2, Screen.SCREEN_H/2 + 25);
			g.drawString(getPrestige(enemyManager.getScore()), Screen.SCREEN_W/2, Screen.SCREEN_H/2 + 50);
			
			g.drawString("Press 'R' to replay or 'X' to exit the game.", Screen.SCREEN_W/2, Screen.SCREEN_H/2 + 75);
			g.setColor(Color.black);
		}
	}
	
	public String getPrestige(int score){
		if(score < 10000){
			return "You're casual, bro.";
		}
		if(score >= 10000 && score < 20000){
			return "Eh, not bad.";
		}
		if(score >= 20000 && score < 50000){
			return "nice!";
		}
		if(score >= 50000 && score < 100000){
			return "You're a hardcore.";
		}
		if(score >= 100000 && score < 150000){
			return "You're the master.";
		}
		return "Good jesus, you're serious about this game.";
	}

	
	public void drawBG(){
		backGround.draw(bgX, bgY + scY, Screen.SCREEN_W, Screen.SCREEN_H);
		topBackGround.draw(bgX, bgY - Screen.SCREEN_H + scY, Screen.SCREEN_W, Screen.SCREEN_H);
		if(bgY - Screen.SCREEN_H + scY >= 0){
			bgY -= Screen.SCREEN_H;
		}
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int d) throws SlickException{
		
		if(gc.getInput().isKeyDown(Input.KEY_ENTER)){
			sbg.enterState(1);
			leave(gc, sbg);
		}
		
		if(isIntro){
			introManager.update(gc);
			isIntro = !introManager.isPlayerReady();
		}
		

		
		if(!gameOver && !isPaused){
			scY+=2;
		}
		
		if(!isPaused && !isIntro && !gameOver){
			this.projectiles = player.update(gc, projectiles, enemyProjectiles, enemies, powerUps);
			this.enemies = enemyManager.update(d, projectiles);
			this.enemyProjectiles = enemyManager.getEnemyShots(enemyProjectiles);
			this.powerUps = powerUpManager.getPowerUps();
			
			for(int i = 0; i < powerUps.size(); i++){
				powerUps.get(i).update();
				
				if(!powerUps.get(i).isOnScreen() || powerUps.get(i).getHp() <= 0){
					powerUps.remove(i);
				}
				
			}
			
			
			for(int i =0; i < projectiles.size(); i++){
				
				projectiles.get(i).update(enemies);
			    
				if(!projectiles.get(i).isOnScreen() || projectiles.get(i).getHp() <= 0){
					projectiles.remove(i);
				}
			}
			
			for(int i =0; i < enemyProjectiles.size(); i++){
				enemyProjectiles.get(i).update();
				if(!enemyProjectiles.get(i).isOnScreen() || enemyProjectiles.get(i).getHp() <= 0){
					enemyProjectiles.remove(i);
				}
			}
			
			for(int i= 0; i< enemies.size(); i++){
				if(!enemies.get(i).isOnScreen()){
					enemies.remove(i);
				}
			}
			
			if(!gameOver){
				enemyManager.releaseEnemies();
				powerUpManager.releasePowerUps();
			}
			
			
			if(player.getLives() <= 0){
				//isPaused = true;
				gameOver = true;
			}
			
		}
		
		if(gameOver){
			if(gc.getInput().isKeyPressed(Input.KEY_R)){
				leave(gc, sbg);
				this.init(gc, sbg);
			}
			if(gc.getInput().isKeyPressed(Input.KEY_X)){
				leave(gc, sbg);
				System.exit(d);
			}
		}
		
		if(isPaused){
			if(gc.getInput().isKeyPressed(Input.KEY_ESCAPE)){
				isPaused = false;
			}
		}
		
		if(!isPaused){
			if(gc.getInput().isKeyPressed(Input.KEY_ESCAPE)){
				isPaused = true;
			}
		}
	}
	
	public void keyPressed(int key, char c){
		
		if(key == Input.KEY_M){
			if(music.playing()){
				music.stop();
			}
			if(!music.playing()){
				music.loop(1, (float).1);
			}
		}
			
		player.keyPressed(key, c);
	}
	
	public void keyReleased(int key, char c){
		player.keyReleased(key, c);
	}
	

	
	public int getID() {
		return 0;
	}


	public boolean isMusicOn() {
		return musicOn;
	}


	public void setMusicOn(boolean musicOn) {
		this.musicOn = musicOn;
	}


	public int getScX() {
		return scX;
	}


	public void setScX(int scX) {
		this.scX = scX;
	}


	public IntroManager getIntroManager() {
		return introManager;
	}


	public void setIntroManager(IntroManager introManager) {
		this.introManager = introManager;
	}

	public SaveScore getScoreSaver() {
		return scoreSaver;
	}

	public void setScoreSaver(SaveScore scoreSaver) {
		this.scoreSaver = scoreSaver;
	}



}
