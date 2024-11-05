package br.com.vrfortaleza.integracaoapi.vo.fortes.registros;

public class FortesTRAVO {
    public String campo1 = "";
    public String campo2 = "";

    public FortesTRAVO() {
    }

    public String getString() throws Exception {
        return this.campo1 + "|" + this.campo2;
    }
}
