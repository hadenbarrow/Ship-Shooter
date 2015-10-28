package projectiles;

import java.util.LinkedList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import entities.Entity;
import entities.Id;

public class GreenLaser extends Entity {
	private static final double BASE_HP = 1;
	private static final double BASE_ATTACK = 9;
	private static final double BASE_DEFENSE = 0;
	private static final double BASE_SPEED = 15;
	private static final Id ID = Id.projectile;
	
	private Image laserHit;
	
	public GreenLaser(double x, double y) throws SlickException{
		setHp(BASE_HP);
		setAttack(BASE_ATTACK);
		setDefense(BASE_DEFENSE);
		setSpeed(BASE_SPEED);
		setId(ID);
		setX(x);
		setY(y);
		
		setSprite(new Image("res/laserGreen.png"));
		laserHit = new Image("res/laserGreenShot.png");
	}
	
	public LinkedList<Entity> update(LinkedList<Entity> enemies){
		setY(getY() - getSpeed());
		
		for(int i = 0; i < enemies.size(); i++){
			if(this.collidesWith(enemies.get(i))){
				this.takeDamageFrom(enemies.get(i));
				this.setSprite(laserHit);
				this.setX(getX() - (laserHit.getWidth()/2));
				//this.setX(getX() + laserHit.getWidth());
			}
		}
		return enemies;
	}

}
