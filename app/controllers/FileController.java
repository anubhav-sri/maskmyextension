package controllers;

import org.apache.commons.io.FileUtils;
import play.mvc.Controller;
import play.mvc.Result;

import java.io.File;
import java.net.URL;

public class FileController extends Controller {

    public Result downloadWithExtension(String filePath, String extension) {
        File newFile = null;
        System.out.println(filePath);
        try {
            URL fileUrl = new URL(filePath);

            String fileName = fileUrl.getFile();

            String trimmedFileName = fileName.substring(0, fileName.lastIndexOf('.'));
            newFile = File.createTempFile(trimmedFileName, "." + extension);
            newFile.deleteOnExit();
            FileUtils.copyURLToFile(fileUrl, newFile);
        } catch (Exception e) {
            newFile.delete();
            System.out.println(e);
            return ok("Internal Error!!!");
        }

        return ok(newFile);

    }

}
