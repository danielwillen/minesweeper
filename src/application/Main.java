
package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

public class Main extends Application {

	private int unit, canvasX, canvasY, sceneX, sceneY, tilesInX, tilesInY;
	private GraphicsContext gc;
	private Field field;

	@Override
	public void init() {
		this.tilesInX = 10;
		this.tilesInY = 10;
		this.unit = 20;
		this.sceneX = unit * tilesInX;
		this.sceneY = unit * tilesInY;
		this.canvasX = unit * tilesInX;
		this.canvasY = unit * tilesInY;
	}

	@Override
	public void start(Stage primaryStage) {
		VBox root = new VBox();
		Scene scene = new Scene(root, sceneX, sceneY);
		Canvas canvas = new Canvas(canvasX, canvasY);
		gc = canvas.getGraphicsContext2D();

		root.getChildren().add(canvas);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		primaryStage.setScene(scene);
		primaryStage.setTitle("Minesweeper");
		primaryStage.show();

		field = new Field();
		GameHandler gameHandler = new GameHandler(field);
		field.newBlankField(tilesInX, tilesInY);
		field.mineLayer(10);
		field.setFieldNeighbours();
		printArray(field.getTileArray());

		canvas.setOnMousePressed(event -> {
			if (event.isPrimaryButtonDown()) {
				gameHandler.onClickPosition((int) event.getX() / unit, (int) event.getY() / unit);
			} else if (event.isSecondaryButtonDown()) {
				gameHandler.flagTile((int) event.getX() / unit, (int) event.getY() / unit);
			}
			printArray(field.getTileArray());
		});
	}

	public void printArray(Tile[][] fieldArray) {
		gc.clearRect(0, 0, canvasX, canvasY);
		for (int i = 0; i < field.getHeight(); i++)
			for (int j = 0; j < field.getWidth(); j++)
				gc.drawImage(new Image(fieldArray[j][i].getImage()), fieldArray[j][i].getX() * unit, fieldArray[j][i].getY() * unit, unit, unit);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
