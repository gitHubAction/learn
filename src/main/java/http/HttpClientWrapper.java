package http;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.*;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.CodingErrorAction;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

@Slf4j
public class HttpClientWrapper {
    private static CloseableHttpClient client;
    private List<ContentBody> contentBodies;
    private List<NameValuePair> nameValuePostBodies;
    private static PoolingHttpClientConnectionManager connManager = null;

    static Set<Character> BEING_ESCAPED_CHARS = new HashSet();
    private String protocol;
    private String host;
    private int port;
    private String dir;
    private String uri;
    private static final int DefaultPort = 80;
    private static final String ProtocolSeparator = "://";
    private static final String PortSeparator = ":";
    private static final String HostSeparator = "/";
    private static final String DirSeparator = "/";
    private SSLConnectionSocketFactory sslsf;

    static {
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] arg0, String arg1)
                        throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] arg0, String arg1)
                        throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }
            }, null);
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            log.error("设置安全请求发生异常！",e);
        }

        Registry socketFactoryRegistry = RegistryBuilder.create().register("http", PlainConnectionSocketFactory.INSTANCE).register("https", new SSLConnectionSocketFactory(sslContext)).build();

        connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
        connManager.setDefaultSocketConfig(socketConfig);
        MessageConstraints messageConstraints = MessageConstraints.custom().setMaxHeaderCount(200).setMaxLineLength(2000).build();

        ConnectionConfig connectionConfig = ConnectionConfig.custom().setMalformedInputAction(CodingErrorAction.IGNORE).setUnmappableInputAction(CodingErrorAction.IGNORE).setCharset(Consts.UTF_8).setMessageConstraints(messageConstraints).build();

        connManager.setDefaultConnectionConfig(connectionConfig);
        connManager.setMaxTotal(200);
        connManager.setDefaultMaxPerRoute(20);
    }

    public HttpClientWrapper(HttpProxy proxy) {
        client = getCloseableHttpClient(proxy);
        this.contentBodies = new ArrayList();
        this.nameValuePostBodies = new LinkedList();
    }

    public HttpClientWrapper(HttpProxy proxy, String sslPath, String sslPwd) throws Exception {
        if (Tools.notEmpty(sslPath) && Tools.notEmpty(sslPwd)) {
            setSSL(sslPath, sslPwd);
        }
        client = getCloseableHttpClient(proxy);
        this.contentBodies = new ArrayList();
        this.nameValuePostBodies = new LinkedList();
    }

    private void setSSL(String sslPath, String sslPwd) throws Exception {
        //指定读取证书格式为PKCS12
        KeyStore keyStore = KeyStore.getInstance("PKCS12");

        //读取本机存放的PKCS12证书文件
        FileInputStream instream = new FileInputStream(new File(sslPath));
        try {
            //指定PKCS12的密码
            keyStore.load(instream, sslPwd.toCharArray());
        } finally {
            instream.close();
        }
        //指定TLS版本
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, sslPwd.toCharArray())
                .build();
        //设置httpclient的SSLSocketFactory
        sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[]{"TLSv1"},
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }

    private CloseableHttpClient getCloseableHttpClient(HttpProxy proxy) {
        if (null != proxy) {
            HttpHost proxyHost = new HttpHost(proxy.getHost(), proxy.getPort());
            CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            if ((null != proxy.getUser()) && (null != proxy.getPassword())) {
                credentialsProvider.setCredentials(new AuthScope(proxy.getHost(), proxy.getPort()), new UsernamePasswordCredentials(proxy.getUser(), proxy.getPassword()));
            }

            return HttpClients.custom().setProxy(proxyHost).setDefaultCredentialsProvider(credentialsProvider).build();
        } else if (sslsf != null) {
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        }
        return HttpClients.custom().setConnectionManager(connManager).build();
    }

    public ResponseStatus sendRequest(String url, int connectTimeout, int connectionRequestTimeout, int socketTimeout)
            throws HttpException, IOException {
        return sendRequest(url, "UTF-8", HttpMethod.GET, null, connectTimeout, connectionRequestTimeout, socketTimeout);
    }

    public ResponseStatus sendRequest(String url,Map<String,String> head,String urlEncoding, int connectTimeout, int connectionRequestTimeout, int socketTimeout)
            throws HttpException, IOException {
        return sendRequest(url, urlEncoding, HttpMethod.GET, head, connectTimeout, connectionRequestTimeout, socketTimeout);
    }

    public ResponseStatus sendRequest(String url, String urlEncoding, int connectTimeout, int connectionRequestTimeout, int socketTimeout)
            throws HttpException, IOException {
        return sendRequest(url, urlEncoding, HttpMethod.GET, null, connectTimeout, connectionRequestTimeout, socketTimeout);
    }

    public ResponseStatus postNV(String url, int connectTimeout, int connectionRequestTimeout, int socketTimeout,Map<String,String> headers)
            throws HttpException, IOException {
        return sendRequest(url, "UTF-8", HttpMethod.POST, headers, connectTimeout, connectionRequestTimeout, socketTimeout);
    }

    public ResponseStatus postNV(String url, String contentType, int connectTimeout, int connectionRequestTimeout, int socketTimeout) throws HttpException, IOException {
        return sendRequest(url, "UTF-8", HttpMethod.POST, new HashMap<String,String>(){{put("content-type",contentType);}}, connectTimeout, connectionRequestTimeout, socketTimeout);
    }

    public ResponseStatus postNV(String url, Map<String,String> head, int connectTimeout, int connectionRequestTimeout, int socketTimeout) throws HttpException, IOException {
        return sendRequest(url, "UTF-8", HttpMethod.POST, head, connectTimeout, connectionRequestTimeout, socketTimeout);
    }


    public ResponseStatus postNVForm(String url, int connectTimeout, int connectionRequestTimeout, int socketTimeout,Map<String,String> headers)
            throws HttpException, IOException {
        return sendRequestWithForm(url, "UTF-8", HttpMethod.POST, headers, connectTimeout, connectionRequestTimeout, socketTimeout);
    }

    public ResponseStatus sendRequest(String urlstr, String urlEncoding, HttpMethod bodyType, Map<String,String> head, int connectTimeout, int connectionRequestTimeout, int socketTimeout)
            throws HttpException, IOException {
        if (urlstr == null) {
            return null;
        }
        String url = urlstr;
        if (urlEncoding != null) {
            url = encodeURL(url.trim(), urlEncoding);
        }
        HttpEntity entity = null;
        HttpRequestBase request = null;
        CloseableHttpResponse response = null;
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectionRequestTimeout).setSocketTimeout(socketTimeout).build();
        try {
            if (HttpMethod.GET == bodyType) {
                request = new HttpGet(url);
            } else if (HttpMethod.POST == bodyType) {
                parseUrl(url);
                HttpPost httpPost = new HttpPost(toUrl());
                List nvBodyList = getNVBodies();
                StringEntity requestEntity = new StringEntity(Tools.UrlParamsToStr(nvBodyList, '&'), urlEncoding );
                httpPost.setEntity(requestEntity);
                request = httpPost;
            }
            if (null != head && !head.isEmpty()) {
                for (String key : head.keySet()) {
                    request.setHeader(key, head.get(key));
                }
            }
            request.setConfig(requestConfig);

            //request.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 1.1.4322)");

            response = client.execute(request);
            entity = response.getEntity();
            StatusLine statusLine = response.getStatusLine();
            ResponseStatus ret = new ResponseStatus();
            ret.setStatusCode(statusLine.getStatusCode());
            ret.setEncoding(urlEncoding);
            ret.setHeaders(response.getAllHeaders());
            getResponseStatus(entity, ret);
            return ret;
        } finally {
            close(entity, request, response);
        }
    }

    public ResponseStatus sendRequestWithForm(String urlstr, String urlEncoding, HttpMethod bodyType, Map<String,String> head, int connectTimeout, int connectionRequestTimeout, int socketTimeout)
            throws HttpException, IOException {
        if (urlstr == null) {
            return null;
        }
        String url = urlstr;
        if (urlEncoding != null) {
            url = encodeURL(url.trim(), urlEncoding);
        }
        HttpEntity entity = null;
        HttpRequestBase request = null;
        CloseableHttpResponse response = null;
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectionRequestTimeout).setSocketTimeout(socketTimeout).build();
        try {
            if (HttpMethod.GET == bodyType) {
                request = new HttpGet(url);
            } else if (HttpMethod.POST == bodyType) {
                parseUrl(url);
                HttpPost httpPost = new HttpPost(toUrl());
                List nvBodyList = getNVBodies();
                UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(nvBodyList, urlEncoding);
                /*EntityBuilder builder = EntityBuilder.create();
                builder.setParameters(nvBodyList);
                httpPost.setEntity(builder.build());*/
                httpPost.setEntity(uefEntity);
                request = httpPost;
            }
            if (null != head && !head.isEmpty()) {
                for (String key : head.keySet()) {
                    request.setHeader(key, head.get(key));
                }
            }
            request.setConfig(requestConfig);

            //request.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 1.1.4322)");

            response = client.execute(request);
            entity = response.getEntity();
            StatusLine statusLine = response.getStatusLine();
            ResponseStatus ret = new ResponseStatus();
            ret.setStatusCode(statusLine.getStatusCode());
            ret.setEncoding(urlEncoding);
            ret.setHeaders(response.getAllHeaders());
            getResponseStatus(entity, ret);
            return ret;
        } finally {
            close(entity, request, response);
        }
    }

    public ResponseStatus postWithJson(String url, String json)
            throws IOException {
        ResponseStatus ret = new ResponseStatus();
        HttpEntity entity = null;
        HttpRequestBase request = null;
        CloseableHttpResponse response = null;
        try {
            request = new HttpPost(url);
            HttpPost httpPost=(HttpPost)request;

            StringEntity requestEntity = new StringEntity(json, "utf-8");
            requestEntity.setContentEncoding("UTF-8");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setEntity(requestEntity);

            response = client.execute(httpPost);
            entity = response.getEntity();
            StatusLine statusLine = response.getStatusLine();

            ret.setStatusCode(statusLine.getStatusCode());
            ret.setEncoding("UTF-8");
            getResponseStatus(entity, ret);
            return ret;
        } finally {
            close(entity, request, response);
        }
    }

    public ResponseStatus postWithJsonHead(String url, String json, Map<String, String> head)
            throws IOException {
        ResponseStatus ret = new ResponseStatus();
        HttpEntity entity = null;
        HttpRequestBase request = null;
        CloseableHttpResponse response = null;
        try {
            request = new HttpPost(url);
            HttpPost httpPost=(HttpPost)request;

            StringEntity requestEntity = new StringEntity(json, "utf-8");
            requestEntity.setContentEncoding("UTF-8");
            httpPost.setHeader("Content-type", "application/json");
            if (null != head && !head.isEmpty()) {
                for (String key : head.keySet()) {
                    httpPost.setHeader(key, head.get(key));
                }
            }
            httpPost.setEntity(requestEntity);

            response = client.execute(httpPost);
            entity = response.getEntity();
            StatusLine statusLine = response.getStatusLine();

            ret.setStatusCode(statusLine.getStatusCode());
            ret.setEncoding("UTF-8");
            getResponseStatus(entity, ret);
            return ret;
        }catch (Exception e) {
            log.error("调用远程接口发生异常！",e);
            return null;
        }finally{
            close(entity, request, response);
        }
    }

    private void getResponseStatus(HttpEntity entity, ResponseStatus ret) throws IOException {
        Header enHeader = entity.getContentEncoding();
        if (enHeader != null) {
            String charset = enHeader.getValue().toLowerCase();
            ret.setEncoding(charset);
        }
        String contenttype = getResponseStatusType(entity);
        ret.setContentType(contenttype);
        ret.setContentTypeString(getResponseStatusTypeString(entity));
        ret.setContentBytes(EntityUtils.toByteArray(entity));
    }

    public ResponseStatus postEntity(String url) throws HttpException, IOException {
        return postEntity(url, "UTF-8");
    }

    public ResponseStatus postEntity(String url, String urlEncoding)
            throws HttpException, IOException {
        if (url == null) {
            return null;
        }
        HttpEntity entity = null;
        HttpRequestBase request = null;
        CloseableHttpResponse response = null;
        try {
            parseUrl(url);
            HttpPost httpPost = new HttpPost(toUrl());

            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
            entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            for (NameValuePair nameValuePair : getNVBodies()) {
                entityBuilder.addPart(nameValuePair.getName(), new StringBody(nameValuePair.getValue(), ContentType.create("text/plain", urlEncoding)));
            }

            for (ContentBody contentBody : getContentBodies()) {
                entityBuilder.addPart("file", contentBody);
            }
            entityBuilder.setCharset(CharsetUtils.get(urlEncoding));
            httpPost.setEntity(entityBuilder.build());
            request = httpPost;
            response = client.execute(request);

            StatusLine statusLine = response.getStatusLine();

            entity = response.getEntity();
            ResponseStatus ret = new ResponseStatus();
            ret.setStatusCode(statusLine.getStatusCode());
            getResponseStatus(entity, ret);
            return ret;
        } finally {
            close(entity, request, response);
        }
    }

    private void close(HttpEntity entity, HttpRequestBase request, CloseableHttpResponse response) throws IOException {
        if (request != null)
            request.releaseConnection();
        if (entity != null)
            entity.getContent().close();
        if (response != null)
            response.close();
    }

    public NameValuePair[] getNVBodyArray() {
        List list = getNVBodies();
        if ((list == null) || (list.isEmpty()))
            return null;
        NameValuePair[] nvps = new NameValuePair[list.size()];
        Iterator it = list.iterator();
        int count = 0;
        while (it.hasNext()) {
            NameValuePair nvp = (NameValuePair) it.next();
            nvps[(count++)] = nvp;
        }
        return nvps;
    }

    public List<NameValuePair> getNVBodies() {
        return Collections.unmodifiableList(this.nameValuePostBodies);
    }

    private String getResponseStatusType(HttpEntity method) {
        Header contenttype = method.getContentType();
        if (contenttype == null)
            return null;
        String ret = null;
        try {
            HeaderElement[] hes = contenttype.getElements();
            if ((hes != null) && (hes.length > 0))
                ret = hes[0].getName();
        } catch (Exception localException) {
            log.error("获取头信息发生异常",localException);
        }
        return ret;
    }

    private String getResponseStatusTypeString(HttpEntity method) {
        Header contenttype = method.getContentType();
        if (contenttype == null)
            return null;
        return contenttype.getValue();
    }

    public static String encodeURL(String url, String encoding) {
        if (url == null)
            return null;
        if (encoding == null) {
            return url;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < url.length(); i++) {
            char c = url.charAt(i);
            if (c != '\n') {
                if ((BEING_ESCAPED_CHARS.contains(new Character(c))) || (c == '\r') || (c > '~'))
                    try {
                        sb.append(URLEncoder.encode(String.valueOf(c), encoding));
                    } catch (Exception e) {
                        sb.append(c);
                    }
                else
                    sb.append(c);
            }
        }
        if (url.startsWith("http://"))
            return sb.toString().replaceAll("\\+", "%20").replaceFirst("http%3A", "http:");
        if (url.startsWith("https://")) {
            return sb.toString().replaceAll("\\+", "%20").replaceFirst("https%3A", "https:");
        }
        return sb.toString().replaceAll("\\+", "%20");
    }

    private void parseUrl(String url) {
        this.protocol = null;
        this.host = null;
        this.port = 80;
        this.dir = "/";
        this.uri = this.dir;

        if ((url == null) || (url.length() == 0))
            return;
        String u = url.trim();
        boolean MeetProtocol = false;
        int pos = u.indexOf("://");
        if (pos > 0) {
            MeetProtocol = true;
            this.protocol = u.substring(0, pos);
            pos += "://".length();
        }
        int posStartDir = 0;
        if (MeetProtocol) {
            int pos2 = u.indexOf(":", pos);
            if (pos2 > 0) {
                this.host = u.substring(pos, pos2);
                pos2 += ":".length();
                int pos3 = u.indexOf("/", pos2);
                String PortStr = null;
                if (pos3 > 0) {
                    PortStr = u.substring(pos2, pos3);
                    posStartDir = pos3;
                } else {
                    int pos4 = u.indexOf("?");
                    if (pos4 > 0) {
                        PortStr = u.substring(pos2, pos4);
                        posStartDir = -1;
                    } else {
                        PortStr = u.substring(pos2);
                        posStartDir = -1;
                    }
                }
                try {
                    this.port = Integer.parseInt(PortStr);
                } catch (Exception localException) {
                    log.error("接口转数字时发生异常！",localException);
                }
            } else {
                pos2 = u.indexOf("/", pos);
                if (pos2 > 0) {
                    this.host = u.substring(pos, pos2);
                    posStartDir = pos2;
                } else {
                    this.host = u.substring(pos);
                    posStartDir = -1;
                }
            }

            pos = u.indexOf("/", pos);
            pos2 = u.indexOf("?");
            if ((pos > 0) && (pos2 > 0))
                this.uri = u.substring(pos, pos2);
            else if ((pos > 0) && (pos2 < 0)) {
                this.uri = u.substring(pos);
            }
        }

        if (posStartDir >= 0) {
            int pos2 = u.lastIndexOf("/", posStartDir);
            if (pos2 > 0)
                this.dir = u.substring(posStartDir, pos2 + 1);
        }
    }

    private String toUrl() {
        StringBuffer ret = new StringBuffer();
        if (this.protocol != null) {
            ret.append(this.protocol);
            ret.append("://");
            if (this.host != null)
                ret.append(this.host);
            if (this.port != 80) {
                ret.append(":");
                ret.append(this.port);
            }
        }
        ret.append(this.uri);
        return ret.toString();
    }

    public void addNV(String name, String value) {
        BasicNameValuePair nvp = new BasicNameValuePair(name, value);
        this.nameValuePostBodies.add(nvp);
    }

    public void clearNVBodies() {
        this.nameValuePostBodies.clear();
    }

    public List<ContentBody> getContentBodies() {
        return this.contentBodies;
    }

    static {
        char[] signArray = {' ', '\\', '‘', ']', '!', '^', '#', '`', '$', '{', '%', '|', '}', '(', '+', ')', '<', '>', ';', '[', ':'};

        for (int i = 0; i < signArray.length; i++)
            BEING_ESCAPED_CHARS.add(new Character(signArray[i]));
    }

    private static enum HttpMethod {
        GET, POST;
    }
}