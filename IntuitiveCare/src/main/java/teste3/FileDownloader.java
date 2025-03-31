package teste3;

import java.io.*;
import java.net.*;
import java.util.Calendar;

import java.io.*;
import java.net.*;
import java.util.Calendar;

public class FileDownloader {

    public static void baixarArquivo(String urlStr, String destino) {
        try (InputStream inputStream = new URL(urlStr).openStream();
             FileOutputStream fileOutputStream = new FileOutputStream(destino)) {
            
            byte[] buffer = new byte[4096];
            int bytesRead;
            
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }
            
            System.out.println("Arquivo baixado com sucesso: " + destino);
        } catch (IOException e) {
            System.out.println("Erro ao baixar o arquivo: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String urlOperadoras = "https://dadosabertos.ans.gov.br/FTP/PDA/operadoras_de_plano_de_saude_ativas/Relatorio_cadop.csv";
        String destinoOperadoras = "operadoras.csv";
        
        
        // download em zip das demonstracoes cointabeis dos ultimos dois anos
        int anoAtual = Calendar.getInstance().get(Calendar.YEAR);
        int[] anos = {anoAtual - 1, anoAtual - 2};

        baixarArquivo(urlOperadoras, destinoOperadoras);

        for (int ano : anos) {
            for (int trimestre = 1; trimestre <= 4; trimestre++) {
                String urlDemonstracao = "https://dadosabertos.ans.gov.br/FTP/PDA/demonstracoes_contabeis/" + ano + "/" + trimestre + "T" + ano + ".zip";
                String destinoDemonstracao = "demonstracoes_contabeis_" + trimestre + "T" + ano + ".zip";
                
                baixarArquivo(urlDemonstracao, destinoDemonstracao);
            }
        }
    }
}

