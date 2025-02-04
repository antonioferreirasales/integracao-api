package br.com.vrfortaleza.integracaoapi.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotaFiscalEmissaoDetalhesDTO {
    private Integer id;
    private Integer idLoja;
    private Integer ufLoja;
    private Integer codPaisLoja;
    private Integer idEmpresa;
    private String lojaDescricao;
    private Integer situacaoNfe;
    private String motivo;
    private String numeroRecibo;
    private String numeroProtocolo;
    private String justificativaCancelamento;
    private Integer statusNfe;
    private LocalDateTime dataHoraLancamento;
    private LocalDateTime dataHoraEmissao;
    private LocalDateTime dataSaida;
    private Integer modalidadeFrete;
    private String placa;
    private String placaUf;
    private String observacao;
    private String informacaoComplementar;
    private String acrescimoInformacaoComplementar;
    private Integer numeroNota;
    private Integer serie;
    private Integer tipoMovimentacao;
    private String descricaoTipoMovimentacao;
    private Integer naturezaTipoMovimentacao;
    private double valorNfeTotal;
    private double valorBaseCalculoICMSTotal;
    private double valorICMSTotal;
    private double valorFCPTotal;
    private double valorBaseCalculoICMSSTTotal;
    private double valorICMSSTTotal;
    private double valorFCPSTTotal;
    private double valorIPITotal;
    private double valorPISTotal;
    private double valorCOFINSTotal;
    private double valorBaseCalculoPISCOFINSTotal;
    private double valorTotalBrutoTotal;
    private double valorOutrasDespesasTotal;
    private double valorDescontoTotal;
    private double valorFreteTotal;
    private double valorSeguroTotal;
    private String chaveNfe;
    private String xml;
    private String xmlRetorno;
    private Integer geraMovimentoEstoqueStatus;
    private Integer geraContasAReceberStatus;
    private Integer finalidade;
    private double valorICMSDesoneradoTotal;

    private @JsonProperty("recebimentoNotaFiscalProduto") List<ProdutoDTO> produtos = new ArrayList<>();
    private @JsonProperty("recebimentoNotaFiscalEmitente") EmitenteDTO emitente = new EmitenteDTO();
    private @JsonProperty("recebimentoNotaFiscalTransportador") RecebimentoNotaFiscalTransportadorDTO recebimento = new RecebimentoNotaFiscalTransportadorDTO();
    private @JsonProperty("recebimentoNotaFiscalGNRE") NotaFiscalGNREDTO[] gnre;
    private @JsonProperty("recebimentoNotaFiscalVencimento") NotaFiscalVencimentoDTO[] notaVencimento;
}
