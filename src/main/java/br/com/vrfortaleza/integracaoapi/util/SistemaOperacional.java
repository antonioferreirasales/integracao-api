package br.com.vrfortaleza.integracaoapi.util;

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
}
