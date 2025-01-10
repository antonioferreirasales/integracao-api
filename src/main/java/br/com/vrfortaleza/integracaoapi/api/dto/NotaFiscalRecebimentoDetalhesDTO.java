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
    private double valorTotal;
    private String chaveNFE;
    private String informacaoComplementar;
    private String observacao;
    private Boolean geraFinanceiroGNRE;
    private double valorBaseCalculoICMS;
    private double valorICMS;
    private double valorFCP;
    private double valorBaseCalculoICMSST;
    private double valorICMSST;
    private double valorFCPST;
    private double valorIPI;
    private double valorBaseCalculoPISCOFINS;
    private double valorPIS;
    private double valorCOFINS;
    private double valorTotalProdutos;
    private double valorOutrasDespesas;
    private double valorDesconto;
    private double valorFrete;
    private double valorSeguro;
    private double valorINSS;
    private double valorRAT;
    private double valorSENAR;
    private double valorTotalNFE;
    private String justificativa;
    private String tipoMovimentacao;
    private Integer geraContasAPagarStatus;
    private Integer geraMovimentoEstoqueStatus;
    private Boolean gerarContraNota;
    private @JsonProperty("recebimentoNotaFiscalProduto") List<ProdutoDTO> produtos = new ArrayList<>();
    private @JsonProperty("recebimentoNotaFiscalEmitente") EmitenteDTO emitente = new EmitenteDTO();
    private @JsonProperty("recebimentoNotaFiscalTransportador") RecebimentoNotaFiscalTransportadorDTO recebimento = new RecebimentoNotaFiscalTransportadorDTO();
    private @JsonProperty("recebimentoNotaFiscalGNRE") NotaFiscalGNREDTO[] gnre;
    private @JsonProperty("recebimentoNotaFiscalVencimento") NotaFiscalVencimentoDTO[] notaVencimento;
}
