package com.redsun.platf.util.xml;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.net.URL;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * To change this template use File | Settings | File Templates.
 * User: joker pan
 * Date: 2013/4/8
 * Time: 下午 3:06
 * ------------------------------------------------------------------------------------<p/>
 * Program ID   :                                                                      <p/>
 * Program Name :                                                                      <p/>
 * ------------------------------------------------------------------------------------<p/>
 * <H3> Modification log </H3>
 * <pre>
 * Ver.    Date       Programmer    Remark
 * ------- ---------  ------------  ---------------------------------
 * 1.0     2013/4/8    joker pan    created
 * <pre/>
 * <br/>
 */
public class ParseXML {
      //定义一个Properties 用来存放 dbhost dbuser dbpassword的值
    private Properties props;

    //这里的props
    public Properties getProps() {
        return this.props;
    }

    public void parse(String filename) throws Exception {
        //将我们的解析器对象化
        ConfigParser handler = new ConfigParser();
        //获取SAX工厂对象
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(false);
        factory.setValidating(false);
        //获取SAX解析
        SAXParser parser = factory.newSAXParser();
        //得到配置文件myenv.xml所在目录. tomcat中是在WEB-INF/classes
        //下例中BeansConstants是用来存放xml文件中配置信息的类,可以自己代替或定义
        URL confURL = this.getClass().getClassLoader().getResource(filename);
        try {
            //将解析器和解析对象myenv.xml联系起来,开始解析
            parser.parse(confURL.toString(), handler);
            //获取解析成功后的属性 以后 我们其他应用程序只要调用本程序的props就可以提取出属性名称和值了
            props = handler.getProps();
        } finally {
            factory = null;
            parser = null;
            handler = null;
        }
    }
}
