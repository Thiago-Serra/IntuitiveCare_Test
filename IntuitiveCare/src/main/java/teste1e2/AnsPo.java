package teste1e2;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class AnsPo extends BaseScraper {
	
	
	// classe para fazer o scraping e download dos pdfs
	
    private static final String URL_SITE = "https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos";
    private static final String DOWNLOAD_FOLDER = "C:/Users/Dandee/eclipse-workspace/IntuitiveCare/downloads/";
    private static final int THREAD_POOL_SIZE = 5;

    public void downloadAnexos() {
        try {
            Document doc = getDocument(URL_SITE);
            Elements links = doc.select("a[href$=.pdf]"); // seleciona os links que terminam com .pdf
            ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

            for (Element link : links) {
                String pdfUrl = link.absUrl("href");
                String fileName = link.text().replaceAll("[^a-zA-Z0-9.-]", "_") + ".pdf";

                if (fileName.toLowerCase().contains("anexo_i") || fileName.toLowerCase().contains("anexo_ii")) {
                    System.out.println("Agendando download: " + pdfUrl);
                    executor.execute(() -> downloadFile(pdfUrl, fileName, DOWNLOAD_FOLDER));
                }
            }

            executor.shutdown();
            executor.awaitTermination(10, TimeUnit.MINUTES);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
