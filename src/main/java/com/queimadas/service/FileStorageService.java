package com.queimadas.service;

import com.queimadas.model.FileDB;
import com.queimadas.repository.FileDBRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileStorageService {

    private final FileDBRepository fileDBRepository;

    public FileDB store(MultipartFile file) throws IOException {
        String fileName = StringUtils.defaultIfBlank(file.getOriginalFilename(), file.getName());
        fileName = org.springframework.util.StringUtils.cleanPath(fileName);
        FileDB fileDb = new FileDB(fileName, file.getContentType(), file.getBytes());

        return fileDBRepository.save(fileDb);
    }

    public FileDB getFile(UUID id) {
        return fileDBRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
    }
}