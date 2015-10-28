package game;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import states.BattleState;
import states.MainMenu;

public class Game extends StateBasedGame {
	
	private final static String TITLE = "Ship Shooter";

	public Game(String name) {
		super(name);
	}

	
	public void initStatesList(GameContainer arg0) throws SlickException {
		addState(new MainMenu());
		addState(new BattleState());
	}
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new Game(TITLE));
		app.setDisplayMode(Screen.SCREEN_W, Screen.SCREEN_H, false);
		app.setAlwaysRender(true);
		app.setTargetFrameRate(60);
		app.setShowFPS(true);
		app.start();
	}

}
