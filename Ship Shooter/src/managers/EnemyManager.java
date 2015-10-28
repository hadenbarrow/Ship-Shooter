package managers;

import java.util.LinkedList;
import java.util.Random;

import org.newdawn.slick.SlickException;
import entities.AdvancedEnemy;
import entities.Asteroid;
import entities.BasicEnemy;
import entities.Entity;
import game.Screen;

public class EnemyManager {
	private LinkedList<Entity> enemies;
	private LinkedList<Entity> projectiles;
	private int score;
	private Random rand;
	
	public EnemyManager() throws SlickException{
		rand = new Random();
		setScore(0);
		enemies = new LinkedList<Entity>();
		projectiles = new LinkedList<Entity>();
		
		for(int i = 0; i < 98 * 5; i += 98){
			//enemies.add(new BasicEnemy(2*i, 10));
		}
	}
	
	public void releaseEnemies() throws SlickException{
		
		int z = rand.nextInt(100);
		int x = rand.nextInt(Screen.SCREEN_W - 98);
		if(z == 0){
			enemies.add(new BasicEnemy(x,10));
		}
		x = rand.nextInt(Screen.SCREEN_W - 98);
		if(z == 1){
			enemies.add(new Asteroid(x,10));
		}
		
		z = rand.nextInt(500);
		x = rand.nextInt(Screen.SCREEN_W - 98);
		if(z == 0){
			enemies.add(new AdvancedEnemy(x,10));
		}
		
	}
	
	public LinkedList<Entity> update(int delta, LinkedList<Entity> projectiles) throws SlickException{
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).update(projectiles);
			
			if(enemies.get(i).getHp() <= 0){
				setScore(getScore() + enemies.get(i).getPointValue());
				enemies.get(i).playDestroySound();
				enemies.remove(i);
			}
		}
		if(enemies.isEmpty()){
			enemies.add(new BasicEnemy(rand.nextInt(Screen.SCREEN_W - 98),10));
		}
		return enemies;
	}
	
	public  LinkedList<Entity> getEnemyShots(LinkedList<Entity> enemyProjectiles) throws SlickException{
		
		for(int i = 0; i < enemies.size(); i++){
			if(enemies.get(i).rollToShoot()){
				if(enemies.get(i).shoot() != null && !(enemies.get(i) instanceof AdvancedEnemy)){
					projectiles.add(enemies.get(i).shoot());
				}
				if(enemies.get(i) instanceof AdvancedEnemy){
					if(enemies.get(i).rollToShoot()){
						Entity[] lasers = new Entity[3];
						lasers = enemies.get(i).getLasers();
						projectiles.add(lasers[0]);
						projectiles.add(lasers[1]);
						projectiles.add(lasers[2]);
					}
				}
			}
		}
		
		 return projectiles;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}

