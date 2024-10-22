package br.com.vrfortaleza.integracaoapi.util;

import java.util.regex.Pattern;

public class Folder {
    private static final String JAR_LOCATION = System.getProperty("java.class.path");

    private static final boolean isLocalWindows = (!Pattern.compile("^(\\\\)").matcher(JAR_LOCATION).find() && SistemaOperacional.isWindows());

    private static final boolean isLocalLinux = (!Pattern.compile("^(\\/vr_server)").matcher(JAR_LOCATION).find() && !SistemaOperacional.isWindows() && !SistemaOperacional.isMacOs());

    private static final boolean isMacOs = SistemaOperacional.isMacOs();

    private static final boolean isVRUbuntu = SistemaOperacional.isVRUbuntu();

    public static String getDirectory() {
        if (isVRUbuntu)
            return SistemaOperacional.getHome() + "/.vr/integracao-api";
        if ((isLocalWindows || isLocalLinux) && !isMacOs)
            return Arquivo.getRoot() + "vr/integracao-api";
        return SistemaOperacional.getHome() + "/.vr/integracao-api";
    }

    public static String getFolder() {
        if (isVRUbuntu)
            return SistemaOperacional.getHome() + "/.vr/integracao-api";
        if ((isLocalWindows || isLocalLinux) && !isMacOs)
            return "/vr/integracao-api";
        return "/.vr/integracao-api";
    }

    public static String getFolderLocation() {
        if (isVRUbuntu)
            return SistemaOperacional.getHome();
        if ((isLocalWindows || isLocalLinux) && !isMacOs)
            return Arquivo.getRoot();
        return SistemaOperacional.getHome();
    }
}
