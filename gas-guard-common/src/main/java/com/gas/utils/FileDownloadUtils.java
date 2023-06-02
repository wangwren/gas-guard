package com.gas.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
public class FileDownloadUtils<T> {

    @Autowired
    private ExcelUtils excelUtils;

    public void downloadExcel(String fileName, Class<T> clazz, List<T> data, HttpServletResponse response) {
        try {
            excelUtils.writeDataToExcel(fileName, clazz, data);

            Path path = Paths.get(fileName);
            File file = path.toFile();

            if(!file.exists()) {
                throw new FileNotFoundException("The file does not exist.");
            }

            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            response.setContentLengthLong(file.length());

            try(InputStream inputStream = new FileInputStream(file);
                OutputStream outputStream = response.getOutputStream()) {

                byte[] buffer = new byte[8192];
                int bytesRead;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            } finally {
                Files.deleteIfExists(path);
            }
        } catch (IOException e) {
            // handle exception
        }
    }
}

