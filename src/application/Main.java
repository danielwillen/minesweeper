package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

public class Main extends Application {
	
	private int unit, canvasX, canvasY;
	private GraphicsContext gc;
	private Image image;
	
	@Override
	public void init(){
		this.unit = 15;
		this.canvasX = unit * 10;
		this.canvasY = unit * 10;
		this.image = new Image("image/Tile.png");
	}
	
	@Override
	public void start(Stage primaryStage) {
		VBox root = new VBox();
		Scene scene = new Scene(root, 400, 400);
		Canvas canvas = new Canvas(canvasX, canvasY);
		gc = canvas.getGraphicsContext2D();
		
		root.getChildren().add(canvas);

		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		
		

		
		//***TEMPORARY ONLY FOR TESTING***
		Tile[][] testArray = new Tile[10][10];
		for (int i = 0; i < testArray.length; i++) {
			for (int j = 0; j < testArray.length; j++) {
				testArray[j][i] = new Tile(false, j, i);
			}
		}
		printArray(testArray);
		//***TEMPORARY ONLY FOR TESTING***

		canvas.setOnMousePressed(event->{
			Tile tmptile = testArray[(int) event.getX()/unit][(int) event.getY()/unit];
			System.out.println((int)event.getX()/unit);
			System.out.println((int)event.getY()/unit);
			if(event.isPrimaryButtonDown()){
				System.out.println("Left klick");
				//RuleHandlerLeftClick.computetile(tmptile);
			}
			if(event.isSecondaryButtonDown()){
				System.out.println("Right klick");
				//RuleHandlerRightClick.computetile(tmptile);
			}
		});
	}
	
		
	
	/**
	 * @author Martin
	 * @param field
	 * Iterates through the field and prints it relative to the size of the canvas.
	 */
	public void printArray(Tile[][] field){
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field.length; j++) {
				gc.drawImage(image, field[j][i].getX() * unit, field[j][i].getY() * unit, unit, unit);
			}
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
