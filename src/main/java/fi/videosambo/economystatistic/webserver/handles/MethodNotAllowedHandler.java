package fi.videosambo.economystatistic.webserver.handles;

import fi.videosambo.economystatistic.webserver.request.HttpHandlerType;
import fi.videosambo.economystatistic.webserver.request.HttpMethodType;
import fi.videosambo.economystatistic.webserver.request.HttpRequest;
import fi.videosambo.economystatistic.webserver.response.HttpResponse;

import java.io.IOException;

public class MethodNotAllowedHandler extends HttpHandler{

    public MethodNotAllowedHandler() {
        setTypeOfHandler(HttpHandlerType.NOT_ALLOWED_HANDLER);
        addHandledMethod(HttpMethodType.INVALID);
    }

    @Override
    public HttpResponse processRequest(HttpRequest request) throws IOException {
        return getResponseBuilder().getNotAllowedResponse();
    }

    @Override
    public boolean coversPathFromRequest(HttpRequest request) {
        return true;
    }
}
