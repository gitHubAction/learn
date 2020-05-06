package http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 对于接口请求的封装
 * @author huanglitao
 */
@Slf4j
public class HttpClientUtil {

    /**
     * GET方式请求接口
     * @param url 接口请求地址
     * @param cls 要返回对象的class
     * @param <T>
     * @return
     */
    public static <T> T get(String url,Class<T> cls){
        return get(url,null,cls,null,"UTF-8");
    }

    /**
     * GET方式请求接口
     * @param url 接口请求地址
     * @param cls 要返回对象的class
     * @param proxy 接口请求代理
     * @param urlEncoding 请求编码
     * @param <T>
     * @return
     */
    public static <T> T get(String url,Map<String,String> head,Class<T> cls,HttpProxy proxy,String urlEncoding){
        HttpClient client=new HttpClient();
        if(proxy != null){
            client.setProxy(proxy);
        }
        try {
            ResponseStatus status=client.get(url,head,urlEncoding);
            if(status == null){
                return null;
            }
            if(cls.isAssignableFrom(String.class)){
                return cls.cast(status.getContent());
            }
            return Tools.jsonStrToBean(status.getContent(),cls);
        } catch (Exception e) {
            log.error("请求远程接口【"+url+"】时发送异常！",e);
        }
        return null;
    }

    /**
     * 返回类型为ApiResult数据类型的接口(主要为内部接口)
     * @param url 接口请求地址
     * @param head 请求头
     * @param cls 要返回对象的class
     * @param <T>
     * @return
     */
    public static <T> T getWithApiResult(String url,Map<String,String> head,Class<T> cls){
        ApiResult result=get(url,head,ApiResult.class,null,"UTF-8");
        if(result == null || !result.getError().equalsIgnoreCase("ok")){
            log.warn("以GET方式请求【"+url+"】发生错误："+result.getErrorMsg());
            return null;
        }
        if(cls.isAssignableFrom(String.class)){
            return cls.cast(result.getResultdata());
        }
        return Tools.convertJacksonObject(result.getResultdata(),cls);
    }

    /**
     * GET方式请求接口
     * @param url 请求接口地址
     * @param cls 要返回对象的class
     * @param proxyHost 代理地址
     * @param proxyPort 代理接口
     * @param <T>
     * @return
     */
    public static <T> T get(String url,Class<T> cls,String proxyHost,int proxyPort){
        HttpProxy proxy=new HttpProxy(proxyHost,proxyPort);
        return get(url,null,cls,proxy,"UTF-8");
    }

    /**
     * 带请求头的GET请求
     * @param url 请求接口地址
     * @param head 请求头
     * @param cls 要返回的class
     * @param <T>
     * @return
     */
    public static <T> T get(String url,Map<String,String> head,Class<T> cls){
        return get(url,head,cls,null,"UTF-8");
    }

    /**
     * 以表单形式进行post请求
     * @param url 请求API地址
     * @param params 请求参数
     * @param headers 请求头
     * @param proxy 请求代理设置
     * @param cls 返回结果class
     * @param <T>
     * @return
     */
    public static <T> T postForm(String url,Map<String,String> params,Map<String,String> headers,HttpProxy proxy,Class<T> cls){
        HttpClient client=new HttpClient();
        if(proxy != null){
            client.setProxy(proxy);
        }
        ResponseStatus status=client.postForm(url,params,headers);
        if(status == null){
            return null;
        }
        try {
            if(cls.isAssignableFrom(String.class)){
                return cls.cast(status.getContent());
            }
            return Tools.jsonStrToBean(status.getContent(),cls);
        } catch (Exception e) {
            log.error("请求远程接口【"+url+"】发生异常！",e);
        }
        return null;
    }

    /**
     * POST方式请求接口
     * @param url 请求地址
     * @param params 请求参数
     * @param cls 要返回对象的class
     * @param <T>
     * @return
     */
    public static <T> T post(String url,Map<String,String> params,Class<T> cls){
        return post(url,params,null,null,cls);
    }

