package br.com.vrfortaleza.integracaoapi.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotaFiscalRecebimentoDetalhesDTO {
    private Integer id;
    private String nomeUsuario;
    private Integer idLoja;
    private Integer idEmpresa;
    private String lojaDescricao;
    private String lojaCNPJ;
    private String manifestacao;
    private Integer ufLoja;
    private Integer codPaisLoja;
    private Integer numeroNota;
    private Integer idRegimeTributario;
    private Integer serie;
    private String modelo;
    private LocalDateTime dataHoraEntrada;
    private LocalDateTime dataEmissao;
    private Integer statusNfe;
    private String situacaoDocumento;
    private String valorTotal;
    private String chaveNFE;
    private String informacaoComplementar;
    private String observacao;
    private Boolean geraFinanceiroGNRE;
    private String valorBaseCalculoICMS;
    private String valorICMS;
    private String valorFCP;
    private String valorBaseCalculoICMSST;
    private String valorICMSST;
    private String valorFCPST;
    private String valorIPI;
    private String valorBaseCalculoPISCOFINS;
    private String valorPIS;
    private String valorCOFINS;
    private String valorTotalProdutos;
    private String valorOutrasDespesas;
    private String valorDesconto;
    private String valorFrete;
    private String valorSeguro;
    private String valorINSS;
    private String valorRAT;
    private String valorSENAR;
    private String valorTotalNFE;
    private String justificativa;
    private String tipoMovimentacao;
    private Integer geraContasAPagarStatus;
    private Integer geraMovimentoEstoqueStatus;
    private Boolean gerarContraNota;
    private @JsonProperty("recebimentoNotaFiscalProduto") List<ProdutoDTO> produtos = new ArrayList<>();
    private @JsonProperty("recebimentoNotaFiscalEmitente") EmitenteDTO emitente = new EmitenteDTO();
}
