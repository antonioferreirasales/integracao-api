package br.com.vrfortaleza.integracaoapi.interfaces.exportacao;

import br.com.vrfortaleza.integracaoapi.config.AppProperties;
import br.com.vrfortaleza.integracaoapi.vo.FortesConfiguracaoLojaVO;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Deprecated
public class FortesDAO {
    public void salvarParametrosConfiguracao(FortesConfiguracaoLojaVO configuracao) throws IOException {
            AppProperties.setProperty("loja" + configuracao.idLoja + ".id", configuracao.idLoja);
            AppProperties.setProperty("loja" + configuracao.idLoja + ".nome", configuracao.loja);
            AppProperties.setProperty("loja" + configuracao.idLoja + ".token", configuracao.token);
            AppProperties.setProperty("loja" + configuracao.idLoja + ".empresa", configuracao.codigoEmpresa);
            AppProperties.setProperty("loja" + configuracao.idLoja + ".estabelecimento", configuracao.codigoEstabelecimento);
            AppProperties.setProperty("loja" + configuracao.idLoja + ".incidencia", configuracao.codigoIncidencia);
            AppProperties.setProperty("loja" + configuracao.idLoja + ".aliquotas", configuracao.aliquotasEspecificas);
    }

    public Set<FortesConfiguracaoLojaVO> carregarParametrosConfiguracao(Set<Integer> lojas) {
        Set<FortesConfiguracaoLojaVO> lojasParams = new HashSet<>();
        for (var loja : lojas) {
            FortesConfiguracaoLojaVO configuracao = new FortesConfiguracaoLojaVO();
            configuracao.idLoja = AppProperties.getInt("loja" + loja + ".id");
            configuracao.loja = AppProperties.getString("loja" + loja + ".nome");
            configuracao.token = AppProperties.getString("loja" + loja + ".token");
            configuracao.codigoEmpresa = AppProperties.getString("loja" + loja + ".empresa");
            configuracao.codigoEstabelecimento = AppProperties.getString("loja" + loja + ".estabelecimento");
            configuracao.codigoIncidencia = AppProperties.getInt("loja" + loja + ".incidencia");
            configuracao.aliquotasEspecificas = AppProperties.getInt("loja" + loja + ".aliquotas");
            lojasParams.add(configuracao);
        }
        return lojasParams;
    }
}
