package io.sci.nnfl.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.CacheControl;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM;

@RestController
@RequestMapping("/file")
public class LocalFileController {

    @Value("${app.storage.local.base-path}")
    private String location;

    @RequestMapping(value = "/{fileName:.+}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getImage(@PathVariable("fileName") String fileName) {
        HttpHeaders headers = new HttpHeaders();
        Path basePath = Paths.get(location).toAbsolutePath().normalize();
        Path requestedFile = basePath.resolve(fileName).normalize();

        try {
            if (!requestedFile.startsWith(basePath) || !Files.exists(requestedFile) || !Files.isRegularFile(requestedFile)) {
                return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
            }

            byte[] media = Files.readAllBytes(requestedFile);

            headers.setCacheControl(CacheControl.noCache().getHeaderValue());

            String contentType = Files.probeContentType(requestedFile);
            if (contentType != null && !contentType.isBlank()) {
                headers.setContentType(MediaType.parseMediaType(contentType));
            } else {
                headers.setContentType(APPLICATION_OCTET_STREAM);
            }

            headers.setContentDisposition(ContentDisposition.inline()
                    .filename(requestedFile.getFileName().toString())
                    .build());

            return new ResponseEntity<>(media, headers, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}