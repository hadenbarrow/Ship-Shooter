package save;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SaveScore {
	private int score;
	private LoadScore oldScore;
	String fileName = "highscores.txt";
	
	public SaveScore(int score) throws FileNotFoundException, IOException, ClassNotFoundException{
		this.score = score;
		oldScore = new LoadScore();
		setupOS();
	}
	
	public void setupOS() throws FileNotFoundException, IOException{
		int oldscore = oldScore.getScore();
		
		
		int higher = score;
		
		if(this.score < oldscore){
			higher = oldscore;
		}
		
		ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName));
		os.writeObject(higher);
		os.close();
		
		System.out.println("done writing");
	}
}
