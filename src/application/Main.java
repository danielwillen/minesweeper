package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {

		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 400, 400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();

		Canvas canvas = new Canvas();
		ScrollPane sPane = new ScrollPane();
		boolean spawn;

		Button button = new Button("New Game");
		HBox box = new HBox();
		box.setPadding(new Insets(10, 10, 10, 10));
		box.getChildren().add(button);

		sPane.setContent(canvas);
		sPane.setVbarPolicy(ScrollBarPolicy.NEVER);
		sPane.setHbarPolicy(ScrollBarPolicy.NEVER);

		root.setTop(box);
		root.setCenter(sPane);

		button.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {

			}
		});

		sPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
			}
		});

	}

	public static void main(String[] args) {
		launch(args);
	}
}
