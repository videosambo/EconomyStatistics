package fi.videosambo.economystatistic.webserver.response;

import java.util.HashMap;

public class HttpResponseBuilder {

    public HttpResponse getOKResponse(byte[] body, HashMap<HttpResponseHeaderType, String> headers) {
        return new HttpResponse(HttpStatusType.SUCCESSFUL, body, headers);
    }

    public HttpResponse getNotAllowedResponse() {
        return new HttpResponse(HttpStatusType.METHOD_NOT_ALLOWED, null, new HashMap<>());
    }

    public HttpResponse getUnauthorizedResponse() {
        HashMap<HttpResponseHeaderType, String> header = new HashMap<HttpResponseHeaderType, String>() {{
            put(HttpResponseHeaderType.AUTHENTICATE, "Basic realm=\"Access to staging site\"");
        }};
        return new HttpResponse(HttpStatusType.UNAUTHORIZED, null, header);
    }

    public HttpResponse getNotFoundResponse() {
        return new HttpResponse(HttpStatusType.NOT_FOUND, null, new HashMap<>());
    }

    public HttpResponse getInternalErrorResponse() {
        return new HttpResponse(HttpStatusType.SERVER_ERROR, null, new HashMap<>());
    }

    public HttpResponse getCreatedResponse(byte[] body, HashMap<HttpResponseHeaderType, String> headers) {
        return new HttpResponse(HttpStatusType.CREATED, body, headers);
    }

    public HttpResponse getFoundResponse(String path) {
        HashMap<HttpResponseHeaderType, String> location = new HashMap<HttpResponseHeaderType, String>() {{
            put(HttpResponseHeaderType.LOCATION, path);
        }};
        return new HttpResponse(HttpStatusType.FOUND, null, location);
    }

    public HttpResponse getTeapotResponse() {
        byte[] body = "I'm a teapot".getBytes();
        return new HttpResponse(HttpStatusType.IM_TEAPOT, body, new HashMap<>());
    }

    public HttpResponse getNoContentResponse(byte[] body) {
        return new HttpResponse(HttpStatusType.NO_CONTENT, body, new HashMap<>());
    }

    public HttpResponse getPreconditionFailedResponse() {
        return new HttpResponse(HttpStatusType.PRECONDITION_FAILED, null, new HashMap<>());
    }

    public HttpResponse getBadRequestResponse() {
        return new HttpResponse(HttpStatusType.BAD_REQUEST, null, new HashMap<>());
    }
}
