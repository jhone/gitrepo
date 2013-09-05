package com.redsun.platf.web.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.LocaleResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA. </br>
 * To change this template use File | Settings | File Templates.</br>
 * User: joker pan</br>
 * Date: 13-7-11</br>
 * Time: 下午1:06</br>
 * ------------------------------------------------------------------------------------</br>
 * Program ID   :                                                                      </br>
 * Program Name :                                                                      </br>
 * ------------------------------------------------------------------------------------</br>
 * <H3> Modification log </H3>
 * <pre>
 * Ver.    Date       Programmer    Remark
 * ------- ---------  ------------  ---------------------------------------------------
 * 1.0     13-7-11    joker pan    created
 * <pre/>
 */

@Controller
@RequestMapping("/changeLocale")
public class LocaleChangeController {


        @Resource
        private LocaleResolver localeResolver;

        @RequestMapping
        public String changeLocal(String locale,
                HttpServletRequest request,
                HttpServletResponse response){
            Locale l = new Locale(locale);
            localeResolver.setLocale(request, response, l);

//            return "redirect:/index.html";
            return "/home";
        }

}
