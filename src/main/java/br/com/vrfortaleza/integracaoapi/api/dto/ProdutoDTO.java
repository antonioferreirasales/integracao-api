package br.com.vrfortaleza.integracaoapi.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Objects;

@Data
public class ProdutoDTO {
    private Integer id;
    private Integer idTipoMovimentacao;
    private Integer idCenarioFiscal;
    private String descricaoTipoMovimentacao;
    private Integer numeroItem;
    private Integer idProduto;
    private String descricaoProduto;
    private Integer idCodigoBarras;
    private String codigoBarras = "0";
    private double quantidade;
    private double quantidadeProduto;
    private double quantidadeVenda;
    private double quantidadeCompra;
    private String codEmbalagem;
    private String codEmbalagemNFe;
    private double valorUnitario;
    private double valorUnitarioProduto;
    private double valorTotalBruto;
    private @JsonProperty("NCM") String NCM;
    private Integer excecaoNCM;
    private @JsonProperty("CEST") String CEST;
    private @JsonProperty("CFOP") String CFOP;
    private @JsonProperty("CSTICMS") Integer CSTICMS;
    private double valorOutrasDespesas;
    private double valorDesconto;
    private double valorFrete;
    private double valorSeguro;
    private double valorIPI;
    private String custoUnitarioProduto;
    private String custoUnitarioAnterior;
    private String custoUnitarioSemImpostoAnterior;
    private String precoComum;
    private String custoCompra;
    private Integer origemMercadoria;
    private Integer modalidadeBaseCalculo;
    private double valorIsento;
    private double reducaoICMS;
    private double valorBaseCalculoICMS;
    private @JsonProperty("ICMS") double ICMS;
    private double valorICMS;
    private String diferido;
    private double valorICMSDiferido;
    private @JsonProperty("FCP") double FCP;
    private double valorFCP;
    private @JsonProperty("ICMSDesonerado") String ICMSDesonerado;
    private double valorICMSDesonerado;
    private double valorIVA;
    private String percentualIVA;
    private double reducaoICMSST;
    private double valorBaseCalculoICMSST;
    private @JsonProperty("ICMSST") double ICMSST;
    private double valorICMSST;
    private @JsonProperty("FCPST") double FCPST;
    private double valorFCPST;
    private double valorBaseCalculoICMSSTRetido;
    private double valorICMSSTRetido;
    private double valorFCPSTRetido;
    private Integer idLocalEstoque;
    private Integer cstpiscofins;
    private double valorBaseCalculoPISCOFINS;
    private double pis;
    private double valorPIS;
    private double cofins;
    private double valorCOFINS;
    private Boolean custoAtualizado;
    private double valorBonificado;
    private double valorBonificacaoDisponivel;
    private double baseCalculoICMSSTRetAnterior;
    private double valorICMSSTRetAnterior;
    private double valorFCPSTRetAnterior;
    private String tipoBaseCalculoCreditoPisCofins;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProdutoDTO produto = (ProdutoDTO) o;
        return idProduto.equals(produto.idProduto);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idProduto);
    }
}
