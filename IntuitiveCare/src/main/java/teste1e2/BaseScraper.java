package teste1e2;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

abstract class BaseScraper {
    protected static Document getDocument(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

    public void downloadFile(String fileURL, String fileName, String folder) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(fileURL);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    Path folderPath = Paths.get(folder);
                    if (!Files.exists(folderPath)) {
                        Files.createDirectories(folderPath);
                    }
                    Path filePath = folderPath.resolve(fileName);
                    try (InputStream inputStream = entity.getContent();
                         OutputStream outputStream = new FileOutputStream(filePath.toFile())) {
                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                        System.out.println("Download concluído: " + filePath);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao baixar " + fileName + ": " + e.getMessage());
        }
    }
}
