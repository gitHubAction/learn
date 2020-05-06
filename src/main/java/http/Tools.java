package http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import org.apache.http.HttpException;
import org.apache.http.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.*;


/**
 * @author Administrator
 * String 相关
 */
public class Tools {

	static Logger logger = LoggerFactory.getLogger(Tools.class);
	private static ObjectMapper mapper;

	private static ObjectMapper converterMapper;

	private static final PropertyNamingStrategy DEFAULT_NAMING_STRATEGY=new PropertyNamingStrategy.SnakeCaseStrategy();

	static{
		mapper=new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);

		converterMapper=new ObjectMapper();
		converterMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
		converterMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		converterMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
	}

	private static ObjectMapper getMapper(){
		mapper.setPropertyNamingStrategy(DEFAULT_NAMING_STRATEGY);
		return mapper;
	}

	private static ObjectMapper getConverterMapper(){
		return converterMapper;
	}

	/**
	 * 检测字符串是否不为空(null,"","null")
	 * 
	 * @param s
	 * @return 不为空则返回true，否则返回false
	 */
	public static boolean notEmpty(String s) {
		return s != null && !"".equals(s.trim()) && !"null".equals(s.trim());
	}

	/**
	 * 检测字符串是否为空(null,"","null")
	 * 
	 * @param s
	 * @return 为空则返回true，不否则返回false
	 */
	public static boolean isEmpty(String s) {
		return s == null || "".equals(s.trim()) || "null".equals(s.trim());
	}

	public static boolean isEmpty(Object obj){
		return obj == null || Tools.isEmpty(obj.toString());
	}

	/**
	 * 检测Integer是否为空(null,"","null",0)
	 * 
	 * @return 为空则返回true，不否则返回false
	 */
	public static boolean isEmpty(Integer v) {
		return v == null || "".equals(v) || "null".equals(v) || 0 == v;
	}

	public static boolean isEmpty(Byte v) {
		return v == null || "".equals(v) || "null".equals(v) || 0 == v;
	}
	/**
	 * 检测Long是否为空(null,"","null",0)
	 * 
	 * @return 为空则返回true，不否则返回false
	 */
	public static boolean isEmpty(Long v) {
		return v == null || "".equals(v) || "null".equals(v) || 0 == v;
	}

	/**
	 * 字符数组转字符串(以，分割)
	 * @param array
	 * @return 
	 */
	public static String strArr2String(String[] array){
		if (array == null || array.length == 0) {
			return "";
		}
		StringBuffer strArray = new StringBuffer();
		for(String data : array){
			if (isEmpty(data)) {
				continue;
			}
			strArray.append(data + ",");
		}
		if (strArray.length() > 0) {
			strArray.deleteCharAt(strArray.length() - 1);
		}
		return strArray.toString();
	}
	/**
	 * 字符串转换为字符串数组
	 * 
	 * @param str
	 *            字符串
	 * @param splitRegex
	 *            分隔符
	 * @return
	 */
	public static String[] str2StrArray(String str, String splitRegex) {
		if (isEmpty(str)) {
			return null;
		}
		return str.split(splitRegex);
	}
	
	/**
	 * 数组中是否包含某值
	 * @param objs
	 * @param obj
	 * @return
	 */
	public static boolean isArrayContains(Object[] objs,Object obj){
		if(objs == null || objs.length == 0){
			return false;
		}
		for(Object o : objs){
			if(o != null && o.equals(obj)){
				return true;
			}
		}
		return false;
	}

	/**
	 * 将字符串以分隔符分隔后放入集合中
	 * 
	 * @param str
	 *            要分隔的字符串
	 * @param format
	 *            分隔符
	 * @return
	 */
	public static Set<String> str2Set(String str, String format) {
		if (isEmpty(str)) {
			return null;
		}
		if (isEmpty(format)) {
			format = ",";
		}
		Set<String> set = new HashSet<String>();
		for (String item : str.split(format)) {
			set.add(item);
		}
		return set;
	}

	/**
	 * 用默认的分隔符(,)将字符串转换为字符串数组
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static String[] str2StrArray(String str) {
		return str2StrArray(str, ",\\s*");
	}
	
	/**
	 * 将字符串通过分隔符拆分，在前后两端加特殊符号，再拼装成字符串
	 * @param str 字符串
	 * @param split 分隔符
	 * @param firstMark 在字符串前端 加特殊符号
	 * @param lastMark 在字符串后端 加特殊符号
	 * @return
	 */
	public static String instrStrSplit(String str,String split,String firstMark,String lastMark){
		StringBuffer result = new StringBuffer();
		if(notEmpty(str)&&notEmpty(split)){
			String[] strs = str.split(split);
			for(String s:strs){
				result.append(firstMark+s+lastMark+split);
			}
		}
		if(result.indexOf(split)>-1)
			return result.substring(0,result.lastIndexOf(split));
		return result.toString();
	}
	
	/**
	 * 将异常堆栈转换成字符串
	 * @param e
	 * @return
	 */
	public static String stackTraceToString(Exception e){
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}
	
	/**
	 * 读取流
	 * 
	 * @param inStream
	 * @return 字节数组
	 * @throws Exception
	 */
	public static byte[] readStream(InputStream inStream) throws Exception {
		int count = 0; 
		while (count == 0) { 
			count = inStream.available(); 
		} 
		byte[] buffer = new byte[count];
		inStream.read(buffer);
		inStream.close();
		return buffer;
	}
	
	/** 
     * Json字符串转对象 
     * @param <T> 
     * @param jsonStr
     * @param clazz 
     * @return 
     * @throws Exception 
     */  
    public static <T> T jsonStrToBean(String jsonStr, Class<T> clazz) throws Exception {  
        return getMapper().readValue(jsonStr, clazz);
    }

    public static <T> T jsonStrToBean(String jsonStr, PropertyNamingStrategy namingStrategy, Class<T> cls) throws IOException {
    	ObjectMapper mapper=getMapper();
    	if(namingStrategy != null){
			mapper.setPropertyNamingStrategy(namingStrategy);
		}
		return mapper.readValue(jsonStr, cls);
	}

	/**
	 * 将json字符串转换为指定的对象集合
	 * @param jsonStr json字符串
	 * @param collectionCls 集合类class
	 * @param clazz 对象class
	 * @param <T>
	 * @return
	 * @throws IOException
	 */
    public static <T,S extends  Collection> Collection<T> jsonStrToCollectionBean(String jsonStr,Class<S> collectionCls,Class<T> clazz) throws IOException {
    	JavaType javaType=getCollectionType(collectionCls,clazz);
    	return getConverterMapper().readValue(jsonStr,javaType);
	}
    
    /** 
     * 对象转Json字符串 
     * @param bean 
     * @return 
     * @throws Exception 
     */  
    public static String beanToJsonStr(Object bean) throws Exception {  
        return getMapper().writeValueAsString(bean);
    }

    public static String beanToJsonStr(Object bean, PropertyNamingStrategy namingStrategy) throws JsonProcessingException {
    	ObjectMapper mapper=getMapper();
    	if(namingStrategy != null){
    		mapper.setPropertyNamingStrategy(namingStrategy);
		}
		return mapper.writeValueAsString(bean);
	}

	/**
	 * 获取泛型的Collection Type
	 * @param collectionClass 泛型的Collection
	 * @param elementClasses 元素类
	 * @return JavaType Java类型
	 * @since 1.0
	 */
	public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
		return getMapper().getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}

	/**
	 * jackson序列化Object的转换
	 * @param obj
	 * @param cls
	 * @param <T>
	 * @return
	 */
	public static <T> T convertJacksonObject(Object obj,Class<T> cls){
		return getConverterMapper().convertValue(obj, cls);
	}

	/**
	 * jackson序列化Object转换为集合
	 * @param obj
	 * @param collectionCls
	 * @param clazz
	 * @param <T>
	 * @param <S>
	 * @return
	 */
	public static <T,S extends Collection> Collection<T> convertJacksonCollection(Object obj,Class<S> collectionCls,Class<T> clazz){
		JavaType javaType=getCollectionType(collectionCls,clazz);
		return getConverterMapper().convertValue(obj,javaType);
	}


	/**
	 * 将list转换为以指定字段值为键值的map
	 * @param list 要转换的list
	 * @param fieldName 指定字段
	 * @param isSort 是否按先进先出的顺序排序
	 * @return
	 */
	public static Map<?,?> listConvertToMap(List<?> list, String fieldName, boolean isSort){
		if(list == null || list.size()==0){
			return null;
		}
		Map<Object,Object> rmap=null;
		if(isSort){
			rmap=new LinkedHashMap<Object, Object>();
		}else{
			rmap=new HashMap<Object,Object>();
		}
		try {
			/*for(Object vobj : list){
				rmap.put(ReflectHelper.getFieldValueByFieldName(vobj, String.valueOf(fieldName)),vobj);
			}*/
		} catch (SecurityException | IllegalArgumentException e) {
			e.printStackTrace();
		}
		return rmap;
	}

	/**
	 * 将网址处理为标准的url地址
	 * @param url 要做处理的url地址
	 * @return
	 */
	public static String processStandardUrl(String url){
		if(isEmpty(url)){
			return null;
		}
		url=url.trim();
		if(url.startsWith("http://") || url.startsWith("https://")){
			return url;
		}else if(!url.startsWith("/")){
			url="/"+url;
		}
		if(url.endsWith("/")){
			url=url.substring(0,url.length()-1);
		}
		return url;
	}

	/**
	 * 将list转换为以指定字段值为键值的map（不进行排序）
	 * @param list 要转换的list
	 * @param fieldName 指定字段
	 * @return
	 */
	public static Map<?,?> listConvertToMap(List<?> list,String fieldName){
		return listConvertToMap(list,fieldName,false);
	}

	/**
	 * 判断字符串是否为纯数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static void inputStreamToFile(InputStream inputStream,File file) throws IOException {
		OutputStream os=null;
		try {
			os=new FileOutputStream(file);
			byte[] buffer=new byte[8192];
			int len=0;
			while((len=inputStream.read(buffer,0,8192)) != -1){
                os.write(buffer,0,len);
			}
		} finally {
			if(os != null){
				os.close();
			}
			if(inputStream != null){
				inputStream.close();
			}
		}
	}

	public static byte[] File2byte(File file)
	{
		byte[] buffer = null;
		try
		{
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int n;
			while ((n = fis.read(b)) != -1)
			{
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		}
		catch (IOException e){
			e.printStackTrace();
		}
		return buffer;
	}

	public static void byte2File(byte[] buf, String filePath, String fileName)
	{
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try
		{
			File dir = new File(filePath);
			if (!dir.exists() && dir.isDirectory())
			{
				dir.mkdirs();
			}
			file = new File(filePath + File.separator + fileName);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(buf);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (bos != null)
			{
				try
				{
					bos.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			if (fos != null)
			{
				try
				{
					fos.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 获取远程文件
	 * @param attaUrl 远程文件URL
	 * @return
	 * @throws IOException
	 * @throws HttpException
	 */
	public static InputStream getInputStreamByUrl(String attaUrl) throws IOException, HttpException {
		if(Tools.isEmpty(attaUrl) /*|| !ToolsURL.isURL(attaUrl)*/){
			return null;
		}
		attaUrl=attaUrl.trim();
		HttpClient client=new HttpClient();
		ResponseStatus responseStatus=client.get(attaUrl);
		if(responseStatus == null){
			return null;
		}

		byte[] contentBytes=responseStatus.getContentBytes();
		return new ByteArrayInputStream(contentBytes);
	}

	/**
	 * url请求参数拼装
	 * @param parameters 请求参数
	 * @param parameterSeparator 连接符号
	 * @return
	 */
	public static String UrlParamsToStr(List<? extends NameValuePair> parameters, char parameterSeparator) {
		StringBuilder result = new StringBuilder();
		Iterator it = parameters.iterator();

		while(it.hasNext()) {
			NameValuePair parameter = (NameValuePair)it.next();
			String encodedName = parameter.getName();
			String encodedValue = parameter.getValue();
			if (result.length() > 0) {
				result.append(parameterSeparator);
			}

			result.append(encodedName);
			if (Tools.notEmpty(encodedName)) {
				result.append("=");
			}
			if(encodedValue != null){
				result.append(encodedValue);
			}
		}

		return result.toString();
	}

	/**
	 * 将LinkedHashMap 转为 object
	 */
	public static Object LinkedHashMapToObject(LinkedHashMap linkedHashMap, Object object){
		Map map = linkedHashMap;
		if(linkedHashMap.isEmpty()||!(object instanceof Object)){
			return null;
		}
		Iterator iterator = map.entrySet().iterator();
		Class clazz = object.getClass();
		Field[] fields=clazz.getDeclaredFields();
		List list = new ArrayList();
		for(Field field:fields){
			list.add(field.getName());
		}
		//目前支持一级父类的属性
		Class superClass = clazz.getSuperclass();
		List list2 = new ArrayList();
		if(superClass!=null){
			Field[] fields1=superClass.getDeclaredFields();
			for(Field field1:fields1){
				list2.add(field1.getName());
			}
		}
		while (iterator.hasNext()){
			Map.Entry hashSet = (Map.Entry)iterator.next();
			Object key = hashSet.getKey();
			Object value = hashSet.getValue();
			addFieldValue(list,key,value,clazz,object);
			if(list2.size()>0){
				addFieldValue(list2,key,value,superClass,object);
			}
		}
		return object;
	}

	/**
	 * 为集合对象添加触发懒加载的字段
	 * @param models 集合对象
	 * @param unlazyFields 需要触发懒加载的字段
	 */
	/*public static void addUnLazyFields(Collection<? extends SwordsAbstractModel> models,String... unlazyFields){
		if(models == null || models.size() == 0 || unlazyFields == null || unlazyFields.length == 0){
			return;
		}
		for(SwordsAbstractModel model : models){
			model.unlazy(unlazyFields);
		}
	}*/

	/**
	 * 为分页集合对象添加需要触发懒加载的字段
	 * @param caps 分页集合对象
	 * @param unlazyFields 需要触发懒加载的字段
	 */
	/*public static void addUnLazyFields(ContentsAndPage<? extends SwordsAbstractModel> caps,String... unlazyFields){
		if(caps == null || caps.getContents() == null || caps.getContents().size() == 0 || unlazyFields == null || unlazyFields.length == 0){
			return;
		}
		addUnLazyFields(caps.getContents(),unlazyFields);
	}*/

	/**
	 * 类属性赋值
	 */
	private static void addFieldValue(List list,Object key,Object value,Class clazz,Object object){
		if(list.contains(key.toString())&&value!=null){
			try {
				Field field = clazz.getDeclaredField(key.toString());
				String type = field.getGenericType().toString();//获取字段类型

				field.setAccessible(true);
				if (type.equals("class java.lang.String")) {
					field.set(object,""+value);
				}else if(type.equals("class java.lang.Integer")){
					field.set(object,Integer.valueOf(value.toString()));
				}else if(type.equals("class java.lang.Boolean")){
					field.set(object,Boolean.valueOf(value.toString()));
				}/*else if(type.equals("class java.util.Date")){
					field.set(object, ToolsDate.parse(value.toString()));
				}*/else if(type.equals("class java.sql.Timestamp")){
					field.set(object,new Timestamp((Long)value));
				}else if(type.equals("class java.lang.Byte")){
					field.set(object,Byte.parseByte(value.toString()));
				}else if(type.equals("class java.lang.Short")){
					field.set(object,Short.valueOf(value.toString()));
				}else if(type.equals("class java.lang.Long")){
					field.set(object,Long.valueOf(value.toString()));
				}else{
					field.set(object,value);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 占位符替换
	 */
	public static String replacePlaceholder(String data,Map<String, String> value){
		for (Map.Entry<String, String> entry : value.entrySet()) {
			data = data.replace( entry.getKey() , entry.getValue());
		}
		return data;
	}

	/**
	 * 信息脱敏
	 */
	public static String dataDesensitization(String str){
		if (Tools.isEmpty(str) || str.length() < 8) {
			return str;
		}
		if (str.length() == 11) {//手机号
			return str.substring(0,3)+"*****"+str.substring(str.length()-4,str.length());
		}
		return str.substring(0,4)+"*****"+str.substring(str.length()-4,str.length());
	}

	public static void test(){
		//测试git用户
		//测试2
	}
}
