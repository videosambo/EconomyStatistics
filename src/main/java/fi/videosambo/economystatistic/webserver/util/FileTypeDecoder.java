package fi.videosambo.economystatistic.webserver.util;

public class FileTypeDecoder {

    public FileTypeDecoder(){}

    public HttpMimeType getFileType(String fileName) {
        return (hasExtension(fileName)) ? getFileTypeWhenOneExists(fileName) : getDefaultFileType();
    }

    private boolean hasExtension(String fileName) {
        return fileName.contains(".");
    }

    private HttpMimeType getDefaultFileType() {
        return HttpMimeType.PLAIN;
    }

    private HttpMimeType getFileTypeWhenOneExists(String fileName) {
        String clientFileExtension = getFileExtension(fileName);
        for (HttpMimeType fileType : HttpMimeType.getMimeTypes()) {
            if (fileType.getExtension().equals(clientFileExtension)) {
                return fileType;
            }
        }
        return this.getDefaultFileType();
    }

    private String getFileExtension(String fileName) {
        return fileName.split("\\.")[1];
    }
}
