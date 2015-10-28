package projectiles;

import java.util.LinkedList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import entities.Entity;
import entities.Id;

public class RedLaser extends Entity {
	private static final double BASE_HP = 1;
	private static final double BASE_ATTACK = 10;
	private static final double BASE_DEFENSE = 0;
	private static final double BASE_SPEED = 5;
	private static final Id ID = Id.projectile;
	
	public RedLaser(double x, double y) throws SlickException{
		setHp(BASE_HP);
		setAttack(BASE_ATTACK);
		setDefense(BASE_DEFENSE);
		setSpeed(BASE_SPEED);
		setId(ID);
		setX(x);
		setY(y);
		
		setSprite(new Image("res/laserRed.png"));
	}
	
	public LinkedList<Entity> update(LinkedList<Entity> enemies){
		setY(getY() + getSpeed());
		return enemies;
	}

}
