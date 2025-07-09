package br.com.vrfortaleza.integracaoapi.pages.components.controllers.tables;

import atlantafx.base.theme.Styles;
import atlantafx.base.util.IntegerStringConverter;
import br.com.vrfortaleza.integracaoapi.vo.FortesConfiguracaoLojaVO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;

import java.util.Map;
import java.util.Set;

public class LojaConfigTable extends TableView<FortesConfiguracaoLojaVO> {
    private final Map<Integer, String> incidenciaOptions = Map.of(
            1, "Exclusivamente no regime não-cumulativo",
            2, "Exclusivamente no regime cumulativo",
            3, "Nos regimes cumulativo e não-cumulativo"
    );

    StringConverter<Integer> incidenciaConverter = new StringConverter<>() {
        @Override
        public String toString(Integer object) {
            return incidenciaOptions.getOrDefault(object, "Unknown");
        }

        @Override
        public Integer fromString(String string) {
            return incidenciaOptions.entrySet().stream()
                    .filter(entry -> entry.getValue().equals(string))
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElse(null); // Or throw exception, depending on your logic
        }
    };

    private final Map<Integer, String> aliquotasOptions = Map.of(
            0, "Não",
            1, "Sim"
    );

    StringConverter<Integer> aliquotasConverter = new StringConverter<>() {
        @Override
        public String toString(Integer object) {
            return aliquotasOptions.getOrDefault(object, "Unknown");
        }

        @Override
        public Integer fromString(String string) {
            return aliquotasOptions.entrySet().stream()
                    .filter(entry -> entry.getValue().equals(string))
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElse(null);
        }
    };

    private TableColumn<FortesConfiguracaoLojaVO, Integer> cLojaId = new TableColumn<>("ID");
    private TableColumn<FortesConfiguracaoLojaVO, String> cLojaNome = new TableColumn<>("Loja");
    private TableColumn<FortesConfiguracaoLojaVO, String> cToken = new TableColumn<>("Token");
    private TableColumn<FortesConfiguracaoLojaVO, String> cCodigoEmpresa = new TableColumn<>("Código Empresa");
    private TableColumn<FortesConfiguracaoLojaVO, String> cCodigoEstabelecimento = new TableColumn<>("Código Estabelecimento");
    private TableColumn<FortesConfiguracaoLojaVO, Integer> cCodigoIncidencia = new TableColumn<>("Incidência PIS/COFINS");
    private TableColumn<FortesConfiguracaoLojaVO, Integer> cAliquotasEspecificas = new TableColumn<>("Alíquotas Específicas");
    private String style = """
            -color-cell-bg-selected: -color-accent-emphasis;
            -color-cell-fg-selected: -color-fg-emphasis;
            -color-cell-bg-selected-focused: -color-accent-emphasis;
            -color-cell-fg-selected-focused: -color-fg-emphasis;""";

    private void configColumn() {
        cLojaId.setCellValueFactory(cellData -> cellData.getValue().idLojaProperty());
        cLojaId.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        cLojaId.setOnEditCommit(event -> event.getRowValue().setIdLoja(event.getNewValue()));
        cLojaId.setMaxWidth(30.0);
        cLojaId.setResizable(false);
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

        ObservableList<Integer> options = FXCollections.observableArrayList(1, 2, 3);
        cCodigoIncidencia.setCellValueFactory(cellData -> cellData.getValue().codigoIncidenciaProperty());
        cCodigoIncidencia.setCellFactory(ComboBoxTableCell.forTableColumn(incidenciaConverter, options));
        cCodigoIncidencia.setOnEditCommit(event -> event.getRowValue().setCodigoIncidencia(event.getNewValue()));

        ObservableList<Integer> aliquotasOptionsList = FXCollections.observableArrayList(0, 1);
        cAliquotasEspecificas.setCellValueFactory(cellData -> cellData.getValue().aliquotasEspecificasProperty());
        cAliquotasEspecificas.setCellFactory(ComboBoxTableCell.forTableColumn(aliquotasConverter, aliquotasOptionsList));
        cAliquotasEspecificas.setOnEditCommit(event -> event.getRowValue().setAliquotasEspecificas(event.getNewValue()));

        this.getColumns().setAll(cLojaId, cLojaNome, cToken, cCodigoEmpresa, cCodigoEstabelecimento, cCodigoIncidencia, cAliquotasEspecificas);
        this.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        this.getSelectionModel().selectFirst();
        this.setStyle(style);
        this.getStyleClass().addAll(Styles.BORDERED ,Styles.DENSE);
        this.setEditable(true);

        for (var column : this.getColumns()) {
            if (column != cLojaId) {
                column.setPrefWidth(200);
                column.setResizable(true);
            }
        }
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
