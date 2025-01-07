package br.com.vrfortaleza.integracaoapi.vo;

import lombok.Getter;

@Getter
public enum SituacaoNfe {
    NAO_TRANSMITIDA(0),
    AUTORIZADA(1),
    REJEITADA(2),
    CANCELADA(3),
    INUTILIZADA(4),
    DENEGADA(5);

    private int id = 0;

    SituacaoNfe(int i_id) {
        this.id = i_id;
    }

    public static SituacaoNfe getEnumById(int i_id) {
        for (SituacaoNfe e : values()) {
            if (e.getId() == i_id)
                return e;
        }
        return null;
    }
}
