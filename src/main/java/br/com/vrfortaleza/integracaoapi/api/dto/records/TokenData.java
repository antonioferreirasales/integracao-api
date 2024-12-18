package br.com.vrfortaleza.integracaoapi.api.dto.records;

public record TokenData(String access_token, String token_type, int expires_in) {}
