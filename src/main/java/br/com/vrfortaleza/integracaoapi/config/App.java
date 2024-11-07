package br.com.vrfortaleza.integracaoapi.config;

public class App {

    public static String getAppName() {
        Package p = App.class.getPackage();
        String name = p.getImplementationTitle();
        return name != null ? name : "Integração API";
    }

    public static String getAppVersion() {
        Package p = App.class.getPackage();
        String version = p.getImplementationVersion();
        return version != null ? version : "dev";
    }
}
