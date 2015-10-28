package entities;

import java.util.LinkedList;
import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Asteroid extends Entity{
	private static final double BASE_HP = 20;
	private static final double BASE_ATTACK = .02;
	private static final double BASE_DEFENSE = 0;
	private static final double BASE_SPEED = 4;
	private static final Id ID = Id.projectile;
	
	private Image smallAsteroid;
	private Image largeAsteroid;
	private Random rand;
	private Sound destroySound;
	
	public Asteroid(double x, double y) throws SlickException{
		setHp(BASE_HP);
		setAttack(BASE_ATTACK);
		setDefense(BASE_DEFENSE);
		setSpeed(BASE_SPEED);
		setId(ID);
		setX(x);
		setY(y);
		
		rand = new Random();
		int n = rand.nextInt(2);
		
		smallAsteroid = new Image("res/meteorSmall.png");
		largeAsteroid = new Image("res/meteorBig.png");
		
		if(n == 0){
			this.setSprite(smallAsteroid);
			setHp(2);
			setPointValue(50);
		}
		else{
			this.setSprite(largeAsteroid);
			setHp(35);
			setPointValue(125);
		}
		
		destroySound = new Sound("res/sounds/shipExplosion3.wav");
	}
	
	public void playDestroySound(){
		destroySound.play();
	}
	
	public void splitAsteroid(){
		
	}
	
	public LinkedList<Entity> update(LinkedList<Entity> projectiles){
		setY(getY() + getSpeed());
		
		for(int i = 0; i < projectiles.size(); i++){
			
			if(this.collidesWith(projectiles.get(i))){
				this.takeDamageFrom(projectiles.get(i));
				projectiles.remove(i);
				
			}
		}
		return projectiles;
	}
	
	public Entity shoot() throws SlickException{
		return null;
	}
}
