package com.redsun.platf.web.controller.converter;

import com.redsun.platf.sys.EPApplicationAttributes;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import java.text.SimpleDateFormat;

/**
 * Created with IntelliJ IDEA.
 * User: dick pan
 * Date: 2012/7/13
 * Time: 上午 8:50
 * To change this template use File | Settings | File Templates.
 */
//@Component
public class EPJsonObjectMapper extends ObjectMapper {
    String mask =EPApplicationAttributes.DATE_FORMAT;
    public EPJsonObjectMapper() {
        super();
        super.configure(
                      SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);

//      yyyy-MM-dd HH:mm:ss
        this.getSerializationConfig().withDateFormat(new SimpleDateFormat(mask));
//        this.getSerializationConfig().setDateFormat(new SimpleDateFormat(mask));
    }
//    @PostConstruct
//    	public void afterPropertiesSet() throws Exception {
//    		super.configure(
//                    SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
//                                //Using Jackson > 1.8 it's possible to invoke 'getSerializationConfig().withDateFormat(new SimpleDateFormat(mask));', doesnt work ;(
////    		getSerializationConfig().setDateFormat(new SimpleDateFormat(mask));
//        getSerializationConfig().withDateFormat(new SimpleDateFormat(mask))  ;
//    	}

}
