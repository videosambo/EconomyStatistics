package fi.videosambo.economystatistic.webserver.handles;

import fi.videosambo.economystatistic.Util;
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
        if (this.getFileOperator().fileExists(this.rootPath + request.getPath())) {
            try {
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
        String rewrite = getFileContentConverter().getFileContentAsString(rootPath + request.getPath());
        rewrite = rewrite.replace("{PLAYER_LIST}", Util.getPlayerListAsHTML());
        rewrite = rewrite.replace("{ITEM_LIST}", Util.getItemListAsHTML());
        byte[] body = rewrite.getBytes();
        HttpMimeType fileType = this.getFileTypeDecoder().getFileType(request.getPath());
        HashMap<HttpResponseHeaderType, String> headers = new HashMap<>() {{
            put(HttpResponseHeaderType.CONTENT_TYPE, fileType.getValue());
        }};
        return this.getResponseBuilder().getOKResponse(body, headers);
    }

    private boolean isRangeRequest(HttpRequest request) {
        return (request.getHeaders().containsKey("Range"));
    }

}
