package br.com.vrfortaleza.integracaoapi.util;

import lombok.Getter;

@Getter
public enum TipoSistemaOperacional {
    WINDOWS(0, "WINDOWS"),
    LINUX(1, "LINUX"),
    MAC(2, "MAC");
    private int id = 0;
    private String descricao = "";

    TipoSistemaOperacional(int i_id, String i_descricao) {
        this.id = i_id;
        this.descricao = i_descricao;
    }
}
