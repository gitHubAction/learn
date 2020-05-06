package http;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpException;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class HttpClient {
    private int connectTimeout;
    private int connectionRequestTimeout;
    private int socketTimeout;
    private HttpProxy proxy;
    private HttpClientWrapper wrapper;

    public HttpClient() {
        this.connectTimeout = 3000;
        this.connectionRequestTimeout = 60000;
        this.socketTimeout = 60000;
    }

    public HttpClient(HttpClientWrapper wrapper) {
        this();
        this.wrapper = wrapper;
    }

    public HttpClient(int connectTimeout, int connectionRequestTimeout, int socketTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
        this.connectTimeout = connectTimeout;
        this.socketTimeout = socketTimeout;
    }

    public void setProxy(HttpProxy proxy) {
        this.proxy = proxy;
    }

    public ResponseStatus get(String url) throws HttpException, IOException {
        HttpClientWrapper hw = wrapper;
        if (hw == null) {
            hw = new HttpClientWrapper(this.proxy);
        }
        return hw.sendRequest(url, this.connectTimeout, this.connectionRequestTimeout, this.socketTimeout);
    }

    public ResponseStatus get(String url,Map<String,String> head,String urlEncoding) throws HttpException, IOException {
        HttpClientWrapper hw = wrapper;
        if (hw == null) {
            hw = new HttpClientWrapper(this.proxy);
        }
        return hw.sendRequest(url, head,urlEncoding,this.connectTimeout, this.connectionRequestTimeout, this.socketTimeout);
    }

    public ResponseStatus get(String url, String urlEncoding) {
        HttpClientWrapper hw = wrapper;
        if (hw == null) {
            hw = new HttpClientWrapper(this.proxy);
        }
        ResponseStatus response = null;
        try {
            response = hw.sendRequest(url, urlEncoding, this.connectTimeout, this.connectionRequestTimeout,
                    this.socketTimeout);
        } catch (Exception e) {
            log.error("请求发生异常！",e);
        }
        return response;
    }

    public ResponseStatus postForm(String url,Map<String,String> params,Map<String,String> headers) {
        HttpClientWrapper hw = wrapper;
        if (hw == null) {
            hw = new HttpClientWrapper(this.proxy);
        }
        ResponseStatus ret = null;
        try {
            setParams(url, hw);
            if(params != null && !params.isEmpty()){
                for(String key : params.keySet()){
                    hw.addNV(key,params.get(key));
                }
            }
            ret = hw.postNVForm(url, this.connectTimeout, this.connectionRequestTimeout, this.socketTimeout,headers);
        } catch (Exception e) {
            log.error("请求发生异常！",e);
        }
        return ret;
    }

    private void setParams(String url, HttpClientWrapper hw) {
        String[] paramStr = url.split("[?]", 2);
        if ((paramStr == null) || (paramStr.length != 2)) {
            return;
        }
        String[] paramArray = paramStr[1].split("[&]");
        if (paramArray == null) {
            return;
        }
        for (String param : paramArray)
            if ((param != null) && (!"".equals(param.trim()))) {
                String[] keyValue = param.split("[=]", 2);
                if ((keyValue != null) && (keyValue.length == 2)) {
                    hw.addNV(keyValue[0], keyValue[1]);
                }
            }
    }

    private void setParams(Map<String, String> bodyMap, HttpClientWrapper hw) {
        if ((bodyMap == null) || (bodyMap.isEmpty())) {
            return;
        }
        for (Map.Entry entry : bodyMap.entrySet()) {
            System.out.println("key= " + (String) entry.getKey() + " and value= " + (String) entry.getValue());
            if ((entry.getKey() != null) && (!"".equals(((String) entry.getKey()).trim())) && (entry.getValue() != null)
                    && (!"".equals(((String) entry.getValue()).trim()))) {
                hw.addNV((String) entry.getKey(), (String) entry.getValue());
            }
        }
    }


    @Deprecated
    public ResponseStatus post(String url, String jsonBody) {
        return post(url, jsonBody, "application/json");
    }

    public ResponseStatus postWithJson(String url, String jsonBody) {
        HttpClientWrapper hw = wrapper;
        if (hw == null) {
            hw = new HttpClientWrapper(this.proxy);
        }
        ResponseStatus ret = null;
        try {
            ret = hw.postWithJson(url, jsonBody);
        } catch (IOException e) {
            log.error("请求发生异常！",e);
        }
        return ret;
    }

    public ResponseStatus postWithJsonHead(String url, Map<String,String> params, Map<String, String> head) {
        HttpClientWrapper hw = wrapper;
        if (hw == null) {
            hw = new HttpClientWrapper(this.proxy);
        }
        ResponseStatus ret = null;
        try {
            setParams(params,hw);
            ret=hw.postWithJsonHead(url, Tools.beanToJsonStr(params),head);
            //ret = hw.postNV(url,head, this.connectTimeout, this.connectionRequestTimeout, this.socketTimeout);
        } catch (Exception e) {
            log.error("请求发生异常！",e);
        }
        return ret;
    }

    public ResponseStatus postWithJsonHead(String url, String jsonBody, Map<String, String> head) {
        HttpClientWrapper hw = wrapper;
        if (hw == null) {
            hw = new HttpClientWrapper(this.proxy);
        }
        ResponseStatus ret = null;
        try {
            ret = hw.postWithJsonHead(url, jsonBody, head);
        } catch (IOException e) {
            log.error("请求发生异常！",e);
        }
        return ret;
    }


    public ResponseStatus postXml(String url, String xmlBody) {
        return post(url, xmlBody, "application/xml");
    }

    public ResponseStatus post(String url, Map<String, String> body, String contentType) {
        HttpClientWrapper hw = wrapper;
        if (hw == null) {
            hw = new HttpClientWrapper(this.proxy);
        }
        ResponseStatus ret = null;
        try {
            setParams(body, hw);
            ret = hw.postNV(url, contentType, this.connectTimeout, this.connectionRequestTimeout, this.socketTimeout);
        } catch (Exception e) {
            log.error("请求发生异常！",e);
        }
        return ret;
    }

    private ResponseStatus post(String url, String body, String contentType) {
        HttpClientWrapper hw = wrapper;
        if (hw == null) {
            hw = new HttpClientWrapper(this.proxy);
        }
        ResponseStatus ret = null;
        try {
            hw.addNV("", body);
            ret = hw.postNV(url, contentType, this.connectTimeout, this.connectionRequestTimeout, this.socketTimeout);
        } catch (Exception e) {
            log.error("请求发生异常！",e);
        }
        return ret;
    }

    public int getConnectTimeout() {
        return this.connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getConnectionRequestTimeout() {
        return this.connectionRequestTimeout;
    }

    public void setConnectionRequestTimeout(int connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
    }

    public int getSocketTimeout() {
        return this.socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }
}