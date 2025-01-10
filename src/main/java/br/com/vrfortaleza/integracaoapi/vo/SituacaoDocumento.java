package br.com.vrfortaleza.integracaoapi.vo;

import lombok.Getter;

@Getter
public enum SituacaoDocumento {
    REGULAR("00"),
    EXTEMPORANEO("01"),
    CANCELADO("02"),
    CANCELADO_EXTEMPORANEO("03"),
    DENEGADO("04"),
    INUTILIZADO("05"),
    COMPLEMENTO("06"),
    COMPLEMENTO_EXTEMPORANEO("07"),
    NORMA_ESPECIFICA("08");

    private String id;

    SituacaoDocumento(String i_id) {
        this.id = i_id;
    }

}
