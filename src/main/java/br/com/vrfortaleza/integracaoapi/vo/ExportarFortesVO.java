package br.com.vrfortaleza.integracaoapi.vo;
// interface da tela de exportação do usuário
public class ExportarFortesVO {
    public String caminho = ""; //caminho de geração do arquivo
    public int tipoData = 2; //tipo de filtro de data
    public String dataInicio = "";
    public String dataTermino = "";
    public int idLoja = 0;

    /* registros selecionados para exportação do arquivo */
    public boolean participantes = false;
    public boolean produto = false;
    public boolean notaServico = false;
    public boolean notaEntrada = false;
    public boolean notaSaida = false;
    public boolean cupomFiscal = false;
    public boolean cupomFiscalEletronico = false;
    public boolean conhecimentoTransporteCarga = false;
    public boolean inventario = false;
    public boolean operacaoCreditoDebito = false;
    public boolean notaFiscalMercadoriaOutrosValores = false;
    public boolean outrosValoresDocumento = false;
    public boolean estoqueEscriturado = false;
    public boolean instrPagamentoEletronico = false;

    /* quantidade de registros */
    public int qtdRegistro = 0;
}
