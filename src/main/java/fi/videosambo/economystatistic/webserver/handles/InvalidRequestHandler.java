package fi.videosambo.economystatistic.webserver.handles;

import fi.videosambo.economystatistic.webserver.request.HttpHandlerType;
import fi.videosambo.economystatistic.webserver.request.HttpRequest;
import fi.videosambo.economystatistic.webserver.response.HttpResponse;

import java.io.IOException;

public class InvalidRequestHandler extends HttpHandler {

    public InvalidRequestHandler() {
        setTypeOfHandler(HttpHandlerType.INVALID_REQUEST_HANDLER);
    }

    @Override
    public boolean handles(HttpRequest request) {
        return (request.getBody() != null) && request.getBody().contains("Error in parsing");
    }

    @Override
    public HttpResponse processRequest(HttpRequest request) throws IOException {
        return getResponseBuilder().getBadRequestResponse();
    }

    @Override
    public boolean coversPathFromRequest(HttpRequest request) {
        return true;
    }
}
