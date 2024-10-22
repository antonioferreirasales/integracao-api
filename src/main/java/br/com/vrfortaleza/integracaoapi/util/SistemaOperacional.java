package br.com.vrfortaleza.integracaoapi.util;

import java.io.File;

public class SistemaOperacional {
    public static int get() {
        String OSName = System.getProperty("os.name");
        System.out.println("os.name " + OSName);
        if (OSName.toUpperCase().contains(TipoSistemaOperacional.WINDOWS.getDescricao()))
            return TipoSistemaOperacional.WINDOWS.getId();
        if (OSName.toUpperCase().contains(TipoSistemaOperacional.MAC.getDescricao()))
            return TipoSistemaOperacional.MAC.getId();
        return TipoSistemaOperacional.LINUX.getId();
    }

    public static boolean isMacOs() {
        try {
            return Texto.substring(System.getProperty("os.name").toUpperCase(), 0, 7).startsWith("MAC OS");
        } catch (Exception ex) {
            return true;
        }
    }

    public static boolean isWindows() {
        try {
            return Texto.substring(System.getProperty("os.name").toUpperCase(), 0, 7).equals("WINDOWS");
        } catch (Exception ex) {
            return true;
        }
    }

    public static boolean isVRUbuntu() {
        return (new File("/etc/vrubuntu-os-release")).exists();
    }

    public static String getHome() {
        return System.getProperty("user.home");
    }
}
