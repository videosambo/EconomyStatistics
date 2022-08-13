package fi.videosambo.economystatistic.webserver.handles;

import fi.videosambo.economystatistic.webserver.request.HttpHandlerType;
import fi.videosambo.economystatistic.webserver.request.HttpMethodType;
import fi.videosambo.economystatistic.webserver.request.HttpRequest;
import fi.videosambo.economystatistic.webserver.response.HttpResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class PutHandler extends HttpHandler {
    private final String rootPath;

    public PutHandler(String rootPath) {
        this.rootPath = rootPath;
        setTypeOfHandler(HttpHandlerType.PUT_HANDLER);
        addHandledMethod(HttpMethodType.PUT);
    }

    @Override
    public HttpResponse processRequest(HttpRequest request) {
        String fileName = getFileOperator().removeKeyFromPathIfExists(request.getPath());
        File file = getFileOperator().getRequestedFile(this.rootPath + fileName);
        try {
            if (getFileOperator().fileExists(this.rootPath + fileName)) {
                getFileOperator().writeToFile(file, request.getBody());
                return getResponseForUpdatedFile(file);
            } else {
                this.getFileOperator().writeToFile(file, request.getBody());
                return this.getResponseForCreatedFile(file);
            }
        } catch (IOException e) {
            return this.getResponseBuilder().getInternalErrorResponse();
        }
    }

    @Override
    public boolean coversPathFromRequest(HttpRequest request) {
        return true;
    }

    private HttpResponse getResponseForCreatedFile(File file) throws IOException {
        byte[] body =  Files.readAllBytes(Paths.get(file.getPath()));
        return getResponseBuilder().getCreatedResponse(body, new HashMap<>());
    }

    private HttpResponse getResponseForUpdatedFile(File file) throws IOException {
        byte[] body =  Files.readAllBytes(Paths.get(file.getPath()));
        return getResponseBuilder().getOKResponse(body, new HashMap<>());
    }
}
