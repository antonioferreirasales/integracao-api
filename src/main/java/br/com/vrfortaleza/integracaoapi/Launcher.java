package br.com.vrfortaleza.integracaoapi;

import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loadMainPage = new FXMLLoader(Launcher.class.getResource("pages/main-view.fxml"));
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        Scene scene = new Scene(loadMainPage.load(), 700, 400);
        stage.setResizable(false);
        stage.setTitle("Integração API");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}