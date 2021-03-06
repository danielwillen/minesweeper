
package application;


import javafx.application.Application;
import javafx.stage.Stage;

import application.GameHandler.GameState;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.stage.Modality;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Main extends Application {

	private int unit, tilesInX, tilesInY, mines;
	private GraphicsContext gc;
	private HighScore highscore = new HighScore();
	private Field field;
	private GameHandler gameHandler;
	private Image[] imageArray;
	private long timeStart;
	private long timePassed;
	// private MediaPlayer mediaPlayer;

	@Override
	public void init() {
		this.tilesInX = 9;
		this.tilesInY = 9;
		this.mines = 10;
		this.unit = 25;
		this.imageArray = new Image[12];
		// Media media = new Media(new File("src/Sounds/Main
		// Theme.mp3").toURI().toString());
		// this.mediaPlayer = new MediaPlayer(media);
	}

	@Override
	public void start(Stage primaryStage) {
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, (unit * tilesInX) + 50, (unit * tilesInY) + 50);
		Canvas canvas = new Canvas(unit * tilesInX, unit * tilesInY);
		gc = canvas.getGraphicsContext2D();

		MenuBar mb = new MenuBar();
		Menu file = new Menu("File");
		Menu newgame = new Menu("New Game");

		MenuItem exit = new MenuItem("Exit");
		MenuItem option = new MenuItem("Options");
		MenuItem easy = new MenuItem("Easy");
		MenuItem medium = new MenuItem("Medium");
		MenuItem hard = new MenuItem("Hard");

		// if(!mediaPlayer.getStatus().equals(Status.PLAYING))
		// mediaPlayer.play();

		newgame.getItems().addAll(easy, medium, hard);
		file.getItems().addAll(newgame, new SeparatorMenuItem(), option, new SeparatorMenuItem(), exit);
		mb.getMenus().addAll(file);

		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		root.setTop(mb);
		root.setCenter(canvas);

		primaryStage.setScene(scene);
		primaryStage.setTitle("Minesweeper");
		primaryStage.show();

		fetchImages();
		field = new Field();
		gameHandler = new GameHandler(field);
		field.newBlankField(tilesInX, tilesInY);
		field.mineLayer(mines);
		field.setFieldNeighbours();
		printArray(field.getTileArray());

		timeStart = System.currentTimeMillis();

		newgame.setOnAction(event -> {
			if (event.getTarget().equals(easy)) {
				tilesInX = 9;
				tilesInY = 9;
				mines = 10;
			} else if (event.getTarget().equals(medium)) {
				tilesInX = 16;
				tilesInY = 16;
				mines = 40;
			} else if (event.getTarget().equals(hard)) {
				tilesInX = 30;
				tilesInY = 16;
				mines = 99;
			}
			file.hide();
			newgame.hide();
			restartGame(primaryStage);
		});

		option.setOnAction(event -> {
			optionsWindow(primaryStage);
		});

		exit.setOnAction(event -> {
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
				endText(primaryStage);
			}
		});

	}

	private void restartGame(Stage stage) {
		start(stage);
	}

	private void optionsWindow(Stage primaryStage) {
		Stage optionsStage = new Stage();
		BorderPane root = new BorderPane();
		GridPane gPane = new GridPane();
		HBox hbox = new HBox();
		Scene scene = new Scene(root, 300, 150);

		Label label = new Label("Custom Minefield");
		Label label2 = new Label("Width");
		Label label3 = new Label("Height");
		Label label4 = new Label("Mines");
		Label warningLabel = new Label();

		TextField textFieldX = new TextField(String.valueOf(field.getWidth()));
		TextField textFieldY = new TextField(String.valueOf(field.getHeight()));
		TextField textFieldMines = new TextField(String.valueOf(field.getMines()));

		Button acceptBtn = new Button("New Game");
		Button cancelBtn = new Button("Cancel");

		optionsStage.setResizable(false);

		root.setPadding(new Insets(10, 10, 10, 10));
		root.setTop(label);
		root.setCenter(gPane);
		root.setBottom(hbox);

		warningLabel.setTextFill(Color.RED);
		acceptBtn.setPrefWidth(75);
		cancelBtn.setPrefWidth(75);
		textFieldX.setPrefWidth(40);
		textFieldY.setPrefWidth(40);
		textFieldMines.setPrefWidth(40);

		gPane.setAlignment(Pos.CENTER_LEFT);
		gPane.setHgap(10);
		gPane.setVgap(10);
		gPane.add(label2, 1, 0);
		gPane.add(textFieldX, 2, 0);
		gPane.add(label3, 1, 1);
		gPane.add(textFieldY, 2, 1);
		gPane.add(label4, 1, 2);
		gPane.add(textFieldMines, 2, 2);

		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		optionsStage.initModality(Modality.WINDOW_MODAL);
		optionsStage.initOwner(primaryStage);
		optionsStage.setScene(scene);
		optionsStage.setTitle("Options");
		optionsStage.show();

		hbox.setSpacing(10);
		hbox.getChildren().addAll(warningLabel, acceptBtn, cancelBtn);
		hbox.setAlignment(Pos.BASELINE_RIGHT);

		acceptBtn.setOnAction(event -> {
			try {
				int tmpTilesX, tmpTilesY, tmpMineAmount;
				tmpTilesX = (Integer.parseInt(textFieldX.getText()));
				tmpTilesY = (Integer.parseInt(textFieldY.getText()));
				tmpMineAmount = (Integer.parseInt(textFieldMines.getText()));
				if (tmpMineAmount <= (tmpTilesX * tmpTilesY) * 0.85) {
					tilesInX = (Integer.parseInt(textFieldX.getText()));
					tilesInY = (Integer.parseInt(textFieldY.getText()));
					mines = (Integer.parseInt(textFieldMines.getText()));
					optionsStage.close();
					restartGame(primaryStage);
				} else {
					warningLabel.setText("Too many mines");
				}
			} catch (IllegalArgumentException e) {
				warningLabel.setText("Invalid input");
			}
		});

		cancelBtn.setOnAction(event -> {
			optionsStage.close();
		});
	}

	public void endText(Stage primaryStage) {
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);
		gc.setFont(new Font("Impact", 65));
		if (gameHandler.getGameState() == GameState.GAMEWON) {
			gc.setFill(Color.GREEN);
			gc.fillText("You won", (unit * tilesInX) / 2, (unit * tilesInY) / 2);

			timePassed = System.currentTimeMillis() - timeStart; // detta anv�nds till
																	// spelarens
																	// po�ng
			highScoreWindow(primaryStage);
		} else if (gameHandler.getGameState() == GameState.GAMELOST) {
			gc.setFill(Color.RED);
			gc.fillText("You lost", (unit * tilesInX) / 2, (unit * tilesInY) / 2);
			highScoreWindow(primaryStage);
		}
	}

	private void highScoreWindow(Stage primaryStage) {
		Stage highScoreStage = new Stage();
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 300, 150);
		Label label = new Label();
		highScoreStage.setX(primaryStage.getX() + 300);
		highScoreStage.setY(primaryStage.getY() + 50);

		if (gameHandler.getGameState() == GameState.GAMEWON) {
			HBox hb = new HBox();
			Label name = new Label("Enter name:");
			TextField tf = new TextField();

			hb.getChildren().addAll(name, tf);
			root.setBottom(hb);

			tf.setOnAction(event -> {
				String score = highscore.calculateHighScore(field, timePassed) + ":" + tf.getText();
				highscore.writeToHighScore(score);
				label.setText(highscore.readHighScore());
				hb.getChildren().clear();
			});
		}

		label.setText(highscore.readHighScore());

		root.setCenter(label);
		label.setAlignment(Pos.CENTER);

		highScoreStage.setResizable(false);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		highScoreStage.initModality(Modality.WINDOW_MODAL);
		highScoreStage.initOwner(primaryStage);
		highScoreStage.setScene(scene);
		highScoreStage.setTitle("High score");
		highScoreStage.show();
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
		gc.clearRect(0, 0, unit * tilesInX, unit * tilesInY);
		for (int i = 0; i < field.getHeight(); i++)
			for (int j = 0; j < field.getWidth(); j++) {
				gc.drawImage(imageArray[fieldArray[j][i].getImage()], fieldArray[j][i].getX() * unit,
						fieldArray[j][i].getY() * unit, unit, unit);

			}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
