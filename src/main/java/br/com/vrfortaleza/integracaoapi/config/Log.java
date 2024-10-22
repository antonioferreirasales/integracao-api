package br.com.vrfortaleza.integracaoapi.config;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

import br.com.vrfortaleza.integracaoapi.util.EnderecosDiretorio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.com.vrfortaleza.integracaoapi.util.Arquivo;

public class Log<T> {
    public static String LEVEL = "off";
    private Logger LOGGER;
    public static final HashSet<String> exclusoes = new HashSet<>();

    public Log(Class i_classe) {
        this.LOGGER = LoggerFactory.getLogger(i_classe);
    }

    private static void logRotate() {
        FileFilter filter = new FileFilter() {
            public boolean accept(File file) {
                return file.getName().endsWith(".log");
            }
        };
        File folder = new File(EnderecosDiretorio.LOG_FOLDER);
        File[] listOfFiles = folder.listFiles(filter);
        Long hoje = Calendar.getInstance().getTimeInMillis();
        File[] var4 = listOfFiles;
        int listLength = listOfFiles.length;

        for(int var6 = 0; var6 < listLength; ++var6) {
            File file = var4[var6];

            try {
                BasicFileAttributes attr = Files.readAttributes(Paths.get(file.getPath()), BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
                Long diaArquivo = attr.creationTime().toMillis();
                long diff = hoje - diaArquivo;
                long diffEmDias = diff / 86400000L;
                if (diffEmDias >= 30L) {
                    file.delete();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void setLogLevel() {
        String data = (new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss")).format(new Date());
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", LEVEL);

        try {
            System.setProperty("org.slf4j.simpleLogger.logFile", EnderecosDiretorio.DIRECTORY + "/log/" + data + "_" + "integracao-api" + ".log");
        } catch (Exception var2) {
            System.setProperty("org.slf4j.simpleLogger.logFile", EnderecosDiretorio.LOG_FOLDER + data + "_" + "integracao-api" + ".log");
        }

    }

    public void info(String message) {
        this.LOGGER.info(message);
    }

    public void debug(String message) {
        this.LOGGER.debug(message);
    }

    public void trace(String message) {
        this.LOGGER.trace(message);
    }

    public void error(String message) {
        this.LOGGER.error(message);
    }

    public static void info(Class clazz, String message) {
        LoggerFactory.getLogger(clazz).info(message);
    }

    public static void debug(Class clazz, String message) {
        LoggerFactory.getLogger(clazz).debug(message);
    }

    public static void trace(Class clazz, String message) {
        LoggerFactory.getLogger(clazz).trace(message);
    }

    public static void error(Class clazz, String message) {
        LoggerFactory.getLogger(clazz).error(message);
    }

    static {
        try {
            if (!Arquivo.exists(EnderecosDiretorio.LOG_FOLDER)) {
                Arquivo.mkdir(EnderecosDiretorio.LOG_FOLDER);
            }
        } catch (Exception var1) {
            Exception ex = var1;
            System.err.println(ex.getMessage());
        }

        logRotate();
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "off");
        System.setProperty("org.slf4j.simpleLogger.showDateTime", "true");
        System.setProperty("org.slf4j.simpleLogger.dateTimeFormat", "[dd/MM/Y HH:mm:ss.S]");
        System.setProperty("org.slf4j.simpleLogger.showShortLogName", "true");
        System.setProperty("org.slf4j.simpleLogger.levelInBrackets", "true");
    }
}
