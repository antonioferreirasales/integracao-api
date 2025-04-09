package br.com.vrfortaleza.integracaoapi.services.interfaces.exportacao.fortes;

import br.com.vrfortaleza.integracaoapi.api.dto.NotaFiscalEmissaoDTO;
import br.com.vrfortaleza.integracaoapi.api.dto.NotaFiscalEmissaoDetalhesDTO;
import br.com.vrfortaleza.integracaoapi.config.Log;
import br.com.vrfortaleza.integracaoapi.util.Arquivo;
import br.com.vrfortaleza.integracaoapi.util.Format;
import br.com.vrfortaleza.integracaoapi.vo.ExportarFortesVO;
import br.com.vrfortaleza.integracaoapi.vo.FortesConfiguracaoLojaVO;
import br.com.vrfortaleza.integracaoapi.vo.SituacaoNfeEmissao;
import br.com.vrfortaleza.integracaoapi.vo.TipoFreteNotaFiscal;
import br.com.vrfortaleza.integracaoapi.vo.fortes.registros.FortesNFMVO;

import java.util.List;

public class NotasEmissao {
    public void exportar(ExportarFortesVO exportacao, FortesConfiguracaoLojaVO fortesConfiguracaoVO, Arquivo arquivo, List<NotaFiscalEmissaoDTO> notasEmissao) {
        try {
            for (NotaFiscalEmissaoDTO nota : notasEmissao) {
                System.out.println("ID: " + nota.getId() + " | Numero Nota: " + nota.getNumeroNota());
                NotaFiscalEmissaoDetalhesDTO notaDetalhes = nota.getNotaFiscalEmissaoDetalhes();
                FortesNFMVO oNFM = new FortesNFMVO();
                oNFM.campo1 = "NFM";
                oNFM.campo2 = Format.number(String.valueOf(fortesConfiguracaoVO.idLoja), 4);
                oNFM.campo3 = "S";
                oNFM.campo4 = "NFE";
                oNFM.campo5 = "S";
                oNFM.campo6 = "";
                oNFM.campo7 = notaDetalhes.getSerie().toString();
                oNFM.campo8 = "";
                oNFM.campo9 = Format.number(String.valueOf(nota.getNumeroNota()), 9);
                oNFM.campo10 = "";
                oNFM.campo11 = "";
                oNFM.campo12 = Format.data(notaDetalhes.getDataHoraEmissao(), "yyyyMMdd");
                oNFM.campo28 = "0.00";

                SituacaoNfeEmissao situacaoNFE = SituacaoNfeEmissao.getEnumById(notaDetalhes.getSituacaoNfe());
                switch (situacaoNFE) {
                    case CANCELADA:
                        oNFM.campo13 = "1";
                        oNFM.campo28 = "";
                        break;
                    case DENEGADA:
                        oNFM.campo13 = "4";
                        break;
                    case INUTILIZADA:
                        oNFM.campo13 = "5";
                        break;
                    default:
                        oNFM.campo13 = "0";
                        break;
                }
                if (situacaoNFE != SituacaoNfeEmissao.AUTORIZADA) {
                    oNFM.campo14 = "";
                    oNFM.campo15 = "";
                    oNFM.campo26 = "";
                    oNFM.campo27 = "";
                    oNFM.campo36 = "";
                    oNFM.campo37 = "";
                    oNFM.campo48 = "";
                    oNFM.campo52 = "";
                    oNFM.campo53 = "";
                    oNFM.campo60 = "";
                    oNFM.campo61 = "";
                } else {
                    oNFM.campo14 = Format.data(notaDetalhes.getDataSaida(), "yyyyMMdd");
                    oNFM.campo15 = Format.number(notaDetalhes.getPessoaDestinatario().getIdPessoa().toString(), 9);
                    double valorBruto = notaDetalhes.getValorTotalBrutoTotal();
                    oNFM.campo26 = Format.decimal2(valorBruto);
                    oNFM.campo36 = Format.decimal2(valorBruto);
                    oNFM.campo37 = String.valueOf(notaDetalhes.getProdutos().size());
                    if (notaDetalhes.getModalidadeFrete() == TipoFreteNotaFiscal.DESTINATARIO.getId()) {
                        oNFM.campo48 = "D";
                    } else if (notaDetalhes.getModalidadeFrete() == TipoFreteNotaFiscal.EMITENTE.getId()) {
                        oNFM.campo48 = "R";
                    } else {
                        oNFM.campo48 = "N";
                    }
                    int qtdParcelas = 0;
                    if (notaDetalhes.getEmissaoNotaFiscalVencimento().getEmissaoNotaFiscalVencimento() != null) {
                        qtdParcelas = notaDetalhes.getEmissaoNotaFiscalVencimento().getEmissaoNotaFiscalVencimento().size();
                    }
                    if (qtdParcelas >= 1) {
                        oNFM.campo49 = "P";
                    } else {
                        oNFM.campo49 = "";
                    }
                    // TODO | Deve ser preenchido apenas se a loja é do tipo distribuidor
                    oNFM.campo52 = Format.decimal2(notaDetalhes.getValorBaseCalculoPISCOFINSTotal());
                    oNFM.campo53 = Format.decimal2(notaDetalhes.getValorBaseCalculoPISCOFINSTotal());

                    oNFM.campo60 = Format.decimal2(notaDetalhes.getValorCOFINSTotal());
                    oNFM.campo61 = Format.decimal2(notaDetalhes.getValorPISTotal());
                }
                oNFM.campo16 = "";
                oNFM.campo17 = "";
                oNFM.campo18 = "";
                oNFM.campo19 = "";
                oNFM.campo20 = "";
                oNFM.campo21 = "";
                oNFM.campo22 = "";
                oNFM.campo23 = "";
                oNFM.campo24 = "";
                oNFM.campo25 = "";
                oNFM.campo27 = Format.decimal2(notaDetalhes.getValorFreteTotal());
                oNFM.campo29 = Format.decimal2(notaDetalhes.getValorOutrasDespesasTotal());
                oNFM.campo30 = "";
                oNFM.campo31 = "";
                oNFM.campo32 = Format.decimal2(notaDetalhes.getValorIPITotal());
                oNFM.campo33 = Format.decimal2(notaDetalhes.getValorICMSSTTotal());
                oNFM.campo34 = "0.00";
                oNFM.campo35 = Format.decimal2(notaDetalhes.getValorDescontoTotal());
                oNFM.campo38 = "";
                oNFM.campo39 = "";
                oNFM.campo40 = "";
                if(situacaoNFE != SituacaoNfeEmissao.CANCELADA && situacaoNFE != SituacaoNfeEmissao.INUTILIZADA) {
                    oNFM.campo41 = Format.decimal2(notaDetalhes.getValorICMSTotal());
                    oNFM.campo42 = Format.decimal2(notaDetalhes.getValorBaseCalculoICMSSTTotal());
                    oNFM.campo43 = "0.00";
                    oNFM.campo44 = "";
                    oNFM.campo45 = "";
                    oNFM.campo46 = "";
                    oNFM.campo47 = "";
                    int qtdParcelas = 0;
                    if (notaDetalhes.getEmissaoNotaFiscalVencimento().getEmissaoNotaFiscalVencimento() != null) {
                        qtdParcelas = notaDetalhes.getEmissaoNotaFiscalVencimento().getEmissaoNotaFiscalVencimento().size();
                    }
                    if (qtdParcelas >= 1) {
                        oNFM.campo49 = "P";
                    } else {
                        oNFM.campo49 = "";
                    }
                    oNFM.campo50 = "";
                    oNFM.campo51 = "";
                    oNFM.campo54 = "";
                    oNFM.campo55 = "";
                    oNFM.campo56 = "";
                    oNFM.campo57 = "";
                    oNFM.campo58 = "";
                    oNFM.campo59 = "";
                    oNFM.campo62 = "0.00";
                    oNFM.campo63 = "0.00";
                    oNFM.campo64 = "";
                    oNFM.campo65 = notaDetalhes.getObservacao();
                    oNFM.campo66 = "";
                }
                oNFM.campo67 = notaDetalhes.getChaveNfe();
                oNFM.campo68 = (situacaoNFE != SituacaoNfeEmissao.CANCELADA) ? "0.00" : "";
                oNFM.campo69 = "";
                oNFM.campo70 = "";
                oNFM.campo71 = Format.number(notaDetalhes.getProdutos().get(0).getCFOP().replace(".", ""), 8);
                oNFM.campo72 = "";
                oNFM.campo73 = "";
                oNFM.campo74 = "";
                oNFM.campo75 = "";
                oNFM.campo76 = "";
                oNFM.campo77 = "";
                oNFM.campo78 = "";
                oNFM.campo79 = "";
                oNFM.campo80 = "N";
                oNFM.campo81 = "1";
                oNFM.campo82 = "";
                oNFM.campo83 = "";
                oNFM.campo84 = "";
                oNFM.campo85 = "";
                oNFM.campo86 = "";
                oNFM.campo87 = "";
                oNFM.campo88 = "";
                oNFM.campo89 = Format.data(notaDetalhes.getDataHoraEmissao(), "yyyyMMdd");
                oNFM.campo90 = Format.decimal2(notaDetalhes.getValorFCPSTTotal());

                exportacao.qtdRegistro++;
                arquivo.write(oNFM.getStringLayout175());
            }
        } catch (Exception e) {
            Log.error(this.getClass(), e.getMessage());
            System.err.println(e.getMessage());
        }
    }
}
