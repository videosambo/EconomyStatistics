package fi.videosambo.economystatistic.webserver.request;

import fi.videosambo.economystatistic.webserver.handles.*;
import fi.videosambo.economystatistic.webserver.handles.custom.ItemEconomyHandler;
import fi.videosambo.economystatistic.webserver.handles.custom.ServerEconomyHandler;

import java.util.ArrayList;
import java.util.Arrays;

public class HttpRequestRouter {

    private final ArrayList<HttpHandler> handlers = new ArrayList<>();
    private final String rootPath;

    public HttpRequestRouter(String rootPath, String indexFile) {
        this.rootPath = rootPath;
        handlers.addAll(Arrays.asList(new DeleteHandler(rootPath),
                new ItemEconomyHandler(rootPath),
                new ServerEconomyHandler(rootPath),
                new PatchHandler(rootPath),
                new InvalidRequestHandler(),
                indexFile == null ? new DirectoryLinkHandler(rootPath) : new DirectoryLinkHandler(rootPath, indexFile),
                new InternalErrorHandler(),
                new PutHandler(rootPath),
                new GetHandler(rootPath),
                new PostHandler(rootPath),
                new MethodNotAllowedHandler(),
                new OptionHandler(),
                new HeadHandler(rootPath)));
    }

    public HttpHandler findHandler(HttpRequest request) {
        for (HttpHandler handler : handlers) {
            if (handler.handles(request)) {
                return handler;
            }
        }
        return new MethodNotAllowedHandler();
    }

    public ArrayList<HttpHandler> getHandlers() {
        return handlers;
    }

    public String getRootPath() {
        return rootPath;
    }
}
