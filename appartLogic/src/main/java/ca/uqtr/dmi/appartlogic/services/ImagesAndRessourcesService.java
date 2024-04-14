package ca.uqtr.dmi.appartlogic.services;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ImagesAndRessourcesService {
    /**
     * Upload files
     * @param multipartFiles
     * @return List of file names
     * @throws IOException
     */
    Optional<List<String>> uploadFiles(List<MultipartFile> multipartFiles) throws IOException;

    /**
     * Download file
     * @param filename
     * @return Resource
     * @throws IOException
     */
    Optional<Resource> downloadFile(String filename) throws IOException;

    /**
     * Get all files of a certain Appartement
     * @param
     * @return List of resources
     * @throws IOException
     */
    List<Resource> getAllFilesByAppartementId(Long appartementId) throws IOException;

    /**
     * Get all files
     * @param
     * @return List of resources
     * @throws IOException
     */
    Optional<List<Resource>> getAllFiles() throws IOException;
}
