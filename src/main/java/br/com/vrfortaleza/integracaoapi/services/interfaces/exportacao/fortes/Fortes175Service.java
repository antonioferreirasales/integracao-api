package br.com.vrfortaleza.integracaoapi.services.interfaces.exportacao.fortes;

import br.com.vrfortaleza.integracaoapi.config.Log;
import br.com.vrfortaleza.integracaoapi.dao.interfaces.exportacao.fortes.FortesDAO;
import br.com.vrfortaleza.integracaoapi.util.Arquivo;
import br.com.vrfortaleza.integracaoapi.vo.ExportarFortesVO;
import br.com.vrfortaleza.integracaoapi.vo.TipoSimNao;
import br.com.vrfortaleza.integracaoapi.vo.fortes.registros.FortesCABVO;
import br.com.vrfortaleza.integracaoapi.vo.fortes.registros.FortesTRAVO;

import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Fortes175Service {
    private final FortesDAO oFortesDAO = new FortesDAO();

    public void exportar(ExportarFortesVO exportacao, int idLoja) throws Exception {
        var configuracao = oFortesDAO.carregarLoja(idLoja);
        if (configuracao == null) {
            throw new InvalidParameterException("Loja não encontrada");
        }

        String caminhoArquivo = Paths.get(exportacao.caminho, "Fortes_loja" + idLoja + ".fs").toString();
        Log.info( this.getClass(), "Exportando arquivo: " + caminhoArquivo);
        Arquivo arquivo = new Arquivo(caminhoArquivo, "w", "windows-1252");
        exportacao.qtdRegistro = 0;

        FortesCABVO oCab = exportarCabecalho(exportacao);
        ++exportacao.qtdRegistro;
        arquivo.write(oCab.getString());

        ++exportacao.qtdRegistro;
        FortesTRAVO oTRA = new FortesTRAVO();
        oTRA.campo1 = "TRA";
        oTRA. campo2 = String.valueOf(exportacao.qtdRegistro);
        arquivo.write(oTRA.getString());

        arquivo.close();

    }

    private FortesCABVO exportarCabecalho(ExportarFortesVO exportacao) throws ParseException {
        var lojaConfig = oFortesDAO.carregarLoja(exportacao.idLoja);
        var oCab = new FortesCABVO();
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyyMMdd");

        Date dataInicio = inputFormat.parse(exportacao.dataInicio);
        Date dataTermino = inputFormat.parse(exportacao.dataTermino);

        oCab.campo1 = "CAB";
        oCab.campo2 = "175";
        oCab.campo3 = "Integração API " + "0.0.0";
        oCab.campo4 = new SimpleDateFormat("yyyyMMdd").format(new Date());
        oCab.campo5 = lojaConfig.codigoEmpresa;
        oCab.campo6 = outputFormat.format(dataInicio);
        oCab.campo7 = outputFormat.format(dataTermino);
        oCab.campo8 = exportacao.dataInicio + " A " + exportacao.dataTermino;
        oCab.campo9 = lojaConfig.aliquotasEspecificas == TipoSimNao.SIM.getId() ? "S" : "N";
        return oCab;
    }
}
