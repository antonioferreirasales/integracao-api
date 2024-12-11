package br.com.vrfortaleza.integracaoapi.config;

import java.io.IOException;
import java.util.Properties;
import br.com.vrfortaleza.integracaoapi.config.Log;

public class App {
    public static String getAppName() {
        Package p = App.class.getPackage();
        String name = p.getImplementationTitle();
        return name != null ? name : "Integração API";
    }

    public static String getAppVersion() {
        final Properties properties = new Properties();
        try {
            properties.load(App.class.getClassLoader().getResourceAsStream("project.properties"));
        } catch (IOException e) {
            Log.error(App.class, "Erro ao carregar arquivo de propriedades" + e.getMessage());
        }
        String version = properties.getProperty("version");
        return version != null ? version : "dev";
    }
}