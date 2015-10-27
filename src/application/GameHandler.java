package application;

public class GameHandler {


	Tile tile = new Tile(false, 5, 3); //Instead of new, swap with "field[x][y]".
	private int exposeCount=0;
	private boolean isGameOver=false;
	private GameState gameState;
	
	GameHandler(){
		
		
		if (!tileIsMined()) {
	
			if (hasNeighboors()){
				exposeTile();
			}
			else // Expand cells with recursion, for now the functionality is the same.
			{
				exposeTile();
			}
			
		}
		else { 
			isGameOver = true;
		}
		
	}
	
	
	public void expandCells(){
		
		
	}
	
	public boolean isGameOver() {
		return isGameOver;
	}


	public void setGameOver(boolean isGameOver) {
		this.isGameOver = isGameOver;
	}


	private void exposeTile(){
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
