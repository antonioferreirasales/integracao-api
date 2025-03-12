package br.com.vrfortaleza.integracaoapi.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
//@JsonIgnoreProperties(ignoreUnknown = true)
public class PessoaDestinatarioDTO {
    private Integer id;
    private Integer idPessoa;
    private String razaoSocial;
    private String cnpj;
    private String ie;
    private Integer idEndereco;
    private String cep;
    private String endereco;
    private String num;
    private Integer uf;
    private String siglaUf;
    private String complemento;
    private Integer codMunicipio;
    private String municipio;
    private String bairro;
    private Integer codPais;
    private String pais;
    private Integer codConsumidorFinal;
    private Integer codPresencaComprador;
    private String telefone;
    private Integer tipoContribuinte;
    private Integer tipoDestinatario;
    private String inscricaoSuframa;
    private Integer orgaoPublico;
    private String retencaoIrrf;
}
