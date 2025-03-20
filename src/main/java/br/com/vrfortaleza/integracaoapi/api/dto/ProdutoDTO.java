package br.com.vrfortaleza.integracaoapi.api.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
//@JsonIgnoreProperties(ignoreUnknown = true)
public class ProdutoDTO {
    private Integer id;
    private Integer idProdutoRecebimento;
    private Integer idTipoEcf;
    private Integer idSatNfce;
    private Integer idVendaCupom;
    private Integer idPedidoVenda;
    private @JsonProperty("isReferenciado") boolean isReferenciado;
    private Integer idTipoMovimentacao;
    private Integer idCenarioFiscal;
    private String descricaoTipoMovimentacao;
    private Integer numeroItem;
    private Integer idProduto;
    private List<Integer> tipoProduto;
    private String descricaoProduto;
    private Integer idCodigoBarras;
    private String codigoBarras = "0";
    private double quantidade;
    private String quantidadeEmbalagem;
    private String embalagem;
    private double quantidadeProduto;
    private double quantidadeVenda;
    private double quantidadeCompra;
    private String codEmbalagem;
    private String codEmbalagemNFe;
    private double valorUnitario;
    private double valorUnitarioProduto;
    private double valorTotalBruto;
    private @JsonAlias({"NCM", "ncm"}) String NCM;
    private Integer excecaoNCM;
    private @JsonAlias({"CEST", "cest"}) String CEST;
    private @JsonAlias({"CFOP", "cfop"}) String CFOP;
    private @JsonAlias({"CSTICMS", "csticms"}) Integer CSTICMS;
    private String reducaost;
    private double valorOutrasDespesas;
    private double valorDesconto;
    private double valorFrete;
    private double valorSeguro;
    private double valorIPI;
    private double valorIPIDevolvido;
    private String custoUnitarioProduto;
    private String custoUnitarioAnterior;
    private String custoUnitarioSemImpostoAnterior;
    private double custoComImposto;
    private String precoComum;
    private String custoCompra;
    private Integer origemMercadoria;
    private Integer modalidadeBaseCalculo;
    private double valorIsento;
    private double reducao;
    private double reducaoICMS;
    private double valorBaseCalculoICMS;
    private Integer motivoDesoneracao;
    private @JsonAlias({"ICMS", "icms"}) double ICMS;
    private @JsonAlias({"CSOSN", "csosn"}) String CSOSN;
    private double valorICMS;
    private String diferido;
    private double valorICMSDiferido;
    private @JsonAlias({"FCP", "fcp"}) double FCP;
    private double valorBaseCalculoSTRetido;
    private double valorFCP;
    private @JsonAlias({"ICMSDesonerado", "icmsdesonerado"}) String ICMSDesonerado;
    private double valorICMSDesonerado;
    private double valorIVA;
    private String percentualIVA;
    private double reducaoICMSST;
    private double valorBaseCalculoICMSST;
    private @JsonAlias({"ICMSST", "icmsst"}) double ICMSST;
    private double valorICMSST;
    private @JsonAlias({"FCPST", "fcpst"}) double FCPST;
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
    private double quantidadeBonificada;

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
