package entities;

import java.util.LinkedList;
import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import projectiles.RedLaser;

public class AdvancedEnemy extends Entity {
	private static final double BASE_HP = 25;
	private static final double BASE_ATTACK = 0.2;
	private static final double BASE_DEFENSE = 2;
	private static final double BASE_SPEED = 4;
	private static final Id ID = Id.advancedEnemy;
	
	public AdvancedEnemy(int x, int y) throws SlickException{
		setHp(BASE_HP);
		setAttack(BASE_ATTACK);
		setDefense(BASE_DEFENSE);
		setSpeed(BASE_SPEED);
		setId(ID);
		setX(x);
		setY(y);
		
		setSprite(new Image("res/enemyUFO.png"));
		setPointValue(500);
	}
	
	public Entity[] getLasers() throws SlickException{
		Entity[] lasers = new Entity[3];
		lasers[0] = new RedLaser(this.getX(), this.getY());
		lasers[0].setSpeed(7);
		lasers[1] =	new RedLaser(this.getX() + this.getSprite().getWidth()/2, this.getY());
		lasers[1].setSpeed(7);
		lasers[2] = new RedLaser(this.getX() + this.getSprite().getWidth(), this.getY());
		lasers[2].setSpeed(7);
		
		return lasers;
	}
	
	public boolean rollToShoot(){
		Random rand = new Random();
		
		int x = rand.nextInt(8);
		if(x == 0){
			return true;
		}
		else{
			return false;
		}
		
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
}
