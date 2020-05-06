package http;

import org.apache.http.Header;

import java.io.UnsupportedEncodingException;

public class ResponseStatus {
    private String encoding;
    private byte[] contentBytes;
    private int statusCode;
    private String contentType;
    private String contentTypeString;
    private Header[] headers;

    public String getEncoding() {
        return this.encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentTypeString() {
        return this.contentTypeString;
    }

    public void setContentTypeString(String contenttypeString) {
        this.contentTypeString = contenttypeString;
    }

    public String getContent() throws UnsupportedEncodingException {
        return getContent(this.encoding);
    }

    public String getContent(String encoding) throws UnsupportedEncodingException {
        if (encoding == null) {
            return new String(this.contentBytes);
        }
        return new String(this.contentBytes, encoding);
    }

    public String getUTFContent() throws UnsupportedEncodingException {
        return getContent("UTF-8");
    }

    public byte[] getContentBytes() {
        return this.contentBytes;
    }

    public void setContentBytes(byte[] contentBytes) {
        this.contentBytes = contentBytes;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Header[] getHeaders() {
        return headers;
    }

    public void setHeaders(Header[] headers) {
        this.headers = headers;
    }

    public String getHeader(String name) {
        if (Tools.isEmpty(name) || headers == null || headers.length == 0) {
            return null;
        }
        name = name.trim();
        for (Header header : headers) {
            if (name.equalsIgnoreCase(header.getName().trim())) {
                return header.getValue();
            }
        }
        return null;
    }
}