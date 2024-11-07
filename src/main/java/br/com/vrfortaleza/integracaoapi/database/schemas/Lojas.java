package br.com.vrfortaleza.integracaoapi.database.schemas;

import org.jetbrains.annotations.NotNull;

public class Lojas {
    public static @NotNull StringBuilder getCreateLojasSQL() {
        var createSQL = new StringBuilder();
        createSQL.append("CREATE TABLE IF NOT EXISTS lojas (");
        createSQL.append("id INTEGER PRIMARY KEY,");
        createSQL.append("nome TEXT NOT NULL,");
        createSQL.append("token TEXT UNIQUE NOT NULL,");
        createSQL.append("codigoempresa TEXT NOT NULL,");
        createSQL.append("codigoestabelecimento TEXT NOT NULL,");
        createSQL.append("codigoincidencia INTEGER,");
        createSQL.append("aliquotasespecificas INTEGER)");
        return createSQL;
    }
}