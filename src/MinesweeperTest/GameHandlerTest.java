package MinesweeperTest;

import static org.junit.Assert.*;

import org.junit.Test;

import application.Field;
import application.GameHandler;

public class GameHandlerTest {

	@Test
	public void testisWithinBounds() {
		Field field = new Field();
		GameHandler gameHandler = new GameHandler(field);
		field.newBlankField(10, 10);
		assertTrue(gameHandler.isWithinBounds(5,9));
	}

}