    /**
     * POST方式请求接口
     * @param url 请求地址
     * @param params 请求参数
     * @param headers 请求的head
     * @param cls 要返回对象的class
     * @param <T>
     * @return
     */
    public static <T> T post(String url,Map<String,String> params,Map<String,String> headers,Class<T> cls){
        return post(url,params,headers,null,cls);
    }

    /**
     * POST方式请求接口
     * @param url 请求地址
     * @param params 请求参数
     * @param proxy 请求代理
     * @param cls 要返回对象的class
     * @param <T>
     * @return
     */
    public static <T> T post(String url,Map<String,String> params,HttpProxy proxy,Class<T> cls){
        return post(url,params,null,proxy,cls);
    }

    /**
     * POST方式请求
     * @param url 请求接口地址
     * @param params 请求参数
     * @param proxyHost 代理地址
     * @param proxyPort 代理端口号
     * @param cls 要返回对象的class
     * @param <T>
     * @return
     */
    public static <T> T post(String url,Map<String,String> params,String proxyHost,int proxyPort,Class<T> cls){
        HttpProxy proxy=new HttpProxy(proxyHost,proxyPort);
        return post(url,params,null,proxy,cls);
    }

    /**
     * POST方式请求接口
     * @param url 请求接口地址
     * @param params 请求参数
     * @param headers 请求head头
     * @param proxy 请求代理
     * @param cls 要返回对象的class
     * @param <T>
     * @return
     */
    public static <T> T post(String url,Map<String,String> params,Map<String,String> headers,HttpProxy proxy,Class<T> cls){
        String jsonbody=null;
        if(params != null){
            try {
                jsonbody=Tools.beanToJsonStr(params);
            } catch (Exception e) {
                log.error("请求参数转换为json格式数据时发生异常！",e);
                return null;
            }
        }
        return postJson(url,jsonbody,headers,proxy,cls);
    }

    /**
     * 以json格式进行API调用
     * @param url 请求接口地址
     * @param jsonbody 请求接口json格式参数
     * @param headers 请求头
     * @param proxy 请求代理
     * @param cls 返回class
     * @param <T>
     * @return
     */
    public static <T> T postJson(String url, String jsonbody, Map<String,String> headers, HttpProxy proxy, PropertyNamingStrategy namingStrategy, Class<T> cls){
        HttpClient client=new HttpClient();
        if(proxy != null){
            client.setProxy(proxy);
        }
        if(headers == null){
            headers = new HashMap<>();
        }
        if(!headers.containsKey("content-type") || !headers.containsKey("contentType")){
            headers.put("content-type","application/json");
        }
        ResponseStatus status=client.postWithJsonHead(url,jsonbody,headers);
        if(status == null){
            return null;
        }
        try {
            if(cls.isAssignableFrom(String.class)){
                return cls.cast(status.getContent());
            }
            if(namingStrategy != null){
                return Tools.jsonStrToBean(status.getContent(),namingStrategy,cls);
            }
            return Tools.jsonStrToBean(status.getContent(),cls);
        } catch (Exception e) {
            log.error("请求远程接口【"+url+"】发生异常！",e);
        }
        return null;
    }

    public static <T> T postJson(String url, String jsonbody, Map<String,String> headers, HttpProxy proxy, Class<T> cls){
        return postJson(url,jsonbody,headers,proxy,null,cls);
    }

    /**
     * 返回接口请求格式类型为ApiResult的(主要为内部接口)
     * @param url 接口请求地址
     * @param params 接口请求参数
     * @param headers 接口请求头
     * @param cls 要返回对象的class
     * @param <T>
     * @return
     */
    public static <T> T postWithApiResult(String url,Map<String,String> params,Map<String,String> headers,Class<T> cls){
        ApiResult result=post(url,params,headers,null,ApiResult.class);
        if(result == null || !"ok".equalsIgnoreCase(result.getError())){
            log.warn("以POST方式请求【"+url+"】发生错误："+result.getErrorMsg());
            return null;
        }
        if(cls.isAssignableFrom(String.class)){
            return cls.cast(result.getResultdata());
        }
        return Tools.convertJacksonObject(result.getResultdata(),cls);
    }

