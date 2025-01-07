package br.com.vrfortaleza.integracaoapi.vo;

import lombok.Getter;

@Getter
public enum TipoFreteNotaFiscal {
    EMITENTE(0),
    DESTINATARIO(1),
    TERCEIROS(2),
    SEM_COBRANCA(9);

    private int id;

    TipoFreteNotaFiscal(int i_id) {
        this.id = i_id;
    }

}
