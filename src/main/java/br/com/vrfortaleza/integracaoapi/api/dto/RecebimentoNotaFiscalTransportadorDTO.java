package br.com.vrfortaleza.integracaoapi.api.dto;

import lombok.Data;

@Data
public class RecebimentoNotaFiscalTransportadorDTO {
    private Long id;
    private Integer modalidadeFrete;
    private String razaoSocial;
    private String cnpjCpf;
    private String ie;
    private String endereco;
    private String municipio;
    private String siglaUF;
    private String placa;
    private String siglaUFPlaca;
}

