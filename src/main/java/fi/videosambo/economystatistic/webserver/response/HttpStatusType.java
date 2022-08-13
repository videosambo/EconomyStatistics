package fi.videosambo.economystatistic.webserver.response;

public enum HttpStatusType {

    CONTINUE(100, "Continue"),
    PROTOCOL_SW(101, "Switching Protocols"),

    SUCCESSFUL(200, "OK"),
    CREATED(201, "Created"),
    ACCPETED(202, "Accepted"),
    NON_AUTH(203, "Non-Authoritative Information"),
    NO_CONTENT(204, "No Content"),
    RESET_CONTENT(205, "Reset Content"),
    PARTIAL_CONTENT(206, "Partial Content"),

    MULTIPLE(300, "Multiple Choises"),
    MOVED(301, "Moved Permanently"),
    FOUND(302, "Found"),
    SEE_OTHER(303, "See Other"),
    NOT_MODIFIED(304, "Not Modified"),
    USE_PROXY(305, "Use Proxy"),
    TEMP_REDIRECT(307, "Temporary Redirect"),

    BAD_REQUEST(400, "Bad Requst"),
    UNAUTHORIZED(401, "Unauthorized"),
    PAYMENT_REQUIRED(402, "Payment Required"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    NOT_ACCEPTABLE(406, "Not Acceptable"),
    PROXY_AUTH_REQUIRED(407, "Proxy Authentication Required"),
    REQUEST_TIMEOUT(408, "Request Timeout"),
    CONFLICT(409, "Conflict"),
    GONE(410, "Gone"),
    LENGHT_REQUIRED(411, "Length Required"),
    PRECONDITION_FAILED(412, "Precondition Failed"),
    ENT_TOO_LARGE(413, "Request Entity Too Large"),
    URI_TOO_LONG(414, "Request-URI Too Long"),
    UNSUPPORTED_MEDIA(415, "Unsupported Media Type"),
    REQUST_RANGE_NOT_SATISFIABLE(416, "Request Range Not Satisfiable"),
    EXPECTATION_FAILED(417, "Expectation Failed"),
    IM_TEAPOT(418, "I'm a teapot"),
    ENCHANSE_YOUR_CALM(420, "Enhance Your Calm"),

    SERVER_ERROR(500, "Internal Server Error"),
    NOT_IMPLEMENTED(501, "Not Implemented"),
    BAD_GATEWAY(502, "Bad Gateway"),
    SERVICE_UNAVAIBLE(503, "Service Unavaliable"),
    GATEWAY_TIMEOUT(504, "Gateway Timeout"),
    NOT_SUPPORTED(505, "HTTP Version Not Supported");

    private int code;
    private String message;

    HttpStatusType(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return code + " " + message;
    }
}
