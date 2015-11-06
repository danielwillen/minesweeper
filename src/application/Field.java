package application;

import java.util.Random;

public class Field {

	private int height;
	private int width;
	private Tile[][] tileArray;
	int summa = 0;
	private int mines;

	public void exposeAll() {
		for (int y = 0; y < this.height; y++){
			for (int x = 0; x < this.width; x++){
				tileArray[x][y].setVisible(true);
				if (tileArray[x][y].isFlagged())
					tileArray[x][y].toggleFlagged();
			}
		}
	}
	
	public void newBlankField(int width, int height) {
		tileArray = new Tile[width][height];
		this.height = height;
		this.width = width;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tileArray[x][y] = new Tile(false, x, y);
			}
		}
	}

	/*public void printField(Tile tileArray[][]) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				System.out.print("X: " + tileArray[x][y].getX());
				System.out.print(" Y: " + tileArray[x][y].getY());
				System.out.println(" Mine: " + tileArray[x][y].isMine());
			}
		}
	}*/
	
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
	
	public int mineCounter() {
		int mines = 0;
		for (int y = 0; y < height; y++){
			for (int x = 0; x < width; x++){
				if (tileArray[x][y].isMine())
					mines++;
			}
		}
		return mines;
	}

	public Tile[][] mineLayer(int mines) {
		int minesLeft = mines;
		this.mines = mines;
		
		// Kontrollera att antal mines som ska läggas får plats på spelplanen
		// (height*width)
		if (mines > height * width) {
			// Om inte, fyll skärmen med minor och meddela användaren att spelet
			// kan bli lite för lätt att vinna.
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					tileArray[x][y].setMine(true);
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
	
	public int numberOfTiles(){
		return this.height*this.width;
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

	public int getMines() {
		return mines;
	}

	public void setMines(int mines) {
		this.mines = mines;
	}
}