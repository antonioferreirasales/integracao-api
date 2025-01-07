package br.com.vrfortaleza.integracaoapi.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;

public class Numero {
    public static double round(double i_valor, int i_qtd) {
        if (Double.isNaN(i_valor) || Double.isInfinite(i_valor))
            i_valor = 0.0D;
        BigDecimal valorExato = (new BigDecimal(String.valueOf(i_valor))).setScale(i_qtd, RoundingMode.HALF_UP);
        return valorExato.doubleValue();
    }

    public static double trunc(double i_valor, int i_qtd) {
        if (Double.isNaN(i_valor) || Double.isInfinite(i_valor))
            i_valor = 0.0D;
        BigDecimal valorExato = (new BigDecimal(String.valueOf(i_valor))).setScale(i_qtd, RoundingMode.DOWN);
        return valorExato.doubleValue();
    }

    public static String get(String i_valor) throws Exception {
        String valor = "";
        for (int i = 0; i < i_valor.length(); i++) {
            String letra = i_valor.substring(i, i + 1);
            if (is(letra))
                valor = valor + letra;
        }
        return valor;
    }

    public static String getDecimal(String i_valor) throws Exception {
        String valor = "";
        for (int i = 0; i < i_valor.length(); i++) {
            String letra = i_valor.substring(i, i + 1);
            if (is(letra) || letra.equals("."))
                valor = valor + letra;
        }
        return valor;
    }

    public static boolean is(String i_valor) throws Exception {
        String accepted = "1234567890";
        for (int i = 0; i < i_valor.length(); i++) {
            if (!accepted.contains(i_valor.substring(i, i + 1)))
                return false;
        }
        if (i_valor.equals(""))
            return false;
        return true;
    }

    public static String converteDecimalBinario(int i_decimal) throws Exception {
        String binario = "";
        int decimal = i_decimal;
        while (decimal > 0) {
            int resultado = decimal % 2;
            decimal /= 2;
            binario = resultado + binario;
        }
        if (binario.isEmpty())
            binario = "0";
        return Format.number(binario, 8);
    }

