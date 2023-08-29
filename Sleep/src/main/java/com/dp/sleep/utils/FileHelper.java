package com.dp.sleep.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Slf4j
public class FileHelper {

  public static String createFile(MultipartFile file) {
    String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
    String resultFileName = "";
    try {
      Path path = Paths.get("files/meditations/" + fileName).toAbsolutePath();
      if (!path.toFile().exists()) {
        Files.createDirectories(path);
      }
      Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
      resultFileName = path.toString();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return resultFileName;
  }
}
