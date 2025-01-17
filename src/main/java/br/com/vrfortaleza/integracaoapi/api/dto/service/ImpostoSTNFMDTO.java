package br.com.vrfortaleza.integracaoapi.api.dto.service;

public record ImpostoSTNFMDTO(
        double valorTotal,
        Integer CST,
        double FCP
) {}