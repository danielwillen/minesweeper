package application;

import javafx.scene.control.SelectionModel;
import javafx.scene.layout.TilePane;

public class Field {
	public int height = 10;
	public int width = 10;
	int summa = 0;
	
	
	public Tile[][] newBlankField(int width, int height){
		Tile field[][] = new Tile[width][height];
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				field[y][x] = new Tile(false, x, y);
			}
		}
		return field;
	}
	
	public void printField(Tile field[][]){
		for (int y = 0; y < height; y++){
			for (int x = 0; x < width; x++)	{
				System.out.print("X: " + field[x][y].getX());
				System.out.print(" Y: " + field[x][y].getY());
				System.out.println(" Mine: " + field[x][y].isMine());
			}
		}
	}
}
