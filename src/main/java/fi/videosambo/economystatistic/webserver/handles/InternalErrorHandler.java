package fi.videosambo.economystatistic.webserver.handles;

import fi.videosambo.economystatistic.webserver.request.HttpHandlerType;
import fi.videosambo.economystatistic.webserver.request.HttpRequest;
import fi.videosambo.economystatistic.webserver.response.HttpResponse;

import java.io.IOException;

public class InternalErrorHandler extends HttpHandler{

    public InternalErrorHandler() {
        setTypeOfHandler(HttpHandlerType.INTERNAL_ERROR_HANDLER);
    }

    @Override
    public boolean handles(HttpRequest request) {
        return (request.getBody() != null) && (request.getBody().contains("Error in buffering"));
    }

    @Override
    public HttpResponse processRequest(HttpRequest request) {
        return getResponseBuilder().getInternalErrorResponse();
    }

    @Override
    public boolean coversPathFromRequest(HttpRequest request) {
        return true;
    }
}
