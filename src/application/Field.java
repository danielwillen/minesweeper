package application;

import java.util.Random;

public class Field {
	public int height = 10;
	public int width = 10;
	int summa = 0;
	
	Tile[][] tileArray = newBlankField(height, width);
	
	
	public Tile[][] newBlankField(int width, int height){
		Random rand = new Random();
		Tile tileArray[][] = new Tile[width][height];
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if(rand.nextInt(100) >= 90)
					tileArray[y][x] = new Tile(true, x, y);
				else
					tileArray[y][x] = new Tile(false, x, y);
			}
		}
		return tileArray;
	}
	
	public void printField(Tile tileArray[][]){
		for (int y = 0; y < height; y++){
			for (int x = 0; x < width; x++)	{
				System.out.print("X: " + tileArray[x][y].getX());
				System.out.print(" Y: " + tileArray[x][y].getY());
				System.out.println(" Mine: " + tileArray[x][y].isMine());
			}
		}
	}
	
	public void mineLayer(){
		
	}
	
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Tile[][] getTileArray() {
		return tileArray;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getSumma() {
		return summa;
	}

	public void setSumma(int summa) {
		this.summa = summa;
	}
}