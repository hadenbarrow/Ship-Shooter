package managers;

import java.util.LinkedList;
import java.util.Random;

import org.newdawn.slick.SlickException;

import entities.PowerUp;
import entities.Repairs;
import game.Screen;

public class PowerUpManager {
	LinkedList<PowerUp> powerUps;
	
	public PowerUpManager(){
		powerUps = new LinkedList<PowerUp>();
	}
	
	public void releasePowerUps() throws SlickException{
		Random rand = new Random();
		
		int z = rand.nextInt(1500);
		int x = rand.nextInt(Screen.SCREEN_W - 98);
		
		if(z == 0){
			powerUps.add(new PowerUp(x,10));
		}
		
		 z = rand.nextInt(1500);
		 x = rand.nextInt(Screen.SCREEN_W - 98);
		
		if(z == 0){
			powerUps.add(new Repairs(x,10));
		}
	}
	

	public LinkedList<PowerUp> getPowerUps() {
		return powerUps;
	}
}
