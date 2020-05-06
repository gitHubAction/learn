package http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * 利用jackson做xml转换
 * @author huanglitao
 */
public class JacksonXmlUtils {

    /**
     * can reuse, share globally
     */
    private static final XmlMapper xml = new XmlMapper();

    private static final PropertyNamingStrategy DEFAULT_NAMING_STRATEGY= PropertyNamingStrategy.SNAKE_CASE;

    static {
        //采用驼峰式命名规则
        xml.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        xml.configure(SerializationFeature.WRAP_ROOT_VALUE,false);
        xml.configure(DeserializationFeature.UNWRAP_ROOT_VALUE,false);
        xml.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        xml.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        xml.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
    }

    /**
     * return a XmlMapper that is singleton
     * @return
     */
    public static XmlMapper getXmlMapper() {
        xml.setPropertyNamingStrategy(DEFAULT_NAMING_STRATEGY);
        return xml;
    }

    public static <T> T xmlToBean(String content, Class<T> cls) throws IOException {
        XmlMapper xml = getXmlMapper();
        T obj = xml.readValue(content,cls);
        return obj;
    }

    public static <T> T xmlToBean(String content, PropertyNamingStrategy namingStrategy, Class<T> cls) throws IOException {
        XmlMapper xml = getXmlMapper();
        if(namingStrategy != null){
            xml.setPropertyNamingStrategy(namingStrategy);
        }
        return xml.readValue(content,cls);
    }

    /**
     * @param xmlFile
     * @param cls
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T xmlToBean(File xmlFile, Class<T> cls) throws IOException {
        XmlMapper xml = getXmlMapper();
        T obj = xml.readValue(xmlFile, cls);
        return obj;
    }

    /**
     * @param xmlInputStream
     * @param cls
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T xmlToBean(InputStream xmlInputStream, Class<T> cls) throws IOException {
        XmlMapper xml = getXmlMapper();
        T obj = xml.readValue(xmlInputStream, cls);
        return obj;
    }

    public static <T> String beanToXml(T bean) throws JsonProcessingException {
        XmlMapper xml = getXmlMapper();
        xml.configure(SerializationFeature.WRAP_ROOT_VALUE,false);
        xml.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS,true);
        xml.configure(MapperFeature.USE_WRAPPER_NAME_AS_PROPERTY_NAME,false);
        xml.addMixIn(Map.class,HashMapMixIn.class);
        //设置转换模式
        xml.enable(MapperFeature.USE_STD_BEAN_NAMING);
        String string = xml.writeValueAsString(bean);
        return string;
    }

    @JacksonXmlRootElement(localName = "xml")
    public interface HashMapMixIn {

        @JacksonXmlCData(true)
        String getBody();
    }
}
