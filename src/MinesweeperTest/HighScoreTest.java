package MinesweeperTest;

import static org.junit.Assert.*;

import application.Field;
import application.HighScore;


import org.junit.Test;

public class HighScoreTest {

	@Test
	public void testCalculateHighScore() {
		Field field = new Field();
		HighScore highScore = new HighScore();
		field.newBlankField(10, 10);
		field.mineLayer(10);
		assertEquals(1000, highScore.calculateHighScore(field, 60000));
	}
}
