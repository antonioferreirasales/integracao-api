package br.com.vrfortaleza.integracaoapi.api.dto;

import lombok.Data;

@Data
public class EmitenteDTO {
    private Integer id;
    private Integer idEmitente;
    private Integer idRamoAtividadeEmitente;
    private Integer codPaisEmitente;
    private Integer tipoEmitente;
    private String nomeRazaoSocial;
    private String cnpjCpf = "";
    private String ie = "";
    private Integer uf;
    private String siglaUF = "";
    private String endereco = "";
    private String numeroEndereco = "";
    private String codMunicipio = "";
    private String complementoEndereco = "";
    private String bairro = "";
    private String inscricaoSuframa = "";
    private String retencaoFunrural = "";
    private String inscricao = "";
}
