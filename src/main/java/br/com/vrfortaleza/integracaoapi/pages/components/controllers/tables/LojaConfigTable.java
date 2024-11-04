package br.com.vrfortaleza.integracaoapi.pages.components.controllers.tables;

import atlantafx.base.theme.Styles;
import atlantafx.base.util.IntegerStringConverter;
import br.com.vrfortaleza.integracaoapi.vo.FortesConfiguracaoLojaVO;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;

import java.util.Set;

public class LojaConfigTable extends TableView<FortesConfiguracaoLojaVO> {
    private TableColumn<FortesConfiguracaoLojaVO, Integer> cLojaId = new TableColumn<>("ID");
    private TableColumn<FortesConfiguracaoLojaVO, String> cLojaNome = new TableColumn<>("Loja");
    private TableColumn<FortesConfiguracaoLojaVO, String> cToken = new TableColumn<>("Token");
    private TableColumn<FortesConfiguracaoLojaVO, String> cCodigoEmpresa = new TableColumn<>("Código Empresa");
    private TableColumn<FortesConfiguracaoLojaVO, String> cCodigoEstabelecimento = new TableColumn<>("Código Estabelecimento");
    private TableColumn<FortesConfiguracaoLojaVO, Integer> cCodigoIncidencia = new TableColumn<>("Incidência PIS/COFINS");
    private TableColumn<FortesConfiguracaoLojaVO, Integer> cAliquotasEspecificas = new TableColumn<>("Alíquotas Específicas");

    private void configColumn() {
        cLojaId.setCellValueFactory(cellData -> cellData.getValue().idLojaProperty());
        cLojaId.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        cLojaId.setOnEditCommit(event -> event.getRowValue().setIdLoja(event.getNewValue()));
        cLojaId.setEditable(false);

        cLojaNome.setCellFactory(TextFieldTableCell.forTableColumn());
        cLojaNome.setCellValueFactory(cellData -> cellData.getValue().lojaProperty());
        cLojaNome.setOnEditCommit(event -> event.getRowValue().setLoja(event.getNewValue()));

        cToken.setCellValueFactory(cellData -> cellData.getValue().tokenProperty());
        cToken.setCellFactory(TextFieldTableCell.forTableColumn());
        cToken.setOnEditCommit(event -> event.getRowValue().setToken(event.getNewValue()));

        cCodigoEmpresa.setCellValueFactory(cellData -> cellData.getValue().codigoEmpresaProperty());
        cCodigoEmpresa.setCellFactory(TextFieldTableCell.forTableColumn());
        cCodigoEmpresa.setOnEditCommit(event -> event.getRowValue().setCodigoEmpresa(event.getNewValue()));

        cCodigoEstabelecimento.setCellValueFactory(cellData -> cellData.getValue().codigoEstabelecimentoProperty());
        cCodigoEstabelecimento.setCellFactory(TextFieldTableCell.forTableColumn());
        cCodigoEstabelecimento.setOnEditCommit(event -> event.getRowValue().setCodigoEstabelecimento(event.getNewValue()));

        cCodigoIncidencia.setCellValueFactory(cellData -> cellData.getValue().codigoIncidenciaProperty());
        cCodigoIncidencia.setCellFactory(ComboBoxTableCell.forTableColumn(1, 2, 3));
        cCodigoIncidencia.setOnEditCommit(event -> event.getRowValue().setCodigoIncidencia(event.getNewValue()));

        cAliquotasEspecificas.setCellValueFactory(cellData -> cellData.getValue().aliquotasEspecificasProperty());
        cAliquotasEspecificas.setCellFactory(ComboBoxTableCell.forTableColumn(0, 1));
        cAliquotasEspecificas.setOnEditCommit(event -> event.getRowValue().setAliquotasEspecificas(event.getNewValue()));

        this.getColumns().setAll(cLojaId, cLojaNome, cToken, cCodigoEmpresa, cCodigoEstabelecimento, cCodigoIncidencia, cAliquotasEspecificas);
        this.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        this.getSelectionModel().selectFirst();
        this.getStyleClass().add(Styles.DENSE);
        this.setEditable(true);
    }

    public FortesConfiguracaoLojaVO getFortesConfiguracao() {
        return this.getSelectionModel().getSelectedItem();
    }

    public void loadLojas(Set<FortesConfiguracaoLojaVO> lojas) {
        this.getItems().clear();
        for (var loja : lojas) {
            this.getItems().add(loja);
        }
    }

    @Deprecated
    public void adicionarLoja(FortesConfiguracaoLojaVO fortesConfiguracaoLojaVO) {
        var row = new FortesConfiguracaoLojaVO(1, "", "", "", "", 0, 0);
        this.getItems().add(fortesConfiguracaoLojaVO);
    }

    public LojaConfigTable() {
        super();
        configColumn();
    }
}
