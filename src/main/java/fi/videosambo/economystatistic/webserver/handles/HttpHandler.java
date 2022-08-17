package fi.videosambo.economystatistic.webserver.handles;

import fi.videosambo.economystatistic.webserver.request.HttpHandlerType;
import fi.videosambo.economystatistic.webserver.request.HttpMethodType;
import fi.videosambo.economystatistic.webserver.request.HttpRequest;
import fi.videosambo.economystatistic.webserver.response.HttpResponse;
import fi.videosambo.economystatistic.webserver.response.HttpResponseBuilder;
import fi.videosambo.economystatistic.webserver.util.Encoder;
import fi.videosambo.economystatistic.webserver.util.FileContentConverter;
import fi.videosambo.economystatistic.webserver.util.FileOperator;
import fi.videosambo.economystatistic.webserver.util.FileTypeDecoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class HttpHandler {


    private static final FileContentConverter fileContentConverter = new FileContentConverter();
    private static final FileTypeDecoder fileTypeDecoder = new FileTypeDecoder();
    private static final FileOperator fileOperator = new FileOperator();
    private static final HttpResponseBuilder responseBuilder = new HttpResponseBuilder();
    private static final Encoder encoder = new Encoder();
    private final ArrayList<HttpMethodType> handledMethods = new ArrayList<>();
    private HttpHandlerType typeOfHandler = null;

    public abstract HttpResponse processRequest(HttpRequest request) throws IOException;

    public abstract boolean coversPathFromRequest(HttpRequest request);

    public HttpHandlerType getTypeOfHandler() {
        return typeOfHandler;
    }

    public void setTypeOfHandler(HttpHandlerType handlerType) {
        typeOfHandler = handlerType;
    }

    public void addHandledMethod(HttpMethodType method) {
        handledMethods.add(method);
    }

    public void addHandledMethods(List<HttpMethodType> methods) { handledMethods.addAll(methods); }

    public FileContentConverter getFileContentConverter() {
        return fileContentConverter;
    }

    public FileTypeDecoder getFileTypeDecoder() {
        return fileTypeDecoder;
    }

    public FileOperator getFileOperator() {
        return fileOperator;
    }

    public HttpResponseBuilder getResponseBuilder() { return responseBuilder; }

    public Encoder getEncoder() { return encoder; }

    public boolean handles(HttpRequest request) {
        return (this.handledMethods.contains(request.getMethodType()) && this.coversPathFromRequest(request));
    }

    public boolean requestHasAllowedMethod(HttpRequest request) {
        return this.handledMethods.contains(request.getMethodType());
    }
}
