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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
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
 * Created with IntelliJ IDEA.
 * User: joker pan
 * Date: 13-6-3
 * Time: 下午3:16
 * To change this template use File | Settings | File Templates.
 * <pre>
 * -----------------------------------------------------------------------------
 * Program ID   : com.redsun.platf.web.controller
 * Program Name :
 * ----------------------------------------------------------------------------- <pre/>
 * <H3> Modification log </H3>
 * <pre>
 * Ver.    Date       Programmer    Remark
 * ------- ---------  ------------  ---------------------------------
 * 1.0     13-6-3    joker pan       created
 *
 * </pre>
 */

/**
 * 參數可以是以下：
 * HttpServletRequest/HttpServletResponse、HttpSession、Locale、InputStream、 OutputStream、File[]
 */
//@Controller
//@Transactional
@SuppressWarnings("unchecked")
public abstract class AbstractJqGridController<T extends BaseEntity> extends StandardController {

    //當前URL
    //    protected String URL = "";
    //--分頁記錄属性--- 改在request 參數裡//
    //    private PagedResult<T> tempPagedResult = new PagedResult<T>(8, "id");// 每页10条记录

    //-- 页面属性 --//
    protected Long id = 0L;
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
     * <p/>
     * eg:daoFactory.getAuthorityDao()
     *
     * @return IPagedDao<T, Long>
     */
    public abstract IPagedDao<T, Long> getDao();

