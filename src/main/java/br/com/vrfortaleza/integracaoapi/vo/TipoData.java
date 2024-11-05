package br.com.vrfortaleza.integracaoapi.vo;

import lombok.Getter;

@Getter
public enum TipoData {
    EMISSAO(1, "EMISSAO"),
    ENTRADA(2, "ENTRADA"),
    VENCIMENTO(3, "VENCIMENTO"),
    PAGAMENTO(4, "PAGAMENTO"),
    PAGTO_CONTABIL(5, "PAGTO CONTABIL");

    private int id = 0;
    private String descricao = "";

    private TipoData(int i_id, String i_descricao) {
        this.id = i_id;
        this.descricao = i_descricao;
    }

}
