package org.satellite.antenna.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.satellite.antenna.service.FileService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletResponse;

@RestController
public class FileController {

    private static Logger LOGGER = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/photo", produces = "application/**")
    public void downloadContent(@RequestParam(name = "timestamp", required = false) Long timestamp,
            HttpServletResponse response) {
        String fileName = fileService.getFileName(timestamp);

        try {
            File file = ResourceUtils.getFile("classpath:" + fileName);
            if (file.exists()) {
                response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
                Path path = Paths.get(file.getAbsolutePath());
                Files.copy(path, response.getOutputStream());
                response.getOutputStream().flush();
                LOGGER.debug("Test message {}", fileName);
            } else {
                LOGGER.debug("File {} doesn't exist", fileName);
            }
        } catch (IOException e) {
            LOGGER.error("Couldn't prepare the file for downloading", e);
        }
    }
}
