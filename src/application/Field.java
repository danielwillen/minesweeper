package application;

import java.util.Random;

import javafx.stage.DirectoryChooser;

public class Field {
	public int height = 10;
	public int width = 10;


	Tile[][] tileArray = newBlankField(width, height);

	public Tile[][] newBlankField(int width, int height) {
		Tile tileArray[][] = new Tile[width][height];

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tileArray[x][y] = new Tile(false, x, y);
			}
		}
		return tileArray;
	}

	public void printField(Tile tileArray[][]) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				System.out.print("X: " + tileArray[x][y].getX());
				System.out.print(" Y: " + tileArray[x][y].getY());
				System.out.println(" Mine: " + tileArray[x][y].isMine());
			}
		}
	}
	
	public void setFieldNeighbours(){
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				tileArray[j][i].setNeighbour(countTileNeighbours(tileArray[j][i]));
						
			}
		}
	}
	
	private int countTileNeighbours(Tile tile){
		int neighbourCount = 0;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if(j != 0 || i != 0){
					if(tile.getX() + j >= 0 && tile.getX() + j < width && tile.getY() + i >= 0 && tile.getY() + i < height){
						if(tileArray[tile.getX() + j][tile.getY() + i].isMine()){
							neighbourCount++;
						}
					}
				}
			}
		}
		return neighbourCount;
	}

	public Tile[][] mineLayer(int mines) {
		int minesLeft = mines;
		
		// Kontrollera att antal mines som ska läggas får plats på spelplanen
		// (height*width)
		if (mines > height * width) {
			// Om inte, fyll skärmen med minor och meddela användaren att spelet
			// kan bli lite för lätt att vinna.
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					tileArray[x][y].setMine(true);
					System.out.println("There will be mines... EVERYWHERE!");
				}
			}
			minesLeft = 0;
		}

		// Annars
		else {
			// Så länge det finns minor kvar att lägga.
			while (minesLeft > 0) {
				// slumpa x och y-led med planens height och width som constraints
				int currentX = randInt(0, width-1); //Slumpa mellan 0 och width
				int currentY = randInt(0, height-1); //Slumpa mellan 0 och Height
				
				// Kontrollera om det redan ligger en mina där.
				// Om inte, lägg ut mina och räkna ner antal mines som finns
				// kvar att lägga (minesLeft)
				if (!tileArray[currentX][currentY].isMine())	{
					tileArray[currentX][currentY].setMine(true);
					minesLeft--;
				}
			}
		}
		return tileArray;
	}
	
	public static int randInt(int min, int max) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}

	public Tile[][] getTileArray() {
		return tileArray;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

}