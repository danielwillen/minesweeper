package application;

import javafx.scene.image.Image;

public class Tile {

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

	public Tile(boolean mine, int x, int y) { // Konstruktor för tile, mina
												// eller inte?
		super();
		this.mine = mine;
		this.neighbour = 0; // Sätter antal min-grannar till 0
		this.visible = false; // Sätter visible till false
		this.x = x;
		this.y = y;
		this.flagged = false;
	}

	public boolean isMine() {
		return mine;
	}

	public String getImage() {
		if (this.flagged)
			return "Image/flagged.jpg";
		if (!this.isVisible())
			return "Image/hidden.jpg";
		else if (this.mine)
			return "Image/bomb.jpg";
		else
			return "Image/" + neighbour + ".jpg";

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

	public void setFlagged() {
		if (flagged)
			this.flagged = false;
		else
			this.flagged = true;
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
