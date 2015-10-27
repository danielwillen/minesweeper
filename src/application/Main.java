
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
	public void init() {
		this.canvasX = 200;
		this.canvasY = 200;
		this.unit = canvasX / 10;
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

		Field field = new Field();
		Tile[][] fieldArray = field.newBlankField(10, 10);
		printArray(fieldArray);

		canvas.setOnMousePressed(event -> {
			Tile tmptile = fieldArray[(int) event.getX() / unit][(int) event.getY() / unit];
			System.out.println(tmptile.getX());
			System.out.println(tmptile.getY());
			if (event.isPrimaryButtonDown()) {
				// Leftclick
				// RuleHandlerLeftClick.computetile(tmptile);
			} else if (event.isSecondaryButtonDown()) {
				// Rightclick
				// RuleHandlerRightClick.computetile(tmptile);
			}
		});
	}

	/**
	 * @author Martin
	 * @param field
	 *            Iterates through the field and prints it relative to the size
	 *            of the canvas.
	 */
	public void printArray(Tile[][] field) {
		for (int i = 0; i < field.length; i++)
			for (int j = 0; j < field.length; j++)
				gc.drawImage(image, field[j][i].getX() * unit, field[j][i].getY() * unit, unit, unit);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
