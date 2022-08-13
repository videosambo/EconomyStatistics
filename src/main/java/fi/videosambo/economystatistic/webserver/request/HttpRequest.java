package fi.videosambo.economystatistic.webserver.request;

import java.util.LinkedHashMap;

public class HttpRequest {

    private final HttpMethodType methodType;
    private final String path;
    private final String httpVersion;
    private final LinkedHashMap<String, String> headers;
    private final String body;

    public HttpRequest(HttpMethodType methodType, String path, String httpVersion, LinkedHashMap<String, String> headers, String body) {
        this.methodType = methodType;
        this.path = path;
        this.httpVersion = httpVersion;
        this.headers = headers;
        this.body = body;
    }

    public HttpMethodType getMethodType() {
        return methodType;
    }

    public String getPath() {
        return path;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public LinkedHashMap<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }
}
