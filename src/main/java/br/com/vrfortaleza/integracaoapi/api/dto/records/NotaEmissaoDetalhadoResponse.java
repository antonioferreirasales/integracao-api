package br.com.vrfortaleza.integracaoapi.api.dto.records;

import br.com.vrfortaleza.integracaoapi.api.dto.NotaFiscalEmissaoDetalhesDTO;

public record NotaEmissaoDetalhadoResponse(String message, NotaFiscalEmissaoDetalhesDTO data) {
}
