package application;

import java.util.ArrayList;

public class GameHandler {

	private int exposeCount = 0;
	private GameState gameState = GameState.ONGOING;
	private Field field;
	private ArrayList<Tile> flaggedList;
	private int[] directions;

	GameHandler(Field field) {
		this.field = field;
		flaggedList = new ArrayList<Tile>();
		this.directions = new int[] { -1, 0, 1 };
	}

	public void onLeftClickPosition(int x, int y) {
		Tile tile = field.getTileArray()[x][y];

		if (!tile.isFlagged()) {
			if (!tileIsMined(tile)) {
				testExpose(tile);
			} else {
				tile.setVisible(true);
				gameState = GameState.GAMELOST;
			}
		}
		if (winCondition()) {
			gameState = GameState.GAMEWON;
		}
	}

	public void onRightClickPosition(int x, int y) {
		Tile tile = field.getTileArray()[x][y];
		if (!tile.isVisible()) {
			flagTile(tile);
		}
	}

	private void testExpose(Tile tile) {
		try {
			if (!tile.isVisible()) {
				tile.setVisible(true);
				exposeCount++;
				if (tile.getNeighbour() == 0)
					for (int i = 0; i < directions.length; i++)
						for (int j = 0; j < directions.length; j++)
							if ((directions[i] != 0 || directions[j] != 0)
									&& isWithinBounds(tile.getX() + directions[j], tile.getY() + directions[i])) {
								Tile tmptile = field.getTileArray()[tile.getX() + directions[j]][tile.getY()
										+ directions[i]];
								if (!tmptile.isVisible() && !tmptile.isFlagged())
									testExpose(tmptile);

							}
			}

		} catch (StackOverflowError e) {
			System.out.println("Stack Overflow");
		}
	}

	private boolean isWithinBounds(int j, int i) {
		if (j >= 0 && j < field.getWidth() && i >= 0 && i < field.getHeight()) {
			return true;
		}
		return false;
	}

	private void expose(Tile tile) {
		exposeCount++;
		tile.setVisible(true);
		if (!hasNeighboors(tile) && (!tileIsMined(tile))) {
			int xOffset, yOffset;
			for (Direction direction : Direction.values()) {
				xOffset = direction.getX();
				yOffset = direction.getY();

				if (isNeighborWithinBoundary(tile, xOffset, yOffset) && (!isNeighboorExposed(tile, xOffset, yOffset))) {
					Tile neighbor = field.getTileArray()[tile.getX() + xOffset][tile.getY() + yOffset];
					expose(neighbor);
				}
			}
		}
	}

	private boolean isNeighboorExposed(Tile tile, int xOffset, int yOffset) {
		int checkX = tile.getX() + xOffset;
		int checkY = tile.getY() + yOffset;
		Tile tmpTile = field.getTileArray()[checkX][checkY];
		if (tmpTile.isVisible())
			return true;
		return false;

	}

	private boolean isNeighborWithinBoundary(Tile tile, int xOffset, int yOffset) {
		int fieldWidth = field.getWidth();
		int fieldHeight = field.getHeight();
		int checkX = tile.getX() + xOffset;
		int checkY = tile.getY() + yOffset;

		if (((checkX >= 0) && (checkX <= fieldWidth - 1)) && ((checkY >= 0) && (checkY <= fieldHeight - 1)))
			return true;
		return false;
	}

	private boolean winCondition() {
		System.out.println(exposeCount);
		System.out.println(field.numberOfTiles() - field.getMines());
		if (exposeCount == field.numberOfTiles() - field.getMines())
			System.out.println("won by exposure");
		return exposeCount == field.numberOfTiles() - field.getMines();
	}

	private boolean hasNeighboors(Tile tile) {
		return (tile.getNeighbour() > 0);
	}

	private boolean tileIsMined(Tile tile) {
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

	public enum GameState {
		ONGOING, GAMEWON, GAMELOST;
	}

	public enum Direction {

		LEFT(-1, 0), RIGHT(1, 0), UP(0, -1), DOWN(0, 1), TOPLEFT(-1, -1), TOPRIGHT(1, -1), BOTLEFT(-1, 1), BOTRIGHT(1,
				1);

		private int x;
		private int y;

		Direction(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getY() {
			return y;
		}

		public int getX() {
			return x;
		}
	}

	public void flagTile(Tile tile) {
		if (tile.isFlagged()) {
			flaggedList.remove(tile);
			tile.toggleFlagged();
		} else {
			flaggedList.add(tile);
			tile.toggleFlagged();
		}
		wonByFlagging();
	}

	private void wonByFlagging() {
		int count = 0;
		for (Tile tile : flaggedList) {
			if (tile.isMine() && tile.isFlagged()) {
				count++;
			}
		}
		if (count == field.getMines()) {
			gameState = GameState.GAMEWON;
		}
	}

	public void updateTileImage(Tile tile) {

	}

}