    /**
     * 查询
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    protected ModelAndView list() throws
            Exception {
        return this.listModel(null, null/*pagedResult*/);
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
    protected ModelAndView listModel(HttpServletRequest request, PagedResult<T> parameteredPage) throws
            Exception {

        logger.info("request param gridModel :{}", parameteredPage);

        List<MorePropertyFilter> filters = new ArrayList<MorePropertyFilter>();

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

        logger.debug("json of pagedResult {}", json);
        //request
        if (request != null && parameteredPage.isSearch() && StringUtils.isNotBlank(json)) {

                /*     filters = MorePropertyFilter
                                    .buildFromHttpRequest(request);   //error 因為傳過來的是json，需將json轉換後
                    logger.debug("search json {} ,{}, {}",
                                        parameteredPage.getSearchOper(), parameteredPage.getSearchField(),
                                        parameteredPage.getSearchString());
                */


            filters = PropertyFilterUtil.buildFilterFromJson(json, this.getModel().getClass());
            //            logger.debug("search JSON {}", json); json 在 parameteredPage.filters中
        }


        //根據gridModel request 的參數，返回查詢結果 pagedResult
        parameteredPage = getDao().findPage(parameteredPage, filters);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pagedResult", parameteredPage);


        map.put("page", parameteredPage.getCurrentPage());//第幾頁
        map.put("rows", parameteredPage.getPageSize());//每頁共幾行
        map.put("total", parameteredPage.getTotalPages());//total pages
        map.put("record", parameteredPage.getResult().size());//所有共幾條記錄

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
    protected Map<String, String> editModel(HttpServletRequest request,
                                            PagedResult<T> gridModel,
                                            String oper, String ids) throws Exception {


        //        String operation = gridModel.getOper().toLowerCase(); //直接參數傳過來


        logger.debug("oper :{},idAll:{},gridModel {}:", oper, ids, gridModel);

        if (oper.equalsIgnoreCase("add")) {
            this.id = null;
            prepareModelFromRequest(request); //從parameters 裡取得model
            //            logger.debug("add ...{}" + model);
            logger.debug("prepareModelFromRequest gridModel {}:", gridModel);

            return this.save(model);
        } else if (oper.equalsIgnoreCase("edit")) {
            //            String[] ids = gridModel.getId();
            this.id = Long.parseLong(gridModel.getId()[0]);
            //依request id將舊DATA SET 給原始的、當前model
            this.setOriginalModel(getDao().get(this.id));

            prepareModelFromRequest(request);

            return this.update(model);
        } else if (oper.equalsIgnoreCase("del")) {
            String[] idAll = ids.split(",");
            //        for (String ss:idAll)
            //            System.out.println(ss);
            //                 System.out.println(idAll.length);

            this.id = null;
            prepareModelFromRequest(request);

            return this.deleteId(idAll);
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
     *
     * @return
     * @throws Exception
     * @ModelAttribute Map<String, List<Department>> listDept,
     * ModelMap model, BindingResult result
     */
    @RequestMapping(method = RequestMethod.GET, value = "/input")
    //   直接放在視圖裡，不用json @ResponseBody
    protected ModelAndView input(@RequestParam("id") Long id) throws Exception {
        //        prepareModel();

        return new ModelAndView(getUrl() + "/input");
    }


    //---------------------保存 save() -------------------------------------//

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
    protected Map<String, String> save(T entity) throws Exception {
        if (!beforeSave()) {
            resultMessage.put("message", "操作失败");
            return resultMessage;
        }
        try {
            logger.debug("saving entity:" + entity);

            getDao().save(entity);
            resultMessage.put("message", "操作成功");
            logger.info("saved, ok:" + entity);
        } catch (Exception e) {
            e.printStackTrace();
            resultMessage.put("message", "操作失败");
            throw e;
        }

        afterSave();

        return resultMessage;
    }

    /**
     * 保存model後
     */

    protected void afterSave() {

    }
    //---------------------更新 update() -------------------------------------//


    /**
     * before更新
     */
    protected boolean beforeUpdate() {
        return true;
    }


    /**
     * 更新
     *
     * @param entity
     * @return
     * @throws Exception
     */

    @RequestMapping(method = RequestMethod.POST, value = "/update")
    @ResponseBody
    protected Map<String, String> update(T entity) throws Exception {
        if (!beforeUpdate()) {
            resultMessage.put("message", "更新失败");
            return resultMessage;
        }
        try {
            getDao().update(entity);
            logger.info(" updated control  entity:" + entity);
            resultMessage.put("message", "更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            resultMessage.put("message", "更新失败");
            throw e;
        }
        afterUpdate();
        return resultMessage;
    }

    /**
     * after 更新
     *
     * @return
     * @throws Exception
     */

    protected void afterUpdate() {
    }


    //--------------------- 刪除給定的id[] delete() -------------------------------------//

    /**
     * delete a list of ids
     *
     * @return
     * @throws Exception
     */
       /*
       @RequestMapping(method = RequestMethod.POST, value = "/delete")
        @ResponseBody
        protected Map<String, String> delete(HttpServletRequest request,
                                             @RequestParam("uid") String idString)
                throws Exception {
            return null;
        }
        */
    protected boolean beforeDelete(String[] idList) {
        return true;
    }

    protected void afterDelete(String[] idList) {
    }

    /**
     * 刪除給定的id[]
     *
     * @param idList
     * @return
     * @throws Exception
     */
    protected Map<String, String> deleteId(String[] idList) throws Exception {
        //        Map<String, String> map = new HashMap<String, String>();
        resultMessage.clear();
        if (!beforeDelete(idList)) {
            resultMessage.put("message", "删除失败");
            return resultMessage;
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
            resultMessage.put("message", "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            resultMessage.put("message", "删除失败" + e.getMessage());
            throw e;

        }

        int size = getDao().count(hql, null/*where Name<>",new String[] {"a"}*/);

        logger.info("rows of delete  before:{} and after: {}", oldSize, size);

        afterDelete(idList);

        return resultMessage;// 重定向近回message
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


class CustomDateEditor1 extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        Timestamp time = null;
        //        System.out.println("parse date:" + text);
        if (StringUtils.isNotBlank(text)) {
            try {
                time = new Timestamp(new SimpleDateFormat(EPApplicationAttributes.DATE_FORMAT).parse(text).getTime());
            } catch (ParseException e) {
                throw new IllegalArgumentException(e.getMessage(), e);
            }
        }
        setValue(time);
    }

    @Override
    public String getAsText() {
        return super.getAsText();
    }
}
