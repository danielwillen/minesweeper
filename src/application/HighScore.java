package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class HighScore {

	/*Själva high score ska bestå av arraylist/länkad lista?
	 * spara allt i en fin sorterad fil som går att öppna och läsa
	 */
	public void writeToHighScore() {	//lägg till själva listan(?) som argument
		File file = new File("highscore.txt");
		String pathname = file.getPath();
		System.out.println(pathname);
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathname))) {
			bw.write("test");		//ändra så att listan(?) skickas som argument här
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
