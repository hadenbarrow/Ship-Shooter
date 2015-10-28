package entities;

import java.util.LinkedList;
import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import game.Screen;
import projectiles.GreenLaser;
import projectiles.RedLaser;

public class Entity {
	private double hp, attack, defense, speed;
	private Image sprite;
	private double x, y;
	private Id Id;
	private int pointValue;
	
	public Entity(){
		
	}
	
	public boolean isOnScreen(){
		if(x < 0 || x > Screen.SCREEN_W){
			return false;
		}
		if(y < 0 || y > Screen.SCREEN_H){
			return false;
		}
		else{
			return true;
		}
	}
	
	public Entity shoot() throws SlickException{
		return new GreenLaser(getX() + getSprite().getWidth()/2, getY());
	}
	
	public Entity[] getLasers() throws SlickException{
		
		Entity[] lasers = new Entity[3];
		lasers[0] = new RedLaser(this.getX(), this.getY());
		lasers[1] =	new RedLaser(this.getX() + this.getSprite().getWidth()/2, this.getY());
		lasers[2] = new RedLaser(this.getX() + this.getSprite().getWidth(), this.getY());
		
		return lasers;
	}
	
	public void playDestroySound(){
		
	}
	
	public LinkedList<Entity> update(LinkedList<Entity> projectiles){
		move();
		return projectiles;
	}
	
	public void update(){
		move();
	}
	
	public void move(){
		//basic movement is to move forward at the speed of the entity. ie: lasers and enemies
		setY(getY() + getSpeed());
	}
	
	public void draw(Graphics g){
		g.drawImage(this.getSprite(), (float)this.getX(), (float)this.getY());
	}
	
	public boolean collidesWith(Entity other){
		return this.getBounds().intersects(other.getBounds());
	}
	
	public void takeDamageFrom(Entity other){
		double difference = other.getAttack() - getDefense();
		
		if(difference > 0){
			setHp(getHp() - difference);
		}
	}
	
	public boolean rollToShoot(){
		Random rand = new Random();
		
		int x = rand.nextInt(65);
		if(x == 0){
			return true;
		}
		else{
			return false;
		}
		
	}
	
	public Rectangle getBounds(){
		return new Rectangle((float) this.getX(), (float) this.getY(), getSprite().getWidth(), getSprite().getHeight());
	}
	
	public void drawBounds(Graphics g){
		g.draw(getBounds());
	}

	public double getHp() {
		return hp;
	}

	public void setHp(double hp) {
		this.hp = hp;
	}

	public double getAttack() {
		return attack;
	}

	public void setAttack(double attack) {
		this.attack = attack;
	}

	public double getDefense() {
		return defense;
	}

	public void setDefense(double defense) {
		this.defense = defense;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public Image getSprite() {
		return sprite;
	}

	public void setSprite(Image sprite) {
		this.sprite = sprite;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public Id getId() {
		return Id;
	}

	public void setId(Id id) {
		Id = id;
	}

	public int getPointValue() {
		return pointValue;
	}

	public void setPointValue(int pointValue) {
		this.pointValue = pointValue;
	} 
	
}
