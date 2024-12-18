package br.com.vrfortaleza.integracaoapi.util;

import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Texto {
    public static String repeatString(String i_texto, int i_tamanho) {
        return String.valueOf(i_texto).repeat(Math.max(0, i_tamanho));
    }

    public static String toProperCase(String s) {
        Pattern p = Pattern.compile("(^|\\W)([a-z])");
        Matcher m = p.matcher(s.toLowerCase());
        StringBuilder sb = new StringBuilder(s.length());
        while (m.find())
            m.appendReplacement(sb, m.group(1) + m.group(2).toUpperCase());
        m.appendTail(sb);
        return sb.toString();
    }

    public static String converteCaracter(String i_texto) {
        String texto = Normalizer.normalize(i_texto, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        texto = texto.replace("'", "");
        return texto;
    }

    public static String substring(String i_valor, int i_beginIndex, int i_endIndex) throws Exception {
        if (i_valor == null) {
            return "";
        }
        if (i_beginIndex >= i_valor.length())
            return "";
        if (i_endIndex > i_valor.length())
            i_endIndex = i_valor.length();
        return i_valor.substring(i_beginIndex, i_endIndex);
    }

    public static String substring(String i_valor, int i_beginIndex) throws Exception {
        return substring(i_valor, i_beginIndex, i_valor.length());
    }

    public static boolean contemCaracterEspecial(String i_texto) throws Exception {
        for (int i = 0; i < i_texto.length(); i++) {
            if ("/?!@#$%&*()-_=+[{]}:;.>,<\\\"\n".contains(String.valueOf(i_texto.charAt(i))))
                return true;
        }
        return false;
    }

    public static boolean isNullOrEmpty(String i_texto) {
        return (i_texto == null || i_texto.isEmpty());
    }

    public static String concatenar(String i_texto1, String i_texto2) throws Exception {
        return concatenar(i_texto1, i_texto2, "");
    }

    public static String concatenar(String i_texto1, String i_texto2, String i_separador) throws Exception {
        String resultado = "";
        if (!isNullOrEmpty(i_texto1))
            resultado = i_texto1;
        if (!isNullOrEmpty(i_texto2)) {
            if (!isNullOrEmpty(resultado) && !isNullOrEmpty(i_separador))
                resultado = resultado + i_separador;
            resultado = resultado + i_texto2;
        }
        return resultado;
    }

    public static String garanteFim(String i_texto, String i_fim) {
        if (isNullOrEmpty(i_texto))
            return i_fim;
        if (isNullOrEmpty(i_fim) || i_texto.endsWith(i_fim))
            return i_texto;
        return i_texto + i_fim;
    }
}
