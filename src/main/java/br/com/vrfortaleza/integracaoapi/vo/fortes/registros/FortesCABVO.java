package br.com.vrfortaleza.integracaoapi.vo.fortes.registros;

public class FortesCABVO {
    public String campo1 = "";
    public String campo2 = "";
    public String campo3 = "";
    public String campo4 = "";
    public String campo5 = "";
    public String campo6 = "";
    public String campo7 = "";
    public String campo8 = "";
    public String campo9 = "";

    public String getString() throws Exception {
        return this.campo1 + "|" + this.campo2 + "|" + this.campo3 + "|" + this.campo4 + "|" + this.campo5 + "|" + this.campo6 + "|" + this.campo7 + "|" + this.campo8 + "|" + this.campo9;
    }

    public FortesCABVO() {
    }
}
