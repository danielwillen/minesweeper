package application;

public class Tile {

	/*
	 * Struktur för att spara spelet
	 *  
	 * 0 = !visible
	 * 1 = !visible & flagged
	 * 2 = !visible & mine
	 * 3 = !visible & flagged & mine
	 * 4 = visible
	 * width
	 * height
	 * 
	*/
	
	private boolean mine, visible, flagged;
	private int neighbour, x, y;

	public Tile() {
		this.mine = false;
		this.neighbour = 0;
		this.visible = false;
		this.x = 0;
		this.y = 0;
		this.flagged = false;
	}

	public Tile(int x, int y) {
		this.mine = false;
		this.neighbour = 0;
		this.visible = false;
		this.x = x;
		this.y = y;
		this.flagged = false;
	}

	public Tile(boolean mine, int x, int y) {
		super();
		this.mine = mine;
		this.neighbour = 0; 
		this.visible = false;
		this.x = x;
		this.y = y;
		this.flagged = false;
	}

	public boolean isMine() {
		return mine;
	}

	public int getImage() {
		if (this.flagged)
			return 10;
		if (!this.isVisible())
			return 11;
		else if (this.mine)
			return 9;
		else
			return neighbour;

	}

	public void setMine(boolean mine) {
		this.mine = mine;
	}

	public int getNeighbour() {
		return neighbour;
	}

	public void setNeighbour(int neighbour) {
		this.neighbour = neighbour;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public void toggleFlagged() {
		if (flagged)
			this.flagged = false;
		else 
			this.flagged = true;
	}
	
	public boolean isFlagged(){
		return this.flagged;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
