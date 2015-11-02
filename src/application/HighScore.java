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
	
	public void writeToHighScore(String score) {	//spelaren som parameter
		File file = new File("highscore.txt");
		ArrayList<String> scores = new ArrayList<>();	//skapar ArrayList
		String pathname = file.getPath();
		String[] highScore = readHighScore().split(System.lineSeparator()); //skapar array av gamla high score
		String newHighScore = "";
		
		for (String s:highScore)	//lägger till gamla high score i ArrayList
			if (s.length() > 0)
				scores.add(s);
		
		scores.add(score);		//lägger till nya spelaren
		Collections.sort(scores); //bör fixa bättre sortering!!! typ göra egen comparator som
								//tar ut poängen med parseInteger/liknande
		
		//bakar ihop allt till en string, för att slippa fula formatet man får med toString()
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
