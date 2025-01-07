package br.com.vrfortaleza.integracaoapi.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class Format {
    public Format() {
    }

    public static String string(String i_texto, int i_tamanho, String i_alinhamento) {
        String texto = "";
        String espaco = "";
        if (i_texto.length() > i_tamanho) {
            texto = i_texto.substring(0, i_tamanho);
        } else {
            texto = i_texto;
        }
        if (i_alinhamento.equalsIgnoreCase("e")) {
            for (int i = 0; i < i_tamanho - texto.length(); i++)
                espaco = espaco + " ";
            return texto + espaco;
        }
        if (i_alinhamento.equalsIgnoreCase("d")) {
            for (int i = 0; i < i_tamanho - texto.length(); i++)
                espaco = espaco + " ";
            return espaco + texto;
        }
        if (i_alinhamento.equalsIgnoreCase("c")) {
            for (int i = 0; i < (i_tamanho - texto.length()) / 2; i++)
                espaco = espaco + " ";
            String retorno = espaco + texto + espaco;
            if (retorno.length() < i_tamanho)
                retorno = retorno + string(" ", i_tamanho - retorno.length());
            return retorno;
        }
        return i_texto;
    }

    public static String string(String i_texto, int i_tamanho) {
        return string(i_texto, i_tamanho, "e");
    }

    public static String data(String data, String formatoEntrada, String formatoSaida) throws ParseException {
        return (new SimpleDateFormat(formatoSaida)).format((new SimpleDateFormat(formatoEntrada)).parse(data));
    }

    public static String number(String i_valor, int i_tamanho) {
        return "0".repeat(Math.max(0, i_tamanho - i_valor.length())) + i_valor;
    }
    public static String decimal(double i_valor, int i_qtdCasasDecimais) {
        String mascara = "###,##0";
        for (int i = 0; i < i_qtdCasasDecimais; i++) {
            if (!mascara.contains("."))
                mascara = mascara + ".";
            mascara = mascara + "0";
        }
        return (new DecimalFormat(mascara)).format(Numero.round(i_valor, i_qtdCasasDecimais));
    }
    public static String decimal2(double i_valor) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("0.00", symbols);
        decimalFormat.setGroupingUsed(false);
        return decimalFormat.format(Numero.round(Numero.round(i_valor, 3), 2));
    }
}
