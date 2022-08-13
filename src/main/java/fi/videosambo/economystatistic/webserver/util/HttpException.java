package fi.videosambo.economystatistic.webserver.util;

public class HttpException extends Exception{

    public HttpException() {}
    public HttpException(String errorMessage) {
        super(errorMessage);
    }
}
