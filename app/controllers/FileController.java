package controllers;

import org.apache.commons.io.FileUtils;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;

import java.io.File;
import java.net.URL;

import static play.data.Form.form;

public class FileController extends Controller {

    public Result downloadWithExtension() {
        DynamicForm dynamicForm = form().bindFromRequest();

        String filePath = dynamicForm.get("fileUrl");

        String extension = dynamicForm.get("extension");
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

        response().setContentType("application/x-apple-diskimage");
        return ok(newFile);

    }

}
