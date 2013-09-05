package com.redsun.platf.util.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * To change this template use File | Settings | File Templates.
 * User: joker pan
 * Date: 2013/4/8
 * Time: 下午 3:08
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
public class ConfigParser extends DefaultHandler{


    private Properties props;
    private String currentSet;
    private String currentName;
    private StringBuffer currentValue = new StringBuffer();

    public ConfigParser() {
        this.props = new Properties();
    }

    public Properties getProps() {
        return this.props;
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
        currentValue.delete(0, currentValue.length());
        this.currentName = qName;


    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        currentValue.append(ch, start, length);
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        props.put(qName.toLowerCase(), currentValue.toString().trim());
    }
}
