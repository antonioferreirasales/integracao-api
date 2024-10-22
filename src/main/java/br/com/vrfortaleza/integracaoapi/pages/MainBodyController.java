package br.com.vrfortaleza.integracaoapi.pages;

import br.com.vrfortaleza.integracaoapi.config.AppProperties;
import br.com.vrfortaleza.integracaoapi.vo.ExportarFortesVO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import org.controlsfx.control.CheckComboBox;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class MainBodyController {
    @FXML
    public DatePicker dataFinal;
    @FXML
    public DatePicker dataInicio;
    @FXML
    private VBox vBoxPane;
    @FXML
    private CheckComboBox<String> selecionarCampos;
    @FXML
    private Button selecionarArquivoButton;
    @FXML
    private TextField destinoTextField;
    private ExportarFortesVO exportarFortes;

    public void instanciarExportarFortesVO( ExportarFortesVO exportarFortesObj ) {
        exportarFortes = exportarFortesObj;
    }

    public void setDestinoTextField() {
        destinoTextField.setText(exportarFortes.caminho);
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
