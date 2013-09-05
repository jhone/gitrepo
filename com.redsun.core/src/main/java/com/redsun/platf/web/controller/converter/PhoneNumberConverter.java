package com.redsun.platf.web.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Dick Pan
 * Date: 2012/7/9
 * Time: 上午 11:32
 * To change this template use File | Settings | File Templates.
 */

public class PhoneNumberConverter implements Converter<String, Object> {

    Pattern pattern = Pattern.compile("^(\\d{3,4})-(\\d{7,8})$");

    public PhoneNumberConverter(Pattern pattern) {
        this.pattern = pattern;
    }

    /**
     * 把string 換成相應的對象
     * @param source
     * @return
     */
    @Override
    public Object convert(String source) {


        if (!StringUtils.hasLength(source)) {
//①如果source为空 返回null
            return null;
        }
        System.out.println(source);

        Matcher matcher = pattern.matcher(source);
        if (matcher.matches()) {
//②如果匹配 进行转换
            PhoneNumberModel phoneNumber = new PhoneNumberModel();

            phoneNumber.setAreaCode(matcher.group(1));
            phoneNumber.setPhoneNumber(matcher.group(2));
            return phoneNumber;
        } else {
//③如果不匹配 转换失败
            throw new IllegalArgumentException(String.format("类型转换失败，需要格式[010-12345678]，但格式是[%s]", source));
        }
    }

}
