package fi.videosambo.economystatistic.webserver.request;

import java.util.LinkedHashMap;

public class HttpRequestBuilder {

    public HttpRequest getRequest(HttpMethodType methodType, String path, String httpVersion, LinkedHashMap<String, String> headers, String body) {
        return new HttpRequest(methodType, path, httpVersion, headers, body);
    }

    public HttpRequest getBadRequest() {
        return new HttpRequest(null, null, null, null, "Error in parsing");
    }

    public HttpRequest getRequestForInternalError() {
        return new HttpRequest(null, null, null, null, "Error in buffering");
    }
}
