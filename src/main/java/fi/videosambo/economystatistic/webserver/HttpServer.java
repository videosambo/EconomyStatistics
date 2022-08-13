package fi.videosambo.economystatistic.webserver;

import fi.videosambo.economystatistic.webserver.request.HttpRequestParser;
import fi.videosambo.economystatistic.webserver.request.HttpRequestRouter;
import fi.videosambo.economystatistic.webserver.response.HttpResponseWriter;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;

public class HttpServer extends Thread{

    private final ServerSocket serverSocket;
    private final HttpRequestParser requestParser;
    private final HttpRequestRouter requestRouter;
    private final Executor executor;

    private boolean running;

    public HttpServer(ServerSocket serverSocket, HttpRequestParser requestParser, HttpRequestRouter requestRouter, Executor executor) {
        this.serverSocket = serverSocket;
        this.requestParser = requestParser;
        this.requestRouter = requestRouter;
        this.executor = executor;
        this.running = true;
    }

    @Override
    public void run() {
        System.out.println("Web servers started on " + serverSocket.getInetAddress().getHostAddress().toString() + ":" + serverSocket.getLocalPort() + ". Root path: " + requestRouter.getRootPath());
        while (running) {
            connectWithClients();
        }
    }

    private void connectWithClients() {
        try {
            Socket client = serverSocket.accept();
            System.out.println("Accepted connection from " + client.getInetAddress().toString());
            HttpResponseWriter respondWriter = new HttpResponseWriter(client.getOutputStream());
            HttpConnectionManager connectionManager = new HttpConnectionManager(client, requestParser, requestRouter, respondWriter);
            executor.execute(connectionManager);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
