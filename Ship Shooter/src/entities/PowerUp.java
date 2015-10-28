package entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class PowerUp extends Entity {
	private static final double BASE_HP = 20;
	private static final double BASE_ATTACK = 0.2;
	private static final double BASE_DEFENSE = 2;
	private static final double BASE_SPEED = 3;
	private static final Id ID = Id.powerUp;
	private Image tripleLasers;
	
	public PowerUp(int x, int y) throws SlickException{
		setHp(BASE_HP);
		setAttack(BASE_ATTACK);
		setDefense(BASE_DEFENSE);
		setSpeed(BASE_SPEED);
		setId(ID);
		setX(x);
		setY(y);
		
		tripleLasers = new Image("res/ui/powerUps.png");
		
		setSprite(tripleLasers);
	}
}
