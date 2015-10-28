package application;

import java.util.ArrayList;

public class GameHandler {

	private int exposeCount = 0;
	private GameState gameState = GameState.ONGOING;
	private Field field;
	private Tile tile;
	private int[] directions;
	private ArrayList<Tile> checkList;
	private ArrayList<Tile> checkedList;

	GameHandler(Field field) {
		this.field = field;
		this.directions = new int[] { -1, 0, 1 };
		this.checkList = new ArrayList<>();
		this.checkedList = new ArrayList<>();
	}

	public void onClickPosition(int x, int y) {
		tile = field.getTileArray()[x][y];

		if (!tileIsMined()) {
			// expose();
			testExpose(tile);
			// if (winCondition()) {
			// gameState = GameState.GAMEWON;
		}
		// } else {
		// gameState = GameState.GAMELOST;
		// }

	}

	private void testExpose(Tile tile) {
		for (int i = 0; i < directions.length; i++) {
			for (int j = 0; j < directions.length; j++) {
				if (directions[i] != 0 || directions[j] != 0) {
					if (tile.getX() + directions[j] >= 0 && tile.getX() + directions[j] < field.getWidth()
						&& tile.getY() + directions[i] >= 0 && tile.getY() + directions[i] < field.getHeight()) {
						Tile tmptile = field.getTileArray()[tile.getX() + directions[j]][tile.getY() + directions[i]];
						if (!checkContains(checkedList, tmptile) && !checkContains(checkList, tmptile)) {
							if (tmptile.getNeighbour() <= 0) {
								checkList.add(tmptile);
							}
						}
					}
				}
			}
		}
		if (!checkList.isEmpty()) {
			tile.setVisible(true);
			checkedList.add(tile);
			checkList.remove(tile);
			if (!checkList.isEmpty())
				testExpose(checkList.get(0));
			else {
				checkedList.clear();
				checkList.clear();
			}

		} else {
			checkedList.clear();
			checkList.clear();
		}

	}

	private boolean checkContains(ArrayList<Tile> list, Tile tile) {
		for (Tile listTile : list) {
			if (tile == listTile) {
				return true;
			}
		}
		return false;
	}

	private void expose() {
		if (hasNeighboors()) {
			exposeSelf(); // if you know what I mean >:D
		} else // Expand cells with recursion
		{
			exposeSelf();
			exposeSurrounding();
		}
	}

	private void exposeSurrounding() {
		final int surroundingCellCount = 8;

		/*
		 * Går att göra en array med offset positioner, kan bli lite smidigare
		 * typ array = new int[]{-1,0,1} for(i) for(j) xOffset = array[i]
		 * yOffset = array[j]
		 */

		for (int i = 0; i < surroundingCellCount; i++) {

			// Relative co-ordinates to the middle cell.
			int xOffset = 0;
			int yOffset = 0;

			switch (i) {

			case 0: {
				xOffset = -1;
				yOffset = 0;
			}
				break; // Left
			case 1: {
				xOffset = -1;
				yOffset = -1;
			}
				break; // TopLeft
			case 2: {
				xOffset = 0;
				yOffset = -1;
			}
				break; // Top
			case 3: {
				xOffset = 1;
				yOffset = -1;
			}
				break; // TopRight
			case 4: {
				xOffset = 1;
				yOffset = 0;
			}
				break; // Right
			case 5: {
				xOffset = 1;
				yOffset = 1;
			}
				break; // BottomRight
			case 6: {
				xOffset = 0;
				yOffset = 1;
			}
				break; // Bottom
			case 7: {
				xOffset = -1;
				yOffset = 1;
			}
				break; // BottomLeft

			default:
				break;
			}

			if (isNeighborWithinBoundary(xOffset, yOffset)) {
				expose();
			}

		}
	}

	private boolean isNeighborWithinBoundary(int xOffset, int yOffset) {
		int fieldWidth = field.getWidth();
		int fieldHeight = field.getHeight();
		int checkX = tile.getX() + xOffset;
		int checkY = tile.getY() + yOffset;

		if (((checkX >= 0) && (checkX <= fieldWidth)) && ((checkY >= 0) && (checkY <= fieldHeight)))
			return true;
		return false;
	}

	private void exposeSelf() {
		exposeCount++;
		tile.setVisible(true);
	}

	private boolean winCondition() {
		return false; // Change this to true when Field functions are
						// implemented.

		// return exposeCount == field.getNumberOfCells() -
		// field.getTotalMines();
	}

	private boolean hasNeighboors() {
		return (tile.getNeighbour() > 0);
	}

	private boolean tileIsMined() {
		return tile.isMine();
	}

	public int getExposeCount() {
		return exposeCount;
	}

	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public void setExposeCount(int exposeCount) {
		this.exposeCount = exposeCount;
	}

	enum GameState {
		ONGOING, GAMEWON, GAMELOST;
	}

}
