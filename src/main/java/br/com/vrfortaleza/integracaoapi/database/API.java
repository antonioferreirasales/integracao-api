package br.com.vrfortaleza.integracaoapi.database;

import br.com.vrfortaleza.integracaoapi.config.Log;
import br.com.vrfortaleza.integracaoapi.util.Arquivo;
import br.com.vrfortaleza.integracaoapi.util.EnderecosDiretorio;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class API {
    public static final String url = "jdbc:sqlite:" + EnderecosDiretorio.DB_FOLDER + "\\integracao.db";

    public static Connection connect() {
        String dbDir = EnderecosDiretorio.DB_FOLDER;
        if (!Arquivo.exists(dbDir)) {
            try {
                Arquivo.mkdir(dbDir);
            } catch (IOException e) {
                Log.error(Service.class, e.getMessage());
            }
        }

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Conectado no banco: " + url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}
