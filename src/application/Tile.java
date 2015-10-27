package application;

public class Tile {
	
	private boolean mine, visible;
	private int neighbour, x, y;

	public Tile(int x, int y) {
		this.mine = false;
		this.neighbour = 0;
		this.visible = false;
		this.x = x;
		this.y = y;
	}
	
	public Tile(boolean mine, int x, int y) {	//Konstruktor för tile, mina eller inte?
		super();
		this.mine = mine;
		this.neighbour = 0;		//Sätter antal min-grannar  till 0
		this.visible = false;	//Sätter visible till false
		this.x = x;
		this.y = y;
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
