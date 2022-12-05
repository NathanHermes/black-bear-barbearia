package br.ifsp.edu.blackbearbarbearia.application.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class WindowLoader extends Application {
    private static Scene scene;
    private static Object controller;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("Login"));
        stage.getIcons().add(new Image(Objects.requireNonNull(WindowLoader.class.getResource("image/barber.png")).openStream()));
        stage.setTitle("Black Bear");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent parent = fxmlLoader.load(Objects.requireNonNull(WindowLoader.class.getResource(fxml + ".fxml")).openStream());
        parent.getStylesheets().add(Objects.requireNonNull(WindowLoader.class.getResource( "css/"+ fxml +".css")).toExternalForm());
        controller = fxmlLoader.getController();
        return parent;
    }

    public static void main(String[] args) {
        launch();
    }

    public static Object getController() {
        return controller;
    }
}