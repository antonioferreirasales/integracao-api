package br.com.vrfortaleza.integracaoapi.vo.fortes.registros;

public class FortesUNDVO {
    public String campo1 = "";

    public String campo2 = "";

    public String campo3 = "";

    public String getStringLayout175() throws Exception {
        return this.campo1 + "|" + this.campo2 + "|" + this.campo3;
    }
}
