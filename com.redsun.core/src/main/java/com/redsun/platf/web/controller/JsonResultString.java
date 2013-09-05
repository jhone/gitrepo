package com.redsun.platf.web.controller;

import com.redsun.platf.util.JsonUtils;
import com.redsun.platf.util.JspUtils;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: joker pan
 * Date: 13-6-18
 * Time: 下午3:09
 * To change this template use File | Settings | File Templates.
 */
public class JsonResultString implements Serializable {


    private int status = 0;   //0 success ;-1 failure

    Object message;


    public JsonResultString() {
        this.status = 0;

    }

    public JsonResultString(Object message) {
        this.status = 0;
        this.message = message;
    }
    /**
     * 取得国际化消息
     * @param request
     * @param status
     */
    public  JsonResultString(HttpServletRequest request, int status) {
        this.status = status;
        String code = (status == 0 ? "message.success" : "message.failure");

        this.message = I18NMessage(request, code);
    }


    public int getStatus() {
        return status;
    }



    public void setStatus(int status) {
        this.status = status;

    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public String jsonString() {
        String result = "";
        try {
            result = JsonUtils.toJsonString(this);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return result;

    }

    public ModelMap mapResult() {

        ModelMap map = new ModelMap("message", this.getMessage());
        map.put("status", this.getStatus());
        return map;

    }

    /**
     * 取得國際化訊息
     *
     * @param code getRequestContext()  or <br>
     *             WebApplicationContextUtils.getWebApplicationContext().getServletContext()
     */
    String I18NMessage(HttpServletRequest request, String code) {
        try {
            return JspUtils.getMessage(request, code);

        } catch (Exception ex) {
            return code;
        }
    }
}
