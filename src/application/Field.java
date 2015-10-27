package application;

import javafx.scene.control.SelectionModel;
import javafx.scene.layout.TilePane;

public class Field {
	public int hight = 10;
	public int width = 10;
	int summa = 0;
	
	Field field[][] = newBlankField(10,10);
	
	public Field newBlankField(int width, int height){
		Tile field[][] = new Tile[width][height];
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < hight; j++) {
				field[i][j].setX(i);
				field[i][j].setY(j);
			}
		}
		return field;
	}
}
