package br.com.vrfortaleza.integracaoapi.services.interfaces.exportacao.fortes;

import br.com.vrfortaleza.integracaoapi.api.dto.EmitenteDTO;
import br.com.vrfortaleza.integracaoapi.api.dto.NotaFiscalRecebimentoDTO;
import br.com.vrfortaleza.integracaoapi.api.dto.NotaFiscalRecebimentoDetalhesDTO;
import br.com.vrfortaleza.integracaoapi.api.service.AuthService;
import br.com.vrfortaleza.integracaoapi.api.service.NotaFiscalRecebimentoDetalhesService;
import br.com.vrfortaleza.integracaoapi.api.service.NotaFiscalRecebimentoService;
import br.com.vrfortaleza.integracaoapi.config.Log;
import br.com.vrfortaleza.integracaoapi.util.Arquivo;
import br.com.vrfortaleza.integracaoapi.util.Format;
import br.com.vrfortaleza.integracaoapi.util.Texto;
import br.com.vrfortaleza.integracaoapi.vo.ExportarFortesVO;
import br.com.vrfortaleza.integracaoapi.vo.FortesConfiguracaoLojaVO;
import br.com.vrfortaleza.integracaoapi.vo.TipoContribuinteICMS;
import br.com.vrfortaleza.integracaoapi.vo.fortes.registros.FortesPARVO;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExportacaoFortesDAO {
    private List<NotaFiscalRecebimentoDTO> value = null;
    private final Set<EmitenteDTO> emitentes = new HashSet<>();
    public void exportarParticipantes(ExportarFortesVO exportacao, FortesConfiguracaoLojaVO fortesConfiguracaoVO, Arquivo arquivo) {
        System.out.println("Exportando participantes");
        try {
            for (EmitenteDTO emitente: emitentes) {
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
                oPAR.campo7 = ""; //validar
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
                oPAR.campo22 = ""; //validar
                oPAR.campo23 = Texto.substring(emitente.getCodMunicipio(), 0, 5);
                oPAR.campo24 = ""; //validar
                oPAR.campo25 = ""; //validar
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
                oPAR.campo35 = ""; //validar
                oPAR.campo36 = ""; //validar
                oPAR.campo37 = "N";
                oPAR.campo38 = ""; // validar
                oPAR.campo39 = "";
                oPAR.campo40 = "";
                exportacao.qtdRegistro++;
                arquivo.write(oPAR.getStringLayout175());
            }
        } catch (Exception e) {
            Log.error(this.getClass(), e.getMessage());
        }
    }
    public void exportarNotasRecebimento(ExportarFortesVO exportacao, FortesConfiguracaoLojaVO fortesConfiguracaoVO, Arquivo arquivo) {
        System.out.println("Exportando notas de recebimento");
        String lojaToken = fortesConfiguracaoVO.token;
        AuthService authService = new AuthService();
        try {
            String acess_token = authService.authenticate(lojaToken);
            NotaFiscalRecebimentoService recebimentoService = new NotaFiscalRecebimentoService();
            this.value = recebimentoService.getNotaFiscalRecebimento(acess_token, LocalDate.parse(exportacao.dataInicio), LocalDate.parse(exportacao.dataTermino), exportacao.tipoData);
            for (var item: value) {
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
            }
        } catch (Exception e) {
            Log.error(this.getClass(), e.getMessage());
        }
    }
}
