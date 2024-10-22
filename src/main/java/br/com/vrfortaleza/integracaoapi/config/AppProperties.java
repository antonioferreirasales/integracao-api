package br.com.vrfortaleza.integracaoapi.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;
import br.com.vrfortaleza.integracaoapi.util.Arquivo;
import br.com.vrfortaleza.integracaoapi.util.EnderecosDiretorio;
import br.com.vrfortaleza.integracaoapi.util.Folder;
import br.com.vrfortaleza.integracaoapi.util.SistemaOperacional;

public class AppProperties {
    private static Properties oProperty = null;

    static {
        try {
            verficarProperties();
            FileInputStream oFile = new FileInputStream(new File(EnderecosDiretorio.FILE_PROPERTIES));
            oProperty = new Properties();
            oProperty.load(oFile);
            Log.LEVEL = getString("log.nivel", "off");
            Log.setLogLevel();
            String[] exclusao = getString("log.exclusao", "").split(",", -1);
            Collections.addAll(Log.exclusoes, exclusao);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    public AppProperties() {
        new AppProperties(false);
    }

    public AppProperties(boolean forcePropertiesUserDir) {
        try {
            FileInputStream oFile;
            verficarProperties(forcePropertiesUserDir);
            if (forcePropertiesUserDir) {
                oFile = new FileInputStream(new File(SistemaOperacional.getHome() + "/.vr/vr.properties"));
            } else {
                oFile = new FileInputStream(new File(EnderecosDiretorio.FILE_PROPERTIES));
            }
            oProperty = new Properties();
            oProperty.load(oFile);
            Log.LEVEL = getString("log.nivel", "off");
            Log.setLogLevel();
            String[] exclusao = getString("log.exclusao", "").split(",", -1);
            Log.exclusoes.addAll(Arrays.asList(exclusao));
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    private static <T> T get(String i_chave, Class<T> i_tipo) {
        Object value = oProperty.get(i_chave);
        if (value == null)
            return null;
        return i_tipo.cast(value);
    }

    private static <T> T get(String i_chave, Class<T> i_tipo, T i_valorpadrao) {
        String value = oProperty.getProperty(i_chave, String.valueOf(i_valorpadrao)).trim();
        value = value.isEmpty() ? String.valueOf(i_valorpadrao) : value;
        if (i_tipo.isAssignableFrom(int.class))
            return (T)Integer.valueOf(value);
        if (i_tipo.isAssignableFrom(long.class))
            return (T)Long.valueOf(value);
        if (i_tipo.isAssignableFrom(boolean.class))
            return (T)Boolean.valueOf(value);
        if (i_tipo.isAssignableFrom(double.class))
            return (T)Double.valueOf(value);
        return (T)value;
    }

    public static String getString(String i_propriedade) {
        return getString(i_propriedade, "");
    }

    public static String getString(String i_propriedade, String i_valorpadrao) {
        return get(i_propriedade, String.class, i_valorpadrao);
    }

    public static boolean getBoolean(String i_chave) {
        return getBoolean(i_chave, false);
    }

    public static boolean getBoolean(String i_chave, boolean i_valorpadrao) {
        return ((Boolean)get(i_chave, (Class)boolean.class, Boolean.valueOf(i_valorpadrao))).booleanValue();
    }

    public static int getInt(String i_chave) {
        return getInt(i_chave, 0);
    }

    public static int getInt(String i_chave, int i_valorpadrao) {
        return ((Integer)get(i_chave, (Class)int.class, Integer.valueOf(i_valorpadrao))).intValue();
    }

    public static long getLong(String i_chave) {
        return getLong(i_chave, 0L);
    }

    public static long getLong(String i_chave, long i_valorpadrao) {
        return ((Long)get(i_chave, (Class)long.class, Long.valueOf(i_valorpadrao))).longValue();
    }

    public static double getDouble(String i_chave) {
        return getDouble(i_chave, 0.0D);
    }

    public static double getDouble(String i_chave, double i_valorpadrao) {
        return ((Double)get(i_chave, (Class)double.class, Double.valueOf(i_valorpadrao))).doubleValue();
    }

    public static void setProperty(String i_chave, Object i_valor) throws IOException {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(Folder.getFolder() + "/integracao-api.properties"));
            properties.setProperty(i_chave, String.valueOf(i_valor));
            properties.store(new FileOutputStream(Folder.getFolder() + "/integracao-api.properties"), null);
        } catch (Exception ex) {
            Log.error(Properties.class, ex.getMessage());
            System.out.println("Erro ao gravar arquivo properties " + ex.getMessage());
        }
    }

    public static void replace(String i_valor, String i_novoValor) {
        oProperty.replace(i_valor, i_novoValor);
    }

    public static String replaceBarras(String i_content) {
        try {
            if (SistemaOperacional.isWindows()) {
                i_content = i_content.replaceAll("((C:)([\\\\]+|\\/+)vr)", SistemaOperacional.getHome().replace("\\", "\\\\\\\\") + "\\\\\\\\.vr");
            } else {
                i_content = i_content.replaceAll("(([\\\\]+|\\/+)vr)", SistemaOperacional.getHome() + "/.vr/integracao-api");
            }
        } catch (Exception e) {
            Log.error(AppProperties.class, e.getMessage());
        }
        return i_content;
    }

    public static void replaceDiretorioProperties() throws IOException, Exception {
        String originalFile = SistemaOperacional.getHome() + "/.vr/integracao-api/integracao-api.properties";
        String content = new String(Files.readAllBytes(Paths.get(originalFile, new String[0])));
        content = replaceBarras(content);
        Files.write(Paths.get(originalFile, new String[0]), content.getBytes(StandardCharsets.UTF_8), new java.nio.file.OpenOption[0]);
    }

    public static void verficarProperties() throws Exception {
        verficarProperties(false);
    }

    public static void verficarProperties(boolean forcePropertiesUserDir) throws Exception {
        try {
            boolean achou = true;
            if (forcePropertiesUserDir) {
                if (!Arquivo.exists(SistemaOperacional.getHome() + "/.vr/vr.properties"))
                    achou = false;
            } else if (!Arquivo.exists(EnderecosDiretorio.FILE_PROPERTIES)) {
                achou = false;
            }
            if (!achou) {
                Log.error(AppProperties.class, "Arquivo de propriedades não encontrado");
            }
        } catch (Exception e) {
            Log.error(AppProperties.class, e.getMessage());
            System.exit(0);
        }
    }

    public static boolean propriedadeExiste(String i_chave) throws Exception {
        return (oProperty.getProperty(i_chave) != null);
    }
}
