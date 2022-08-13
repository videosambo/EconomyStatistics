package fi.videosambo.economystatistic.webserver.response;

import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    private byte[] bodyContent;
    private String httpVersion;

    private HttpStatusType statusType;
    private HashMap<String, String> headers = new HashMap<>();

    public HttpResponse(HttpStatusType statusType, byte[] bodyContent, HashMap<HttpResponseHeaderType, String> headers) {
        this.httpVersion = "HTTP/1.1";
        this.statusType = statusType;
        addBodyContent(bodyContent);
        setHeaders(headers);
    }

    private void addBodyContent(byte[] fileContentInBytes) {
        if (fileContentInBytes != null) {
            this.bodyContent = fileContentInBytes;
        }
    }

    private void setHeaders(HashMap<HttpResponseHeaderType, String> headers) {
        for (Map.Entry<HttpResponseHeaderType, String> entry : headers.entrySet()) {
            this.headers.put(entry.getKey().toString(), entry.getValue());
        }
    }

    public byte[] getBodyContent() {
        return bodyContent;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public HttpStatusType getStatusType() {
        return statusType;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }
}