    public static String getValorExtenso(double i_valor) {
        String extenso = "";
        String tipo = "";
        String parte1 = "";
        String valor = Format.decimal2(i_valor);
        int cont = valor.length();
        int i = 0;
        int somar = 1;
        if (i_valor <= 0.0D)
            return "";
        while (i < cont) {
            somar = 1;
            if (!valor.substring(i, i + 1).equals(",") && !valor.substring(i, i + 1).equals(".")) {
                if (cont == 4 && i == 0) {
                    tipo = "unitario";
                } else if (cont == 4 && i == 2) {
                    tipo = "dezena";
                } else if (cont == 4 && i == 3) {
                    tipo = "unitario";
                } else if (cont == 5 && i == 0) {
                    tipo = "dezena";
                } else if (cont == 5 && i == 1) {
                    tipo = "unitario";
                } else if (cont == 5 && i == 3) {
                    tipo = "dezena";
                } else if (cont == 5 && i == 4) {
                    tipo = "unitario";
                } else if (cont == 6 && i == 0) {
                    tipo = "centena";
                } else if (cont == 6 && i == 1) {
                    tipo = "dezena";
                } else if (cont == 6 && i == 2) {
                    tipo = "unitario";
                } else if (cont == 6 && i == 4) {
                    tipo = "dezena";
                } else if (cont == 6 && i == 5) {
                    tipo = "unitario";
                } else if (cont == 8 && i == 0) {
                    tipo = "unitario";
                } else if (cont == 8 && i == 2) {
                    tipo = "centena";
                } else if (cont == 8 && i == 3) {
                    tipo = "dezena";
                } else if (cont == 8 && i == 4) {
                    tipo = "unitario";
                } else if (cont == 8 && i == 6) {
                    tipo = "dezena";
                } else if (cont == 8 && i == 7) {
                    tipo = "unitario";
                } else if (cont == 9 && i == 0) {
                    tipo = "dezena";
                } else if (cont == 9 && i == 1) {
                    tipo = "unitario";
                } else if (cont == 9 && i == 3) {
                    tipo = "centena";
                } else if (cont == 9 && i == 4) {
                    tipo = "dezena";
                } else if (cont == 9 && i == 5) {
                    tipo = "unitario";
                } else if (cont == 9 && i == 7) {
                    tipo = "dezena";
                } else if (cont == 9 && i == 8) {
                    tipo = "unitario";
                } else if (cont == 10 && i == 0) {
                    tipo = "centena";
                } else if (cont == 10 && i == 1) {
                    tipo = "dezena";
                } else if (cont == 10 && i == 2) {
                    tipo = "unitario";
                } else if (cont == 10 && i == 4) {
                    tipo = "centena";
                } else if (cont == 10 && i == 5) {
                    tipo = "dezena";
                } else if (cont == 10 && i == 6) {
                    tipo = "unitario";
                } else if (cont == 10 && i == 8) {
                    tipo = "dezena";
                } else if (cont == 10 && i == 9) {
                    tipo = "unitario";
                }
                if (tipo.equals("unitario"))
                    if (valor.substring(i, i + 1).equals("1")) {
                        parte1 = "Um ";
                    } else if (valor.substring(i, i + 1).equals("2")) {
                        parte1 = "Dois ";
                    } else if (valor.substring(i, i + 1).equals("3")) {
                        parte1 = "Tres ";
                    } else if (valor.substring(i, i + 1).equals("4")) {
                        parte1 = "Quatro ";
                    } else if (valor.substring(i, i + 1).equals("5")) {
                        parte1 = "Cinco ";
                    } else if (valor.substring(i, i + 1).equals("6")) {
                        parte1 = "Seis ";
                    } else if (valor.substring(i, i + 1).equals("7")) {
                        parte1 = "Sete ";
                    } else if (valor.substring(i, i + 1).equals("8")) {
                        parte1 = "Oito ";
                    } else if (valor.substring(i, i + 1).equals("9")) {
                        parte1 = "Nove ";
                    } else if (valor.substring(i, i + 1).equals("0")) {
                        parte1 = "";
                    }
                if (tipo.equals("dezena"))
                    if (valor.substring(i, i + 1).equals("1")) {
                        if (valor.substring(i, i + 2).equals("10")) {
                            parte1 = "Dez ";
                        } else if (valor.substring(i, i + 2).equals("11")) {
                            parte1 = "Onze ";
                        } else if (valor.substring(i, i + 2).equals("12")) {
                            parte1 = "Doze ";
                        } else if (valor.substring(i, i + 2).equals("13")) {
                            parte1 = "Treze ";
                        } else if (valor.substring(i, i + 2).equals("14")) {
                            parte1 = "Quatorze ";
                        } else if (valor.substring(i, i + 2).equals("15")) {
                            parte1 = "Quinze ";
                        } else if (valor.substring(i, i + 2).equals("16")) {
                            parte1 = "Dezesseis ";
                        } else if (valor.substring(i, i + 2).equals("17")) {
                            parte1 = "Dezesete ";
                        } else if (valor.substring(i, i + 2).equals("18")) {
                            parte1 = "Dezoito ";
                        } else if (valor.substring(i, i + 2).equals("19")) {
                            parte1 = "Dezenove ";
                        }
                        somar = 2;
                    } else if (valor.substring(i, i + 1).equals("2")) {
                        parte1 = "Vinte ";
                    } else if (valor.substring(i, i + 1).equals("3")) {
                        parte1 = "Trinta ";
                    } else if (valor.substring(i, i + 1).equals("4")) {
                        parte1 = "Quarenta ";
                    } else if (valor.substring(i, i + 1).equals("5")) {
                        parte1 = "Cinquenta ";
                    } else if (valor.substring(i, i + 1).equals("6")) {
                        parte1 = "Sessenta ";
                    } else if (valor.substring(i, i + 1).equals("7")) {
                        parte1 = "Setenta ";
                    } else if (valor.substring(i, i + 1).equals("8")) {
                        parte1 = "Oitenta ";
                    } else if (valor.substring(i, i + 1).equals("9")) {
                        parte1 = "Noventa ";
                    } else if (valor.substring(i, i + 1).equals("0")) {
                        parte1 = "";
                    }
                if (tipo.equals("centena"))
                    if (valor.substring(i, i + 1).equals("1")) {
                        if (valor.substring(i, i + 3).equals("100")) {
                            parte1 = "Cem ";
                        } else {
                            parte1 = "Cento ";
                        }
                    } else if (valor.substring(i, i + 1).equals("2")) {
                        parte1 = "Duzentos ";
                    } else if (valor.substring(i, i + 1).equals("3")) {
                        parte1 = "Trezentos ";
                    } else if (valor.substring(i, i + 1).equals("4")) {
                        parte1 = "Quatrocentos ";
                    } else if (valor.substring(i, i + 1).equals("5")) {
                        parte1 = "Quinhentos ";
                    } else if (valor.substring(i, i + 1).equals("6")) {
                        parte1 = "Seiscentos ";
                    } else if (valor.substring(i, i + 1).equals("7")) {
                        parte1 = "Setecentos ";
                    } else if (valor.substring(i, i + 1).equals("8")) {
                        parte1 = "Oitocentos ";
                    } else if (valor.substring(i, i + 1).equals("9")) {
                        parte1 = "Novecentos ";
                    } else if (valor.substring(i, i + 1).equals("0")) {
                        parte1 = "";
                    }
                if (i == 0 || valor.substring(i - 1, i).equals(",")) {
                    extenso = extenso + parte1;
                } else if (!valor.substring(i, i + 1).equals("0")) {
                    if (extenso.equals("")) {
                        extenso = extenso + parte1;
                    } else {
                        extenso = extenso + "e " + parte1;
                    }
                }
            } else if (valor.substring(i, i + 1).equals(",")) {
                if (cont == 4 && Integer.parseInt(valor.substring(i - 1, i)) == 1) {
                    extenso = extenso + "Real ";
                } else if (cont > 4 || Integer.parseInt(valor.substring(i - 1, i)) > 1) {
                    extenso = extenso + "Reais ";
                }
            } else if (valor.substring(i, i + 1).equals(".")) {
                extenso = extenso + "Mil ";
            }
            i += somar;
        }
        if (Integer.parseInt(valor.substring(cont - 2, cont)) > 1) {
            extenso = extenso + "Centavos";
        } else if (Integer.parseInt(valor.substring(cont - 2, cont)) == 1) {
            extenso = extenso + "Centavo";
        }
        return extenso.trim();
    }

    public static String removeZeroEsquerda(String i_valor) throws Exception {
        if (!is(i_valor))
            return i_valor;
        return String.valueOf(Long.parseLong(i_valor));
    }

    public static int[] toIntArray(String pStringToSplit, String pCharSplit) {
        return toIntArray(pStringToSplit.split(pCharSplit));
    }

    public static int[] toIntArray(String[] pStringToSplit) {
        return Arrays.<String>stream(pStringToSplit).mapToInt(Integer::parseInt).toArray();
    }

    public static BigDecimal asBigDecimal(String value) {
        return asBigDecimal(value, 0);
    }

    public static BigDecimal asBigDecimal(String value, int precision) {
        String zeros = "";
        while (precision > 0) {
            zeros = zeros.concat("0");
            precision--;
        }
        String pattern = "#." + zeros;
        DecimalFormat df = new DecimalFormat(pattern);
        df.setRoundingMode(RoundingMode.HALF_UP);
        return new BigDecimal(df.format(Double.parseDouble(value)).replace(",", "."));
    }
}
