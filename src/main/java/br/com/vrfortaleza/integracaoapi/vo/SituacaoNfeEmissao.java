package br.com.vrfortaleza.integracaoapi.vo;

import lombok.Getter;

@Getter
public enum SituacaoNfeEmissao {
    NAO_TRANSMITIDA(1),
    AUTORIZADA(2),
    REJEITADA(3),
    DENEGADA(4),
    CANCELADA(5),
    INUTILIZADA(7);

    private int id = 0;

    SituacaoNfeEmissao(int i_id) {
        this.id = i_id;
    }

    public static SituacaoNfeEmissao getEnumById(int i_id) {
        for (SituacaoNfeEmissao e : values()) {
            if (e.getId() == i_id)
                return e;
        }
        return null;
    }
}
