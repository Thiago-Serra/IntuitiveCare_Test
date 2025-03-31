package teste1e2;

import java.io.IOException;
import java.util.List;

public class Exec extends AnsPo {
	

	
    public static void main(String[] args) {
        try {
            // extracao de dados do PDF
            String pdfText = PdfDataProcessor.extractTextFromPdf("downloads/Anexo_I.pdf");

            // processor do texto
            List<String[]> data = PdfDataProcessor.processPdfText(pdfText);

            // substitui as abreviacoes
            data = PdfDataProcessor.replaceAbbreviations(data);

            // salva em CSV
            PdfDataProcessor.writeDataToCsv(data, "C:\\Users\\Dandee\\eclipse-workspace\\IntuitiveCare\\downloads");
            
         // compactacao
            String nomeArquivoZip = "Teste_ThiagoSerra.zip"; //
            PdfDataProcessor.compressFilesToZip("C:\\Users\\Dandee\\eclipse-workspace\\IntuitiveCare\\downloads\\", nomeArquivoZip);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}