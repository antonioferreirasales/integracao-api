package br.com.vrfortaleza.integracaoapi.vo;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FortesConfiguracaoLojaVO {
    public FortesConfiguracaoLojaVO(int idLoja, String loja, String token, String codigoEmpresa, String codigoEstabelecimento, int codigoIncidencia, int aliquotasEspecificas) {
        this.idLoja = idLoja;
        this.loja = loja;
        this.token = token;
        this.codigoEmpresa = codigoEmpresa;
        this.codigoEstabelecimento = codigoEstabelecimento;
        this.codigoIncidencia = codigoIncidencia;
        this.aliquotasEspecificas = aliquotasEspecificas;
    }

    public FortesConfiguracaoLojaVO() {
    }

    public int idLoja = 0;

    public String loja = "";

    public String token = "";

    public String codigoEmpresa = "";

    public String codigoEstabelecimento = "";

    public int codigoIncidencia = 0;

    public int aliquotasEspecificas = 0;

    public ObservableValue<Integer> idLojaProperty() {
        return new SimpleIntegerProperty(idLoja).asObject();
    }

    public ObservableValue<String> lojaProperty() {
        return new SimpleStringProperty(loja);
    }

    public ObservableValue<String> tokenProperty() {
        return new SimpleStringProperty(token);
    }

    public ObservableValue<String> codigoEmpresaProperty() {
        return new SimpleStringProperty(codigoEmpresa);
    }

    public ObservableValue<String> codigoEstabelecimentoProperty() {
        return new SimpleStringProperty(codigoEstabelecimento);
    }

    public ObservableValue<Integer> codigoIncidenciaProperty() {
        return new SimpleIntegerProperty(codigoIncidencia).asObject();
    }

    public ObservableValue<Integer> aliquotasEspecificasProperty() {
        return new SimpleIntegerProperty(aliquotasEspecificas).asObject();
    }
}
