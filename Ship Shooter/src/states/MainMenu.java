package states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import game.Screen;
import save.LoadScore;

public class MainMenu extends BasicGameState {
	
	private Image bg, button;
	LoadScore loadScore;
	
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		bg = new Image("res/Background/backgroundColor.png");
		button = new Image("res/redButton.png");
		
		try {
			loadScore = new LoadScore();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		bg.draw(0, 0, Screen.SCREEN_W, Screen.SCREEN_H);

		button.draw(Screen.SCREEN_W - 560, Screen.SCREEN_H - 460, 160, 80);
		button.draw(Screen.SCREEN_W - 560, Screen.SCREEN_H - 360, 160, 80);
		
		g.setColor(Color.black);
		g.drawString("PLAY", Screen.SCREEN_W - 560, Screen.SCREEN_H - 460);
		g.drawString("EXIT", Screen.SCREEN_W - 560, Screen.SCREEN_H - 360);
		
		g.setColor(Color.orange);
		g.drawString("Your all-time highscore is: " + String.valueOf(loadScore.getScore()), Screen.SCREEN_W/2 - 150, 60);
	}

	
	public void update(GameContainer gc, StateBasedGame sbg, int d) throws SlickException {
		Input input = gc.getInput();
		int mX = input.getMouseX();
		int mY = input.getMouseY();
		
		int pCorner = Screen.SCREEN_W - 560;
		int buttonWidth = 160;
		int pTop = Screen.SCREEN_H - 460;
		int buttonHeight = 80;
		
		if((mX >= pCorner && mX <= pCorner + buttonWidth) && (mY > pTop && mY < pTop + buttonHeight)){
			if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
				sbg.enterState(0);
			}
		}
		
		if((mX >= pCorner && mX <= pCorner + buttonWidth) && (mY > pTop + 100 && mY < pTop + buttonHeight + 100)){
			if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
				System.exit(1);
			}
		}
	}
	
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException{
		init(gc, sbg);
	}
	
	public void leave(){
		
	}

	
	public int getID() {
		return 1;
	}

}
