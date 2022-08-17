package fi.videosambo.economystatistic.webserver.handles.custom;

import fi.videosambo.economystatistic.EconomyDataObject;
import fi.videosambo.economystatistic.Main;
import fi.videosambo.economystatistic.Util;
import fi.videosambo.economystatistic.webserver.handles.HttpHandler;
import fi.videosambo.economystatistic.webserver.request.HttpHandlerType;
import fi.videosambo.economystatistic.webserver.request.HttpMethodType;
import fi.videosambo.economystatistic.webserver.request.HttpRequest;
import fi.videosambo.economystatistic.webserver.response.HttpResponse;
import fi.videosambo.economystatistic.webserver.response.HttpResponseHeaderType;
import fi.videosambo.economystatistic.webserver.util.HttpMimeType;
import fi.videosambo.economystatistic.webserver.util.StatisticsOptionParser;
import org.bukkit.Material;

import java.io.IOException;
import java.util.HashMap;

public class ItemEconomyHandler extends HttpHandler {

    private final String rootPath;
    private final StatisticsOptionParser stats;

    public ItemEconomyHandler(String rootPath) {
        this.rootPath = rootPath;
        stats = new StatisticsOptionParser();
        setTypeOfHandler(HttpHandlerType.GET_HANDLER);
        addHandledMethod(HttpMethodType.GET);
    }

    @Override
    public HttpResponse processRequest(HttpRequest request) {
        Material requestedItem = getItemFromURL(request);
        if (requestedItem == null)
            return getResponseBuilder().getFoundResponse("/");
        if (this.getFileOperator().fileExists(this.rootPath + request.getPath())) {
            try {
                return this.getResponse(request, requestedItem);
            } catch (IOException e) {
                return this.getResponseBuilder().getInternalErrorResponse();
            }
        } else {
            return this.getResponseBuilder().getNotFoundResponse();
        }
    }

    @Override
    public boolean coversPathFromRequest(HttpRequest request) {
        return request.getPath().startsWith("/item.html");
    }

    private Material getItemFromURL(HttpRequest request) {
        if (request.getURIParameters().containsKey("item"))
            return Material.getMaterial(request.getURIParameters().get("item").toUpperCase());
        return null;
    }

    private HttpResponse getResponse(HttpRequest request, Material material) throws IOException {
        String timeScale = request.getURIParameters().get("timeScale");
        if (timeScale == null)
            timeScale = "all";
        int time = 1000000;
        if (Util.isNumeric(timeScale))
            time = Integer.parseInt(timeScale);
        String rewrite = getFileContentConverter().getFileContentAsString(rootPath + request.getPath());
        EconomyDataObject edo = stats.getItemData(material, time);
        rewrite = rewrite.replace("{TIME_SCALE_" + timeScale + "}", "btn-primary");
        rewrite = rewrite.replaceAll("(\\{TIME_SCALE_).*?(})", "btn-outline-primary");
        rewrite = request.getURIParameters().get("chartType") != null ? rewrite.replace("{CHART_TYPE}", request.getURIParameters().get("chartType")) : rewrite.replace("{CHART_TYPE}", "line");
        rewrite = rewrite.replace("{TIME_SCALE}", time == 1000000 ? "all" : Integer.toString(time));
        rewrite = rewrite.replace("{OPTIONS}", edo.getOptionJSON());
        rewrite = rewrite.replace("{PLAYER_LIST}", Util.getPlayerListAsHTML());
        rewrite = rewrite.replace("{ITEM_LIST}", Util.getItemListAsHTML());
        rewrite = rewrite.replace("{ITEM_ID}", material.toString().toLowerCase());
        rewrite = rewrite.replace("{ITEM_NAME}", Util.capitalizeEveryWord(material.toString().replace("_", " ")));
        if (request.getURIParameters().get("chartType") != null && request.getURIParameters().get("chartType").equalsIgnoreCase("candlestick")) {
            rewrite = rewrite.replace("{CHART_LINE}", " btn-outline-primary");
            rewrite = rewrite.replace("{CHART_CANDLESTICK}", " btn-primary");
        } else {
            rewrite = rewrite.replace("{CHART_CANDLESTICK}", " btn-outline-primary");
            rewrite = rewrite.replace("{CHART_LINE}", " btn-primary");
        }
        double change = 0;
        if (edo.getObjects().size() != 0)
            change = Util.getPrecentualChange(edo, 1);
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
