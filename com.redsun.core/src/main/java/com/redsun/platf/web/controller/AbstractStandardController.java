package com.redsun.platf.web.controller;

import com.redsun.platf.dao.base.IPagedDao;
import com.redsun.platf.entity.BaseEntity;
import com.redsun.platf.sys.EPApplicationAttributes;
import com.redsun.platf.util.search.MorePropertyFilter;
import com.redsun.platf.util.search.PropertyFilterUtil;
import com.redsun.platf.util.sideutil.PagedResult;
import com.redsun.platf.web.framework.StandardController;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 參數可以是以下：
 * HttpServletRequest/HttpServletResponse、HttpSession、Locale、InputStream、 OutputStream、File[]
 */
@Controller
//@RequestMapping("/authority")
@Transactional
@SuppressWarnings("unchecked")
public abstract class AbstractStandardController<T extends BaseEntity> extends StandardController {

    protected final static String URL_ERROR = "/failure";
    //當前URL
//    protected String URL = "";
    //--分頁記錄属性--- 改在request 參數裡//
//    private PagedResult<T> tempPagedResult = new PagedResult<T>(8, "id");// 每页10条记录

    //-- 页面属性 --//
    protected Long id = null;// 0L;
    //--頁面的model entity --//
    private T model;

    private T originalModel; //修改前狀態


    //回傳的map
    protected Map<String, String> resultMessage = new HashMap<String, String>();

    IPagedDao<T, Long> dao = null;

    /**
     * 子類必須設定URL前綴
     *
     * @return url前綴，不包括*號
     */
    public abstract String getUrl();

    /**
     * 子類必須設定EntityDAO
     * <p/>eg:daoFactory.getAuthorityDao()
     *
     * @return IPagedDao<T, Long>
     */
    public abstract IPagedDao<T, Long> getDao();

    /**
     * 默認路徑
     *
     * @return
     * @throws Exception
     */
    @RequestMapping( method = RequestMethod.GET)
    protected ModelAndView listRoot(HttpServletRequest request,
                                PagedResult<T> parameteredPage) throws
            Exception {
        return this.listModel(request, parameteredPage);
    }
 /**
     * 查询
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    protected ModelAndView list(HttpServletRequest request,
                                PagedResult<T> parameteredPage) throws
            Exception {
        return this.listModel(request, parameteredPage);
    }


    /**
     * 主要查询 for jqrid
     *
     * @param request
     * @param parameteredPage 保存了request參數的page對象
     * @return ModelAndView with json result
     * @throws Exception
     * @author pyc 2013/01/08 pagedReaulst =null ,parameteredPage=tempPagedResult
     * <p/>
     * jqGrid 是用get方法
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/grid_list")
    protected ModelAndView listModel(HttpServletRequest request,
                                     PagedResult<T> parameteredPage
//                                     String order
    ) throws Exception {

        logger.info("request param gridModel :{}", parameteredPage);
//        logger.info("order param gridModel :{}", order);
        List<MorePropertyFilter> filters = new ArrayList<MorePropertyFilter>();

        System.out.println(parameteredPage);
        // bug fixed for  test testunit
        if (parameteredPage == null)
            //get from attribute
            try {
                parameteredPage = (PagedResult<T>) request.getAttribute("pagedResult");
                logger.debug("request attribute pagedResult: {}", parameteredPage);

            } catch (Exception ex) {

            }

        if (parameteredPage == null) {
            parameteredPage = new PagedResult<T>(10, "id");  // 每页10条记录
            logger.debug("request empty pagedResult: {}", parameteredPage);
        }

        String json = (parameteredPage == null ? "" : parameteredPage.getFilters());

//        logger.debug("json of pagedResult {}", json);
        //request
        if (request != null && parameteredPage.isSearch() && StringUtils.isNotBlank(json)) {

            /*     filters = MorePropertyFilter
                                .buildFromHttpRequest(request);   //error 因為傳過來的是json，需將json轉換後
                logger.debug("search json {} ,{}, {}",
                                    parameteredPage.getSearchOper(), parameteredPage.getSearchField(),
                                    parameteredPage.getSearchString());
            */


            prepareModel();
            filters = PropertyFilterUtil.buildFilterFromJson(json, model.getClass());

            logger.debug("search filter {}", filters);
        }


        //根據gridModel request 的參數，返回查詢結果 pagedResult
        parameteredPage = getDao().findPage(parameteredPage, filters);

        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("pagedResult", parameteredPage);

        map.put("page", parameteredPage.getCurrentPage());//第幾頁
        map.put("record", parameteredPage.getResult().size());//所有共幾條記錄

        map.put("rows", parameteredPage.getResult());//每頁的数据,共幾行
        map.put("total", parameteredPage.getTotalCount());//totalcount
        map.put("pages", parameteredPage.getTotalPages());//total pages

