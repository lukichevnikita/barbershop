package ru.lukichev.barbershop.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FileService {

    @Value("${fileStorage:C://fileStorage}")
    private String fileStoragePath;


    public String savePhoto(MultipartFile img) throws IOException {
        byte[] bytes = img.getBytes();

        Path path = Paths.get(fileStoragePath + "/" + img.getOriginalFilename());

        Files.write(path, bytes);

        return img.getOriginalFilename();
    }
}
