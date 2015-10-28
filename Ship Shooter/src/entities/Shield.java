package entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Shield extends Entity {
	
	private static final double BASE_HP = 1;
	private static final double BASE_ATTACK = 100;
	private static final double BASE_DEFENSE = 0;
	private static final double BASE_SPEED = 0;
	private static final Id ID = Id.shield;
	
	private Image shield;
	private int lives;
	
	public Shield(double x, double y) throws SlickException{
		setAttack(BASE_ATTACK);
		setDefense(BASE_DEFENSE);
		setSpeed(BASE_SPEED);
		setId(ID);
		setX(x);
		setY(y);
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public Image getShield() {
		return shield;
	}

	public void setShield(Image shield) {
		this.shield = shield;
	}

	public static double getBaseHp() {
		return BASE_HP;
	}
}
