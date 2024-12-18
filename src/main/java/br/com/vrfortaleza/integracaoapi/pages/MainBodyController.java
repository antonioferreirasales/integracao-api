package br.com.vrfortaleza.integracaoapi.pages;

import br.com.vrfortaleza.integracaoapi.config.AppProperties;
import br.com.vrfortaleza.integracaoapi.config.Log;
import br.com.vrfortaleza.integracaoapi.controller.interfaces.exportacao.fortes.ExportacaoFortesController;
import br.com.vrfortaleza.integracaoapi.dao.interfaces.exportacao.fortes.FortesDAO;
import br.com.vrfortaleza.integracaoapi.vo.ExportarFortesVO;
import br.com.vrfortaleza.integracaoapi.vo.FortesConfiguracaoVO;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import org.controlsfx.control.CheckComboBox;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;

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
    @FXML
    private Button exportarButton;
    @FXML
    private ProgressBar progressBar;
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
                layoutLabel.setVisible(newValueNotNull);
            });
            layoutComboBox.getSelectionModel().selectFirst();
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
    public void exportar() throws Exception {
        exportarFortes.idLoja = Integer.parseInt(lojaComboBox.getSelectionModel().getSelectedItem().split(" ")[1]);
        exportarFortes.caminho = destinoTextField.getText();
        exportarFortes.tipoData = tipoDataComboBox.getSelectionModel().getSelectedIndex() + 1;
        exportarFortes.dataTermino = dataFinal.getValue().toString();
        exportarFortes.dataInicio = dataInicio.getValue().toString();
        exportarFortes.produto = registrosComboBox.getCheckModel().isChecked(0);
        exportarFortes.participantes = registrosComboBox.getCheckModel().isChecked(1);
        exportarFortes.notaEntrada = registrosComboBox.getCheckModel().isChecked(2);
        exportarFortes.notaSaida = registrosComboBox.getCheckModel().isChecked(3);
        exportarFortes.cupomFiscalEletronico = registrosComboBox.getCheckModel().isChecked(4);
        exportarFortes.instrPagamentoEletronico = registrosComboBox.getCheckModel().isChecked(5);
        exportarFortes.operacaoCreditoDebito = registrosComboBox.getCheckModel().isChecked(6);
        exportarFortes.inventario = registrosComboBox.getCheckModel().isChecked(7);
        exportarFortes.estoqueEscriturado = registrosComboBox.getCheckModel().isChecked(8);
        if (Integer.parseInt(layoutComboBox.getSelectionModel().getSelectedItem().split(" ")[1]) == 175) {
            exportarButton.setDisable(true);

            progressBar.setProgress(0);
            progressBar.setProgress(-1.0);
            progressBar.setVisible(true);
                // Cria uma Task para executar a função de fundo
            Task<Void> exportTask = getExportTask();

            new Thread(exportTask).start();
        } else {
            throw new InvalidParameterException("Layout inválido");
        }
    }

    private @NotNull Task<Void> getExportTask() {
        Task<Void> exportTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                new ExportacaoFortesController().exportar(exportarFortes);
                return null;
            }
        };

        exportTask.setOnSucceeded(event -> {
            // Completa a progressbar
            progressBar.setProgress(1.0);

            // Reativa a UI
            exportarButton.setDisable(false);

            // Mostra mensagem de sucesso
            var alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Exportação");
            alert.setHeaderText("Exportação de dados");
            alert.setContentText("Exportação realizada com sucesso no diretório: " + exportarFortes.caminho);
            alert.initOwner(exportarButton.getScene().getWindow());
            alert.showAndWait();
            Log.info(this.getClass(), "Exportação realizada com sucesso no diretório: " + exportarFortes.caminho);
        });

        exportTask.setOnFailed(event -> {
            // Zera o progresso e esconde a barra
            progressBar.setProgress(0.0);
            progressBar.setVisible(false);

            // Reativa a UI
            exportarButton.setDisable(false);

            // Mostra mensagem de falha.
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro na Exportação");
            alert.setHeaderText("Erro durante a exportação dos dados");
            alert.setContentText("Ocorreu um erro durante a exportação. Verifique os logs para mais detalhes.");
            alert.initOwner(exportarButton.getScene().getWindow());
            alert.showAndWait();
            Log.error(this.getClass(), "Erro durante a exportação: " + exportTask.getException());
        });
        return exportTask;
    }
}
