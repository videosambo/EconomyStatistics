package fi.videosambo.economystatistic.webserver.request;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRequest {

    private final HttpMethodType methodType;
    private String path;
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
        return path.split("\\?")[0];
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

    public void setPath(String path) {
        this.path = path;
    }

    public String getFullPath() {
        return path;
    }

    public HashMap<String, String> getURIParameters() {
        String[] blocks = Pattern.compile("[^&?]*?=[^&?]*").matcher(getFullPath()).results().map(MatchResult::group).toArray(String[]::new);
        HashMap<String, String> parameters = new HashMap<>();
        for (String s : blocks) {
            if (s.split("=").length != 2)
                continue;
            parameters.put(s.split("=")[0], s.split("=")[1]);
        }
        return parameters;
    }
}
