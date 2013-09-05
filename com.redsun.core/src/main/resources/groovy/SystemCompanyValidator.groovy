package groovy

import com.redsun.platf.dao.base.IPagedDao
import com.redsun.platf.dao.sys.SystemCompanyDao
import com.redsun.platf.entity.sys.SystemCompany
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.ValidationUtils
import org.springframework.validation.Validator

/**
 * Created with IntelliJ IDEA. </br>
 * To change this template use File | Settings | File Templates.</br>
 * User: joker pan</br>
 * Date: 13-7-10</br>
 * Time: 下午1:29</br>
 * ------------------------------------------------------------------------------------</br>
 * Program ID   :                                                                      </br>
 * Program Name :                                                                      </br>
 * ------------------------------------------------------------------------------------</br>
 * <H3> Modification log </H3>
 * <pre>
 * Ver.    Date       Programmer    Remark
 * ------- ---------  ------------  ---------------------------------------------------
 * 1.0     13-7-10    joker pan    created
 * <pre/>
 *
 * */

@Component
class SystemCompanyValidator implements Validator {

    public SystemCompanyValidator() {}

    public SystemCompanyValidator(IPagedDao<SystemCompany, Long> dao) {
        systemCompanyDao = dao
    }

    public boolean supports(Class clazz) {
        return SystemCompany.class.isAssignableFrom(clazz)
    }

  private   SystemCompanyDao systemCompanyDao

    @Override
    public void validate(Object bean, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "companyNo",
                "system.companyNo.required", "编号不可为空");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "companyName",
                "system.companyName.required", "名称不能为空");


        SystemCompany entry = (SystemCompany) bean
        println entry.companyNo
        println systemCompanyDao

        println entry.companyName

        List<SystemCompany> result = systemCompanyDao.findBy("companyNo", entry.companyNo)
        println result

        if (result.size() > 0) {
            println "error companyno"
            errors.rejectValue("companyNo",
                    "error.field.duplicate", "输入的资料已存在！");
        }

        if (systemCompanyDao.findBy("companyName", entry.companyName).size() > 0)
            errors.rejectValue("companyName",
                    "error.field.duplicate", "输入的资料已存在！");


        if (((SystemCompany) bean).companyName?.trim()?.size() < 2) {

//               errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
            errors.rejectValue("companyName", "error.field.too-low"
                    , 2, "最少输入{0}  个字符！");
        }
//           errors.reject("errors.minlength",  "Cannot be less than 2 latter.")
    }


}
