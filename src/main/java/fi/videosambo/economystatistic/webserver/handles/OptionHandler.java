package fi.videosambo.economystatistic.webserver.handles;

import fi.videosambo.economystatistic.webserver.request.HttpHandlerType;
import fi.videosambo.economystatistic.webserver.request.HttpMethodType;
import fi.videosambo.economystatistic.webserver.request.HttpRequest;
import fi.videosambo.economystatistic.webserver.response.HttpResponse;
import fi.videosambo.economystatistic.webserver.response.HttpResponseHeaderType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class OptionHandler extends HttpHandler{

    public OptionHandler() {
        setTypeOfHandler(HttpHandlerType.OPTION_HANDLER);
        addHandledMethod(HttpMethodType.OPTIONS);
    }

    @Override
    public HttpResponse processRequest(HttpRequest request) throws IOException {
        ArrayList<String> allowedMethods = getAllowedMethods(request);
        String allowedMethodsString = this.turnArrayListIntoString(allowedMethods);
        HashMap<HttpResponseHeaderType, String> headers = new HashMap<HttpResponseHeaderType, String>() {{
            put(HttpResponseHeaderType.ALLOW, allowedMethodsString);
        }};
        return this.getResponseBuilder().getOKResponse(null, headers);
    }

    @Override
    public boolean coversPathFromRequest(HttpRequest request) {
        return false;
    }


    private ArrayList<String> getAllowedMethods(HttpRequest request) {
        ArrayList<String> allowedMethods = new ArrayList<>(Arrays.asList(HttpMethodType.GET.toString(), HttpMethodType.HEAD.toString(), HttpMethodType.OPTIONS.toString()));
        if (!(requestLogs(request))) {
            allowedMethods.add(HttpMethodType.PUT.toString());
            allowedMethods.add(HttpMethodType.DELETE.toString());
        }
        return allowedMethods;
    }

    private boolean requestLogs(HttpRequest request) {
        return (request.getPath().equals("/logs"));
    }

    private String turnArrayListIntoString(ArrayList<String> allowedMethods) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < allowedMethods.size() -1; i++) {
            stringBuilder.append(allowedMethods.get(i) + ",");
        }
        stringBuilder.append(allowedMethods.get(allowedMethods.size()-1));
        return stringBuilder.toString();
    }
}
