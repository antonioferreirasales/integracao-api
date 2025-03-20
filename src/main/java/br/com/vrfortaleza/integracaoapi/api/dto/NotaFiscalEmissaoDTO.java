package br.com.vrfortaleza.integracaoapi.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NotaFiscalEmissaoDTO {
    private int id;
    private String lojaDescricao;
    private int statusNfe;
    private int situacaoNfe;
    private String motivo;
    private String justificativaCancelamento;
    private String dataHoraLancamento;
    private String dataHoraEmissao;
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
    private double valorOutrasDespesasTotal;
    private double valorDescontoTotal;
    private double valorFreteTotal;
    private double valorSeguroTotal;
    private int numeroNota;
    private String chaveNfe;
    private int geraMovimentoEstoqueStatus;
    private int geraContasAReceberStatus;
    private String razaoSocial;
    private String siglaUf;
    NotaFiscalEmissaoDetalhesDTO notaFiscalEmissaoDetalhes = new NotaFiscalEmissaoDetalhesDTO();
}
