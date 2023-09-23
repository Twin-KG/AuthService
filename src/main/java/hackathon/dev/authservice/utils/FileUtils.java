package hackathon.dev.authservice.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileUtils {

    private static final String defaultPath = "D:/uploads";

    public static String save(MultipartFile file, String folderPath){

        String resultPath = "";

        try {

            if(!StringUtils.isBlank(file.getOriginalFilename())) {
                // Create the directory if it doesn't exist
                File directory = new File(defaultPath + folderPath);
                if (!directory.exists()) {
                    directory.mkdirs(); // Create the directory and any necessary parent directories
                }

                // Get the original file name
                String fileName = file.getOriginalFilename();


                resultPath = folderPath + "/" + fileName;

                // Create a new file object with the desired path
                File destFile = new File(defaultPath + resultPath);
                file.transferTo(destFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultPath;
    }
}
