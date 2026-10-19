package br.com.vrfortaleza.integracaoapi.pages;

import br.com.vrfortaleza.integracaoapi.config.Log;
import br.com.vrfortaleza.integracaoapi.config.ApiConfig;
import br.com.vrfortaleza.integracaoapi.database.Service;
import br.com.vrfortaleza.integracaoapi.pages.components.controllers.menu.CustomToolBar;
import br.com.vrfortaleza.integracaoapi.pages.components.controllers.tables.LojaConfigTable;
import br.com.vrfortaleza.integracaoapi.vo.FortesConfiguracaoLojaVO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.ResourceBundle;

public class ConfigController implements Initializable {
    @FXML
    private PasswordField configTokenField;
    @FXML
    private Label apiStatusLabel;

    @FXML
    private void salvarApi() {
        try {
            ApiConfig.saveConfigToken(configTokenField.getText());
            apiStatusLabel.setText("Configuração salva. Será usada nas próximas consultas.");
        } catch (IllegalArgumentException e) {
            apiStatusLabel.setText(e.getMessage());
        } catch (java.io.IOException e) {
            apiStatusLabel.setText("Não foi possível salvar. Verifique a permissão de escrita no diretório da aplicação.");
        }
    }
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
    private final Service dbService = new Service();

    private void setTable() {
//        Carrega as tabelas
        lojaConfigTable.loadLojas(dbService.selectLojas());
//        Configura botão de salvar
        customToolBar.getSalvarButton().setOnAction(event -> {
            try {
                dbService.updateLoja(lojaConfigTable.getFortesConfiguracao());
            } catch (Exception e) {
                System.err.println("Erro na inserção de dados: " + e.getMessage());
                Log.error(this.getClass(), e.getMessage());
            }
        });
//        Configura botão de criar
        customToolBar.getCriarButton().setOnAction(e -> {
            int lojaID = dbService.getMaxId() + 1;
            var novaLoja = new FortesConfiguracaoLojaVO(lojaID, "", "", "xxxxx", "000" + lojaID, 1, 0);
            dbService.insertLoja(novaLoja);
            lojaConfigTable.loadLojas(dbService.selectLojas());
        });
//        Configura botão de excluir
        customToolBar.getExcluirButton().setOnAction(e -> {
            var loja = lojaConfigTable.getSelectionModel().getSelectedItem();
            dbService.deleteLoja(loja.idLoja);
            lojaConfigTable.loadLojas(dbService.selectLojas());
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            configTokenField.setText(ApiConfig.loadConfigToken());
        } catch (java.io.IOException e) {
            apiStatusLabel.setText("Não foi possível carregar a configuração da API.");
        }
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setMinWidth(450);

        lojaTab.setGraphic(new FontIcon(Feather.SHOPPING_BAG));
        invetarioTab.setGraphic(new FontIcon(Feather.BOX));
        setTable();
    }
}
