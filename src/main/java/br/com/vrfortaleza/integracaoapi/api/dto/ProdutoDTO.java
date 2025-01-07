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
    private String quantidade;
    private String quantidadeProduto;
    private String quantidadeVenda;
    private String quantidadeCompra;
    private String codEmbalagem;
    private String codEmbalagemNFe;
    private String valorUnitario;
    private String valorUnitarioProduto;
    private String valorTotalBruto;
    private @JsonProperty("NCM") String NCM;
    private Integer excecaoNCM;
    private @JsonProperty("CEST") String CEST;
    private @JsonProperty("CFOP") String CFOP;
    private @JsonProperty("CSTICMS") String CSTICMS;
    private String valorOutrasDespesas;
    private String valorDesconto;
    private String valorFrete;
    private String valorSeguro;
    private String valorIPI;
    private String custoUnitarioProduto;
    private String custoUnitarioAnterior;
    private String custoUnitarioSemImpostoAnterior;
    private String precoComum;
    private String custoCompra;
    private Integer origemMercadoria;
    private Integer modalidadeBaseCalculo;
    private String valorIsento;
    private double reducaoICMS;
    private String valorBaseCalculoICMS;
    private @JsonProperty("ICMS") double ICMS;
    private String valorICMS;
    private String diferido;
    private String valorICMSDiferido;
    private @JsonProperty("FCP") double FCP;
    private double valorFCP;
    private @JsonProperty("ICMSDesonerado") String ICMSDesonerado;
    private String valorICMSDesonerado;
    private String valorIVA;
    private String percentualIVA;
    private String reducaoICMSST;
    private String valorBaseCalculoICMSST;
    private @JsonProperty("ICMSST") String ICMSST;
    private String valorICMSST;
    private @JsonProperty("FCPST") double FCPST;
    private double valorFCPST;
    private double valorBaseCalculoICMSSTRetido;
    private double valorICMSSTRetido;
    private double valorFCPSTRetido;
    private Integer idLocalEstoque;
    private Integer cstpiscofins;
    private double valorBaseCalculoPISCOFINS;
    private String pis;
    private double valorPIS;
    private String cofins;
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
