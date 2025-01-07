package br.com.vrfortaleza.integracaoapi.services.interfaces.exportacao.fortes;

import br.com.vrfortaleza.integracaoapi.api.dto.EmitenteDTO;
import br.com.vrfortaleza.integracaoapi.api.dto.NotaFiscalRecebimentoDTO;
import br.com.vrfortaleza.integracaoapi.api.dto.NotaFiscalRecebimentoDetalhesDTO;
import br.com.vrfortaleza.integracaoapi.api.dto.ProdutoDTO;
import br.com.vrfortaleza.integracaoapi.api.service.AuthService;
import br.com.vrfortaleza.integracaoapi.api.service.NotaFiscalRecebimentoDetalhesService;
import br.com.vrfortaleza.integracaoapi.api.service.NotaFiscalRecebimentoService;
import br.com.vrfortaleza.integracaoapi.config.Log;
import br.com.vrfortaleza.integracaoapi.util.Arquivo;
import br.com.vrfortaleza.integracaoapi.util.Format;
import br.com.vrfortaleza.integracaoapi.util.Texto;
import br.com.vrfortaleza.integracaoapi.vo.*;
import br.com.vrfortaleza.integracaoapi.vo.fortes.registros.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ExportacaoFortesDAO {
    private List<NotaFiscalRecebimentoDTO> value = null;
    private final Set<EmitenteDTO> emitentes = new HashSet<>();
    private final Set<ProdutoDTO> PRODUTOS = new HashSet<>();
    private final Set<String> UNIDADES = new HashSet<>();
    private final Set<String> CFOPs = new HashSet<>();

    public void importarNotasRecebimento(ExportarFortesVO exportacao, FortesConfiguracaoLojaVO fortesConfiguracaoVO) {
        System.out.println("Exportando notas de recebimento");
        String lojaToken = fortesConfiguracaoVO.token;
        AuthService authService = new AuthService();
        try {
            String acess_token = authService.authenticate(lojaToken);
            NotaFiscalRecebimentoService recebimentoService = new NotaFiscalRecebimentoService();
            this.value = recebimentoService.getNotaFiscalRecebimento(acess_token, LocalDate.parse(exportacao.dataInicio), LocalDate.parse(exportacao.dataTermino), exportacao.tipoData);
            for (var item : value) {
                if (item.isXML()) {
                    continue;
                }
                NotaFiscalRecebimentoDetalhesService notaFiscalRecebimentoDetalhesService = new NotaFiscalRecebimentoDetalhesService();
                NotaFiscalRecebimentoDetalhesDTO notaFiscalRecebimentoDetalhes = notaFiscalRecebimentoDetalhesService.getNotaFiscalRecebimentoDetalhes(acess_token, item.getId());
                if (notaFiscalRecebimentoDetalhes != null) {
                    item.setNotaFiscalRecebimentoDetalhes(notaFiscalRecebimentoDetalhes);
                    System.out.println(item.getNotaFiscalRecebimentoDetalhes().getProdutos().size());
                } else {
                    System.out.println("NotaFiscalRecebimentoDetalhes is null");
                }
                EmitenteDTO emitente = item.getNotaFiscalRecebimentoDetalhes().getEmitente();
                emitentes.add(emitente);
                List<ProdutoDTO> produtos = item.getNotaFiscalRecebimentoDetalhes().getProdutos();
                Set<ProdutoDTO> produtosUnicos = new HashSet<>(item.getNotaFiscalRecebimentoDetalhes().getProdutos());
                PRODUTOS.addAll(produtosUnicos);
                Set<String> subsetUnidades = produtos.stream().map(ProdutoDTO::getCodEmbalagem).collect(Collectors.toSet());
                UNIDADES.addAll(subsetUnidades);
                Set<String> subsetCFOP = produtos.stream().map(ProdutoDTO::getCFOP).collect(Collectors.toSet());
                CFOPs.addAll(subsetCFOP);
            }
        } catch (Exception e) {
            Log.error(this.getClass(), e.getMessage());
        }
    }

    public void exportarParticipantes(ExportarFortesVO exportacao, FortesConfiguracaoLojaVO fortesConfiguracaoVO, Arquivo arquivo) {
        System.out.println("Exportando participantes");
        try {
            for (EmitenteDTO emitente : emitentes) {
                FortesPARVO oPAR = new FortesPARVO();
                oPAR.campo1 = "PAR";
                oPAR.campo2 = Format.number(emitente.getIdEmitente().toString(), 9);
                oPAR.campo3 = emitente.getNomeRazaoSocial().length() > 100 ? Format.string(emitente.getNomeRazaoSocial(), 100) : emitente.getNomeRazaoSocial();
                oPAR.campo4 = String.valueOf(emitente.getUf());
                oPAR.campo5 = emitente.getCnpjCpf();
                if (emitente.getTipoEmitente() == TipoContribuinteICMS.NAO_CONTRIBUINTE.getId() || emitente.getTipoEmitente() == TipoContribuinteICMS.ISENTO.getId()) {
                    oPAR.campo6 = "";
                } else {
                    oPAR.campo6 = Texto.substring(emitente.getIe(), 0, 14);
                }
                oPAR.campo7 = ""; //TODO | Inscrição Municipal do Emitente
                oPAR.campo8 = "N";
                oPAR.campo9 = "N";
                oPAR.campo10 = "N";
                oPAR.campo11 = "N";
                oPAR.campo12 = "N";
                oPAR.campo13 = "N";
                oPAR.campo14 = "N";
                oPAR.campo15 = "N";
                oPAR.campo16 = "";
                oPAR.campo17 = Texto.substring(emitente.getEndereco(), 0, 60);
                oPAR.campo18 = Texto.substring(emitente.getNumeroEndereco(), 0, 6);
                oPAR.campo19 = Texto.substring(emitente.getComplementoEndereco(), 0, 20);
                oPAR.campo20 = "";
                oPAR.campo21 = Texto.substring(emitente.getBairro(), 0, 50);
                oPAR.campo22 = ""; //TODO | CEP do Emitente
                oPAR.campo23 = Texto.substring(emitente.getCodMunicipio(), 0, 5);
                oPAR.campo24 = ""; //TODO | DDD do Emitente
                oPAR.campo25 = ""; //TODO | Telefone do Emitente
                oPAR.campo26 = Texto.substring(emitente.getInscricaoSuframa(), 0, 14);
                oPAR.campo27 = "N";
                oPAR.campo28 = "";
                oPAR.campo29 = "";
                oPAR.campo30 = emitente.getCodPaisEmitente().toString();
                oPAR.campo31 = "N";
                if (emitente.getTipoEmitente() == TipoContribuinteICMS.CONTRINUINTE_ICMS.getId()) {
                    oPAR.campo32 = "1";
                } else if (emitente.getTipoEmitente() == TipoContribuinteICMS.ISENTO.getId()) {
                    oPAR.campo32 = "2";
                } else if (emitente.getTipoEmitente() == TipoContribuinteICMS.NAO_CONTRIBUINTE.getId()) {
                    oPAR.campo32 = "3";
                }
                oPAR.campo33 = "";
                oPAR.campo34 = "N";
                oPAR.campo35 = ""; //TODO | Indica se o Emitente é uma Administradora de Cartão de Crédito
                oPAR.campo36 = ""; //TODO | Indica CNAE21
                oPAR.campo37 = "N";
                oPAR.campo38 = ""; //TODO | Situação Tributária do Emitente
                oPAR.campo39 = "";
                oPAR.campo40 = "";
                exportacao.qtdRegistro++;
                arquivo.write(oPAR.getStringLayout175());
            }
        } catch (Exception e) {
            Log.error(this.getClass(), e.getMessage());
        }
    }

    public void exportarUnidadeMedida(ExportarFortesVO exportacao, FortesConfiguracaoLojaVO fortesConfiguracaoVO, Arquivo arquivo) {
        System.out.println("Exportando unidades de medida");
        try {
            for (String unidade : UNIDADES) {
                FortesUNDVO oUND = new FortesUNDVO();
                oUND.campo1 = "UND";
                oUND.campo2 = unidade;
                oUND.campo3 = unidade;
                exportacao.qtdRegistro++;
                arquivo.write(oUND.getStringLayout175());
            }
        } catch (Exception e) {
            Log.error(this.getClass(), e.getMessage());
        }
    }

    public void exportarNaturezaOperacao(ExportarFortesVO exportacao, FortesConfiguracaoLojaVO fortesConfiguracaoVO, Arquivo arquivo) {
        System.out.println("Exportando natureza da operação.");
        try {
            for (String cfop : CFOPs) {
                FortesNOPVO NOP = new FortesNOPVO();
                NOP.campo1 = "NOP";
                NOP.campo2 = Format.number(cfop.replace(".", ""), 8);
                NOP.campo3 = cfop; //TODO | precisar ser a descrição do CFOP
                NOP.campo4 = "N";
                exportacao.qtdRegistro++;
                arquivo.write(NOP.getStringLayout175());
            }
        } catch (Exception e) {
            Log.error(this.getClass(), e.getMessage());
        }
    }

    public void exportarProdutos(ExportarFortesVO exportacao, FortesConfiguracaoLojaVO fortesConfiguracaoVO, Arquivo arquivo) {
        System.out.println("Exportando produtos.");
        try {
            for (ProdutoDTO produto : PRODUTOS) {
                FortesPROVO oPRO = new FortesPROVO();
                oPRO.campo1 = "PRO";
                oPRO.campo2 = produto.getIdProduto().toString();
                oPRO.campo3 = produto.getDescricaoProduto();
                oPRO.campo4 = produto.getIdProduto().toString();
                oPRO.campo5 = produto.getNCM().replace(".", "");
                oPRO.campo6 = produto.getCodEmbalagem(); //validar!!!
                oPRO.campo7 = "";
                oPRO.campo8 = "";
                oPRO.campo9 = "";
                oPRO.campo10 = "";
                oPRO.campo11 = "";
                oPRO.campo12 = Format.number(produto.getCodigoBarras(), 20);
                oPRO.campo13 = String.valueOf(produto.getReducaoICMS());
                oPRO.campo14 = "";
                oPRO.campo15 = produto.getCSTICMS();
                oPRO.campo16 = "";
                oPRO.campo17 = Format.number(produto.getCstpiscofins().toString(), 2);
                oPRO.campo18 = Format.number(produto.getCstpiscofins().toString(), 2);
                oPRO.campo19 = "";
                oPRO.campo20 = "";
                oPRO.campo21 = "";
                oPRO.campo22 = "";
                oPRO.campo23 = "";
                oPRO.campo24 = "N"; //TODO | precisar realmente validar se o produto está ativo
                oPRO.campo25 = "";
                oPRO.campo26 = "";
                oPRO.campo27 = "";
                oPRO.campo28 = "01";
                oPRO.campo29 = "";
                oPRO.campo30 = "";
                oPRO.campo31 = "";
                oPRO.campo32 = "";
                oPRO.campo33 = "";
                oPRO.campo34 = "";
                oPRO.campo35 = "4";
                oPRO.campo36 = "6";
                oPRO.campo37 = Format.decimal2(produto.getICMS() - (produto.getICMS() * (produto.getReducaoICMS() / 100)) + produto.getFCP()); //TODO | Confirma se não será disponibilizado
                oPRO.campo38 = "N";
                oPRO.campo39 = "";
                oPRO.campo40 = "";
                oPRO.campo41 = produto.getCEST();
                oPRO.campo42 = "";
                oPRO.campo43 = (produto.getCstpiscofins() == 60 ? "S" : "N");
                oPRO.campo44 = (produto.getOrigemMercadoria() == TipoOrigemMercadoria.ESTRANGEIRA_IMPORTACAO_DIRETA.getId() ? "S" : "N");
                oPRO.campo45 = (produto.getCstpiscofins() == 6 ? "S" : "N");
                oPRO.campo46 = (produto.getCstpiscofins() == 6 ? "S" : "N");
                oPRO.campo47 = (produto.getCstpiscofins() == 4 ? "S" : "N");
                oPRO.campo48 = (produto.getCstpiscofins() == 4 ? "S" : "N");
                if (fortesConfiguracaoVO.codigoIncidencia == TipoIncidencia.NAO_CUMULATIVO.getId()) {
                    oPRO.campo49 = "1";
                } else if (fortesConfiguracaoVO.codigoIncidencia == TipoIncidencia.CUMULATIVO.getId()) {
                    oPRO.campo49 = "2";
                } else {
                    oPRO.campo49 = "";
                }
                exportacao.qtdRegistro++;
                arquivo.write(oPRO.getStringLayout175());

                FortesOUMVO oOUM = new FortesOUMVO();
                oOUM.campo1 = "OUM";
                oOUM.campo2 = "id"; //TODO | Atualmente não disponível
                oOUM.campo3 = produto.getCodEmbalagem(); //TODO | É apenas o código da embalagem da nota fiscal
                oOUM.campo4 = Format.decimal(1.0D, 3).replace(".", "").replace(",", ".");
                oOUM.campo5 = Format.number(produto.getCodigoBarras(), 20);
                exportacao.qtdRegistro++;
                arquivo.write(oOUM.getStringLayout175());
            }
        } catch (Exception e) {
            Log.error(this.getClass(), e.getMessage());
        }
    }

    public void exportarNotasRecebimento(ExportarFortesVO exportacao, FortesConfiguracaoLojaVO fortesConfiguracaoVO, Arquivo arquivo) {
        try {
            for (var item : value) {
                NotaFiscalRecebimentoDetalhesDTO notaDetalhes = item.getNotaFiscalRecebimentoDetalhes();
                FortesNFMVO oNFM = new FortesNFMVO();
                oNFM.campo1 = "NFM";
                oNFM.campo2 = Format.number(String.valueOf(fortesConfiguracaoVO.idLoja), 4);
                oNFM.campo3 = "E";
                if (notaDetalhes.getModelo().equals(ModeloNotaFiscal.NOTAFISCAL.getModelo())) {
                    oNFM.campo4 = "NF1";
                } else if (notaDetalhes.getModelo().equals(ModeloNotaFiscal.PRODUTOR.getModelo())) {
                    oNFM.campo4 = "NFP";
                }
                oNFM.campo5 = "N";
                oNFM.campo6 = "";
                oNFM.campo7 = notaDetalhes.getSerie().toString();
                oNFM.campo8 = "";
                oNFM.campo9 = Format.number(String.valueOf(notaDetalhes.getNumeroNota()), notaDetalhes.getModelo().equals(ModeloNotaFiscal.NFE.getModelo()) ? 9 : 6);
                oNFM.campo10 = "";
                oNFM.campo11 = "";
                oNFM.campo12 = Format.data(String.valueOf(notaDetalhes.getDataEmissao()), "yyyy-MM-dd'T'HH:mm:ss", "yyyyMMdd");
                oNFM.campo13 = ""; //TODO | Verificar melhor
                oNFM.campo14 = Format.data(String.valueOf(notaDetalhes.getDataHoraEntrada()), "yyyy-MM-dd'T'HH:mm:ss", "yyyyMMdd");
                oNFM.campo15 = Format.number(String.valueOf(notaDetalhes.getEmitente().getIdEmitente()), 9);
                oNFM.campo16 = ""; //TODO | Verificar objeto recebimentoNotaFiscalGNRE
                oNFM.campo17 = "";
                oNFM.campo18 = ""; //TODO | Verificar objeto recebimentoNotaFiscalGNRE, atributo de mês referência
                oNFM.campo19 = "";
                oNFM.campo20 = "";
                oNFM.campo21 = "";
                oNFM.campo22 = "";
                oNFM.campo23 = "";
                oNFM.campo24 = "";
                oNFM.campo25 = "";
                oNFM.campo26 = Format.decimal2(notaDetalhes.getValorTotal());
                oNFM.campo27 = Format.decimal2(notaDetalhes.getValorFrete());
                oNFM.campo28 = "";
                oNFM.campo29 = Format.decimal2(notaDetalhes.getValorOutrasDespesas());
                oNFM.campo30 = "";
                oNFM.campo31 = "";
                oNFM.campo32 = Format.decimal2(notaDetalhes.getValorIPI());
                oNFM.campo33 = Format.decimal2(notaDetalhes.getValorICMSST());
                oNFM.campo34 = "0.00";
                oNFM.campo35 = Format.decimal2(notaDetalhes.getValorDesconto());
                oNFM.campo36 = Format.decimal2(notaDetalhes.getValorIPI() + notaDetalhes.getValorICMSST());
                oNFM.campo37 = Format.decimal2(notaDetalhes.getValorTotalProdutos()); //TODO | Validar
                oNFM.campo38 = "";
                oNFM.campo39 = "";
                oNFM.campo40 = "";
                oNFM.campo41 = Format.decimal2(notaDetalhes.getValorICMSST());
                oNFM.campo42 = Format.decimal2(notaDetalhes.getValorBaseCalculoICMSST());
                oNFM.campo43 = "0.00";
                oNFM.campo44 = "";
                oNFM.campo45 = "";
                oNFM.campo46 = "";
                oNFM.campo47 = "";
                if (notaDetalhes.getRecebimento().getModalidadeFrete() == TipoFreteNotaFiscal.DESTINATARIO.getId()) {
                    oNFM.campo48 = "D";
                } else if (notaDetalhes.getRecebimento().getModalidadeFrete() == TipoFreteNotaFiscal.EMITENTE.getId()) {
                    oNFM.campo48 = "R";
                } else {
                    oNFM.campo48 = "N";
                }
                oNFM.campo49 = ""; //TODO | Confirmar número de parcelas
                oNFM.campo50 = "";
                oNFM.campo51 = "";
                boolean isFornecedorDistribuidor = true; //TODO | Verificar se é fornecedor distribuidor
                if (isFornecedorDistribuidor) {
                    oNFM.campo52 = "";
                    oNFM.campo53 = "";
                } else {
                    oNFM.campo52 = Format.decimal2(notaDetalhes.getValorBaseCalculoPISCOFINS());
                    oNFM.campo53 = Format.decimal2(notaDetalhes.getValorBaseCalculoPISCOFINS());
                }
                oNFM.campo54 = "";
                oNFM.campo55 = "";
                oNFM.campo56 = "";
                oNFM.campo57 = "";
                oNFM.campo58 = "";
                oNFM.campo59 = "";
                oNFM.campo60 = ""; //TODO | Verificar informações de PIS/COFINS
                oNFM.campo61 = ""; //TODO | Verificar informações de PIS/COFINS
                oNFM.campo62 = "0.00";
                oNFM.campo63 = "0.00";
                oNFM.campo64 = "";
                oNFM.campo65 = notaDetalhes.getObservacao().replace("\n", " ").replace("|", " ");
                oNFM.campo66 = "";
                oNFM.campo67 = notaDetalhes.getChaveNFE();
                oNFM.campo68 = "0.00";
                oNFM.campo69 = "";
                oNFM.campo70 = "";
                oNFM.campo71 = "";
                oNFM.campo72 = "";
                oNFM.campo73 = "";
                oNFM.campo74 = "";
                oNFM.campo75 = "";
                oNFM.campo76 = "";
                oNFM.campo77 = "";
                oNFM.campo78 = "";
                oNFM.campo79 = "";
                oNFM.campo80 = "N";
                oNFM.campo81 = "";
                oNFM.campo82 = "";
                oNFM.campo83 = "";
                oNFM.campo84 = "";
                oNFM.campo85 = "";
                oNFM.campo86 = "";
                oNFM.campo87 = "";
                oNFM.campo88 = "";
                oNFM.campo89 = Format.data(String.valueOf(notaDetalhes.getDataEmissao()), "yyyy-MM-dd'T'HH:mm:ss", "yyyyMMdd");
                double somaValorFcpSt = notaDetalhes.getProdutos().stream().filter(oRegistro -> Integer.parseInt(oRegistro.getCSTICMS()) != SituacaoTributaria.SUBSTITUIDO.getId()).mapToDouble(ProdutoDTO::getValorFCPST).sum();
                oNFM.campo90 = (somaValorFcpSt > 0.0D) ? Format.decimal2(somaValorFcpSt) : "0.00";
                exportacao.qtdRegistro++;
                arquivo.write(oNFM.getStringLayout175());
            }
        } catch (Exception e) {
            Log.error(this.getClass(), e.getMessage());
        }
    }
}