    /**
     * 以POST方式提交xml格式数据的接口
     * @param url 接口请求地址
     * @param params 接口请求参数
     * @param cls 要返回对象的class
     * @param <T>
     * @return
     */
    public static <T> T postXml(String url,Object params,Class<T> cls){
        try {
            String xmlbody= JacksonXmlUtils.beanToXml(params);
            return postXml(url,xmlbody,cls);
        } catch (JsonProcessingException e) {
            log.error("数据做xml转换时发生异常！",e);
        }
        return null;
    }

    /**
     * 以POST方式提交xml格式数据的接口
     * @param url 接口请求地址
     * @param xmlbody 接口请求参数
     * @param cls 要返回对象的class
     * @param <T>
     * @return
     */
    public static <T> T postXml(String url,String xmlbody,Class<T> cls){
        return postXml(url,xmlbody,null,cls);
    }

    /**
     * 以POST方式提交xml格式数据的接口
     * @param url 接口请求地址
     * @param xmlbody 接口请求参数
     * @param proxy 代理
     * @param cls 要返回对象的class
     * @param <T>
     * @return
     */
    public static <T> T postXml(String url,String xmlbody,HttpProxy proxy,Class<T> cls){
        HttpClient client=new HttpClient();
        if(proxy != null){
            client.setProxy(proxy);
        }
        try {
            ResponseStatus status=client.postXml(url,xmlbody);
            if(status == null){
                return null;
            }
            if(cls.isAssignableFrom(String.class)){
                return cls.cast(status.getContent());
            }
            return JacksonXmlUtils.xmlToBean(status.getContent(),cls);
        } catch (IOException e) {
            log.error("采用POST方式以xml格式请求接口时发生异常！",e);
        }
        return null;
    }

    /**
     * 采用双向证书以xml格式进行API调用
     * @param url 请求接口地址
     * @param xmlbody 请求体参数
     * @param certPath 证书路径
     * @param certPwd 证书密码
     * @param proxy 代理服务器
     * @param cls 要返回的class
     * @param <T>
     * @return
     */
    public static <T> T postXmlWithCert(String url,String xmlbody,String certPath,String certPwd,HttpProxy proxy,Class<T> cls){
        HttpClientWrapper wrapper=null;
        try {
            wrapper=new HttpClientWrapper(proxy,certPath,certPwd);
        } catch (Exception e) {
            log.error("在证书调用时设置证书发生错误！",e);
            return null;
        }
        HttpClient client=new HttpClient(wrapper);
        try {
            ResponseStatus status=client.postXml(url,xmlbody);
            if(status == null){
                return null;
            }
            if(cls.isAssignableFrom(String.class)){
                return cls.cast(status.getContent());
            }
            return JacksonXmlUtils.xmlToBean(status.getContent(),cls);
        } catch (IOException e) {
            log.error("采用POST方式以xml格式请求接口时发生异常！",e);
        }
        return null;
    }

    /**
     * 采用双向证书以xml格式进行API调用
     * @param url 请求接口地址
     * @param xmlbody 请求体参数
     * @param certPath 证书路径
     * @param certPwd 证书密码
     * @param proxyHost 代理host
     * @param proxyPort 代理端口号
     * @param cls 要返回的class
     * @param <T>
     * @return
     */
    public static <T> T postXmlWithCert(String url,String xmlbody,String certPath,String certPwd,String proxyHost,int proxyPort,Class<T> cls){
        HttpProxy proxy=new HttpProxy(proxyHost,proxyPort);
        return postXmlWithCert(url,xmlbody,certPath,certPwd,proxy,cls);
    }
}
