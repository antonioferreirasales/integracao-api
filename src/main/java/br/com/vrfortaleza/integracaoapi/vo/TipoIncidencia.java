package br.com.vrfortaleza.integracaoapi.vo;

import lombok.Getter;

@Getter
public enum TipoIncidencia {
    NAO_CUMULATIVO(1),
    CUMULATIVO(2),
    CUMULATIVO_E_NAO_CUMULATIVO(3);

    private int id = 0;

    TipoIncidencia(int i_id) {
        this.id = i_id;
    }

}
