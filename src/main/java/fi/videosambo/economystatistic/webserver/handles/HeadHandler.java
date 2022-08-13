package fi.videosambo.economystatistic.webserver.handles;

import fi.videosambo.economystatistic.webserver.request.HttpHandlerType;
import fi.videosambo.economystatistic.webserver.request.HttpMethodType;
import fi.videosambo.economystatistic.webserver.request.HttpRequest;
import fi.videosambo.economystatistic.webserver.response.HttpResponse;

import java.io.IOException;
import java.util.HashMap;

public class HeadHandler extends HttpHandler{

    private final String rootPath;

    public HeadHandler(String rootPath) {
        this.rootPath = rootPath;
        setTypeOfHandler(HttpHandlerType.HEAD_HANDLER);
        addHandledMethod(HttpMethodType.HEAD);
    }

    @Override
    public HttpResponse processRequest(HttpRequest request) throws IOException {
        return (getFileOperator().fileExists(rootPath + request.getPath())) ? getResponseBuilder().getOKResponse(null, new HashMap<>()) : getResponseBuilder().getNotFoundResponse();
    }

    @Override
    public boolean coversPathFromRequest(HttpRequest request) {
        return true;
    }
}
