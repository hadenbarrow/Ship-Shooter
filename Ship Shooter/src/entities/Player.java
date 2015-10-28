package entities;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import game.Screen;
import projectiles.GreenLaser;

public class Player extends Entity {
	private static final double BASE_HP = 100;
	private static final double BASE_ATTACK = 100;
	private static final double BASE_DEFENSE = 0;
	private static final double BASE_SPEED = 8;
	private static final Id ID = Id.basicEnemy;
	
	private Image player;
	private Image playerLeft;
	private Image playerRight;
	private Image playerDamaged;
	
	
	
	private Image[] blinking;
	private Animation invulnerable;
	private Animation cur;
	
	private int lives;
	private Timer timer;
	
	private Sound laser;
	private Sound hit; 
	private Sound weapon;
	private Sound life;
	
	private boolean tripleLasers;
	private boolean isInvulnerable;
	private boolean isAnimated;
	private boolean isPaused;
		
	public Player(int x, int y) throws SlickException{
		setHp(BASE_HP);
		setAttack(BASE_ATTACK);
		setDefense(BASE_DEFENSE);
		setSpeed(BASE_SPEED);
		setId(ID);
		setX(x);
		setY(y);
		tripleLasers = false;
		laser = new Sound("res/sounds/laser5.wav");
		hit = new Sound("res/sounds/hit.wav");
		setWeapon(new Sound("res/sounds/weapon.wav"));
		setLife(new Sound("res/sounds/lifePickup.wav"));
		
		lives = 3;
		isInvulnerable = false;
		isPaused = false;
	
		setupAnimations();
	}
	
	public void setupAnimations() throws SlickException{
		cur = new Animation();
		isAnimated = false;
		
		player = new Image("res/player.png");
		playerLeft = new Image("res/playerLeft.png");
		playerRight = new Image("res/playerRight.png");
		
		setSprite(player);
		
		blinking = new Image[2];
		blinking[0] = new Image("res/playerGrey.png");
		blinking[1] = new Image("res/player.png");
		invulnerable = new Animation(blinking, 200);
		invulnerable.setPingPong(true);
	}
	
	public void draw(Graphics g){
		if(isAnimated){
			cur.draw((float)this.getX(), (float)this.getY(), (float)this.getSprite().getWidth(), (float)this.getSprite().getHeight());
		}
		else{
			g.drawImage(this.getSprite(), (float)this.getX(), (float)this.getY());
		}
	}
	
	public LinkedList<Entity> update(GameContainer gc, LinkedList<Entity> projectiles, LinkedList<Entity> enemyProjectiles, LinkedList<Entity> enemies, LinkedList<PowerUp> powerUps) throws SlickException{
		Input input = gc.getInput();
		LinkedList<Entity> p = projectiles;
		
		if(input.isKeyDown(Input.KEY_ESCAPE)){
			isPaused = !isPaused;
		}
	
		if(input.isKeyDown(Input.KEY_W)){
			if(getY() > 0){
				setY(getY() - getSpeed());
			}
		
			setSprite(player);
		}
		
		if(input.isKeyDown(Input.KEY_S)){
			if(getY() + getSprite().getHeight() < Screen.SCREEN_H){
				setY(getY() + getSpeed());
			}
			setSprite(player);
		}
		
		if(input.isKeyDown(Input.KEY_D)){
			if(getX() + getSprite().getWidth() < Screen.SCREEN_W){
				setX(getX() + getSpeed());
			}
			setSprite(playerRight);
		}
		
		if(input.isKeyDown(Input.KEY_A)){
			if(getX() > 0){
				setX(getX() - getSpeed());
			}
			setSprite(playerLeft);
		}
		
		if(input.isKeyPressed(Input.KEY_SPACE)){
			
			
			if(tripleLasers){
				Entity[] lasers = tripleLaser();
				laser.play();
				for(int i = 0; i < 3; i++){
					p.add(lasers[i]);
				}
				
			}
			else{
				p.add(shoot());
				laser.play();
			}
		}
		
		for(int i = 0; i < enemyProjectiles.size(); i++){
			if(this.collidesWith(enemyProjectiles.get(i))){
				//this.takeDamageFrom(enemyProjectiles.get(i));
				if(!isInvulnerable){
					isInvulnerable = true;
					isAnimated = true;
					cur = invulnerable;
					
					setInvulnerable(3);
					lives--;
					hit.play();
					enemyProjectiles.get(i).takeDamageFrom(this);
				}
			}
		}
		
		for(int i = 0; i < enemies.size(); i++){
			if(this.collidesWith(enemies.get(i))){
				if(!isInvulnerable){
					isInvulnerable = true;
					isAnimated = true;
					cur = invulnerable;
					
					setInvulnerable(3);
					lives--;
					hit.play();
					enemies.get(i).takeDamageFrom(this);
				}
			}
		}
		
		for(int i = 0; i < powerUps.size(); i++){
			if(this.collidesWith(powerUps.get(i))){
				if(powerUps.get(i) instanceof Repairs){
					if(lives < 3){
						life.play();
						lives++;
						powerUps.get(i).takeDamageFrom(this);
					}
				}
				else{
					weapon.play();
					tripleLasers = true;
					setTripleLasers(15);
					powerUps.get(i).takeDamageFrom(this);
				}
			}
		}
		
		return p;
	}
	
	public void keyPressed(int key, char c) {
		
	}
	
	public void keyReleased(int key, char c){
		if(key == Input.KEY_D || key == Input.KEY_A){
			setSprite(player);
		}
	}
	
	public void setTripleLasers(int t){
		timer = new Timer();
		timer.schedule(new Lasers(), t * 1000);
	}
	
	class Lasers extends TimerTask{
		public void run() {
			tripleLasers = false;
		}
	}
	
	public void setInvulnerable(int t){
		timer = new Timer();
		timer.schedule(new Invulnerable(), t*1000);
	}
	
	class Invulnerable extends TimerTask{
		public void run() {
			isInvulnerable = false;
			isAnimated = false;
		}
	}
	
	public Entity[] tripleLaser() throws SlickException{
		Entity[] lasers = new Entity[3];
		lasers[0] = new GreenLaser(this.getX(), this.getY());
		lasers[1] =	new GreenLaser(this.getX() + this.getSprite().getWidth()/2, this.getY());
		lasers[2] = new GreenLaser(this.getX() + this.getSprite().getWidth(), this.getY());
		
		return lasers;
	}
	
	public boolean isPaused(){
		return isPaused;
	}
	
	public boolean getTripleLasers(){
		return tripleLasers;
	}
	
	public void setTripleLasers(boolean b){
		tripleLasers = b;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public Sound getWeapon() {
		return weapon;
	}

	public void setWeapon(Sound weapon) {
		this.weapon = weapon;
	}

	public Sound getLife() {
		return life;
	}

	public void setLife(Sound life) {
		this.life = life;
	}

	public Image getPlayerDamaged() {
		return playerDamaged;
	}

	public void setPlayerDamaged(Image playerDamaged) {
		this.playerDamaged = playerDamaged;
	}
}
