package fi.videosambo.economystatistic.webserver.handles;

import fi.videosambo.economystatistic.webserver.request.HttpHandlerType;
import fi.videosambo.economystatistic.webserver.request.HttpMethodType;
import fi.videosambo.economystatistic.webserver.request.HttpRequest;
import fi.videosambo.economystatistic.webserver.response.HttpResponse;
import fi.videosambo.economystatistic.webserver.response.HttpResponseBuilder;
import fi.videosambo.economystatistic.webserver.response.HttpResponseHeaderType;
import fi.videosambo.economystatistic.webserver.util.HttpMimeType;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class DirectoryLinkHandler extends HttpHandler {

    private final String rootPath;
    private String indexFile;

    public DirectoryLinkHandler(String rootPath) {
        this.rootPath = rootPath;
        setTypeOfHandler(HttpHandlerType.DIRECTORY_LISTING_HANDLER);
        addHandledMethod(HttpMethodType.GET);
    }
    public DirectoryLinkHandler(String rootPath, String indexFile) {
        this.rootPath = rootPath;
        setTypeOfHandler(HttpHandlerType.DIRECTORY_LISTING_HANDLER);
        addHandledMethod(HttpMethodType.GET);
        this.indexFile = indexFile;
    }

    @Override
    public HttpResponse processRequest(HttpRequest request) {
        File[] files = this.getFileOperator().getFilesFromDirectory(this.rootPath);
        if (indexFile != null)
            for (File file : files) {
                if (file.getName().equals(indexFile)) {
                    return getResponseBuilder().getFoundResponse(indexFile);
                }
            }
        byte[] body = this.getBodyContent(files);
        HashMap<HttpResponseHeaderType, String> headers = this.getHeaders();
        return this.getResponseBuilder().getOKResponse(body, headers);
    }

    @Override
    public boolean coversPathFromRequest(HttpRequest request) {
        return request.getPath().equals("/");
    }

    private byte[] getBodyContent(File[] files) {
        StringBuilder body = new StringBuilder();
        body.append("<html><head></head><body>");
        for (File file : files) {
            String link = this.generateLink(file);
            body.append(link);
        }
        body.append("</body></html>");
        return body.toString().getBytes();
    }

    private String generateLink(File file) {
        return "<a href='/" + file.getName() + "'>" + file.getName() + "</a><br>";
    }

    private HashMap<HttpResponseHeaderType, String> getHeaders(){
        return new HashMap<>() {{
            put(HttpResponseHeaderType.CONTENT_TYPE, HttpMimeType.HTML.getValue());
        }};
    }
}
