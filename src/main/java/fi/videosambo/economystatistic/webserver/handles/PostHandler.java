package fi.videosambo.economystatistic.webserver.handles;

import fi.videosambo.economystatistic.webserver.request.HttpHandlerType;
import fi.videosambo.economystatistic.webserver.request.HttpMethodType;
import fi.videosambo.economystatistic.webserver.request.HttpRequest;
import fi.videosambo.economystatistic.webserver.response.HttpResponse;
import fi.videosambo.economystatistic.webserver.response.HttpResponseHeaderType;
import fi.videosambo.economystatistic.webserver.util.HttpMimeType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class PostHandler extends HttpHandler {

    private final String rootPath;

    public PostHandler(String rootPath) {
        this.rootPath = rootPath;
        setTypeOfHandler(HttpHandlerType.POST_HANDLER);
        addHandledMethod(HttpMethodType.POST);
    }

    @Override
    public HttpResponse processRequest(HttpRequest request) {
        File file = this.getFileOperator().getRequestedFile(this.rootPath + request.getPath());
        if (this.getFileOperator().fileExists(this.rootPath + request.getPath())) {
            return this.getResponseBuilder().getNotAllowedResponse();
        } else {
            try {
                this.getFileOperator().writeToFile(file, request.getBody());
                return this.getResponseForCreatingFile(file, request);
            } catch (IOException e) {
                return this.getResponseBuilder().getInternalErrorResponse();
            }
        }
    }

    @Override
    public boolean coversPathFromRequest(HttpRequest request) {
        return true;
    }

    private HttpResponse getResponseForCreatingFile(File file, HttpRequest request) {
        try {
            byte[] body = Files.readAllBytes(Paths.get(file.getPath()));
            HashMap<HttpResponseHeaderType, String> headers = this.getHeaders(file, request);
            return this.getResponseBuilder().getCreatedResponse(body, headers);
        } catch (IOException e) {
            return this.getResponseBuilder().getInternalErrorResponse();
        }

    }

    private HashMap<HttpResponseHeaderType, String> getHeaders(File file, HttpRequest request) {
        HttpMimeType fileType = this.getFileTypeDecoder().getFileType(file.getName());
        String locationString = this.getLocationString(request);
        return new HashMap<HttpResponseHeaderType, String>() {{
            put(HttpResponseHeaderType.CONTENT_TYPE, fileType.getValue());
            put(HttpResponseHeaderType.LOCATION, locationString);
        }};
    }

    private String getLocationString(HttpRequest request) {
        String[] partsOfFile = request.getBody().split("=");
        return request.getPath() + "/" + partsOfFile[0];
    }
}