//        System.out.println(parameteredPage.getResult());

        return new ModelAndView(getUrl() + "/list", map);
    }

    /**
     * 根據用戶動作，執行對應操作 for jqrid
     *
     * @param request
     * @param gridModel
     * @return Map<String, String>
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/grid_edit")
    protected ModelMap editModel(HttpServletRequest request,
                                 PagedResult<T> gridModel,
                                 String oper, String ids, BindingResult result) throws Exception {


//        String operation = gridModel.getOper().toLowerCase(); //直接參數傳過來


        logger.debug("oper :{},idAll:{},gridModel {}:", oper, ids, gridModel);

        if (oper.equalsIgnoreCase("add")) {
            this.id = null;
            logger.debug("prepareModelFromRequest gridModel {}:", gridModel);

            return this.save(request, model, result);
        } else if (oper.equalsIgnoreCase("edit")) {
            this.id = Long.parseLong(gridModel.getId()[0]);
            //依request id將舊DATA SET 給原始的、當前model
            this.setOriginalModel(getDao().get(this.id));


            return this.update(request, model, result);
        } else if (oper.equalsIgnoreCase("del")) {
            String[] idAll = ids.split(",");
            //        for (String ss:idAll)
            //            System.out.println(ss);
//                 System.out.println(idAll.length);


            return this.deleteId(request, ids);
        } else {
            throw new IllegalArgumentException("操作符只接收『add』、『edit』、『del』當前：" + oper);
        }

//        return null;
    }

    /**
     * 如果是用單獨的增加頁面做input UI時，轉到UI
     *
     * @return URL + "/popWindow"
     * @throws Exception
     */
    @RequestMapping(value = "/popWindow", method = RequestMethod.GET)
    protected String popWindow() throws Exception {
        return getUrl() + "/popWindow";
    }

    /**
     * 顯示輸入畫面
     * <p/>
     * 可將數據帶到畫面中，如將部門顯示給用戶選擇
     * id >0时表示修改
     *
     * @return
     * @throws Exception
     * @ModelAttribute Map<String, List<Department>> listDept,
     * ModelMap model, BindingResult result
     */
    @RequestMapping(method = RequestMethod.GET, value = "/input")
    //   直接放在視圖裡，不用json @ResponseBody
    protected ModelAndView input(HttpServletRequest request,/*@RequestParam("id") */Long id) throws Exception {

        prepareModel();
        ModelMap modelMap = new ModelMap();

        //修改
        if (id != null && id > 0) {
            T model = getDao().get(id);
            //不存在的模型對象
            if (model == null) {
                logger.error("id={}, empty model can't update!", id);

                modelMap.put("message",
                        getMessage(request, "error.model.required",
                                new Long[]{id}, "不存在的模型對象")
                );
                return new ModelAndView("redirect:" + getUrl() + "/list.htm", modelMap);
            }
            //存在
            modelMap.put("model", model);
        }
        return new ModelAndView(getUrl() + "/input", modelMap);
    }


    //---------------------保存 save() -------------------------------------//

    Boolean updateStatus=false;

    /**
     *
     * @return true when is update Entity
     */
    public Boolean isUpdate() {
        return updateStatus;
    }

    /**
     * 保存model前
     */
    protected boolean beforeSave() {
        return true;
    }

    /**
     * 保存model
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST, value = "/save")
    @ResponseBody
    public ModelMap save(HttpServletRequest request,

                         @Validated  T entity, BindingResult result,SessionStatus status) throws Exception {
             status.setComplete();
        return SaveOrUpdate(request, entity, result, false);


    }

    /**
     * 保存model後
     */

    protected void afterSave() {

    }
    //---------------------更新 update() -------------------------------------//


    /**
     * 更新
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST, value = "/update")
    @ResponseBody
    public ModelMap update(HttpServletRequest request,

                           @Validated T entity, BindingResult result) throws Exception {

        return SaveOrUpdate(request, entity, result, true);
    }

    /**
     * Save or update T
     *
     * @param request
     * @param entity
     * @param result
     * @param isUpdate
     * @return
     * @throws Exception
     */
    private ModelMap SaveOrUpdate(HttpServletRequest request, final T entity, BindingResult result, Boolean isUpdate) throws Exception {
        this.setModel(entity);
//        prepareModelFromRequest(request); //從parameters 裡取得model

        this.updateStatus =isUpdate;
         if (!isUpdate)
             ((BaseEntity) this.model).setId(0L);
//             this.model.setId(0L);

        //before
        if (!beforeSave()) {
            return new JsonResultString("操作失败").mapResult();
        }

        if (logger.isDebugEnabled()) {
            logger.debug("process url[/save], method[post] in " + getClass());
        }

        //validator
        if (result.hasErrors() || model == null) {
            logger.debug(" errors found result:{}", result);

            StringBuilder sb = new StringBuilder("error :");

            List<ObjectError> errors = result.getAllErrors();
            for (ObjectError error : errors) {
                sb.append("\r\n ").append(error.getDefaultMessage());
            }

            return new JsonResultString(sb).mapResult();
        }


        try {
            logger.debug("current entity:" + model);
            if (isUpdate)
                getDao().update(model);
            else
                getDao().save(model);

            afterSave(); //run after

            return new JsonResultString(request, 0).mapResult();

        } catch (Exception e) {

            JsonResultString msg=  new JsonResultString(request, -1);
                msg.setMessage(e.getMessage());
            e.printStackTrace();
            return msg.mapResult();
        }
    }


    /**
     * 刪除給定的idList String
     * delete a list of ids
     *
     * @return
     * @throws Exception
     */

    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    @ResponseBody
    protected ModelMap delete(HttpServletRequest request,
                              @RequestParam("idList") String idString)
            throws Exception {

        return deleteId(request, idString);
    }

    protected boolean beforeDelete(String[] idList) throws Exception{
        return true;
    }

    protected void afterDelete(String[] idList) throws Exception {
    }

    /**
     * 刪除給定的id[]
     *
     * @param request
     * @param ids     String with [,]
     * @return
     * @throws Exception
     */

    protected ModelMap deleteId(HttpServletRequest request, String ids)  {
//        Map<String, String> map = new HashMap<String, String>();
        String[] idList = ids.split(",");
        this.id = null;
        try {
            prepareModelFromRequest(request); //必须取得model
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResultString(ids+"数据，删除失败"+e.getLocalizedMessage()).mapResult();
        }

        logger.debug("delete {} of model:{}", ids, model.getClass());

        try {
            if (!beforeDelete(idList)) {

                return new JsonResultString(ids+"存在相关联的数据，删除失败").mapResult();
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return new JsonResultString("删除失败"+e.getLocalizedMessage()).mapResult();
        }


        String hql = "FROM " + model.getClass().getCanonicalName();

        logger.debug("model:{},hql: {}", model, hql);

        int oldSize = getDao().count(hql, null);


        try {
            for (String sId : idList) {
                logger.info("this id will be deleted:" + sId);
                Long idDel = Long.parseLong(sId);
                getDao().delete(idDel);
            }
            afterDelete(idList);
            int size = getDao().count(hql, null/*where Name<>",new String[] {"a"}*/);
            logger.debug("rows of delete  before:{} and after: {}", oldSize, size);

            return new JsonResultString("删除成功").mapResult();


        } catch (Exception e) {
            e.printStackTrace();

            return new JsonResultString("删除失败"+e.getLocalizedMessage()).mapResult();


        }


    }
//-------------------check duplicate---------------------------------------------------------------//

    /**
     * 單欄位重復檢查
     *
     * @param request
     * @param fieldName
     * @param value
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST, value = "/check-duplicate-ui")
    @ResponseBody
    public String checkDuplicate(HttpServletRequest request, String fieldName, String value) throws Exception {
        String message = "true";
        logger.debug("check duplicate {}:{}", fieldName, value);
        if (StringUtils.isEmpty(fieldName) || StringUtils.isEmpty(value)) {
            message = "false";
        } else if (getDao().findBy(fieldName, value).size() > 0) {
            message = "false";
        }

        return message;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/check-duplicate")
    @ResponseBody
    public ModelMap checkDuplicateI18n(HttpServletRequest request, String fieldName, String value) throws Exception {
        String message = "";
        logger.debug("check duplicate {}:{}", fieldName, value);
        if (StringUtils.isEmpty(fieldName) || StringUtils.isEmpty(value)) {
            message = "field name 、value can't empty";
        }else   if (getDao().findBy(fieldName, value).size() > 0) {
            message = getMessage(request, "error.field.duplicate");
        }

        return new ModelMap("message", message);
    }


    /*
    * 表单提交日期绑定格式
    */
    @InitBinder
    public void initBinder(WebDataBinder binder, HttpServletRequest request) {


        //copy from super
//        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
//        binder.registerCustomEditor(Timestamp.class, new PropertyEditorSupport() { }
        binder.registerCustomEditor(Timestamp.class, new CustomDateEditor());
//        logger.debug("InitBinder controller  and CustomDateEditor 共有{}條記錄" + pagedResult.getTotalCount());
        initValidator(binder, request);

    }

    /**
     * 增加自定義驗證支持
     */
    protected void initValidator(WebDataBinder binder, HttpServletRequest request) {

    }


    /**
     * get entity
     *
     * @return model
     */
    public T getModel() {
        return model;
    }

    /**
     * set model
     *
     * @param entity
     */
    public void setModel(T entity) {
        this.model = entity;
    }

    public T getOriginalModel() {
        return originalModel;
    }

    public void setOriginalModel(T originalModel) {
        this.originalModel = originalModel;
    }

    /**
     * 從 request中取提model 的屬性，並調用setXXX
     * <p/>
     * PYC :2012/12/17, 保留原建立日期、時間等未修改的項目。
     *
     * @param request
     * @throws Exception
     */
    protected void prepareModelFromRequest(HttpServletRequest request) throws Exception {

        prepareModel();
        logger.info("舊數據 id{}, model:{} ", id, model);

        BeanUtils.populate(model, request.getParameterMap());
        logger.info("要改成 request  model: {}", model);


    }

    /*將當前ID原來資料寫入到model */

    /**
     * new 時防止 Model 為null
     * model =new Entity();
     * edit 時 防止 Model 的property 為null
     * model=getDao.get(id);
     *
     * @throws Exception
     */
    protected abstract void prepareModel() throws Exception;

    private void setNew() {

        if (id != null) {
            this.setModel(getDao().get(id));
        } else {
            T tmpModel;
//             this.setModel( tmpModel);
        }
    }
}


