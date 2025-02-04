package br.com.vrfortaleza.integracaoapi.vo;

import lombok.Getter;

@Getter
public enum StatusNFe {
    NAO_FINALIZADO(1),
    FINALIZADO(2);

    private int id = 0;

    StatusNFe(int i_id) {
        this.id = i_id;
    }

    public static StatusNFe getEnumById(int i_id) {
        for (StatusNFe e: values()) {
            if (e.getId() == i_id) {
                return e;
            }
        }
        return null;
    }
}
