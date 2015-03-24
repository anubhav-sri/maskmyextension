package controllers;

import org.apache.commons.io.FileUtils;
import play.mvc.Controller;
import play.mvc.Result;

import java.io.File;
import java.net.URL;

public class FileController extends Controller {

    public Result downloadWithExtension(String filePath, String extension) {
        File newFile;
        System.out.println(filePath);
        try {
            URL fileUrl = new URL(filePath);

            String fileName = fileUrl.getFile();

            String trimmedFilenName = fileName.substring(0, fileName.lastIndexOf('.'));
            newFile = new File(String.format("repo/%s.%s", trimmedFilenName, extension));
            FileUtils.copyURLToFile(fileUrl, newFile);
        } catch (Exception e) {
            System.out.println(e);
            return ok("Internal Error!!!");
        }

        return ok(newFile);

    }

}
