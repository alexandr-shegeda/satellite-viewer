package org.stellite.antenna.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.stellite.antenna.service.FileService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletResponse;

@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/photo", produces = "application/**")
    public void downloadPDFResource(@RequestParam(name = "timestamp", required = false) Long timestamp,
            HttpServletResponse response) {
        String fileName = fileService.getFileName(timestamp);

        try {
            File file = ResourceUtils.getFile("classpath:" + fileName);
            if (file.exists()) {
                response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
                Path path = Paths.get(file.getAbsolutePath());
                Files.copy(path, response.getOutputStream());
                response.getOutputStream().flush();
            } else {
                System.out.println("file is not found");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
