package br.com.vrfortaleza.integracaoapi.pages;

import br.com.vrfortaleza.integracaoapi.config.AppProperties;
import br.com.vrfortaleza.integracaoapi.config.Log;
import br.com.vrfortaleza.integracaoapi.pages.components.controllers.MainMenu;
import br.com.vrfortaleza.integracaoapi.util.SistemaOperacional;
import br.com.vrfortaleza.integracaoapi.vo.ExportarFortesVO;
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
import java.time.LocalDate;
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
    private ExportarFortesVO exportarFortes;

    private void loadInitialConfig() {
        exportarFortes = new ExportarFortesVO();
        exportarFortes.idLoja = AppProperties.getInt("loja.numero");
        exportarFortes.caminho = AppProperties.getString("exportar.diretorio");
        exportarFortes.tipoData = AppProperties.getInt("exportar.tipoData");
        exportarFortes.dataInicio = AppProperties.getString("exportar.dataInicio");
        exportarFortes.dataTermino = AppProperties.getString("exportar.dataTermino");
        exportarFortes.participantes = AppProperties.getBoolean("exportar.participantes");
        exportarFortes.produto = AppProperties.getBoolean("exportar.produto");
        exportarFortes.notaServico = AppProperties.getBoolean("exportar.notaServico");
        exportarFortes.notaEntrada = AppProperties.getBoolean("exportar.notaEntrada");
        exportarFortes.notaSaida = AppProperties.getBoolean("exportar.notaSaida");
        exportarFortes.cupomFiscal = AppProperties.getBoolean("exportar.cupomFiscal");
        exportarFortes.cupomFiscalEletronico = AppProperties.getBoolean("exportar.cupomFiscalEletronico");
        exportarFortes.conhecimentoTransporteCarga = AppProperties.getBoolean("exportar.conhecimentoTransporteCarga");
        exportarFortes.inventario = AppProperties.getBoolean("exportar.inventario");
        exportarFortes.operacaoCreditoDebito = AppProperties.getBoolean("exportar.operacaoCreditoDebito");
        exportarFortes.notaFiscalMercadoriaOutrosValores = AppProperties.getBoolean("exportar.notaFiscalMercadoriaOutrosValores");
        exportarFortes.outrosValoresDocumento = AppProperties.getBoolean("exportar.outrosValoresDocumento");
        exportarFortes.estoqueEscriturado = AppProperties.getBoolean("exportar.estoqueEscriturado");
        exportarFortes.instrPagamentoEletronico = AppProperties.getBoolean("exportar.instrPagamentoEletronico");
    }

    @FXML
    protected void loadMainBody() {
        if (!isCurrentLoader(MAIN_BODY_FXML)) {
            var loader = loadFXML(MAIN_BODY_FXML);
            buttonBar.setVisible(false);
            MainBodyController configController = loader.getController();
            configController.instanciarExportarFortesVO(exportarFortes);
            configController.setDestinoTextField();
            configController.dataInicio.setValue(LocalDate.parse(exportarFortes.dataInicio));
            configController.dataFinal.setValue(LocalDate.parse(exportarFortes.dataTermino));
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

    private FXMLLoader loadFXML(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Node newLoadedPane = loader.load();
            newLoadedPane.setUserData(loader);
            contentPane.getChildren().clear();
            contentPane.getChildren().add(newLoadedPane);
            return loader;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Log.LEVEL = AppProperties.getString("log.nivel");
        Log.setLogLevel();
        loadInitialConfig();
        MenuItem exportarMenu = mainMenu.findMenuItemByName(mainMenu.findMenuByName("_Sistema"), "_Exportar");
        MenuItem configuracoesMenu = mainMenu.findMenuItemByName(mainMenu.findMenuByName("_Sistema"), "Configuração");
        MenuItem sairMenu = mainMenu.findMenuItemByName(mainMenu.findMenuByName("_Sistema"), "Sair");
        configuracoesMenu.setOnAction(event -> loadConfigBody());
        sairMenu.setOnAction(event -> Platform.exit());
        homeButton.setGraphic(new FontIcon(Feather.SKIP_BACK));
        homeButton.getStyleClass().addAll("button_icon", "accent");
        loadMainBody();
        Log.info(this.getClass(),"Sistema Operacional: { " + SistemaOperacional.get() + " }");
        Log.trace(this.getClass(),"Inicializando aplicação");
    }
}