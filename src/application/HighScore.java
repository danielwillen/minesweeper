package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class HighScore {

	/*Sj�lva high score ska best� av arraylist/l�nkad lista?
	 * spara allt i en fin sorterad fil som g�r att �ppna och l�sa
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
	
	public void writeToHighScore(String score) {	//spelaren som parameter
		File file = new File("highscore.txt");
		ArrayList<String> scores = new ArrayList<>();	//skapar ArrayList
		String pathname = file.getPath();
		String[] highScore = readHighScore().split(System.lineSeparator()); //skapar array av gamla high score
		String newHighScore = "";
		
		for (String s:highScore)	//l�gger till gamla high score i ArrayList
			if (s.length() > 0)
				scores.add(s);
		
		scores.add(score);		//l�gger till nya spelaren
		Collections.sort(scores); //b�r fixa b�ttre sortering!!! typ g�ra egen comparator som
								//tar ut po�ngen med parseInteger/liknande
		
		//bakar ihop allt till en string, f�r att slippa fula formatet man f�r med toString()
		Iterator<String> iterator=  scores.iterator();
		while (iterator.hasNext()) {
			newHighScore += iterator.next() + System.lineSeparator();
		}
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathname))) {
			bw.write(newHighScore);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
