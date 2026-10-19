package br.com.vrfortaleza.integracaoapi.api;

public class URL {
    public static final String BASE_URL = "https://super-stag.vrsoft.com.br/";
    public static final String AUTH_URL = BASE_URL + "api/v1/rede/oauth2/authorize/";

    public static final String NOTA_RECEBIMENTO_URL = BASE_URL + "api/v1/fiscal/oauth2/recebimento-nota-fiscal";
    public static final String NOTA_EMISSAO_URL = BASE_URL + "api/v1/fiscal/oauth2/emissao-nota-fiscal";
}
