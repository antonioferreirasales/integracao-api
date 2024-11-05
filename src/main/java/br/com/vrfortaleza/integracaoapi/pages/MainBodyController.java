package br.com.vrfortaleza.integracaoapi.pages;

import atlantafx.base.theme.Styles;
import br.com.vrfortaleza.integracaoapi.config.AppProperties;
import br.com.vrfortaleza.integracaoapi.config.Log;
import br.com.vrfortaleza.integracaoapi.interfaces.exportacao.FortesDAO;
import br.com.vrfortaleza.integracaoapi.vo.ExportarFortesVO;
import br.com.vrfortaleza.integracaoapi.vo.FortesConfiguracaoVO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import org.controlsfx.control.CheckComboBox;

import java.io.File;
import java.io.IOException;

public class MainBodyController {
    @FXML
    public DatePicker dataFinal;
    @FXML
    public DatePicker dataInicio;
    @FXML
    private VBox vBoxPane;
    @FXML
    private ComboBox<String> tipoDataComboBox;
    @FXML
    private ComboBox<String> lojaComboBox;
    @FXML
    private CheckComboBox<String> registrosComboBox;
    @FXML
    private Label layoutLabel;
    @FXML
    private ComboBox<String> layoutComboBox;
    @FXML
    private TextField destinoTextField;
    @FXML
    private Button selecionarArquivoButton;
    private ExportarFortesVO exportarFortes;

    public void instanciarExportarFortesVO(ExportarFortesVO exportarFortesObj) {
        exportarFortes = exportarFortesObj;
    }

    public void setDestinoTextField() {
        destinoTextField.setText(exportarFortes.caminho);
    }
// Carrega ComboBoxes
    public void carregarTipoDataComboBox() {
        try {
            tipoDataComboBox.getItems().add(0, "EMISSAO");
            tipoDataComboBox.getItems().add(1, "ENTRADA");
            tipoDataComboBox.getSelectionModel().select(1);
        } catch (Exception e) {
            Log.error(this.getClass(), e.getMessage());
        }
    }

    public void carregarLojaComboBox() {
        try {
            var fortesDAO = new FortesDAO();
            FortesConfiguracaoVO configuracaoLoja = fortesDAO.carregarConfiguracaoLoja();
            for (var loja : configuracaoLoja.vLoja) {
                lojaComboBox.getItems().add("Loja " + loja.idLoja);
            }
            lojaComboBox.getSelectionModel().selectFirst();
        } catch (Exception e) {
            Log.error(this.getClass(), e.getMessage());
        }
    }

    public void carregarRegistrosComboBox() {
        try {
            registrosComboBox.getItems().add(0, "PRODUTO");
            registrosComboBox.getItems().add(1, "PARTICIPANTES");
            registrosComboBox.getItems().add(2, "NOTA ENTRADA");
            registrosComboBox.getItems().add(3, "NOTA SAIDA");
            registrosComboBox.getItems().add(4, "CUPOM FISCAL ELETRONICO");
            registrosComboBox.getItems().add(5, "INSTR. PAGAMENTO ELETRONICO");
            registrosComboBox.getItems().add(6, "CARTAO CREDITO/DEBITO");
            registrosComboBox.getItems().add(7, "INVENTARIO");
            registrosComboBox.getItems().add(8, "ESTOQUE ESCRITURADO");
            registrosComboBox.getCheckModel().checkAll();
            registrosComboBox.getCheckModel().toggleCheckState(7);
            registrosComboBox.getCheckModel().toggleCheckState(8);
        } catch (Exception e) {
            Log.error(this.getClass(), e.getMessage());
        }
    }

    public void carregarLayoutComboBox() {
        try {
            layoutComboBox.getItems().add(0, "FISCAL 175");
            layoutComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
                boolean newValueNotNull = newValue != null;
                layoutComboBox.pseudoClassStateChanged(Styles.STATE_SUCCESS, newValueNotNull);
                layoutLabel.getStyleClass().addAll(Styles.TEXT, Styles.SUCCESS);
                layoutLabel.setVisible(newValueNotNull);
            });
        } catch (Exception e) {
            Log.error(this.getClass(), e.getMessage());
        }
    }

    @FXML
    private void selecionarArquivo() throws IOException {
        var currentScene = vBoxPane.getScene().getWindow();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        String initialDirectory = destinoTextField.getText().isBlank() ? "C:\\" : destinoTextField.getText();
        directoryChooser.setInitialDirectory(new File(initialDirectory));
        var selectedDirectory = directoryChooser.showDialog(currentScene);
        if (selectedDirectory != null) {
            AppProperties.setProperty("exportar.diretorio", selectedDirectory.getAbsolutePath());
            exportarFortes.caminho = selectedDirectory.getAbsolutePath();
            setDestinoTextField();
        }
    }

    @FXML
    private void setDataInicial() throws IOException {
        exportarFortes.dataInicio = dataInicio.getValue().toString();
        AppProperties.setProperty("exportar.dataInicio", exportarFortes.dataInicio);
    }

    @FXML
    private void setDataFinal() throws IOException {
        exportarFortes.dataTermino = dataFinal.getValue().toString();
        AppProperties.setProperty("exportar.dataTermino", exportarFortes.dataTermino);
    }

    @FXML
    private void buttonClicked() {
        System.out.println("Exportar");
    }
}
