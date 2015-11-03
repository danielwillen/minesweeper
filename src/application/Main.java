
package application;

import application.GameHandler.GameState;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.input.InputMethodRequests;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Main extends Application {

	private int unit, canvasX, canvasY, sceneX, sceneY, tilesInX, tilesInY;
	private GraphicsContext gc;

	Button button = new Button("New Game");
	private Field field;
	private GameHandler gameHandler;
	private Image[] imageArray;
	private long timeStart;
	private long timePassed;

	@Override
	public void init() {
		this.tilesInX = 10;
		this.tilesInY = 10;
		this.unit = 25;
		this.sceneX = unit * tilesInX + 50;
		this.sceneY = unit * tilesInY + 50;
		this.canvasX = unit * tilesInX;
		this.canvasY = unit * tilesInY;
		this.imageArray = new Image[12];
	}

	@Override
	public void start(Stage primaryStage) {
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, sceneX, sceneY);
		Canvas canvas = new Canvas(canvasX, canvasY);
		gc = canvas.getGraphicsContext2D();

		MenuBar mb = new MenuBar();
		Menu file = new Menu("File");
		Menu options = new Menu("Options");

		MenuItem exit = new MenuItem("Exit");
		MenuItem save = new MenuItem("Save");
		MenuItem open = new MenuItem("Open");
		MenuItem newgame = new MenuItem("New Game");
		MenuItem option = new MenuItem("Options");

		file.getItems().addAll(newgame, new SeparatorMenuItem(), option, save, open, new SeparatorMenuItem(), exit);
		mb.getMenus().addAll(file, options);

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
		field.mineLayer(field.getMines());
		field.setFieldNeighbours();
		printArray(field.getTileArray());
		
		timeStart = System.currentTimeMillis();

		newgame.setOnAction(event->{
			start(primaryStage);
		});

//			@Override
//			public void handle(ActionEvent event) {
//			}
			
		
		option.setOnAction(event-> {
			optionsWindow(primaryStage);
		});
		
		save.setOnAction(event-> {
			//insert functionality here
		});

		save.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);
            fileChooser.showSaveDialog(primaryStage);
		});

		open.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
            fileChooser.showOpenDialog(primaryStage);
            
            
            
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
				endText();
			}
		});

	}
	
	private void optionsWindow(Stage primaryStage){
		Stage optionsStage = new Stage();
		BorderPane root = new BorderPane();
		GridPane gPane = new GridPane();
		HBox hbox = new HBox();
		Scene scene = new Scene(root, 300, 150);
		optionsStage.setResizable(false);
		
		Label label = new Label("Custom Field");
		Label label2 = new Label("Width");
		Label label3 = new Label("Height");
		TextField textFieldX = new TextField(String.valueOf(field.getWidth()));
		TextField textFieldY = new TextField(String.valueOf(field.getHeight()));
		
		Button acceptBtn = new Button("OK");
		Button cancelBtn = new Button("Cancel");
		
		root.setPadding(new Insets(10,10,10,10));;
		root.setTop(label);
		root.setCenter(gPane);
		root.setBottom(hbox);
		
		acceptBtn.setPrefWidth(75);
		cancelBtn.setPrefWidth(75);
		
		textFieldX.setPrefWidth(40);
		textFieldY.setPrefWidth(40);
		
		gPane.setAlignment(Pos.CENTER_LEFT);
		gPane.setHgap(10);
		gPane.setVgap(10);
		gPane.add(label2, 1, 0);
		gPane.add(textFieldX, 2, 0);
		gPane.add(label3, 1, 1);
		gPane.add(textFieldY, 2, 1);
		
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		optionsStage.initModality(Modality.WINDOW_MODAL);
		optionsStage.initOwner(primaryStage);
		optionsStage.setScene(scene);
		optionsStage.setTitle("Options");
		optionsStage.show();
		
		hbox.getChildren().addAll(acceptBtn,cancelBtn);
		hbox.setAlignment(Pos.BASELINE_RIGHT);
		
		acceptBtn.setOnAction(event->{
			field.setWidth(Integer.parseInt(textFieldX.getText()));
			field.setHeight(Integer.parseInt(textFieldY.getText()));
			start(primaryStage);
		});

		cancelBtn.setOnAction(event->{
			field.setHeight(Integer.parseInt(textFieldX.getText()));
			
		});
	}

	public void endText() {
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);
		gc.setFont(new Font("Impact", 72));
		if (gameHandler.getGameState() == GameState.GAMEWON) {
			gc.setFill(Color.GREEN);
			gc.fillText("You won", canvasX / 2, canvasY / 2);
			
			timePassed = System.currentTimeMillis() - timeStart;	//detta är spelarens poäng
		} else if (gameHandler.getGameState() == GameState.GAMELOST) {
			gc.setFill(Color.RED);
			gc.fillText("You lost", canvasX / 2, canvasY / 2);
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
