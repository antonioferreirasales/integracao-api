package br.com.vrfortaleza.integracaoapi.database;

import org.jetbrains.annotations.NotNull;

public class Schemas {
    public static @NotNull StringBuilder getCreateLojasSQL() {
        var sqlCreateDB = new StringBuilder();
        sqlCreateDB.append("CREATE TABLE IF NOT EXISTS lojas (");
        sqlCreateDB.append("id INTEGER PRIMARY KEY,");
        sqlCreateDB.append("nome TEXT NOT NULL,");
        sqlCreateDB.append("token TEXT NOT NULL,");
        sqlCreateDB.append("codigoempresa TEXT NOT NULL,");
        sqlCreateDB.append("codigoestabelecimento TEXT NOT NULL,");
        sqlCreateDB.append("codigoincidencia INTEGER,");
        sqlCreateDB.append("liquotasespecificas INTEGER)");
        return sqlCreateDB;
    }
}
