package br.com.vrfortaleza.integracaoapi.pages;

import br.com.vrfortaleza.integracaoapi.pages.components.controllers.MainMenu;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private final String MAIN_BODY_FXML = "/br/com/vrfortaleza/integracaoapi/pages/main-body.fxml";
    private final String CONFIG_VIEW_FXML = "/br/com/vrfortaleza/integracaoapi/pages/config-view.fxml";

    @FXML
    private Pane contentPane;
    @FXML
    private ButtonBar buttonBar;
    @FXML
    private Button homeButton;
    @FXML
    private MainMenu mainMenu;

    @FXML
    protected void loadMainBody() {
        if (!isCurrentLoader(MAIN_BODY_FXML)) {
            loadFXML(MAIN_BODY_FXML);
            buttonBar.setVisible(false);
        }
    }

    @FXML
    public void loadConfigBody() {
        if (!isCurrentLoader(CONFIG_VIEW_FXML)) {
            loadFXML(CONFIG_VIEW_FXML);
            buttonBar.setVisible(true);
        }
    }

    private boolean isCurrentLoader(String fxmlPath) {
        if (contentPane.getChildren().isEmpty()) {
            return false;
        }
        Node currentNode = contentPane.getChildren().get(0);
        FXMLLoader currentLoader = (FXMLLoader) currentNode.getUserData();
        return currentLoader != null && fxmlPath.equals(currentLoader.getLocation().toString());
    }

    private void loadFXML(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Node newLoadedPane = loader.load();
            newLoadedPane.setUserData(loader);
            contentPane.getChildren().clear();
            contentPane.getChildren().add(newLoadedPane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MenuItem exportarMenu = mainMenu.findMenuItemByName(mainMenu.findMenuByName("_Sistema"), "_Exportar");
        MenuItem configuracoesMenu = mainMenu.findMenuItemByName(mainMenu.findMenuByName("_Sistema"), "Configuração");
        MenuItem sairMenu = mainMenu.findMenuItemByName(mainMenu.findMenuByName("_Sistema"), "Sair");
        configuracoesMenu.setOnAction(event -> loadConfigBody());
        sairMenu.setOnAction(event -> Platform.exit());
        loadMainBody();
        homeButton.setGraphic(new FontIcon(Feather.SKIP_BACK));
        homeButton.getStyleClass().addAll("button_icon", "accent");

    }
}