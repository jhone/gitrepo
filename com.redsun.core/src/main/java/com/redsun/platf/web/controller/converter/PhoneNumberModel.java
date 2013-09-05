package com.redsun.platf.web.controller.converter;

import com.redsun.platf.entity.BaseEntity;

/**
 * Created with IntelliJ IDEA.
 * User: dick pan
 * Date: 2012/7/9
 * Time: 下午 4:40
 * To change this template use File | Settings | File Templates.
 */

public class PhoneNumberModel extends BaseEntity {
    private String areaCode;
    private String phoneNumber;

    public PhoneNumberModel() {
    }

    public PhoneNumberModel(String areaCode, String phoneNumber) {
        this.areaCode = areaCode;
        this.phoneNumber = phoneNumber;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "PhoneNumberModel{" +
                "areaCode='" + areaCode + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
