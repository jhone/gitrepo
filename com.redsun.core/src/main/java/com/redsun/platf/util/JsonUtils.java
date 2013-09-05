package com.redsun.platf.util;

import com.redsun.platf.entity.account.UserAccount;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2012/12/7
 * Time: 下午 3:57
 * To change this template use File | Settings | File Templates.
 */
public class JsonUtils {
    private static JsonGenerator jsonGenerator = null;
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        //認單引號
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        try {
            jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String toJsonString(Object bean) throws IOException {
        StringWriter w = new StringWriter();

        jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(w/*, JsonEncoding.UTF8*/);

        jsonGenerator.writeObject(bean);
        return w.toString();
    }

    /**
     *    可以转换json字窜到原来的对象
     *    e.g.: prase(jsonString,User.class)
     * @param jsonString
     * @param clazz
     * @return
     * @throws IOException
     */
    public static Object prase(String jsonString, Class<?> clazz) throws IOException {
        //writeObject可以转换java对象，eg:JavaBean/Map/List/Array等
        //readObject可以转换json对象，eg:JavaBean/Map/List/Array等
//        StringWriter w = new StringWriter();

        StringReader r = new StringReader(jsonString);
        JsonParser parser = null;

        parser = objectMapper.getJsonFactory().createJsonParser(r);

        return parser.readValueAs(clazz);

    }

//    public static JSONObject praseJson(String jsonString, Class<?> clazz) throws IOException {
//        StringReader r = new StringReader(jsonString);
//
//        JsonParser parser = null;
//
//        parser = objectMapper.getJsonFactory().createJsonParser(r);
//
//        return new JSONObject(parser.readValueAs(clazz));
//
//    }
//    public static JSONObject praseJson(String jsonString) throws IOException {
//
//        return praseJson(jsonString,java.lang.String.class);
//
//    }

    /**

     * <b>function:</b>将java对象转换成json字符串

     * @author hoojo

     * @createDate 2010-11-23 下午06:01:10

     */



    public  static void convert2JSON(Object bean)  throws IOException  {

        StringWriter r = new StringWriter();


              //writeObject可以转换java对象，eg:JavaBean/Map/List/Array等

            jsonGenerator.writeObject(bean);


            //writeValue具有和writeObject相同的功能

//            objectMapper.writeValue(System.out, bean);


    }

    public  static void main(String[] args) throws IOException {
        UserAccount  bean=new UserAccount();


        Map<String, Object> map = new HashMap<String, Object>();

        map.put("name", bean.getLoginName());

        map.put("account", bean);

//        bean = new AccountBean();
//
//        bean.setAddress("china-Beijin");

        bean.setEmail("hoojo@qq.com");

        map.put("account2", bean);
        System.out.println("jsonGenerator");

//        jsonGenerator.writeObject(map);
//        jsonGenerator.writeObject(map);
        String s ="{\"account2\":{\"id\":null,\"lastUpdateDate\":null,\"creationDate\":null,\"createdBy\":null,\"lastUpdatedBy\":null,\"accountId\":null,\"deptId\":null,\"email\":\"hoojo@qq.com\",\"loginName\":null,\"password\":null,\"enable\":null,\"birthday\":null,\"age\":null,\"roleList\":[],\"activPlants\":[],\"companies\":[],\"roleNames\":\"\",\"roleIds\":[]},\"name\":null,\"account\":{\"id\":null,\"lastUpdateDate\":null,\"creationDate\":null,\"createdBy\":null,\"lastUpdatedBy\":null,\"accountId\":null,\"deptId\":null,\"email\":\"hoojo@qq.com\",\"loginName\":null,\"password\":null,\"enable\":null,\"birthday\":null,\"age\":null,\"roleList\":[],\"activPlants\":[],\"companies\":[],\"roleNames\":\"\",\"roleIds\":[]}}";
//        JSONObject object=  prase("{\"account2\":123}",java.util.Arrays.class);
        Object object=prase(s,map.getClass());
        System.out.println(object);
    }


}
