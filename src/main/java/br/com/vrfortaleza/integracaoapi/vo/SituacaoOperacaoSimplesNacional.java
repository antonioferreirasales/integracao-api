package br.com.vrfortaleza.integracaoapi.vo;

import lombok.Getter;

@Getter
public enum SituacaoOperacaoSimplesNacional {
    TRIBUTADA_COM_CREDITO_ICMS(101, "101 - Tributada pelo Simples Nacional com permissao de credito ICMS"),
    TRIBUTADA_SEM_CREDITO_ICMS(102, "102 - Tributada pelo Simples Nacional sem permissao credito"),
    ISENCAO_ICMS(103, "103 - Isende ICMS no Simples Nacional na faixa de receita bruta"),
    TRIBUTADA_COM_CREDITO_ICMS_ST(201, "201 - Tributada pelo Simples Nacional com permissde cre cobrando ICMS por ST"),
    TRIBUTADA_SEM_CREDITO_ICMS_ST(202, "202 - Tributada pelo Simples Nacional sem permissde cre com cobrando ICMS por ST"),
    ISENCAO_ICMS_ST(203, "203 - Isendo ICMS no Simples Nacional para faixa de receita bruta e cobrande ICMS por ST"),
    IMUNE(300, "300 - Imune de ICMS"),
    NAO_TRIBUTADA(400, "400 - Nao tributada pelo Simples Nacional"),
    COBRADO_ANTERIORMENTE(500, "500 - ICMS cobrado anteriormente por ST ou por antecipacao"),
    OUTRAS(900, "900 - Outros (operacoes que nao se enquadram nos codigos anteriores)");

    private int id = 0;

    private String descricao = "";

    SituacaoOperacaoSimplesNacional(int i_id, String i_descricao) {
        this.id = i_id;
        this.descricao = i_descricao;
    }

    public static SituacaoOperacaoSimplesNacional getById(int i_id) {
        SituacaoOperacaoSimplesNacional oSituacaoTributaria = null;
        for (SituacaoOperacaoSimplesNacional oSituacaoTributariaProcura : values()) {
            if (oSituacaoTributariaProcura.getId() == i_id) {
                oSituacaoTributaria = oSituacaoTributariaProcura;
                break;
            }
        }
        return oSituacaoTributaria;
    }
}
