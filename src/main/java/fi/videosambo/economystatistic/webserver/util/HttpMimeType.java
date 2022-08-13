package fi.videosambo.economystatistic.webserver.util;

public enum HttpMimeType {

    HTML("text/html", "html"),
    PLAIN("text/plain", "txt"),
    CSS("text/css", "css"),
    JAVASCRIPT("text/javascript", "js"),
    MARKDOWN("text/markdown", "md"),
    XML_TEXT("text/xml", "xml"),
    JSON("application/json", "json"),
    OCTET_STREAM("application/octet-stream", ""),
    PDF("application/pdf", "pdf"),
    SQL("application/sql", "sql"),
    XML_APPLICATION("application/xml", "xml"),
    ZIP("application/zip", "zip"),
    JPEG("image/jpeg", "jpeg"),
    PNG("image/png", "png"),
    SVG("image/svg+xml", "svg"),
    GIF("image/gif", "gif"),
    MUILTIPART("multipart/form-data", "");


    private String value;
    private String extension;

    HttpMimeType(String value, String extension) {
        this.value = value;
        this.extension = extension;
    }

    public String getValue() {
        return value;
    }

    public String getExtension() {
        return extension;
    }

    public static HttpMimeType[] getMimeTypes() {
        return HttpMimeType.class.getEnumConstants();
    }

    @Override
    public String toString() {
        return getValue();
    }
}
