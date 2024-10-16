package br.com.vrfortaleza.integracaoapi.pages;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.ResourceBundle;

public class ConfigController implements Initializable {
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab lojaTab;
    @FXML
    private Tab invetarioTab;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setMinWidth(450);

        lojaTab.setGraphic(new FontIcon(Feather.SHOPPING_BAG));
        invetarioTab.setGraphic(new FontIcon(Feather.BOX));
    }
}
