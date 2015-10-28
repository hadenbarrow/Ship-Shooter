package save;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class LoadScore {
	private int score;
	private String fileName = "highscores.txt";
	
	public LoadScore() throws ClassNotFoundException{
		try {
			ObjectInputStream is = new ObjectInputStream(new FileInputStream(fileName));
			score = (int) is.readObject();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getScore(){
		return score;
	}
}
