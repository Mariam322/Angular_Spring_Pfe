package tn.iit.service;

import com.google.api.client.http.FileContent;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Paths;
import java.io.OutputStream;
import java.nio.file.StandardOpenOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.StandardOpenOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.core.io.ByteArrayResource;
@RequiredArgsConstructor
@Service
public class GoogleDriveService {
 private static final Logger logger = LoggerFactory.getLogger(GoogleDriveService.class);
    private static final String PDF_DIRECTORY = "./generated-pdfs/";
    private final Drive driveService;

   public String uploadFile(String filePath, String fileName) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setName(fileName);

        java.io.File file = new java.io.File(filePath);
        FileContent mediaContent = new FileContent("application/pdf", file);

        File uploadedFile = driveService.files().create(fileMetadata, mediaContent)
                .setFields("id")
                .execute();

        return uploadedFile.getId();
    }


    public String uploadPdfContent(byte[] pdfContent, String fileName) throws IOException {
        try {
            // Sauvegarder temporairement
            Path tempPath = Paths.get(PDF_DIRECTORY + fileName);
            Files.createDirectories(tempPath.getParent());
            Files.write(tempPath, pdfContent);

            // Upload vers Drive
            String fileId = uploadFile(tempPath.toString(), fileName);

            // Nettoyer
            Files.deleteIfExists(tempPath);

            return fileId;
        } catch (IOException e) {
            throw new IOException("Erreur lors de l'upload du contenu PDF", e);
        }
    }

  public Resource downloadFile(String fileId) throws IOException {
    Path tempPath = Paths.get(PDF_DIRECTORY + "facture_" + fileId + ".pdf");
    
    try (OutputStream out = Files.newOutputStream(tempPath, 
            StandardOpenOption.CREATE, 
            StandardOpenOption.TRUNCATE_EXISTING)) {
        
        driveService.files().get(fileId)
            .executeMediaAndDownloadTo(out);
    }

    Resource resource = new UrlResource(tempPath.toUri());
    
    if (!resource.exists() || !resource.isReadable()) {
        Files.deleteIfExists(tempPath);
        throw new IOException("Fichier corrompu: " + tempPath);
    }
    
    // Programme le nettoyage du fichier temporaire
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
        try {
            Files.deleteIfExists(tempPath);
        } catch (IOException e) {
            logger.error("Erreur nettoyage fichier temporaire", e);
        }
    }));
    
    return resource;
}
}