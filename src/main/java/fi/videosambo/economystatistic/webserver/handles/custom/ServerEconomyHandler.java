package fi.videosambo.economystatistic.webserver.handles.custom;

import fi.videosambo.economystatistic.EconomyDataObject;
import fi.videosambo.economystatistic.Util;
import fi.videosambo.economystatistic.webserver.handles.HttpHandler;
import fi.videosambo.economystatistic.webserver.request.HttpHandlerType;
import fi.videosambo.economystatistic.webserver.request.HttpMethodType;
import fi.videosambo.economystatistic.webserver.request.HttpRequest;
import fi.videosambo.economystatistic.webserver.response.HttpResponse;
import fi.videosambo.economystatistic.webserver.response.HttpResponseHeaderType;
import fi.videosambo.economystatistic.webserver.util.HttpMimeType;
import fi.videosambo.economystatistic.webserver.util.StatisticsOptionParser;

import java.io.IOException;
import java.util.HashMap;

public class ServerEconomyHandler extends HttpHandler {

    private final String rootPath;
    private final StatisticsOptionParser stats;

    public ServerEconomyHandler(String rootPath) {
        this.rootPath = rootPath;
        stats = new StatisticsOptionParser();
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
        return request.getPath().startsWith("/server.html");
    }

    private HttpResponse getResponse(HttpRequest request) throws IOException {
        String rewrite = getFileContentConverter().getFileContentAsString(rootPath + request.getPath());
        EconomyDataObject edo = stats.getServerEconomyData();
        double change = Util.getPrecentualChange(edo, 1);
        rewrite = rewrite.replace("{OPTIONS}", edo.getOptionJSON());
        rewrite = rewrite.replace("{PLAYER_LIST}", Util.getPlayerListAsHTML());
        rewrite = rewrite.replace("{ITEM_LIST}", Util.getItemListAsHTML());
        rewrite = rewrite.replace("{VALUE_CHANGE}", Double.toString(change));
        String direction;
        if (change > 0) {
            direction = "fa-arrow-trend-up";
        } else if (change < 0 ) {
            direction = "fa-arrow-trend-down";
        } else  {
            direction = "fa-arrow-right";
        }
        rewrite = rewrite.replace("{VALUE_CHANGE_DIRECTION}", direction);
        byte[] body = rewrite.getBytes();
        HttpMimeType fileType = this.getFileTypeDecoder().getFileType(request.getPath());
        HashMap<HttpResponseHeaderType, String> headers = new HashMap<>() {{
            put(HttpResponseHeaderType.CONTENT_TYPE, fileType.getValue());
        }};
        return this.getResponseBuilder().getOKResponse(body, headers);
    }
}
