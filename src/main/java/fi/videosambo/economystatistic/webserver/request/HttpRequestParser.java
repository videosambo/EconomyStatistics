package fi.videosambo.economystatistic.webserver.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;

public class HttpRequestParser {

    private final HttpRequestBuilder requestBuilder;

    public HttpRequestParser(HttpRequestBuilder requestBuilder) {
        this.requestBuilder = requestBuilder;
    }

    public HttpRequest parse(InputStream inputStream) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String firstLine = reader.readLine();

            HttpMethodType methodType = getMethod(firstLine);
            if (methodType == null)
                methodType = HttpMethodType.INVALID;
            String path = getPath(firstLine);
            String httpVersion = getHttpVersion(firstLine);
            LinkedHashMap<String, String> headers = getHeaders(reader);

            String contentLenghtKey = headers.get("Content-Lenght");
            String body = getBody(contentLenghtKey, reader);
            return requestBuilder.getRequest(methodType, path, httpVersion, headers, body);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpMethodType getMethod(String line) {
        try {
            String[] splittedLine = line.split(" ", 3);
            return HttpMethodType.valueOf(splittedLine[0]);
        } catch (IllegalArgumentException|NullPointerException e) {
            return HttpMethodType.INVALID;
        }
    }

    private String getPath(String line) {
        String[] splittedLine = line.split(" ", 3);
        return splittedLine[1].replaceAll("[\n\r]", "");
    }

    private String getHttpVersion(String line) {
        String[] splittedLine = line.split(" ", 3);
        return splittedLine[2].replaceAll("[\n\r]", "");
    }

    private LinkedHashMap getHeaders(BufferedReader bufferedReader) throws IOException {
        LinkedHashMap<String, String> headers = new LinkedHashMap<>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            if (line.equals("")) {
                break;
            } else {
                String[] splittedLine = line.split(" ", 2);
                String key = splittedLine[0].substring(0, splittedLine[0].length() -1);
                String value = splittedLine[1].replaceAll("[\n\r]", "");
                headers.put(key, value);
            }
        }
        return headers;
    }

    private String getBody(String contentLenghtKey, BufferedReader bufferedReader) throws IOException {
        if (contentLenghtKey == null) {
            return null;
        } else {
            int contentLenght = Integer.parseInt(contentLenghtKey);
            StringBuilder body = new StringBuilder();
            for (int i = 0; i < contentLenght; i++) {
                body.append((char) bufferedReader.read());
            }
            return body.toString().trim();
        }
    }
}
