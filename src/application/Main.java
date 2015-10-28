
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

	@Override
	public void init() {
		this.canvasX = 200;
		this.canvasY = 200;
		this.unit = canvasX / 10;
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
		primaryStage.setTitle("Minesweeper");
		primaryStage.show();

		Field field = new Field();
		GameHandler gameHandler = new GameHandler(field);
		Tile[][] fieldArray = field.newBlankField(10, 10);
		field.mineLayer(10);
		field.setFieldNeighbours();
		printArray(fieldArray);

		canvas.setOnMousePressed(event -> {
			if (event.isPrimaryButtonDown()) {
				gameHandler.onClickPosition((int) event.getX() / unit, (int) event.getY() / unit);
				// Leftclick
			} else if (event.isSecondaryButtonDown()) {
				gameHandler.flagTile((int) event.getX() / unit, (int) event.getY() / unit);
			}
			printArray(field.getTileArray());
		});
	}

	public void printArray(Tile[][] field) {
		gc.clearRect(0, 0, canvasX, canvasY);
		for (int i = 0; i < field.length; i++)
			for (int j = 0; j < field.length; j++)
				gc.drawImage(new Image(field[j][i].getImage()), field[j][i].getX() * unit, field[j][i].getY() * unit, unit, unit);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
