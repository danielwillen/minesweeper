package MinesweeperTest;

import static org.junit.Assert.*;

import org.junit.Test;
import application.Field;

public class FieldTest {

	@Test
	public void testMineLayer() {
		int mines = 0;
		Field field = new Field();
		for (int i = 0; i < 100; i++) {
			field.newBlankField(10, 10);
			field.mineLayer(mines);
			assertEquals(mines, field.mineCounter());
			mines++;
		}
	}
}
