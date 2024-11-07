package br.com.vrfortaleza.integracaoapi.database;

import br.com.vrfortaleza.integracaoapi.config.Log;
import br.com.vrfortaleza.integracaoapi.database.schemas.Lojas;
import br.com.vrfortaleza.integracaoapi.util.EnderecosDiretorio;
import br.com.vrfortaleza.integracaoapi.vo.FortesConfiguracaoLojaVO;

import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Set;

public class Service {
    public static final String url = "jdbc:sqlite:" + EnderecosDiretorio.DB_FOLDER + "\\integracao.db";
    private final Connection conn = API.connect();

    public void create() {
        var createLojasSQL = Lojas.getCreateLojasSQL();

        try {
            DatabaseMetaData meta = conn.getMetaData();
            System.out.println("O driver é " + meta.getDriverName());
            var connCreate = conn.createStatement();
            var checkTable = checkTable("lojas");
            if (checkTable) {
                System.out.println("Tabela lojas já existe!");
            } else {
                System.out.println("Criando tabela lojas...");
                connCreate.execute(createLojasSQL.toString());
                System.out.println("Tabela lojas criada com sucesso!");
            }
            conn.close();
        } catch (SQLException e) {
            Log.error(this.getClass(), e.getMessage());
        }
    }

    public void insertLoja(FortesConfiguracaoLojaVO fortesConfig) {
        String sql = "INSERT INTO lojas (id, nome, token, codigoempresa, codigoestabelecimento, codigoincidencia, aliquotasespecificas) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, fortesConfig.idLoja);
            pstmt.setString(2, fortesConfig.loja);
            pstmt.setString(3, fortesConfig.token);
            pstmt.setString(4, fortesConfig.codigoEmpresa);
            pstmt.setString(5, fortesConfig.codigoEstabelecimento);
            if (fortesConfig.codigoIncidencia > 0) {
                pstmt.setInt(6, fortesConfig.codigoIncidencia);
            } else {
                pstmt.setNull(6, java.sql.Types.INTEGER);
            }
            if (fortesConfig.aliquotasEspecificas >= 0) {
                pstmt.setInt(7, fortesConfig.aliquotasEspecificas);
            } else {
                pstmt.setNull(7, java.sql.Types.INTEGER);
            }

            pstmt.executeUpdate();
            System.out.println("Lojas inseridas com sucesso!");
        } catch (SQLException e) {
            Log.error(this.getClass(), e.getMessage());
        }
    }

    public void updateLoja(FortesConfiguracaoLojaVO fortesConfig) throws SQLException {
        String sql = "UPDATE lojas SET nome = ?, token = ?, codigoempresa = ?, codigoestabelecimento = ?, codigoincidencia = ?, aliquotasespecificas = ? WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        buildUpdateSQL(pstmt, fortesConfig);

        pstmt.executeUpdate();
            System.out.println("Lojas atualizadas com sucesso!");
    }

    public void updateLojas(Set<FortesConfiguracaoLojaVO> lojas) throws SQLException {
        String sql = "UPDATE lojas SET nome = ?, token = ?, codigoempresa = ?, codigoestabelecimento = ?, codigoincidencia = ?, aliquotasespecificas = ? WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
            for (FortesConfiguracaoLojaVO fortesConfig : lojas) {
                buildUpdateSQL(pstmt, fortesConfig);
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            System.out.println("Lojas atualizadas com sucesso!");
    }

    private void buildUpdateSQL(PreparedStatement pstmt, FortesConfiguracaoLojaVO fortesConfig) throws SQLException {
        pstmt.setString(1, fortesConfig.getLoja());
        pstmt.setString(2, fortesConfig.getToken());
        pstmt.setString(3, fortesConfig.getCodigoEmpresa());
        pstmt.setString(4, fortesConfig.getCodigoEstabelecimento());
        if (fortesConfig.getCodigoIncidencia() > 0) {
            pstmt.setInt(5, fortesConfig.getCodigoIncidencia());
        } else {
            pstmt.setNull(5, Types.INTEGER);
        }
        if (fortesConfig.getAliquotasEspecificas() >= 0) {
            pstmt.setInt(6, fortesConfig.getAliquotasEspecificas());
        } else {
            pstmt.setNull(6, Types.INTEGER);
        }
        pstmt.setInt(7, fortesConfig.getIdLoja());
    }

    public void deleteLojas(Set<Integer> idLojas) {
        String sql = "DELETE FROM lojas WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (int idLoja : idLojas) {
                pstmt.setInt(1, idLoja);
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            System.out.println("Lojas deletadas com sucesso!");
        } catch (SQLException e) {
            Log.error(this.getClass(), e.getMessage());
        }
    }

    public void deleteLoja(int idLoja) {
        String sql = "DELETE FROM lojas WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idLoja);
            pstmt.executeUpdate();
            System.out.println("Lojas deletadas com sucesso!");
        } catch (SQLException e) {
            Log.error(this.getClass(), e.getMessage());
        }
    }

    public FortesConfiguracaoLojaVO selectLoja(int idLoja) {
        String sql = "SELECT * FROM lojas WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idLoja);
            var rs = pstmt.executeQuery();
            if (rs.next()) {
                return new FortesConfiguracaoLojaVO(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("token"),
                        rs.getString("codigoempresa"),
                        rs.getString("codigoestabelecimento"),
                        rs.getInt("codigoincidencia"),
                        rs.getInt("aliquotasespecificas")
                );
            }
        } catch (SQLException e) {
            Log.error(this.getClass(), e.getMessage());
        }
        return null;
    }

    public Set<FortesConfiguracaoLojaVO> selectLojas() {
        String sql = "SELECT * FROM lojas";
        Set<FortesConfiguracaoLojaVO> lojas = null;
        try (Statement connStatement = conn.createStatement()) {
            var rs = connStatement.executeQuery(sql);
            lojas = new LinkedHashSet<>();

            while (rs.next()) {
                lojas.add(new FortesConfiguracaoLojaVO(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("token"),
                        rs.getString("codigoempresa"),
                        rs.getString("codigoestabelecimento"),
                        rs.getInt("codigoincidencia"),
                        rs.getInt("aliquotasespecificas"))
                );
            }
            rs.close();
        } catch (SQLException e) {
            Log.error(this.getClass(), e.getMessage());
        }
        return lojas;
    }

    public int getMaxId() {
        String sql = "SELECT MAX(id) FROM lojas";
        try (Statement connStatement = conn.createStatement()) {
            var rs = connStatement.executeQuery(sql);
            return rs.getInt(1);
        } catch (SQLException e) {
            Log.error(this.getClass(), e.getMessage());
        }
        return 1;
    }

    private Boolean checkTable(String nomeTabela) throws SQLException {
        var connCreate = conn.createStatement();
        var result = connCreate.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='" + nomeTabela + "'");
        return result.next();
    }
}
