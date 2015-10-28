package ui;

import java.util.LinkedList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

import game.Screen;

public class UiHandler {
	GameContainer gc;
	Graphics g;
	LinkedList<Image> uiElements;
	private boolean musicOn;
	
	public UiHandler(GameContainer gc){
		uiElements = new LinkedList<Image>();
		musicOn = true;
		this.gc = gc;
		this.g = gc.getGraphics();
	}
	
	public void draw(){
		for(int i = 0; i < uiElements.size(); i++){
			g.drawImage(uiElements.get(i), Screen.SCREEN_W / 64 * i, Screen.SCREEN_H - 64);
		}
	}
	
	public void update(){
		Input input = gc.getInput();
		int mX = input.getMouseX();
		int mY = input.getMouseY();
		
		if((mX > 0 && mX < 64) && (mY < Screen.SCREEN_H && mY > Screen.SCREEN_H - 64)){
			if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
				musicOn = !musicOn;
			}
		}
	}
	
	public boolean mouseOverSoundButton(){
		Input input = gc.getInput();
		int mX = input.getMouseX();
		int mY = input.getMouseY();
		
		if((mX > 0 && mX < 64) && (mY < Screen.SCREEN_H && mY > Screen.SCREEN_H - 64)){
			return true;
		}
		
		return false;
	}
	
	public void addElement(Image i){
		Image t = i.getScaledCopy(64,64);
		uiElements.add(t);
	}
	
	public LinkedList<Image> getElements(){
		return uiElements;
	}
	
	public boolean isMusicOn(){
		return musicOn;
	}

	
}
