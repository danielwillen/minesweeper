package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HighScore {

	/*Själva high score ska bestå av arraylist/länkad lista?
	 * spara allt i en fin sorterad fil som går att öppna och läsa
	 */
	
	
	public String readHighScore() {
		String text = null;
		String highScore = "";
		File file = new File("highscore.txt");
		String pathname = file.getPath();
		
		try {
			file.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try (BufferedReader br = new BufferedReader(new FileReader(pathname))) {
			while((text=br.readLine())!=null)
			{
				highScore+=text+System.lineSeparator();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return highScore;
	}
	
	public void writeToHighScore() {	//lägg till själva listan(?) som argument
		File file = new File("highscore.txt");
		String highScore = readHighScore();
		String pathname = file.getPath();
		highScore += "Stefan : 1000000000000";
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathname))) {
			bw.write(highScore);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
