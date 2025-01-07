package br.com.vrfortaleza.integracaoapi.vo;

import lombok.Getter;

@Getter
public enum TipoOrigemMercadoria {
    NACIONAL(0),
    ESTRANGEIRA_IMPORTACAO_DIRETA(1),
    ESTRANGEIRA_ADQUIRIDA_NO_MERCADO_INTERNO(2),
    NACIONAL_CONTEUDO_IMPORTACAO_SUPERIOR_40(3),
    NACIONAL_PROCESSOS_DE_PRODUCAO_ZFM(4),
    ESTRANGEIRA_ADQUIRIDA_NO_MERCADO_INTERNO_SEM_SIMILAR_NACIONAL(7);

    private int id = 0;

    TipoOrigemMercadoria(int i_id) {
        this.id = i_id;
    }

}
