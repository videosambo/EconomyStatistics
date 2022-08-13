package fi.videosambo.economystatistic.webserver.handles;

import fi.videosambo.economystatistic.webserver.request.HttpHandlerType;
import fi.videosambo.economystatistic.webserver.request.HttpMethodType;
import fi.videosambo.economystatistic.webserver.request.HttpRequest;
import fi.videosambo.economystatistic.webserver.response.HttpResponse;

import java.io.IOException;
import java.util.HashMap;

public class DeleteHandler extends HttpHandler {

    private final String rootPath;

    public DeleteHandler(String rootPath) {
        this.rootPath = rootPath;
        setTypeOfHandler(HttpHandlerType.DELETE_HANDLER);
        addHandledMethod(HttpMethodType.DELETE);
    }

    @Override
    public HttpResponse processRequest(HttpRequest request) {
        String fileName = getFileOperator().removeKeyFromPathIfExists(request.getPath());
        String fullFilePath = rootPath + fileName;
        if (this.getFileOperator().fileExists(fullFilePath)) {
            this.getFileOperator().deleteFile(fullFilePath);
            return getResponseBuilder().getOKResponse(null, new HashMap<>());
        } else {
            return getResponseBuilder().getNotFoundResponse();
        }
    }

    @Override
    public boolean coversPathFromRequest(HttpRequest request) {
        return true;
    }
}
