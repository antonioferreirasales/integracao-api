package br.com.vrfortaleza.integracaoapi.interfaces.exportacao;

import br.com.vrfortaleza.integracaoapi.config.Log;
import br.com.vrfortaleza.integracaoapi.database.API;
import br.com.vrfortaleza.integracaoapi.vo.FortesConfiguracaoLojaVO;
import br.com.vrfortaleza.integracaoapi.vo.FortesConfiguracaoVO;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class FortesDAO {
    private final Connection conn = API.connect();

    public FortesConfiguracaoVO carregarConfiguracaoLoja() {
        String sql = "SELECT id, nome, token, codigoempresa, codigoestabelecimento, codigoincidencia, aliquotasespecificas" +
                " FROM lojas";
        FortesConfiguracaoVO configuracao = new FortesConfiguracaoVO();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            var rs = pstmt.executeQuery();
            while (rs.next()) {
                var oloja = new FortesConfiguracaoLojaVO();
                oloja.idLoja = rs.getInt("id");
                oloja.loja = rs.getString("nome");
                oloja.token = rs.getString("token");
                oloja.codigoEmpresa = rs.getString("codigoempresa");
                oloja.codigoEstabelecimento = rs.getString("codigoestabelecimento");
                oloja.codigoIncidencia = rs.getInt("codigoincidencia");
                oloja.aliquotasEspecificas = rs.getInt("aliquotasespecificas");
                configuracao.vLoja.add(oloja);
            }
        } catch (Exception e) {
            Log.error(this.getClass(), e.getMessage());
        }
        return configuracao;
    }
}
