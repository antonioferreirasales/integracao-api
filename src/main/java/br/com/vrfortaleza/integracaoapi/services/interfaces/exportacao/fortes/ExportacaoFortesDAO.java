package br.com.vrfortaleza.integracaoapi.services.interfaces.exportacao.fortes;

import br.com.vrfortaleza.integracaoapi.api.dto.EmitenteDTO;
import br.com.vrfortaleza.integracaoapi.api.dto.NotaFiscalRecebimentoDTO;
import br.com.vrfortaleza.integracaoapi.api.dto.NotaFiscalRecebimentoDetalhesDTO;
import br.com.vrfortaleza.integracaoapi.api.dto.ProdutoDTO;
import br.com.vrfortaleza.integracaoapi.api.dto.service.CalculaImpostoNFM;
import br.com.vrfortaleza.integracaoapi.api.dto.service.ImpostoNFMDTO;
import br.com.vrfortaleza.integracaoapi.api.dto.service.SituacaoTributariaFortesService;
import br.com.vrfortaleza.integracaoapi.api.service.AuthService;
import br.com.vrfortaleza.integracaoapi.api.service.NotaFiscalRecebimentoDetalhesService;
import br.com.vrfortaleza.integracaoapi.api.service.NotaFiscalRecebimentoService;
import br.com.vrfortaleza.integracaoapi.config.Log;
import br.com.vrfortaleza.integracaoapi.dao.interfaces.exportacao.fortes.AliquotaDAO;
import br.com.vrfortaleza.integracaoapi.util.Arquivo;
import br.com.vrfortaleza.integracaoapi.util.Format;
import br.com.vrfortaleza.integracaoapi.util.Texto;
import br.com.vrfortaleza.integracaoapi.vo.*;
import br.com.vrfortaleza.integracaoapi.vo.fortes.registros.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ExportacaoFortesDAO {
    private List<NotaFiscalRecebimentoDTO> value = null;
    private final Set<EmitenteDTO> emitentes = new HashSet<>();
    private final Set<ProdutoDTO> PRODUTOS = new HashSet<>();
    private final Set<String> UNIDADES = new HashSet<>();
    private final Set<String> CFOPs = new HashSet<>();
    private AliquotaDAO oAliquotaDAO = new AliquotaDAO();
    private CalculaImpostoNFM calculaImpostoService = new CalculaImpostoNFM();
    private SituacaoTributariaFortesService oSituacaoTributariaExportacaoFortesNotaEntradaService = new SituacaoTributariaFortesService();

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
                } else {
                    Log.debug(this.getClass(), "Nota: " + item.getNumeroNota() + " | ID: " + item.getId() + ", NotaFiscalRecebimentoDetalhes é nula");
                    continue;
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
            System.err.println(e.getMessage());
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
                if (emitente.getRegimeTributario() == TipoEmpresa.SIMPLES_NACIONAL.getId()) {
                    oPAR.campo38 = "1";
                } else if (emitente.getRegimeTributario() == TipoEmpresa.MEI.getId()) {
                    oPAR.campo38 = "2";
                } else {
                    oPAR.campo38 = "0";
                }
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
                oPRO.campo10 = ""; //TODO | Código do grupo do produto
                oPRO.campo11 = "";
                oPRO.campo12 = Format.number(produto.getCodigoBarras(), 20);
                oPRO.campo13 = String.valueOf(produto.getReducaoICMS());
                oPRO.campo14 = "";
                oPRO.campo15 = produto.getCSTICMS().toString();
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
                oPRO.campo43 = (produto.getCSTICMS() == 60 ? "S" : "N");
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
                oOUM.campo2 = produto.getIdProduto().toString(); //TODO | Atualmente não disponível
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
            for (NotaFiscalRecebimentoDTO item : value) {
                System.out.println("ID: " + item.getId() + " | Numero Nota: " + item.getNumeroNota());
                NotaFiscalRecebimentoDetalhesDTO notaDetalhes = item.getNotaFiscalRecebimentoDetalhes();
                FortesNFMVO oNFM = new FortesNFMVO();
                oNFM.campo1 = "NFM";
                oNFM.campo2 = Format.number(String.valueOf(fortesConfiguracaoVO.idLoja), 4);
                oNFM.campo3 = "E";
                if (notaDetalhes.getModelo().equals(ModeloNotaFiscal.NOTAFISCAL.getModelo())) {
                    oNFM.campo4 = "NF1";
                } else if (notaDetalhes.getModelo().equals(ModeloNotaFiscal.PRODUTOR.getModelo())) {
                    oNFM.campo4 = "NFP";
                } else if (notaDetalhes.getModelo().equals(ModeloNotaFiscal.NFCE.getModelo())) {
                    oNFM.campo4 = "NFC";
                } else {
                    oNFM.campo4 = "NFE";
                }
                oNFM.campo5 = "N";
                oNFM.campo6 = "";
                oNFM.campo7 = notaDetalhes.getSerie().toString();
                oNFM.campo8 = "";
                oNFM.campo9 = Format.number(String.valueOf(notaDetalhes.getNumeroNota()), notaDetalhes.getModelo().equals(ModeloNotaFiscal.NFE.getModelo()) ? 9 : 6);
                oNFM.campo10 = "";
                oNFM.campo11 = "";
                oNFM.campo12 = Format.data(notaDetalhes.getDataEmissao(), "yyyyMMdd");
                if (notaDetalhes.getSituacaoDocumento() == SituacaoDocumento.REGULAR.getId()) {
                    oNFM.campo13 = "0";
                } else if (notaDetalhes.getSituacaoDocumento() == SituacaoDocumento.CANCELADO.getId()) {
                    oNFM.campo13 = "1";
                } else if (notaDetalhes.getSituacaoDocumento() == SituacaoDocumento.COMPLEMENTO.getId()) {
                    oNFM.campo13 = "6";
                } else if (notaDetalhes.getSituacaoDocumento() == SituacaoDocumento.INUTILIZADO.getId() && (notaDetalhes.getEmitente().getRegimeTributario() == TipoEmpresa.PRODUTOR_RURAL_PESSOA_JURIDICA.getId() || notaDetalhes.getEmitente().getRegimeTributario() == TipoEmpresa.PRODUTOR_RURAL_PESSOA_FISICA.getId())) {
                    oNFM.campo13 = "2";
                } else {
                    oNFM.campo13 = "";
                }
                oNFM.campo14 = Format.data(notaDetalhes.getDataHoraEntrada(), "yyyyMMdd");
                oNFM.campo15 = Format.number(notaDetalhes.getEmitente().getIdEmitente().toString(), 9);
                if (notaDetalhes.getGnre().length > 0) {
                    var GNRE = notaDetalhes.getGnre()[0];
                    oNFM.campo16 = GNRE.getModelo() != null ? "S" : "N";
                    oNFM.campo18 = GNRE.getMesReferencia() != null ? Format.data(GNRE.getMesReferencia(), "MMAAAA") : "";
                } else {
                    oNFM.campo16 = "N";
                    oNFM.campo18 = "";

                }
                oNFM.campo17 = "";
                oNFM.campo19 = "";
                oNFM.campo20 = "";
                oNFM.campo21 = "";
                oNFM.campo22 = "";
                oNFM.campo23 = "";
                oNFM.campo24 = "";
                oNFM.campo25 = "";
                oNFM.campo26 = notaDetalhes.getSituacaoDocumento() != SituacaoDocumento.CANCELADO.getId() ? Format.decimal2(notaDetalhes.getValorTotal()) : "";
                oNFM.campo27 = notaDetalhes.getSituacaoDocumento() != SituacaoDocumento.CANCELADO.getId() || notaDetalhes.getSituacaoDocumento() != SituacaoDocumento.INUTILIZADO.getId() ? Format.decimal2(notaDetalhes.getValorFrete()) : "";
                oNFM.campo28 = "";
                oNFM.campo29 = Format.decimal2(notaDetalhes.getValorOutrasDespesas());
                oNFM.campo30 = "";
                oNFM.campo31 = "";
                oNFM.campo32 = notaDetalhes.getSituacaoDocumento() != SituacaoDocumento.CANCELADO.getId() || notaDetalhes.getSituacaoDocumento() != SituacaoDocumento.INUTILIZADO.getId() ? Format.decimal2(notaDetalhes.getValorIPI()) : "";
                oNFM.campo33 = Format.decimal2(notaDetalhes.getValorICMSST());
                oNFM.campo34 = "0.00";
                oNFM.campo35 = notaDetalhes.getSituacaoDocumento() != SituacaoDocumento.CANCELADO.getId() || notaDetalhes.getSituacaoDocumento() != SituacaoDocumento.INUTILIZADO.getId() ? Format.decimal2(notaDetalhes.getValorDesconto()) : "";
                oNFM.campo36 = Format.decimal2(notaDetalhes.getValorTotal());
                oNFM.campo37 = Format.decimal2(notaDetalhes.getValorTotalProdutos());
                oNFM.campo38 = "";
                oNFM.campo39 = "";
                oNFM.campo40 = "";
                boolean isFornecedorDistribuidor = true; //TODO | Verificar se é fornecedor distribuidor
                if (notaDetalhes.getSituacaoDocumento() != SituacaoDocumento.CANCELADO.getId() || notaDetalhes.getSituacaoDocumento() != SituacaoDocumento.INUTILIZADO.getId()) {
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
                    oNFM.campo49 = notaDetalhes.getNotaVencimento().length >= 1 ? "P" : "";
                    oNFM.campo50 = "";
                    oNFM.campo51 = "";
                    if (isFornecedorDistribuidor) {
                        oNFM.campo52 = "";
                        oNFM.campo53 = "";
                    } else {
                        oNFM.campo52 = Format.decimal2(notaDetalhes.getValorBaseCalculoPISCOFINS());
                        oNFM.campo53 = Format.decimal2(notaDetalhes.getValorBaseCalculoPISCOFINS());
                    }
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
                oNFM.campo65 = notaDetalhes.getObservacao() != null ? notaDetalhes.getObservacao().replace("\n", " ").replace("|", " ") : "";
                oNFM.campo66 = "";
                oNFM.campo67 = notaDetalhes.getChaveNFE();
                oNFM.campo68 = notaDetalhes.getSituacaoDocumento() != SituacaoDocumento.CANCELADO.getId() ? "0.00" : "";
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
                oNFM.campo89 = Format.data(notaDetalhes.getDataEmissao(), "yyyyMMdd");
                double somaValorFcpSt = notaDetalhes.getProdutos().stream().filter(oRegistro -> oRegistro.getCSTICMS() != SituacaoTributaria.SUBSTITUIDO.getId()).mapToDouble(ProdutoDTO::getValorFCPST).sum();
                oNFM.campo90 = (somaValorFcpSt > 0.0D) ? Format.decimal2(somaValorFcpSt) : "0.00";
                exportacao.qtdRegistro++;
                arquivo.write(oNFM.getStringLayout175());

                List<ProdutoDTO> produtos = notaDetalhes.getProdutos();
                int remigeTributarioLoja = notaDetalhes.getIdRegimeTributario();
                for (ProdutoDTO produto : produtos) {
                    FortesPNMVO oPNM = new FortesPNMVO();
                    oPNM.campo1 = "PNM";
                    oPNM.campo2 = produto.getIdProduto().toString();
                    oPNM.campo3 = produto.getCFOP().replace(".", "").replace(",", ".");
                    oPNM.campo4 = "";
                    oPNM.campo5 = produto.getOrigemMercadoria().toString();
                    if (remigeTributarioLoja != TipoEmpresa.LUCRO_REAL.getId() && remigeTributarioLoja != TipoEmpresa.LUCRO_PRESUMIDO
                            .getId()) {
                        oPNM.campo6 = "";
                    } else {
                        oPNM.campo6 = Format.number(produto.getCSTICMS().toString(), 2);
                    }
                    oPNM.campo7 = produto.getCodEmbalagem();
                    oPNM.campo8 = Format.decimal2(produto.getQuantidadeProduto());
                    oPNM.campo9 = Format.decimal2(produto.getValorTotalBruto());
                    oPNM.campo10 = Format.decimal2(produto.getValorIPI());
                    Optional<SituacaoTributaria> cst = Optional.ofNullable(SituacaoTributaria.getById(produto.getCSTICMS()));
                    Optional<SituacaoOperacaoSimplesNacional> optCsosn = Optional.ofNullable(SituacaoOperacaoSimplesNacional.getById(produto.getCSTICMS()));
                    if (cst.isPresent()) {
                        if ((SituacaoTributaria) cst.get() == SituacaoTributaria.OUTRAS) {
                            oPNM.campo11 = "3";
                        } else {
                            oPNM.campo11 = "";
                        }
                    } else if (optCsosn.isPresent()) {
                        switch ((SituacaoOperacaoSimplesNacional) optCsosn.get()) {
                            case TRIBUTADA_COM_CREDITO_ICMS:
                            case TRIBUTADA_COM_CREDITO_ICMS_ST:
                                oPNM.campo11 = "1";
                                break;
                            case ISENCAO_ICMS_ST:
                            case IMUNE:
                            case NAO_TRIBUTADA:
                            case TRIBUTADA_SEM_CREDITO_ICMS_ST:
                            case TRIBUTADA_SEM_CREDITO_ICMS:
                            case ISENCAO_ICMS:
                                oPNM.campo11 = "2";
                                break;
                            case COBRADO_ANTERIORMENTE:
                            case OUTRAS:
                                oPNM.campo11 = "3";
                                break;
                            default:
                                oPNM.campo11 = "";
                                break;
                        }
                    }

                    if (oAliquotaDAO.isIsento(produto.getCSTICMS())) {
                        oPNM.campo17 = "";
                        oPNM.campo19 = "0.00";
                    } else if (oAliquotaDAO.isOutras(produto.getCSTICMS())) {
                        oPNM.campo17 = "";
                        oPNM.campo19 = "0.00";
                    } else if (produto.getCSTICMS() == SituacaoTributaria.ISENTO_ICMS_ST.getId() || produto.getCSTICMS() == SituacaoTributaria.SUBSTITUIDO.getId()) {
                        oPNM.campo17 = "1";
                        oPNM.campo19 = "0.00";
                    } else {
                        oPNM.campo17 = "";
                        oPNM.campo19 = "0.00";
                    }
                    boolean isGeraIcms = false; //TODO | API não retorna essa informação
                    if (isGeraIcms && produto.getCSTICMS() != SituacaoTributaria.ISENTO_ICMS_ST.getId()) {
                        if (produto.getCSTICMS() == SituacaoTributaria.ISENTO.getId()) {
                            oPNM.campo12 = Format.decimal2(0.0D);
                        } else if ("1.556,2.556,1.551,2.551,1.910,2.910".contains(produto.getCFOP())) {
                            oPNM.campo12 = Format.decimal2(0.0D);
                        } else {
                            oPNM.campo12 = Format.decimal2(produto.getValorBaseCalculoICMS());
                        }
                        oPNM.campo13 = Format.decimal2(produto.getICMS());
                    } else {
                        oPNM.campo12 = Format.decimal2(0.0D);
                        oPNM.campo13 = Format.decimal2(0.0D);
                    }
                    if (produto.getCSTICMS() == SituacaoTributaria.SUBSTITUIDO.getId()) {
                        oPNM.campo14 = Format.decimal2(produto.getValorBaseCalculoICMSSTRetido());
                        oPNM.campo15 = Format.decimal2(produto.getValorICMSSTRetido());
                        oPNM.campo16 = (produto.getValorICMSST() > 0.0D) ? "1" : "0";
                        oPNM.campo18 = "0.00";
                        oPNM.campo20 = "0.00";
                        oPNM.campo21 = "0.00";
                        //TODO | Validar Tabela Substituição Tributária
//                    } else if (produto.getICMS() == SituacaoTributaria.ISENTO_ICMS_ST.getId()) {
//                        oPNM.campo14 = "0.00";
//                        oPNM.campo15 = "0.00";
////                        oPNM.campo16 = (oNotaEntradaItemDto.getPercentualMva() > 0.0D) ? "1" : "0"; TODO | Precisa validar
//                        oPNM.campo18 = Format.decimal2(produto.getValorTotalBruto());
//                        oPNM.campo20 = Format.decimal2(produto.getValorTotalBruto());
//                        if (oNotaEntradaDto.getIdTipoCrt() == 1 && oNotaEntradaItemDto.isUtilizaTabelaSubstituicaoTributaria()) {
//                            oPNM.campo21 = Format.decimal2(oNotaEntradaItemDto.getPercentualMva() + oNotaEntradaItemDto.getPercentualMvaSimples());
//                        } else {
//                            oPNM.campo21 = Format.decimal2(oNotaEntradaItemDto.getPercentualMva());
//                        }
//                    } else {
                        oPNM.campo14 = "0.00";
                        oPNM.campo15 = "0.00";
                        oPNM.campo16 = "0";
                        oPNM.campo18 = "0.00";
                        oPNM.campo20 = Format.decimal2(produto.getValorBaseCalculoICMSST());
                        oPNM.campo21 = Format.decimal2(produto.getICMSST()); //TODO | Talvez devesse ser getPercentualMva()
                    }
                    oPNM.campo22 = "0.00";
                    oPNM.campo23 = "0.00";
                    oPNM.campo24 = Format.decimal2(produto.getValorTotalBruto());
                    oPNM.campo25 = "0.00";
                    oPNM.campo26 = Format.decimal2(produto.getICMS());
                    oPNM.campo27 = "0.00";
                    oPNM.campo28 = "0.00";
                    oPNM.campo29 = "0.00";
                    oPNM.campo30 = "0.00";
                    oPNM.campo31 = Format.decimal2(produto.getICMS());
                    oPNM.campo32 = "";
                    oPNM.campo33 = "";
                    oPNM.campo34 = "";
                    oPNM.campo35 = "";
                    oPNM.campo36 = "";
                    double aliquotaIcmsSNM = produto.getICMS();
                    String cstPisCofins = "";
                    boolean isNotaProdutor = false; //TODO | Valor fictício, realmente precisa validar se é nota de produtor
                    if (remigeTributarioLoja == TipoEmpresa.LUCRO_PRESUMIDO.getId()) {
                        oPNM.campo37 = "";
                        oPNM.campo38 = "";
                    } else if ("5.929, 6.929".contains(produto.getCFOP())) {
                        oPNM.campo37 = "49";
                        oPNM.campo38 = "49";
                        cstPisCofins = "49";
                    } else if (isNotaProdutor || "1.949,2.949,1.556,2.556,1.551,2.551,1.910,2.910,1.920,2.920,1.908,2.908,1.926,2.926".contains(produto.getCFOP())) {
                        oPNM.campo37 = "98";
                        oPNM.campo38 = "98";
                        cstPisCofins = "98";
                    } else {
                        oPNM.campo37 = Format.number(produto.getCstpiscofins().toString(), 2);
                        oPNM.campo38 = Format.number(produto.getCstpiscofins().toString(), 2);
                        cstPisCofins = Format.number(produto.getCstpiscofins().toString(), 2);
                    }
                    boolean operacaoAliqZero = (produto.getCstpiscofins() == SituacaoTributariaPisCofins.AQUISICAO_ALIQUOTA_ZERO.getId().intValue() || produto.getCstpiscofins() == SituacaoTributariaPisCofins.TRIBUTADO_ALIQUOTA_ZERO.getId().intValue());
                    double baseCalculoContribuicao = operacaoAliqZero ? (produto.getValorTotalBruto() + produto.getValorFrete()) : produto.getValorBaseCalculoPISCOFINS();
                    oPNM.campo39 = Format.decimal2(baseCalculoContribuicao);
                    oPNM.campo40 = Format.decimal2(baseCalculoContribuicao);
                    oPNM.campo41 = Format.decimal2(produto.getValorFrete());
                    oPNM.campo42 = "0.00";
                    oPNM.campo43 = "0.00";
                    oPNM.campo44 = Format.decimal2(produto.getValorTotalBruto() + produto.getValorFrete() + produto.getValorOutrasDespesas());
                    oPNM.campo45 = "";
                    oPNM.campo46 = "";
                    oPNM.campo47 = "";
                    oPNM.campo48 = "";
                    if (remigeTributarioLoja != TipoEmpresa.LUCRO_REAL.getId() && notaDetalhes.getIdRegimeTributario() != TipoEmpresa.LUCRO_PRESUMIDO
                            .getId()) {
                        oPNM.campo49 = produto.getOrigemMercadoria().toString();
                    } else {
                        oPNM.campo49 = "";
                    }
                    if (notaDetalhes.getIdRegimeTributario() != TipoEmpresa.LUCRO_REAL.getId() && notaDetalhes.getIdRegimeTributario() != TipoEmpresa.LUCRO_PRESUMIDO
                            .getId()) {
                        oPNM.campo50 = Format.number(produto.getCSTICMS().toString(), 2);
                    } else {
                        oPNM.campo50 = "";
                    }
                    oPNM.campo51 = "1";
                    oPNM.campo52 = "0.00";
                    oPNM.campo54 = "0.00";
                    oPNM.campo56 = "0.00";
                    oPNM.campo58 = "0.00";
                    if (((notaDetalhes.getIdRegimeTributario() == TipoEmpresa.LUCRO_REAL.getId() || notaDetalhes.getIdRegimeTributario() == TipoEmpresa.LUCRO_PRESUMIDO.getId()) && isFornecedorDistribuidor || produto.getCFOP().startsWith("3."))) { //TODO | Necessário verificar se fornecedor realmente é distribuidor
                        Integer[] arrayAliquotaPisCofins = {3, 4, 6, 73};
                        if (Arrays.asList(arrayAliquotaPisCofins).contains(produto.getCstpiscofins())) {
                            oPNM.campo52 = Format.decimal4(produto.getCofins()).replace(".", "").replace(",", ".");
                            oPNM.campo56 = Format.decimal4(produto.getPis()).replace(".", "").replace(",", ".");
                        }
                        Integer[] arrayValorPisCofins = {4, 6, 73};
                        if (Arrays.asList(arrayValorPisCofins).contains(produto.getCstpiscofins())) {
                            oPNM.campo54 = Format.decimal2(produto.getValorCOFINS()).replace(".", "").replace(",", ".");
                            oPNM.campo58 = Format.decimal2(produto.getValorPIS()).replace(".", "").replace(",", ".");
                        }
                    }
                    oPNM.campo53 = "";
                    oPNM.campo55 = "1";
                    oPNM.campo57 = "";
                    oPNM.campo59 = "";
                    oPNM.campo60 = "";
                    oPNM.campo61 = "";
                    oPNM.campo62 = Format.decimal2(produto.getValorOutrasDespesas());
                    String conta = "";
//                    if (oNotaEntradaItemDto.isContabilidadePadrao()) {
//                        int idContaContabilDebito = this.oParametroContabilidadeDAO.carregarContaContabilPadrao();
//                        conta = this.oExportacaoFortesDAO.getContaContabil(idContaContabilDebito);
//                        oPNM.campo63 = !conta.isEmpty() ? conta : "";
//                    } else {
//                        int idContaContabilDebito = this.oParametroContabilidadeDAO.carregarContaContabil(oNotaEntradaItemDto.getIdTipoEntrada());
//                        conta = this.oExportacaoFortesDAO.getContaContabil(idContaContabilDebito);
//                        oPNM.campo63 = !conta.isEmpty() ? conta : "";
//                    }
                    oPNM.campo63 = conta;
                    oPNM.campo64 = "0";
                    oPNM.campo65 = "";
                    oPNM.campo66 = "";
                    oPNM.campo67 = "";
                    oPNM.campo68 = "";
                    oPNM.campo69 = "";
                    Integer[] arrayCfopDerivadoPetroleo = {
                            1651, 1652, 1653, 1658, 1659, 1660, 1661, 1662, 1663, 1664,
                            2651, 2652, 2653, 2658, 2659, 2660, 2661, 2662, 2663, 2664,
                            3651, 3652, 3653};
                    oPNM.campo70 = Arrays.asList(arrayCfopDerivadoPetroleo).contains(Integer.parseInt(produto.getCFOP().replace(".", "").replace(",", "."))) ? "S" : "N";
                    oPNM.campo71 = "";
                    oPNM.campo72 = "";
                    oPNM.campo73 = "";
                    oPNM.campo74 = "";
                    if (notaDetalhes.getUfLoja() == TipoEstado.PE.getId()) {
                        oPNM.campo74 = "1";
                    }
                    oPNM.campo75 = "";
                    oPNM.campo76 = "";
                    if (exportacao.tipoData == TipoData.EMISSAO.getId()) {
                        oPNM.campo77 = Format.decimal2(produto.getValorTotalBruto());
                        if (produto.getValorTotalBruto() > 0.0D && produto.getFCP() >= 2.0D)
                            oPNM.campo78 = Format.decimal2(produto.getFCP());
//                        oPNM.campo79 = Format.decimal2(produto.getAliqOrigPerc()); TODO | Não fornece essas informações atualmente
//                        oPNM.campo80 = Format.decimal2(produto.getAliqDestPerc());
                    }
                    boolean utilizaTabelaSubstituicaoTributaria = false; //TODO | Precisa realmente se valida ou não
                    if (!Objects.equals(notaDetalhes.getEmitente().getUf(), notaDetalhes.getUfLoja()) && utilizaTabelaSubstituicaoTributaria) {
                        oPNM.campo6 = "60";
                        oPNM.campo16 = "0";
                        oPNM.campo17 = "1";
                        oPNM.campo21 = "";
                    }
//                    Integer[] aCstPisCofinsSaida = { 2, 3, 4, 5, 6, 7, 8, 9 }; TODO | Validar
//                    if (Arrays.asList(aCstPisCofinsSaida).contains(produto.getCstpiscofins())) {
//                        oPNM.campo45 = this.oExportacaoFortesDAO.getCodigoACFiscal(oNotaEntradaItemDto.getTipoNaturezaReceita(), oNotaEntradaItemDto.getCstPisCofins());
//                        oPNM.campo46 = this.oExportacaoFortesDAO.getCodigoACFiscal(oNotaEntradaItemDto.getTipoNaturezaReceita(), oNotaEntradaItemDto.getCstPisCofins());
//                        oPNM.campo47 = (this.oExportacaoFortesDAO.verificaProdepe((int)oNotaEntradaDto.getId()) == true) ? this.oExportacaoFortesDAO.getCodigoACFiscal(oNotaEntradaItemDto.getTipoNaturezaReceita(), oNotaEntradaItemDto.getCstPisCofins()) : "";
//                    } else {
//                        oPNM.campo45 = "";
//                        oPNM.campo46 = "";
//                        oPNM.campo47 = "";
//                    }
                    if (!cstPisCofins.isEmpty()) {
                        Integer[] aCstPisCofinsEntrada = {
                                50, 51, 52, 53, 54, 55, 56, 60, 61, 62,
                                63, 64, 65, 66, 71, 72, 73, 75, 99};
                        if (!Arrays.asList(aCstPisCofinsEntrada).contains(Integer.valueOf(cstPisCofins))) {
                            oPNM.campo39 = "0.00";
                            oPNM.campo40 = "0.00";
                        }
                    }
                    if (oPNM.campo16.equals("1") && produto.getFCP() > 0.0D) {
                        oPNM.campo81 = "S";
                    } else {
                        oPNM.campo81 = "N";
                    }
                    oPNM.campo82 = "";
                    boolean excluirICMSBaseCalculoPisCofins = false; //TODO | Verificar se realmente precisa excluir
                    if (excluirICMSBaseCalculoPisCofins)
                        oPNM.campo83 = Format.decimal2(produto.getICMS());
                    oPNM.campo84 = "";
                    if (!"30,60".contains(oPNM.campo6)) {
                        oPNM.campo85 = "41".contains(oPNM.campo6) ? "0.00" : Format.decimal2(produto.getValorBaseCalculoICMS()); // TODO | Confirma se essa é a base correta
                        oPNM.campo86 = Format.decimal2(produto.getValorFCP() / produto.getValorBaseCalculoICMS() * 100.0D);
                        oPNM.campo87 = Format.decimal2(produto.getValorFCP());
                    } else {
                        oPNM.campo85 = "0.00";
                        oPNM.campo86 = "0.00";
                        oPNM.campo87 = "0.00";
                    }
                    if (!"10,30,60,70,90".contains(oPNM.campo6)) {
                        oPNM.campo88 = "0.00";
                        oPNM.campo89 = "0.00";
                        oPNM.campo90 = "0.00";
                    } else {
                        oPNM.campo88 = Format.decimal2(produto.getValorBaseCalculoICMSST());
                        oPNM.campo89 = Format.decimal2(produto.getFCPST());
                        oPNM.campo90 = Format.decimal2(produto.getValorFCPST());
                    }
                    oPNM.campo91 = "N";
                    oPNM.campo92 = "";
                    oPNM.campo93 = "";
                    oPNM.campo94 = "";
                    oPNM.campo95 = "";
                    oPNM.campo96 = "";
                    oPNM.campo97 = "";
                    oPNM.campo98 = "";
                    oPNM.campo99 = "";
                    oPNM.campo100 = "";
                    oPNM.campo101 = "";
                    oPNM.campo102 = "";
                    oPNM.campo103 = "";
                    oPNM.campo104 = "";
                    oPNM.campo105 = "";
                    oPNM.campo106 = "";
                    oPNM.campo107 = "";
                    oPNM.campo108 = "";
                    oPNM.campo109 = "";
                    oPNM.campo110 = "";
                    oPNM.campo111 = "";
                    oPNM.campo112 = "";
                    oPNM.campo113 = "";
                    oPNM.campo114 = "";
                    oPNM.campo115 = "";
                    oPNM.campo116 = "";
                    oPNM.campo117 = "";
                    oPNM.campo118 = "";
                    oPNM.campo120 = "";
                    oPNM.campo121 = "";
                    oPNM.campo122 = "";
                    oPNM.campo123 = "";
                    oPNM.campo124 = "";
                    oPNM.campo125 = "";
                    oPNM.campo126 = "";
                    oPNM.campo127 = "";
                    oPNM.campo128 = "";
                    oPNM.campo129 = "";
                    oPNM.campo130 = "";
                    oPNM.campo131 = "";
                    oPNM.campo132 = "";
                    oPNM.campo133 = "";
                    oPNM.campo134 = "";
                    oPNM.campo135 = "";
                    oPNM.campo136 = "";
                    oPNM.campo137 = "";

                    exportacao.qtdRegistro++;
                    arquivo.write(oPNM.getStringLayout175());
                }

                List<ImpostoNFMDTO> impostos = this.calculaImpostoService.calculaImpostoNFM(produtos, notaDetalhes.getEmitente());
                for (ImpostoNFMDTO imposto : impostos) {
                    FortesINMVO oINM = new FortesINMVO();
                    oINM.campo1 = "INM";
                    oINM.campo2 = Format.decimal2(imposto.valorTotalOperacao());
                    oINM.campo3 = imposto.uf();
                    oINM.campo4 = imposto.cfop().replace(".", "").replace(",", "");
                    oINM.campo5 = "";
                    oINM.campo6 = Format.decimal2(imposto.baseCalculoICMS());
                    oINM.campo7 = Format.decimal2(imposto.aliquotaICMS());
                    oINM.campo8 = Format.decimal2(imposto.valorICMS());
                    oINM.campo9 = Format.decimal2(imposto.valorIsento());
                    oINM.campo10 = Format.decimal2(imposto.valorOutras());
                    oINM.campo11 = "";
                    oINM.campo12 = Format.decimal2(imposto.valorIPI());
                    oINM.campo13 = "";
                    oINM.campo14 = "";
                    oINM.campo15 = (notaDetalhes.getIdRegimeTributario() == TipoEmpresa.SIMPLES_NACIONAL.getId()) ? "S" : "N";
                    oINM.campo16 = "N";
                    if (notaDetalhes.getIdRegimeTributario() != TipoEmpresa.LUCRO_REAL.getId() && notaDetalhes.getIdRegimeTributario() != TipoEmpresa.LUCRO_PRESUMIDO.getId()) {
                        if (imposto.CSTPisCofins() == 75) {
                            oINM.campo17 = "S";
                            oINM.campo18 = "S";
                        } else {
                            oINM.campo17 = "N";
                            oINM.campo18 = "N";
                        }
                    } else {
                        oINM.campo17 = "";
                        oINM.campo18 = "";
                    }
                    oINM.campo19 = imposto.tipoOrigem().toString();
                    if (notaDetalhes.getIdRegimeTributario() != TipoEmpresa.LUCRO_REAL.getId() && notaDetalhes.getIdRegimeTributario() != TipoEmpresa.LUCRO_PRESUMIDO
                            .getId()) {
                        oINM.campo20 = "";
                        oINM.campo22 = Format.number(imposto.cst().toString(), 2);
                    } else {
                        oINM.campo20 = Format.number(imposto.cst().toString(), 2);
                        oINM.campo22 = "";
                    }
                    oINM.campo21 = "";
                    oINM.campo23 = "";
                    if (notaDetalhes.getIdRegimeTributario() == TipoEmpresa.SIMPLES_NACIONAL.getId() && (imposto.CSTPisCofins() == 4 || imposto.CSTPisCofins() == 70)) {
                        oINM.campo24 = "S";
                        oINM.campo25 = "S";
                    } else {
                        oINM.campo24 = "N";
                        oINM.campo25 = "N";
                    }
                    if (notaDetalhes.getEmitente().getRegimeTributario() != TipoEmpresa.SIMPLES_NACIONAL.getId() && imposto.FCP() > 0.0D) {
                        oINM.campo26 = "S";
                    } else {
                        oINM.campo26 = "N";
                    }
                    oINM.campo27 = "";
                    if ("30,60".contains(oINM.campo20) || notaDetalhes.getUfLoja() == TipoEstado.CE.getId()) {
                        oINM.campo28 = "";
                        oINM.campo29 = "";
                        oINM.campo30 = "";
                    } else {
                        oINM.campo28 = Format.decimal2(imposto.baseCalculoICMS());
                        oINM.campo29 = Format.decimal2(imposto.valorFCP() / imposto.baseCalculoICMS() * 100.0D);
                        oINM.campo30 = Format.decimal2(imposto.valorFCP());
                    }
                    List<Integer> cstPermitido = this.oSituacaoTributariaExportacaoFortesNotaEntradaService.get();
                    if (!oINM.campo20.isEmpty() && cstPermitido.contains(Integer.valueOf(oINM.campo20))) {
                        oINM.campo31 = Format.decimal2(imposto.baseCalculoICMSST());
                        oINM.campo32 = Format.decimal2(imposto.FCP());
                        oINM.campo33 = Format.decimal2(imposto.valorFCPST());
                    } else {
                        oINM.campo31 = "0.00";
                        oINM.campo32 = "0.00";
                        oINM.campo33 = "0.00";
                    }
                    oINM.campo34 = "";
                    exportacao.qtdRegistro++;
                    arquivo.write(oINM.getStringLayout175());
                }

                System.out.println("Fim da exportação da nota fiscal.");
            }
        } catch (Exception e) {
            Log.error(this.getClass(), e.getMessage());
            System.err.println(e.getMessage());
        }
    }
}
