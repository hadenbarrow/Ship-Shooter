package managers;


import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import entities.Player;
import game.Screen;

public class IntroManager {
	private Player player;
	private boolean playerReady;
	
	public IntroManager() throws SlickException{
		setPlayerReady(false);
		player = new Player(Screen.SCREEN_W/2, Screen.SCREEN_H);
		player.setX(Screen.SCREEN_W/2 - (player.getSprite().getWidth()/2));
		//player.setY(Screen.SCREEN_H/2 - (player.getSprite().getHeight()/2));
	}
	
	public void update(GameContainer gc){
		if(player.getY() > Screen.SCREEN_H/2 - (player.getSprite().getHeight()/2)){
			player.setY(player.getY() - player.getSpeed());
		}
		
		if(gc.getInput().isKeyDown(Input.KEY_SPACE)){
			playerReady = true;
		}
	}
	
	public void render(Graphics g) throws InterruptedException{
		player.draw(g);
		if(player.getY() <= Screen.SCREEN_H/2 - (player.getSprite().getHeight()/2)){
			playInstructions(g);
			g.setColor(Color.red);
			g.drawString("  Press spacebar to begin", 0, 425);
		}
	}
	
	public void playInstructions(Graphics g) throws InterruptedException{
		String[] instructions = {" Shoot enemy ships and asteroids to gain points. Avoid being hit my enemy lasers or running into asteroids",
								 " You are awarded points for every enemy or asteroid you destory.",
								"  W - move forward", "  S - move backward", "  A - move left", "  D - move right", "  M - turn music on/off ", "  Enter - return to main menu","  Spacebar - shoot laser",  "  Press esc to pause and unpause."};
		
		for(int i = 0; i < instructions.length; i++){
			g.drawString(instructions[i], 0, 100 + i * 25);
		}
	}

	public boolean isPlayerReady() {
		return playerReady;
	}

	public void setPlayerReady(boolean playerReady) {
		this.playerReady = playerReady;
	}
}
