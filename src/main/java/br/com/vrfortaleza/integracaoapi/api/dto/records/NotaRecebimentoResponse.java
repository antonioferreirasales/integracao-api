package br.com.vrfortaleza.integracaoapi.api.dto.records;

import br.com.vrfortaleza.integracaoapi.api.dto.NotaFiscalRecebimentoDTO;

import java.util.List;


public record NotaRecebimentoResponse(String message, List<NotaFiscalRecebimentoDTO> data, int count, int pageSize) {}
