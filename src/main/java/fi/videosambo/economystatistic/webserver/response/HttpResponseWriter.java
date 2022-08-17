package fi.videosambo.economystatistic.webserver.response;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class HttpResponseWriter {

    private final OutputStream outputStream;
    private HttpResponse response;

    public HttpResponseWriter(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void write(HttpResponse response) throws IOException {

        this.response = response;
        writeBytes((response.getHttpVersion() + " " + response.getStatusType().toString() + "\n").getBytes());

        for (HttpResponseHeaderType responseHeader : HttpResponseHeaderType.getHeaders()) {
            if (hasHeader(responseHeader.toString())) {
                String contentType;
                if (responseHeader.equals(HttpResponseHeaderType.CONTENT_TYPE)) {
                    contentType = responseHeader + ": " + response.getHeaders().get(responseHeader.toString()) + "; charset=utf-8\n";
                } else {
                    contentType = responseHeader + ": " + response.getHeaders().get(responseHeader.toString()) + "\n";
                }
                writeBytes(contentType.getBytes());
            }
        }
        if (response.getBodyContent() != null) {
            writeBytes(("\r\n").getBytes());
            writeBytes(response.getBodyContent());
        }
    }

    private void writeBytes(byte[] bytes) throws IOException {
        outputStream.write(bytes);
    }

    private boolean hasHeader(String header) {
        try {
            return (this.response.getHeaders().containsKey(header));
        } catch (NullPointerException e) {
            return false;
        }
    }
}
