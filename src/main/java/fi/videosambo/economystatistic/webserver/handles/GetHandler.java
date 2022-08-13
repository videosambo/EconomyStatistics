package fi.videosambo.economystatistic.webserver.handles;

import fi.videosambo.economystatistic.webserver.request.HttpHandlerType;
import fi.videosambo.economystatistic.webserver.request.HttpMethodType;
import fi.videosambo.economystatistic.webserver.request.HttpRequest;
import fi.videosambo.economystatistic.webserver.response.HttpResponse;
import fi.videosambo.economystatistic.webserver.response.HttpResponseHeaderType;
import fi.videosambo.economystatistic.webserver.util.HttpMimeType;
import fi.videosambo.economystatistic.webserver.util.RangeRequestResponder;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class GetHandler extends HttpHandler {

    private final String rootPath;
    private final RangeRequestResponder requestResponder;

    public GetHandler(String rootPath) {
        this.rootPath = rootPath;
        requestResponder = new RangeRequestResponder(rootPath, getFileOperator(), getFileContentConverter(), getFileTypeDecoder());
        setTypeOfHandler(HttpHandlerType.GET_HANDLER);
        addHandledMethod(HttpMethodType.GET);
    }

    @Override
    public HttpResponse processRequest(HttpRequest request) {
        System.out.println("Looking for file " + rootPath + request.getPath());
        if (this.getFileOperator().fileExists(this.rootPath + request.getPath())) {
            try {
                System.out.println("Found file, returning " + rootPath + request.getPath());
                return this.getResponse(request);
            } catch (IOException e) {
                return this.getResponseBuilder().getInternalErrorResponse();
            }
        } else {
            return this.getResponseBuilder().getNotFoundResponse();
        }
    }

    @Override
    public boolean coversPathFromRequest(HttpRequest request) {
        return true;
    }

    private HttpResponse getResponse(HttpRequest request) throws IOException {
        if (isRangeRequest(request)) {
            return requestResponder.getRangeResponse(request);
        } else {
            return getFullResponse(request);
        }
    }

    private HttpResponse getFullResponse(HttpRequest request) throws IOException {
        File file = this.getFileOperator().getRequestedFile(this.rootPath + request.getPath());
        byte[] body = this.getFileContentConverter().getFileContentFromFile(file);
        HttpMimeType fileType = this.getFileTypeDecoder().getFileType(file.getName());
        HashMap<HttpResponseHeaderType, String> headers = new HashMap<HttpResponseHeaderType, String>() {{
            put(HttpResponseHeaderType.CONTENT_TYPE, fileType.getValue());
            put(HttpResponseHeaderType.SERVER, "Java HTTP Server");
            //put(HttpResponseHeaderType.CONTENT_LENGHT, String.valueOf(body.length));
        }};
        return this.getResponseBuilder().getOKResponse(body, headers);
    }

    private boolean isRangeRequest(HttpRequest request) {
        return (request.getHeaders().containsKey("Range"));
    }

}
