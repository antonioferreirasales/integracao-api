package br.com.vrfortaleza.integracaoapi.api.dto.records;

import br.com.vrfortaleza.integracaoapi.api.dto.NotaFiscalRecebimentoDetalhesDTO;

public record NotaRecebimentoDetalhadoResponse(String message, NotaFiscalRecebimentoDetalhesDTO data) {
}
