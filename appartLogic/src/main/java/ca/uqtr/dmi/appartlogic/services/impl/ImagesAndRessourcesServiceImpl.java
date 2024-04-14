package ca.uqtr.dmi.appartlogic.services.impl;

import ca.uqtr.dmi.appartlogic.modele.AppartementImage;
import ca.uqtr.dmi.appartlogic.repositories.AppartementImageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import ca.uqtr.dmi.appartlogic.services.ImagesAndRessourcesService;
import org.springframework.core.io.UrlResource;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class ImagesAndRessourcesServiceImpl  implements ImagesAndRessourcesService {
    @Value("${file.upload-dir}")
    private String uploadDir;

    private final AppartementImageRepository appartementImageRepository;
    private final ImagesAndRessourcesService imagesAndRessourcesService;

    public ImagesAndRessourcesServiceImpl(AppartementImageRepository appartementImageRepository, ImagesAndRessourcesService imagesAndRessourcesService) {
        this.appartementImageRepository = appartementImageRepository;
        this.imagesAndRessourcesService = imagesAndRessourcesService;
    }
    @Override
    public Optional<List<String>> uploadFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<String> fileNamesList = new ArrayList<>();
        for (MultipartFile file : multipartFiles) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Path fileStorage = Paths.get(uploadDir).toAbsolutePath().normalize().resolve(fileName);
            Files.copy(file.getInputStream(), fileStorage);
            fileNamesList.add(fileName);
        }
        return Optional.of(fileNamesList);
    }

    @Override
    public Optional<Resource> downloadFile(String filename) throws IOException {
        Path filePath = Paths.get(uploadDir).toAbsolutePath().normalize().resolve(filename);
        if (!Files.exists(filePath)) {
            throw new FileNotFoundException(filename + " was not found on the server");
        }
        return Optional.of(new UrlResource(filePath.toUri()));
    }

    @Override
    public Optional<List<Resource>> getAllFiles() throws IOException {
        List<Resource> resourcesList = Files.walk(Paths.get(uploadDir))
                .filter(Files::isRegularFile)
                .map(path -> {
                    try {
                        return new UrlResource(path.toUri());
                    } catch (MalformedURLException e) {
                        log.error("Erreur lors du chargement des images : {}", e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return Optional.of(resourcesList);
    }

    @Override
    public List<Resource> getAllFilesByAppartementId(Long appartementId) throws IOException {
        Optional<List<AppartementImage>> imagesOptional = appartementImageRepository.getAllAppartementImagesByAppartementId(appartementId);
        if (imagesOptional.isPresent()) {
            List<Resource> resourcesList = imagesOptional.get().stream()
                    .map(appartementImage -> {
                        try {
                            return new UrlResource(Paths.get(uploadDir)
                                    .toAbsolutePath().normalize()
                                    .resolve(appartementImage.getImageUrl())
                                    .toUri());
                        } catch (MalformedURLException e) {
                            log.error("Erreur lors du chargement des images : {}", e.getMessage());
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            return resourcesList;
        } else {
            return new ArrayList<>(); // Retourne une liste vide si aucune image n'est trouvée pour cet appartement
        }
    }



}
