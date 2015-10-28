package entities;

public class MasterEnemy extends Entity {
	private static final double BASE_HP = 80;
	private static final double BASE_ATTACK = 40;
	private static final double BASE_DEFENSE = 15;
	private static final double BASE_SPEED = 1;
	private static final Id ID = Id.masterEnemy;
	
	public MasterEnemy(int x, int y){
		setHp(BASE_HP);
		setAttack(BASE_ATTACK);
		setDefense(BASE_DEFENSE);
		setSpeed(BASE_SPEED);
		setId(ID);
		setX(x);
		setY(y);
	}
}
