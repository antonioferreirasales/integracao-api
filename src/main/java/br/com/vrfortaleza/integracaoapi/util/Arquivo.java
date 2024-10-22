package br.com.vrfortaleza.integracaoapi.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.CopyOption;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Getter;

public class Arquivo {
    @Getter
    private int lineCount = 0;

    private BufferedReader input = null;

    private PrintWriter out = null;

    public static final String LEITURA = "r";

    public static final String ESCRITA = "w";

    public static final String ADICIONAR = "a";

    public Arquivo(String i_nome, String i_tipo) throws IOException {
        switch (i_tipo.toLowerCase()) {
            case "r" -> {
                this.input = new BufferedReader(new InputStreamReader(new FileInputStream(i_nome)));
                this.lineCount = 0;
                while (this.input.ready()) {
                    this.input.readLine();
                    this.lineCount++;
                }
                this.input.close();
                this.input = new BufferedReader(new InputStreamReader(new FileInputStream(i_nome)));
            }
            case "w" -> this.out = new PrintWriter(new FileWriter(i_nome));
            case "a" -> this.out = new PrintWriter(new FileWriter(i_nome, true));
        }
    }

    public Arquivo(String i_nome, String i_tipo, String coding) throws Exception {
        switch (i_tipo.toLowerCase()) {
            case "r" -> {
                this.input = new BufferedReader(new InputStreamReader(new FileInputStream(i_nome), Charset.forName(coding)));
                this.lineCount = 0;
                while (this.input.ready()) {
                    this.input.readLine();
                    this.lineCount++;
                }
                this.input.close();
                this.input = new BufferedReader(new InputStreamReader(new FileInputStream(i_nome), Charset.forName(coding)));
            }
            case "w" -> this.out = new PrintWriter(i_nome, coding);
            case "a" -> this.out = new PrintWriter(new FileWriter(i_nome, true));
        }
    }

    public boolean ready() throws IOException {
        return this.input.ready();
    }

    public String readLine() throws IOException {
        return this.input.readLine();
    }

    public void write(List i_vTexto) {
        for (Object o : i_vTexto)
            write(o.toString());
    }

    public void write(String i_texto) {
        this.out.println(i_texto);
        this.out.flush();
    }

    public void close() throws Exception {
        if (this.input != null)
            this.input.close();
        if (this.out != null) {
            this.out.close();
            this.out.flush();
        }
    }

    public void renameTo(String i_nome, String i_novonome) throws Exception {
        File oArquivo = new File(i_nome.replace("\\", "\\\\"));
        File oNovo = new File(i_novonome.replace("\\", "\\\\"));
        if (oNovo.exists())
            oNovo.delete();
        oArquivo.renameTo(oNovo);
    }

    public static boolean delete(String i_arquivo) {
        return delete(new File(i_arquivo));
    }

    private static boolean delete(File i_arquivo) {
        if (i_arquivo.isDirectory()) {
            String[] children = i_arquivo.list();
            for (String child : children) {
                boolean success = delete(new File(i_arquivo, child));
                if (!success)
                    return false;
            }
        }
        return i_arquivo.delete();
    }

    public static void mkdir(String i_diretorio) {
        try {
            File file = new File(i_diretorio);
            file.mkdirs();
        } catch (Exception ignored) {

        }
    }

    public static byte[] readBytes(String i_arquivo) throws Exception {
        byte[] texto;
        File arquivo = new File(i_arquivo);
        BufferedInputStream leitor = new BufferedInputStream(new FileInputStream(arquivo));
        try {
            texto = new byte[(int)arquivo.length()];
            leitor.read(texto);
        } finally {
            leitor.close();
        }
        return texto;
    }

    public static void moveFile(String i_arquivoOrigem, String i_arquivoDestino) throws IOException {
        Path source = FileSystems.getDefault().getPath(i_arquivoOrigem, new String[0]);
        Path target = FileSystems.getDefault().getPath(i_arquivoDestino, new String[0]);
        Files.move(source, target, new CopyOption[] { StandardCopyOption.REPLACE_EXISTING });
    }

    public static List<String> getFiles(String i_caminho) throws IOException {
        return getFiles(i_caminho, "");
    }

    public static List<String> getFiles(String i_caminho, final String i_filtro) throws IOException {
        File[] arquivos;
        if (i_filtro.isEmpty()) {
            arquivos = (new File(i_caminho)).listFiles();
        } else {
            arquivos = (new File(i_caminho)).listFiles(new FileFilter() {
                public boolean accept(File pathname) {
                    Pattern p = Pattern.compile(i_filtro.replace("*", ".*"));
                    Matcher m = p.matcher(pathname.getName());
                    return m.matches();
                }
            });
        }
        List<String> vArquivos = new ArrayList<>();
        for (File nomeArquivo : arquivos)
            vArquivos.add(nomeArquivo.getName());
        return vArquivos;
    }

    public static boolean exists(String i_arquivo) throws SecurityException {
        return (new File(i_arquivo)).exists();
    }

    public static File download(String i_url, String i_diretorioDestino) throws Exception {
        URL url = new URL(i_url);
        String caminho = url.getPath();
        String arquivo = caminho.split("/")[(caminho.split("/")).length - 1];
        mkdir(i_diretorioDestino);
        InputStream is = url.openStream();
        FileOutputStream os = new FileOutputStream(i_diretorioDestino + arquivo);
        int umByte = 0;
        while ((umByte = is.read()) != -1)
            os.write(umByte);
        is.close();
        os.close();
        return new File(i_diretorioDestino + arquivo);
    }

    public static String getExtensao(String i_nomeArquivo) {
        String extensao = "";
        if (i_nomeArquivo.contains("."))
            extensao = i_nomeArquivo.substring(i_nomeArquivo.lastIndexOf("."), i_nomeArquivo.length());
        return extensao.toUpperCase();
    }

    public static String getRoot() {
        if (System.getProperties().getProperty("file.separator").equals("\\"))
            return "c:/";
        return "/";
    }
}
