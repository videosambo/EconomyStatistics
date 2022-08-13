package fi.videosambo.economystatistic.webserver.util;

import fi.videosambo.economystatistic.webserver.request.HttpRequest;
import fi.videosambo.economystatistic.webserver.response.HttpResponse;
import fi.videosambo.economystatistic.webserver.response.HttpResponseHeaderType;
import fi.videosambo.economystatistic.webserver.response.HttpStatusType;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class RangeRequestResponder {

    private final String rootPath;
    private final FileOperator fileOperator;
    private final FileContentConverter fileContentConverter;
    private final FileTypeDecoder fileTypeDecoder;

    public RangeRequestResponder(String rootPath, FileOperator fileOperator, FileContentConverter fileContentConverter,
                                 FileTypeDecoder fileTypeDecoder) {
        this.rootPath = rootPath;
        this.fileOperator = fileOperator;
        this.fileContentConverter = fileContentConverter;
        this.fileTypeDecoder = fileTypeDecoder;
    }

    public HttpResponse getRangeResponse(HttpRequest request) throws IOException {
        int lengthOfRequestedFile = this.fileOperator.getLengthOfFileContent(this.rootPath + request.getPath());
        String requestedRangeString = request.getHeaders().get("Range").toString();
        HashMap<String, String> rangeLimits = this.getRangeLimits(requestedRangeString, lengthOfRequestedFile);

        if (this.isRangeWithinFileLength(rangeLimits, lengthOfRequestedFile)) {
            return this.getSuccessfulRangeResponse(request, rangeLimits, lengthOfRequestedFile);
        } else {
            return this.getUnsuccessfulRangeResponse(lengthOfRequestedFile);
        }
    }

    private HashMap<String, String> getRangeLimits(String rangeString, int lengthOfRequestedFile) {
        String[] partsOfRangeString = this.getPartsOfRangeString(rangeString);

        String startValue = this.getStartValue(partsOfRangeString, lengthOfRequestedFile);
        String endValue = this.getEndValue(partsOfRangeString, lengthOfRequestedFile);
        return getRangeLimitsMap(startValue, endValue);
    }

    private String[] getPartsOfRangeString(String rangeString) {
        String limits = rangeString.substring(6, rangeString.length());
        return (this.hasBothValues(limits)) ? limits.split("-") : new String[]{limits.split("-")[0], ""};
    }

    private boolean hasBothValues(String limits) {
        return limits.split("-").length == 2;
    }

    private String getStartValue(String[] partsOfRangeString, Integer lengthOfFileContent) {
        String startValue = partsOfRangeString[0];
        String endValue = partsOfRangeString[1];
        return (!startValue.equals("")) ? startValue : Integer.toString(lengthOfFileContent - Integer.parseInt(endValue));
    }

    private String getEndValue(String[] partsOfRangeString, Integer lengthOfFileContent) {
        String startValue = partsOfRangeString[0];
        String endValue = partsOfRangeString[1];
        if (!endValue.equals("")) {
            return (!startValue.equals("")) ? endValue : Integer.toString(lengthOfFileContent - 1);
        } else {
            return Integer.toString(lengthOfFileContent - 1);
        }
    }

    private HashMap<String, String> getRangeLimitsMap(String startValue, String endValue) {
        return new HashMap<String, String>(){{
            put("start", startValue);
            put("end", endValue);
        }};
    }

    private boolean isRangeWithinFileLength(HashMap<String, String> rangeLimits, int fileLength) {
        return Integer.parseInt(rangeLimits.get("end")) <= fileLength;
    }

    private HttpResponse getSuccessfulRangeResponse(HttpRequest request, HashMap<String, String> rangeLimits, int lengthOfRequestedFile) throws IOException {
        File file = this.fileOperator.getRequestedFile(this.rootPath + request.getPath());
        byte[] body = this.fileContentConverter.getPartOfFile(file, rangeLimits);
        HttpMimeType fileType = this.fileTypeDecoder.getFileType(request.getPath());
        String contentRangeHeader = this.getContentRangeHeader(lengthOfRequestedFile, rangeLimits);
        HashMap<HttpResponseHeaderType, String> headers = this.getHeaders(fileType, contentRangeHeader);
        return new HttpResponse(HttpStatusType.PARTIAL_CONTENT, body, headers);
    }

    private String getContentRangeHeader(int fileLength, HashMap rangeLimits) {
        return "bytes " + rangeLimits.get("start") + "-" + rangeLimits.get("end") + "/" + Integer.toString(fileLength);
    }

    private HashMap<HttpResponseHeaderType, String> getHeaders(HttpMimeType fileType, String contentRangeHeader) {
        return new HashMap<HttpResponseHeaderType, String>() {{
            put(HttpResponseHeaderType.CONTENT_TYPE, fileType.getValue());
            put(HttpResponseHeaderType.CONTENT_RANGE, contentRangeHeader);
        }};
    }

    private HttpResponse getUnsuccessfulRangeResponse(int lengthOfRequestedFile) {
        String contentRangeHeader = "bytes */" + Integer.toString(lengthOfRequestedFile);
        HashMap<HttpResponseHeaderType, String> headers = new HashMap<HttpResponseHeaderType, String>() {{
            put(HttpResponseHeaderType.CONTENT_RANGE, contentRangeHeader);
        }};
        return new HttpResponse(HttpStatusType.REQUST_RANGE_NOT_SATISFIABLE, null, headers);
    }
}
