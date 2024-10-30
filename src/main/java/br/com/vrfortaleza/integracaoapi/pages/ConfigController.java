package br.com.vrfortaleza.integracaoapi.pages;

import br.com.vrfortaleza.integracaoapi.config.Log;
import br.com.vrfortaleza.integracaoapi.interfaces.exportacao.FortesDAO;
import br.com.vrfortaleza.integracaoapi.pages.components.controllers.menu.CustomToolBar;
import br.com.vrfortaleza.integracaoapi.pages.components.controllers.tables.LojaConfigTable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class ConfigController implements Initializable {
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab lojaTab;
    @FXML
    private Tab invetarioTab;
    @FXML
    private CustomToolBar customToolBar;
    @FXML
    private LojaConfigTable lojaConfigTable;
    private FortesDAO fortesDAO;

    private void setTable() {
        fortesDAO = new FortesDAO();
//      configura botão de salvar
        customToolBar.getSalvarButton().setOnAction(event -> {
            try {
                fortesDAO.salvarParametrosConfiguracao(lojaConfigTable.getFortesConfiguracao());
            } catch (IOException e) {
                Log.error(this.getClass(), e.getMessage());
            }
        });
        Set<Integer> numberOfLojas = new HashSet<>();
        numberOfLojas.add(1);
        var lojas = fortesDAO.carregarParametrosConfiguracao(numberOfLojas);
        lojaConfigTable.loadLojas(lojas);
        customToolBar.getCriarButton().setOnAction(e -> lojaConfigTable.adicionarLoja());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setMinWidth(450);

        lojaTab.setGraphic(new FontIcon(Feather.SHOPPING_BAG));
        invetarioTab.setGraphic(new FontIcon(Feather.BOX));
        setTable();
    }
}
