package br.com.vrfortaleza.integracaoapi.vo;

import lombok.Getter;

@Getter
public enum SituacaoTributaria {
    TRIBUTADO(0, "00 - Tributado integralmente"),
    TRIBUTADO_ICMS_ST(10, "10 - Tributado e com cobranca do ICMS ST"),
    REDUCAO_BASE_CALCULO(20, "20 - Com redude base de calculo"),
    ISENTO_ICMS_ST(30, "30 - Isento ou nao tributado e com cobranca do ICMS ST"),
    ISENTO(40, "40 - Isento"),
    NAO_TRIBUTADO(41, "41 - Ntributado"),
    SUSPENSAO(50, "50 - Suspensao"),
    DIFERIMENTO(51, "51 - Diferimento"),
    SUBSTITUIDO(60, "60 - Substituido"),
    MONOFASICO(61, "61 - Tributacao monofasica sobre combustiveis cobrada anteriormente"),
    REDUCAO_BASE_CALCULO_ICMS_ST(70, "70 - Com reducao de base de calculo cobrando ICMS ST"),
    OUTRAS(90, "90 - Outras");

    private int id = 0;

    private String descricao = "";

    SituacaoTributaria(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public static SituacaoTributaria getById(int id) {
        SituacaoTributaria oSituacaoTributaria = null;
        for (SituacaoTributaria oSituacaoTributariaProcura : values()) {
            if (oSituacaoTributariaProcura.getId() == id) {
                oSituacaoTributaria = oSituacaoTributariaProcura;
                break;
            }
        }
        return oSituacaoTributaria;
    }
}
