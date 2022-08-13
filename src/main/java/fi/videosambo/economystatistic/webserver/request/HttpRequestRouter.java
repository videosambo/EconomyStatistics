package fi.videosambo.economystatistic.webserver.request;

import fi.videosambo.economystatistic.webserver.handles.*;

import java.util.ArrayList;
import java.util.Arrays;

public class HttpRequestRouter {

    private final ArrayList<HttpHandler> handlers = new ArrayList<HttpHandler>();
    private final String rootPath;

    public HttpRequestRouter(String rootPath) {
        this.rootPath = rootPath;
        handlers.addAll(Arrays.asList(new DeleteHandler(rootPath),
                new DirectoryLinkHandler(rootPath),
                new GetHandler(rootPath),
                new HeadHandler(rootPath),
                new InternalErrorHandler(),
                new InvalidRequestHandler(),
                new MethodNotAllowedHandler(),
                new OptionHandler(),
                new PatchHandler(rootPath),
                new PostHandler(rootPath),
                new PutHandler(rootPath)));
    }

    public HttpHandler findHandler(HttpRequest request) {
        for (HttpHandler handler : handlers) {
            if (handler.handles(request)) {
                System.out.println("Found suitable handler, returning " + handler.getClass().getName());
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
