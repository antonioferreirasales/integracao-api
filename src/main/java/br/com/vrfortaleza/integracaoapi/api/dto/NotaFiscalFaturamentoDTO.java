package br.com.vrfortaleza.integracaoapi.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotaFiscalFaturamentoDTO {
    private int id;
    private int idCondicaoPagamento;
    private int idFormaPagamento;
    private List<NotaFiscalVencimentoDTO> emissaoNotaFiscalVencimento;
}

