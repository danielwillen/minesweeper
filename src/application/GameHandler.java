package application;

public class GameHandler {


	Tile tile = new Tile(false, 5, 3); //Instead of new, swap with "field[x][y]".
	private int exposeCount=0;
	private boolean isGameOver=false;
	private GameState gameState;
	
	GameHandler(){
		
		
		if (tileIsMined()==false) {
	
			expose();
		
			
		}
		else { 
			isGameOver = true;
		}
		
	}
	
	private void expose(){
		if (hasNeighboors()){
			exposeSelf();
		}
		else // Expand cells with recursion, for now the functionality is the same.
		{
			exposeSurrounding();
		}
	}
	
	public void exposeSurrounding(){
		final int surroundingCellCount=8;
		
		for (int i = 0; i < surroundingCellCount; i++) {
			
			//Coordinates for exposing neighbors (relative to the current cell):
			int xOffset = 0;
			int yOffset = 0; 
			
			switch (i) {
			
			case 0: {  xOffset=-1; yOffset=0;  }   break; //Left
			case 1: {  xOffset=-1; yOffset=-1; }   break; //TopLeft
			case 2: {  xOffset=0;  yOffset=-1; }   break; //Top
			case 3: {  xOffset=1;  yOffset=-1; }   break; //TopRight
			case 4: {  xOffset=1;  yOffset=0;  }   break; //Right
			case 5: {  xOffset=1;  yOffset=1;  }   break; //BottomRight
			case 6: {  xOffset=0;  yOffset=1;  }   break; //Bottom
			case 7: {  xOffset=-1; yOffset=1;  }   break; //BottomLeft
			
			default: break;
			}
			
			if (isNeighborWithinBoundary(xOffset,yOffset)){
				expose();
			}
			
	}
		//For every surrounding cell that's not exposed:
			//exposeTile(iterator)
		
		//Run this function on all neighbor cells that are not exposed and have a c
		//Do this: Expose surrounding cells that are closed.
	
	
	}
	
	private boolean isNeighborWithinBoundary(int xOffset, int yOffset){
		//Needs field (cell array) to implement. 
		//arrayWidth, arrayHeight, currentCellX, currentCellY;
		//targetx = tile.getX() + xOffset
		//targety = tile.getY() + yOffset
		//if ((targetx > 0) && (targetx < arrayWidth)) 
		// and if ((targety > 0) && (targety < arrayHeight)) 
		//		return true
		//return false
		return true;
	}
	public boolean isGameOver() {
		return isGameOver;
	}


	public void setGameOver(boolean isGameOver) {
		this.isGameOver = isGameOver;
	}


	private void exposeSelf(){
		exposeCount++;
		tile.setVisible(true);
	}
	
	
	private boolean winCondition(){
		return true;
		//Need field to implement
		//exposeCount == field.getNumberOfCells() - field.getTotalMines(); //we have won when we expose all non-mined cells.
	}
	
	private boolean hasNeighboors(){
		return (tile.getNeighbour() > 0);
	}
	
	private boolean tileIsMined(){
		return tile.isMine();
	}
	
	private int getExposeCount(){
		return exposeCount;
	}
	
	enum GameState{
		ONGOING,GAMEWON,GAMELOST;
	}
	
	
}
