package application;

import java.util.Random;

public class Field {
	public int height = 10;
	public int width = 10;
	int summa = 0;

	Tile[][] tileArray = newBlankField(height, width);

	public Tile[][] newBlankField(int width, int height) {
		Tile tileArray[][] = new Tile[width][height];

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tileArray[y][x] = new Tile(false, x, y);
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

	public void mineLayer(Tile tileArray[][], int mines) {
		int minesLeft = mines;

		// Kontrollera att antal mines som ska läggas får plats på spelplanen
		// (height*width)
		if (mines >= height * width) {
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
				int currentX = randInt(0, width); //Slumpa mellan 0 och width
				int currentY = randInt(0, height); //Slumpa mellan 0 och Height
				
				// Kontrollera om det redan ligger en mina där.
				// Om inte, lägg ut mina och räkna ner antal mines som finns
				// kvar att lägga (minesLeft)
				if (!tileArray[currentX][currentY].isMine())	{
					tileArray[currentX][currentY].setMine(true);
					minesLeft--;
				}
			}
		}
	}
	
	public static int randInt(int min, int max) {
	    Random rand;
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
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

	public int getSumma() {
		return summa;
	}

	public void setSumma(int summa) {
		this.summa = summa;
	}
}