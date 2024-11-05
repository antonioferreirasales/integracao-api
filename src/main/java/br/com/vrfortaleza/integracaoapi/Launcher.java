package br.com.vrfortaleza.integracaoapi;

import atlantafx.base.theme.PrimerLight;
import br.com.vrfortaleza.integracaoapi.database.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Locale.setDefault(new Locale("pt", "BR"));
        FXMLLoader loadMainPage = new FXMLLoader(Launcher.class.getResource("pages/main-view.fxml"));
        var appIcon = new Image(Objects.requireNonNull(Launcher.class.getResourceAsStream("images/icons/fiscal-service.png")));
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        Scene scene = new Scene(loadMainPage.load(), 700, 450);
        stage.setResizable(false);
        stage.setTitle("Integração API");
        stage.getIcons().add(appIcon);
        stage.setScene(scene);
        stage.show();
        var dbService = new Service();
        dbService.create();
    }

    public static void main(String[] args) {
        launch();
    }
}