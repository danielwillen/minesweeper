
package application;

import java.time.LocalDateTime;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

public class Main extends Application {

	private int unit, canvasX, canvasY, sceneX, sceneY, tilesInX, tilesInY;
	private GraphicsContext gc;

	Button button = new Button("New Game");
	private Field field;


	@Override
	public void init() {
		this.tilesInX = 10;
		this.tilesInY = 10;
		this.unit = 20;
		this.sceneX = unit * tilesInX+10;
		this.sceneY = unit * tilesInY+30;
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
		root.getChildren().add(button);
		root.setAlignment(Pos.CENTER);
		button.setAlignment(Pos.TOP_RIGHT);

		primaryStage.setScene(scene);
		primaryStage.setTitle("Minesweeper");
		primaryStage.show();

		field = new Field();
		GameHandler gameHandler = new GameHandler(field);
		field.newBlankField(tilesInX, tilesInY);
		field.mineLayer(field.getMines());
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

		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				start(primaryStage);
			}

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
