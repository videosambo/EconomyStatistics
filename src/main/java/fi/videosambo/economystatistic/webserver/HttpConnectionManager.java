package fi.videosambo.economystatistic.webserver;

import fi.videosambo.economystatistic.webserver.handles.HttpHandler;
import fi.videosambo.economystatistic.webserver.request.HttpRequest;
import fi.videosambo.economystatistic.webserver.request.HttpRequestParser;
import fi.videosambo.economystatistic.webserver.request.HttpRequestRouter;
import fi.videosambo.economystatistic.webserver.response.HttpResponse;
import fi.videosambo.economystatistic.webserver.response.HttpResponseWriter;
import fi.videosambo.economystatistic.webserver.util.HttpException;

import java.io.IOException;
import java.net.Socket;

public class HttpConnectionManager extends Thread {

    private final Socket client;
    private final HttpRequestParser requestParser;
    private final HttpRequestRouter requestRouter;
    private final HttpResponseWriter responseWriter;

    public HttpConnectionManager(Socket client, HttpRequestParser requestParser, HttpRequestRouter requestRouter, HttpResponseWriter responseWriter) {
        this.client = client;
        this.requestParser = requestParser;
        this.requestRouter = requestRouter;
        this.responseWriter = responseWriter;
    }

    @Override
    public void run() {
        try {
            HttpRequest request = requestParser.parse(client.getInputStream());
            HttpHandler handler = requestRouter.findHandler(request);
            HttpResponse response = handler.processRequest(request);
            responseWriter.write(response);
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
