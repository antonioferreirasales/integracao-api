package br.com.vrfortaleza.integracaoapi.api.dto.service;

public record ImpostoNFMDTO(
        double baseCalculoICMS,
        double baseCalculoICMSST,
        double valorTotalOperacao,
        double aliquotaICMS,
        double valorICMS,
        double valorIsento,
        double valorOutras,
        double valorIPI,
        double FCP,
        double valorFCP,
        double valorFCPST,
        String cfop,
        Integer cst,
        Integer CSTPisCofins,
        String uf,
        Integer tipoOrigem
) {}

