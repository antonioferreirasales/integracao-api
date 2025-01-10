package br.com.vrfortaleza.integracaoapi.vo;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum TipoEstado {
    RO(11, "RO"),
    AC(12, "AC"),
    AM(13, "AM"),
    RR(14, "RR"),
    PA(15, "PA"),
    AP(16, "AP"),
    TO(17, "TO"),
    MA(21, "MA"),
    PI(22, "PI"),
    CE(23, "CE"),
    RN(24, "RN"),
    PB(25, "PB"),
    PE(26, "PE"),
    AL(27, "AL"),
    SE(28, "SE"),
    BA(29, "BA"),
    MG(31, "MG"),
    ES(32, "ES"),
    RJ(33, "RJ"),
    SP(35, "SP"),
    PR(41, "PR"),
    SC(42, "SC"),
    RS(43, "RS"),
    MS(50, "MS"),
    MT(51, "MT"),
    GO(52, "GO"),
    DF(53, "DF"),
    EX(99, "EX"),
    DESCONHECIDO(999, "DESCONHECIDO");

    private int id = 0;

    private String sigla = "";

    TipoEstado(int pId, String pSigla) {
        this.id = pId;
        this.sigla = pSigla;
    }

    public static TipoEstado getEstado(int pId) {
        return Arrays.<TipoEstado>stream(values())
                .filter(tpEstado -> (tpEstado.getId() == pId))
                .findFirst()
                .orElse(DESCONHECIDO);
    }

    public static TipoEstado getBySigla(String pSigla) {
        return Arrays.<TipoEstado>stream(values())
                .filter(tipoEstado -> tipoEstado.getSigla().equals(pSigla))
                .findFirst()
                .orElse(DESCONHECIDO);
    }

}
