package br.com.vrfortaleza.integracaoapi.vo;

import lombok.Getter;

public enum TipoSimNao {
    TODOS(-1, false),
    NAO(0, false),
    SIM(1, true);

    @Getter
    private int id = 0;

    private boolean valor = false;

    TipoSimNao(int i_id, boolean i_valor) {
        this.id = i_id;
        this.valor = i_valor;
    }

    public boolean getBoolean() {
        return this.valor;
    }
}
