package fi.videosambo.economystatistic.webserver.response;

public enum HttpResponseHeaderType {

    AUTHENTICATE("WWW-Authenticate"),
    ALLOW("Allow"),
    CONTENT_RANGE("Content-Range"),
    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGHT("Content-Lenght"),
    LOCATION("Location"),
    SERVER("Server"),
    DATE("Date"),
    SET_COOKIE("Set-Cookie");

    private final String headerContent;

    HttpResponseHeaderType(final String headerContent) {
        this.headerContent = headerContent;
    }

    @Override
    public String toString() {
        return headerContent;
    }

    public static HttpResponseHeaderType[] getHeaders() {
        return HttpResponseHeaderType.class.getEnumConstants();
    }

}
