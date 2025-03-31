package teste1e2;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class PdfDataProcessor {

    // extrai texto do PDF
    public static String extractTextFromPdf(String pdfPath) throws IOException {
        PDDocument document = PDDocument.load(new File(pdfPath));
        PDFTextStripper stripper = new PDFTextStripper();
        String text = stripper.getText(document);
        document.close();
        return text;
    }

    // metodo pra processar os dados do texto extraido para criar uma tabela e salvar em CSV
    
    public static List<String[]> processPdfText(String pdfText) {
        List<String[]> data = new ArrayList<>();
        String[] lines = pdfText.split("\n");
        for (String line : lines) {
            
            String[] columns = line.split("\\s{2,}"); // dividindo as colunas por espaços
            data.add(columns);
        }
        return data;
    }

    // esse metodo escreve os dados processados no formato CSV
    
    public static void writeDataToCsv(List<String[]> data, String fileName) throws IOException {
        FileWriter writer = new FileWriter(fileName);
        for (String[] row : data) {
            for (String value : row) {
                writer.append(value).append(",");
            }
            writer.append("\n");
        }
        writer.flush();
        writer.close();
        System.out.println("Dados salvos em " + fileName);
    }

    // metodo para ubstituir abreviações no CSV
    
    public static List<String[]> replaceAbbreviations(List<String[]> data) {
        for (String[] row : data) {
            for (int i = 0; i < row.length; i++) {
                if (row[i].equals("OD")) {
                    row[i] = "Óptico-Diagnóstico";
                } else if (row[i].equals("AMB")) {
                    row[i] = "Ambulatório";
                }
            }
        }
        return data;
    }

    // Compacta o CSV gerado em um arquivo ZIP
    
    public static void compressFilesToZip(String folder, String zipFileName) throws IOException {
        try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(folder + zipFileName))) {
            File fileToZip = new File(folder + "dados_tabela.csv");
            try (FileInputStream fis = new FileInputStream(fileToZip)) {
                ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
                zipOut.putNextEntry(zipEntry);

                byte[] bytes = new byte[1024];
                int length;
                while ((length = fis.read(bytes)) >= 0) {
                    zipOut.write(bytes, 0, length);
                }
                zipOut.closeEntry();
            }
        }
        System.out.println("Arquivo compactado em: " + folder + zipFileName);
    }

    public static void main(String[] args) {
        try {
            // extracao de dados do PDF
            String pdfText = extractTextFromPdf("C:\\Users\\Dandee\\eclipse-workspace\\IntuitiveCare\\downloads");

            // Processamento do texto extraido
            
            List<String[]> data = processPdfText(pdfText);

            //Substituicao das abreviacoes
            
            data = replaceAbbreviations(data);

            // salvando dados em CSV
            
            writeDataToCsv(data, "C:\\Users\\Dandee\\eclipse-workspace\\IntuitiveCare\\downloads\\dados_tabela.csv");

            // compactacao do CSV em ZIP
            
            compressFilesToZip("C:\\Users\\Dandee\\eclipse-workspace\\IntuitiveCare\\downloads", "dados_tabela.zip");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public void processarDados() {
		// TODO Auto-generated method stub
		
	}
}
