package entities;

import java.util.LinkedList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import projectiles.RedLaser;

public class BasicEnemy extends Entity {
	private static final double BASE_HP = 20;
	private static final double BASE_ATTACK = 0.2;
	private static final double BASE_DEFENSE = 2;
	private static final double BASE_SPEED = 3;
	private static final Id ID = Id.basicEnemy;
	
	private Sound destroySound;
	
	public BasicEnemy(int x, int y) throws SlickException{
		setHp(BASE_HP);
		setAttack(BASE_ATTACK);
		setDefense(BASE_DEFENSE);
		setSpeed(BASE_SPEED);
		setId(ID);
		setX(x);
		setY(y);
		setPointValue(100);
		
		destroySound = new Sound("res/sounds/shipExplosion2.wav");
		
		setSprite(new Image("res/enemyShip.png"));
	}
	
	public Entity shoot() throws SlickException{
		return new RedLaser(getX() + this.getSprite().getWidth()/2, this.getY());
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
	
	public void playDestroySound(){
		destroySound.play();
	}
}
