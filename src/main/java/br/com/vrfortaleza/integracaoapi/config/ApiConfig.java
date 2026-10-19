package br.com.vrfortaleza.integracaoapi.config;

import br.com.vrfortaleza.integracaoapi.util.EnderecosDiretorio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class ApiConfig {
    private static final Path FILE = Path.of(EnderecosDiretorio.DIRECTORY, "api.properties");
    private static final String KEY = "api.configToken";

    private ApiConfig() {
    }

    public static String loadConfigToken() throws IOException {
        if (!Files.exists(FILE)) {
            return "";
        }
        Properties properties = new Properties();
        try (var input = Files.newInputStream(FILE)) {
            properties.load(input);
        }
        return properties.getProperty(KEY, "").trim();
    }

    public static void saveConfigToken(String token) throws IOException {
        String value = validate(token);
        Properties properties = new Properties();
        properties.setProperty(KEY, value);
        Files.createDirectories(FILE.getParent());
        try (var output = Files.newOutputStream(FILE)) {
            properties.store(output, "Configuracao local da API");
        }
    }

    public static String requireConfigToken() {
        try {
            return validate(loadConfigToken());
        } catch (IOException e) {
            throw new IllegalStateException("Não foi possível ler a configuração da API.", e);
        }
    }

    private static String validate(String token) {
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("Preencha o Config-Token em Sistema → Configuração → API.");
        }
        String value = token.trim();
        if (value.chars().anyMatch(c -> c < 33 || c > 126)) {
            throw new IllegalArgumentException(
                    "O Config-Token não pode conter espaços, quebras de linha ou caracteres especiais.");
        }
        return value;
    }
}
