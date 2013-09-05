package com.redsun.platf;

import com.redsun.platf.util.JsonUtils;
import com.redsun.platf.util.MultipleSearchFilter;
import com.redsun.platf.util.search.SearchFilterRule;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2012/12/7
 * Time: 下午 2:47
 * To change this template use File | Settings | File Templates.
 */
public class TestJackson {


    private JsonGenerator jsonGenerator = null;
    private ObjectMapper objectMapper = null;
    private MultipleSearchFilter bean = new MultipleSearchFilter();
    //filter:
    //  {"groupOp":"OR","rules":[
    //        {"field":"name","op":"le","data":"CP"},
    //        {"field":"lastUpdatedBy","op":"eq","data":"EPSYS"},
    //        {"field":"name","op":"eq","data":"EPSYS"}
    //    ]}


    private String str = "{\"groupOp\":\"OR\",\"rules\":[\n" +
            "           {\"field\":\"name\",\"op\":\"le\",\"data\":\"CP\"},\n" +
            "           {\"field\":\"lastUpdatedBy\",\"op\":\"eq\",\"data\":\"EPSYS\"},\n" +
            "            {\"field\":\"lastUpdatedDate\",\"op\":\"le\",\"data\":\"2012/12/01\"}\n" +
            "        ]}";

    @Before
    public void init() {
        bean = new MultipleSearchFilter();
        bean.setGroupOp("OR");
        SearchFilterRule rule = new SearchFilterRule();
        rule.setField("name");
        rule.setData("CP");
        rule.setOp("eq");
        List<SearchFilterRule> rules = new ArrayList<SearchFilterRule>();
        rules.add(rule);

        bean.setRules(rules);

        objectMapper = new ObjectMapper();
        try {
            jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testWriter() throws IOException {
        //writeObject可以转换java对象，eg:JavaBean/Map/List/Array等

        jsonGenerator.writeObject(bean);

        System.out.println();


        System.out.println("ObjectMapper");

        //writeValue具有和writeObject相同的功能

        objectMapper.writeValue(System.out, bean);

    }

    @Test
    public void testReader() throws IOException {
        //writeObject可以转换java对象，eg:JavaBean/Map/List/Array等
        //readObject可以转换json对象，eg:JavaBean/Map/List/Array等
//        StringWriter w = new StringWriter();

        StringReader r = new StringReader(str);
        JsonParser parser = objectMapper.getJsonFactory().createJsonParser(r);

//        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
//
//        MultipleSearchFilter filterObject = parser.readValueAs(MultipleSearchFilter.class);

        System.out.println("ObjectMapper");
        //writeValue具有和writeObject相同的功能
//        System.out.println(filterObject);

    }

    @Test
    public void testUtils() throws IOException {
        System.out.println("get 字符竄");
        System.out.println(  JsonUtils.toJsonString(bean));
        System.out.println("read to object:"+str);
        System.out.println(JsonUtils.prase(str, MultipleSearchFilter.class));


    }

    @After
    public void destory() {
        try {
            if (jsonGenerator != null) {
                jsonGenerator.flush();
            }
            if (!jsonGenerator.isClosed()) {
                jsonGenerator.close();
            }
            jsonGenerator = null;
            objectMapper = null;
            bean = null;
            System.gc();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
