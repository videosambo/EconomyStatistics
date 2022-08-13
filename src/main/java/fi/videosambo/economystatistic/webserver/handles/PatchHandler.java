package fi.videosambo.economystatistic.webserver.handles;

import fi.videosambo.economystatistic.webserver.request.HttpHandlerType;
import fi.videosambo.economystatistic.webserver.request.HttpMethodType;
import fi.videosambo.economystatistic.webserver.request.HttpRequest;
import fi.videosambo.economystatistic.webserver.response.HttpResponse;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class PatchHandler extends HttpHandler{

    private final String rootPath;

    public PatchHandler(String rootPath) {
        this.rootPath = rootPath;
        setTypeOfHandler(HttpHandlerType.PATCH_HANDLER);
        addHandledMethod(HttpMethodType.PATCH);
    }

    @Override
    public HttpResponse processRequest(HttpRequest request) {
        File file;
        byte[] fileContent;
        try {
            file = this.getFileOperator().getRequestedFile(this.rootPath + request.getPath());
            fileContent = this.getFileContentConverter().getFileContentFromFile(file);
        } catch (IOException e) {
            return this.getResponseBuilder().getNotFoundResponse();
        }
        try {
            String actualShaOfRequestedFile = this.getEncoder().encode(fileContent, "SHA-1");
            if (this.isValidPatchRequest(request, actualShaOfRequestedFile)) {
                return this.processValidPatchRequest(request, file);
            } else {
                return this.getResponseBuilder().getPreconditionFailedResponse();
            }
        } catch (NoSuchAlgorithmException e) {
            return this.getResponseBuilder().getPreconditionFailedResponse();
        }
    }

    @Override
    public boolean coversPathFromRequest(HttpRequest request) {
        return true;
    }

    private boolean isValidPatchRequest(HttpRequest request, String actualShaOfRequestedFile) {
        return (request.getHeaders().containsKey("If-Match") && request.getHeaders().get("If-Match").equals(actualShaOfRequestedFile));
    }

    private HttpResponse processValidPatchRequest(HttpRequest request, File file) {
        try {
            this.getFileOperator().writeToFile(file, request.getBody());
            return this.getResponseBuilder().getNoContentResponse(request.getBody().getBytes());
        } catch (IOException e) {
            return this.getResponseBuilder().getInternalErrorResponse();
        }
    }
}
