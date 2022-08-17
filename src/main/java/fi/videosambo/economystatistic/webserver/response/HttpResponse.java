package fi.videosambo.economystatistic.webserver.response;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    private byte[] bodyContent;
    private final String httpVersion;

    private final HttpStatusType statusType;
    private final HashMap<String, String> headers = new HashMap<>();

    public HttpResponse(HttpStatusType statusType, byte[] bodyContent, HashMap<HttpResponseHeaderType, String> headers) {
        headers.put(HttpResponseHeaderType.SERVER, "Java HTTP Server");
        if (bodyContent != null)
            headers.put(HttpResponseHeaderType.CONTENT_LENGHT, String.valueOf(bodyContent.length));
        headers.put(HttpResponseHeaderType.DATE, getDateTimestamp());
        this.httpVersion = "HTTP/1.1";
        this.statusType = statusType;
        addBodyContent(bodyContent);
        setHeaders(headers);
    }

    private String getDateTimestamp() {
        return new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z").format(new Date());
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
