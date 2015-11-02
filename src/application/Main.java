
package application;

import application.GameHandler.GameState;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Main extends Application {

	private int unit, canvasX, canvasY, sceneX, sceneY, tilesInX, tilesInY;
	private GraphicsContext gc;
	private Field field;
	private GameHandler gameHandler;
	private Image[] imageArray;

	@Override
	public void init() {
		this.tilesInX = 10;
		this.tilesInY = 10;
		this.unit = 25;
		this.sceneX = unit * tilesInX + 10;
		this.sceneY = unit * tilesInY + 30;
		this.canvasX = unit * tilesInX;
		this.canvasY = unit * tilesInY;
		this.imageArray = new Image[12];
	}

	@Override
	public void start(Stage primaryStage) {
		VBox root = new VBox();
		Scene scene = new Scene(root, sceneX, sceneY);
		Canvas canvas = new Canvas(canvasX, canvasY);
		gc = canvas.getGraphicsContext2D();
		Button button = new Button("New Game");

		MenuBar mb = new MenuBar();
		Menu file = new Menu("File");
		Menu options = new Menu("Options");

		MenuItem exit = new MenuItem("Exit");
		MenuItem save = new MenuItem("Save");
		MenuItem open = new MenuItem("Open");

		file.getItems().addAll(save, open, new SeparatorMenuItem(), exit);
		mb.getMenus().addAll(file, options);
		
		root.getChildren().addAll(mb,canvas);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		root.getChildren().add(button);
		root.setAlignment(Pos.CENTER);
		button.setAlignment(Pos.TOP_RIGHT);

		primaryStage.setScene(scene);
		primaryStage.setTitle("Minesweeper");
		primaryStage.show();

		fetchImages();
		field = new Field();
		gameHandler = new GameHandler(field);
		field.newBlankField(tilesInX, tilesInY);
		field.mineLayer(field.getMines());
		field.setFieldNeighbours();
		printArray(field.getTileArray());
		
		save.setOnAction(event-> {
			//insert functionality here
		});

		open.setOnAction(event-> {
			//insert functionality here
		});

		exit.setOnAction(event-> {
			Platform.exit();
		});

		canvas.setOnMousePressed(event -> {
			if (gameHandler.getGameState() == GameState.ONGOING) {
				if (event.isPrimaryButtonDown()) {
					gameHandler.onLeftClickPosition((int) event.getX() / unit, (int) event.getY() / unit);
				} else if (event.isSecondaryButtonDown()) {
					gameHandler.onRightClickPosition((int) event.getX() / unit, (int) event.getY() / unit);
				}
				printArray(field.getTileArray());
				endText();
			}
		});

		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				start(primaryStage);
			}

		});

	}

	public void endText() {
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);
		gc.setFont(new Font("Impact", 72));
		if(gameHandler.getGameState() == GameState.GAMEWON){
			gc.setFill(Color.GREEN);
			gc.fillText("You won", canvasX/2, canvasY/2);
		}
		else if(gameHandler.getGameState() == GameState.GAMELOST){
			gc.setFill(Color.RED);
			gc.fillText("You lost", canvasX/2, canvasY/2);
		}
			
	}

	private void fetchImages() {
		imageArray[0] = new Image("Image/0.jpg");
		imageArray[1] = new Image("Image/1.jpg");
		imageArray[2] = new Image("Image/2.jpg");
		imageArray[3] = new Image("Image/3.jpg");
		imageArray[4] = new Image("Image/4.jpg");
		imageArray[5] = new Image("Image/5.jpg");
		imageArray[6] = new Image("Image/6.jpg");
		imageArray[7] = new Image("Image/7.jpg");
		imageArray[8] = new Image("Image/8.jpg");
		imageArray[9] = new Image("Image/bomb.jpg");
		imageArray[10] = new Image("Image/flagged.jpg");
		imageArray[11] = new Image("Image/hidden.jpg");
	}

	public void printArray(Tile[][] fieldArray) {
		gc.clearRect(0, 0, canvasX, canvasY);
		for (int i = 0; i < field.getHeight(); i++)
			for (int j = 0; j < field.getWidth(); j++) {
				gameHandler.updateTileImage(field.getTileArray()[j][i]);
				gc.drawImage(imageArray[fieldArray[j][i].getImage()], fieldArray[j][i].getX() * unit,
						fieldArray[j][i].getY() * unit, unit, unit);

			}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
