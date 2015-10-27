package application;

public class Tile {
	
	boolean mine;
	int neighbour;
	boolean visible;
	
	public Tile(boolean mine, int neighbour) {	//Konstruktor för tile, mina eller inte? hur många minor som grannar (0-8)?
		super();
		this.mine = mine;
		this.neighbour = neighbour;
		this.visible = false;					//Sätter visible till false
	}

	public boolean isMine() {
		return mine;
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
}
