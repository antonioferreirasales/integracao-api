package br.com.vrfortaleza.integracaoapi.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NotaFiscalRecebimentoDTO {
    private int id;
    private String lojaDescricao;
    private int numeroNota;
    private String dataHoraEntrada;
    private String nomeRazaoSocial;
    private String chaveNFE;
    private boolean conferidoLancamento;
    private String manifestacao;
    private int idLoja;
    private String dataEmissao;
    private int statusNfe;
    private String valorTotalNFE;
    private String situacaoDocumento;
    private Integer idDestinatario;
    private @JsonProperty("isXML") boolean isXML;
    private @JsonProperty("isDespesa") boolean isDespesa;
    private String nomeArquivo;
    private int geraContasAPagarStatus;
    private int geraMovimentoEstoqueStatus;
    private NotaFiscalRecebimentoDetalhesDTO notaFiscalRecebimentoDetalhes = new NotaFiscalRecebimentoDetalhesDTO();
}