/**
 * 自定義mail格式轉換
 */
/*class CustomEmailDataEditor extends PropertyEditorSupport {

    public void setValue(Object value) {
        if (value instanceof MultipartFile) {//处理上传文件，此处默认上传的是格式正确的CSV文件
            MultipartFile multipartFile = (MultipartFile) value;
            System.out.println(multipartFile.getContentType());//打印Content-Type
            try {
               //使用第三方开源类库OpenCSV来读取CSV文件
                CSVReader reader = new CSVReader(new InputStreamReader(
                        multipartFile.getInputStream()));
                String[] nextLine;
                // 去除第一行header信息
                reader.readNext();

                List<Email> emails = new ArrayList<Email>();
                while ((nextLine = reader.readNext()) != null) {
                    Email email = new Email();
                    email.setName(nextLine[2]);
                    email.setEmail(nextLine[4]);
                    emails.add(email);
                }

                //绑定数据列表
                super.setValue(emails);
            } catch (IOException ex) {
                throw new IllegalArgumentException(
                        "Cannot read contents of multipart file: "
                                + ex.getMessage());
            }
        } else if (value instanceof byte[]) {
            super.setValue(value);
        } else {
            super.setValue(value != null ? value.toString().getBytes() : null);
        }
    }

}*/


class CustomDateEditor extends PropertyEditorSupport {
    private SimpleDateFormat datetimeFormat = new SimpleDateFormat(
            EPApplicationAttributes.DATETIME_FORMAT);
    private SimpleDateFormat dateFormat = new SimpleDateFormat(EPApplicationAttributes.DATE_FORMAT);

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        Timestamp time = null;
//        System.out.println("parse date:" + text);

        if (StringUtils.isNotBlank(text)) {
            try {

//                time = new Timestamp(new SimpleDateFormat(EPApplicationAttributes.DATE_FORMAT).parse(text).getTime());
                if (text.indexOf(":") == -1 && text.length() == 10) {
                    setValue(this.dateFormat.parse(text));
                } else if (text.indexOf(":") > 0 && text.length() == 19) {
                    setValue(this.datetimeFormat.parse(text));
                    //update-begin----author:zhangdaihao----------------------date:20130312 ------- for:时间格式化-------
                } else if (text.indexOf(":") > 0 && text.length() == 21) {
                    text = text.replace(".0", "");
                    setValue(this.datetimeFormat.parse(text));
                    //update-end----author:zhangdaihao----------------------date:20130312 ------- for:时间格式化-------
                } else {
                    throw new IllegalArgumentException(
                            "Could not parse date, date format is error ");
                }

            } catch (ParseException e) {
                throw new IllegalArgumentException(e.getMessage(), e);
            }
        } else {
            setValue(null);
        }
//        setValue(time);
    }

    @Override
    public String getAsText() {
        return super.getAsText();
    }
}
