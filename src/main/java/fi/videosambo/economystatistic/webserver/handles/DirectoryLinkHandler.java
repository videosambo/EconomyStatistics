package fi.videosambo.economystatistic.webserver.handles;

import fi.videosambo.economystatistic.webserver.request.HttpHandlerType;
import fi.videosambo.economystatistic.webserver.request.HttpMethodType;
import fi.videosambo.economystatistic.webserver.request.HttpRequest;
import fi.videosambo.economystatistic.webserver.response.HttpResponse;
import fi.videosambo.economystatistic.webserver.response.HttpResponseHeaderType;
import fi.videosambo.economystatistic.webserver.util.HttpMimeType;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class DirectoryLinkHandler extends HttpHandler {
    private final String rootPath;

    public DirectoryLinkHandler(String rootPath) {
        this.rootPath = rootPath;
        setTypeOfHandler(HttpHandlerType.FORM_HANDLER);
        addHandledMethods(Arrays.asList(HttpMethodType.GET, HttpMethodType.POST, HttpMethodType.PUT, HttpMethodType.DELETE));
    }

    @Override
    public HttpResponse processRequest(HttpRequest request) {
        switch (request.getMethodType()) {
            case GET:
                return this.handleGet(request);
            case POST:
                return this.handlePost(request);
            case PUT:
                return this.handlePut(request);
            case DELETE:
                return this.handleDelete(request);
            default:
                return this.getResponseBuilder().getNotFoundResponse();
        }
    }

    @Override
    public boolean coversPathFromRequest(HttpRequest request) {
        try {
            if (request.getMethodType().equals(HttpMethodType.GET) || request.getMethodType().equals(HttpMethodType.DELETE)) {
                return this.getFileOperator().filePathRefersFileContent(rootPath, request.getPath());
            } else if (request.getMethodType().equals(HttpMethodType.POST) || request.getMethodType().equals(HttpMethodType.PUT)) {
                return request.getHeaders().get("Content-Type").equals("application/x-www-form-urlencoded");
            } else {
                return false;
            }
        } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    private HttpResponse handleGet(HttpRequest request) {
        String fileName = this.getFileOperator().removeKeyFromPathIfExists(request.getPath());
        String fullFilePath = this.rootPath + fileName;
        if (this.requestedFileExists(fullFilePath)) {
            try {
                String keyFromPath = this.getKeyFromFilePath(request.getPath());
                String contentOfFile = this.getFileContentConverter().getFileContentAsString(fullFilePath);
                if (contentOfFile.contains(keyFromPath)) {
                    return this.getResponseBuilder().getOKResponse(contentOfFile.getBytes(), new HashMap<>());
                } else {
                    return this.getResponseBuilder().getNotFoundResponse();
                }
            } catch (IOException e) {
                return this.getResponseBuilder().getInternalErrorResponse();
            }
        } else {
            return this.getResponseBuilder().getNotFoundResponse();
        }
    }

    private HttpResponse handlePost(HttpRequest request) {
        PostHandler postHandler = new PostHandler(this.rootPath);
        return postHandler.processRequest(request);
    }

    private HttpResponse handlePut(HttpRequest request) {
        PutHandler putHandler = new PutHandler(this.rootPath);
        return putHandler.processRequest(request);
    }

    private HttpResponse handleDelete(HttpRequest request) {
        DeleteHandler deleteHandler = new DeleteHandler(this.rootPath);
        return deleteHandler.processRequest(request);
    }

    private String getKeyFromFilePath(String path) {
        String[] pathsOfPath = path.split("/");
        return pathsOfPath[pathsOfPath.length -1];
    }

    private boolean requestedFileExists(String fullFilePath) {
        return this.getFileOperator().fileExists(fullFilePath);
    }
}
