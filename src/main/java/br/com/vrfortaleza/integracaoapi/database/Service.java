package br.com.vrfortaleza.integracaoapi.database;

import br.com.vrfortaleza.integracaoapi.config.Log;
import br.com.vrfortaleza.integracaoapi.util.EnderecosDiretorio;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public class Service {
    public static final String url = "jdbc:sqlite:" + EnderecosDiretorio.DB_FOLDER + "\\integracao.db";

    public void create() {
        var createLojasSQL = Schemas.getCreateLojasSQL();

        try (var conn = API.connect()) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("O driver é " + meta.getDriverName());
                var connCreate = conn.createStatement();
                var checkTable = checkTable(conn, "lojas");
                if (checkTable) {
                    System.out.println("Tabela lojas já existe!");
                } else {
                    System.out.println("Criando tabela lojas...");
                    connCreate.execute(createLojasSQL.toString());
                    System.out.println("Tabela lojas criada com sucesso!");
                }
        } catch (SQLException e) {
            Log.error(this.getClass(), e.getMessage());
        }
    }

    private Boolean checkTable(Connection conn, String nomeTabela) throws SQLException {
        var connCreate = conn.createStatement();
        var result = connCreate.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='" + nomeTabela + "'");
        return result.next();
    }
}
