package br.com.vrfortaleza.integracaoapi.api.dto.records;

import br.com.vrfortaleza.integracaoapi.api.dto.NotaFiscalEmissaoDTO;
import br.com.vrfortaleza.integracaoapi.api.dto.NotaFiscalRecebimentoDTO;

import java.util.List;


public record NotaEmissaoResponse(String message, List<NotaFiscalEmissaoDTO> data, int count, int pageSize) {}
