package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class HighScore {

	/*Sj�lva high score ska best� av arraylist/l�nkad lista?
	 * spara allt i en fin sorterad fil som g�r att �ppna och l�sa
	 */
	public void writeToHighScore() {	//l�gg till sj�lva listan(?) som argument
		File file = new File("highscore.txt");
		String pathname = file.getPath();
		System.out.println(pathname);
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathname))) {
			bw.write("test");		//�ndra s� att listan(?) skickas som argument h�r
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
