package camera;

public class Camera {
	private int x, y;
	private final static int cameraSpeed = 3;
	
	public Camera(){
		this.setX(0);
		this.setY(0);
	}
	
	public void update(){
		setY(getY() + cameraSpeed);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
