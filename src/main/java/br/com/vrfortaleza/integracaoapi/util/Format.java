package br.com.vrfortaleza.integracaoapi.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Format {
    public Format() {
    }
    public static String data(String data, String formatoEntrada, String formatoSaida) throws ParseException {
        return (new SimpleDateFormat(formatoSaida)).format((new SimpleDateFormat(formatoEntrada)).parse(data));
    }
}
