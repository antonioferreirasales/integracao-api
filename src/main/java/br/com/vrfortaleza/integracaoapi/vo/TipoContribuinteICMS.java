package br.com.vrfortaleza.integracaoapi.vo;

import lombok.Getter;

@Getter
public enum TipoContribuinteICMS {
    CONTRINUINTE_ICMS(0),
    NAO_CONTRIBUINTE(1),
    ISENTO(2);

    private int id = 0;

    TipoContribuinteICMS(int i_id) {
        this.id = i_id;
    }

    public static TipoContribuinteICMS getById(int i_id) {
        for (TipoContribuinteICMS oTipoIndicadorIE : values()) {
            if (oTipoIndicadorIE.id == i_id)
                return oTipoIndicadorIE;
        }
        return null;
    }
}
