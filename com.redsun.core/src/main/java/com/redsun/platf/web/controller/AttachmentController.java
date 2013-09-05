package com.redsun.platf.web.controller;

import com.redsun.platf.dao.base.IPagedDao;
import com.redsun.platf.entity.tag.Attachment;
import com.redsun.platf.web.service.AttachmentService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA. </br>
 * To change this template use File | Settings | File Templates.</br>
 * User: joker pan</br>
 * Date: 13-6-28</br>
 * Time: 下午4:19</br>
 * ------------------------------------------------------------------------------------</br>
 * Program ID   :                                                                      </br>
 * Program Name :   附件下載controller                                                                   </br>
 * ------------------------------------------------------------------------------------</br>
 * <H3> Modification log </H3>
 * <pre>
 * Ver.    Date       Programmer    Remark
 * ------- ---------  ------------  ---------------------------------------------------
 * 1.0     13-6-28    joker pan    created
 * <pre/>
 */
@Controller
@RequestMapping("/attachment/")
@Transactional
public class AttachmentController extends AbstractStandardController<Attachment> {
    @Resource
    AttachmentService abstractService;

    @Override
    public String getUrl() {
        return "attachment";
    }

    @Override
    public IPagedDao<Attachment, Long> getDao() {
        return getDaoFactory().getAttachmentDao();
    }

    @Override
    protected void prepareModel() throws Exception {

        Attachment entity;
        if (id != null) {
            entity = getDao().get(id);
        } else {
            entity = new Attachment();

        }
        this.setModel(entity);
    }

    /**
     * 下載
     *
     * @param request
     * @param response
     * @param absoluteFileName
     * @param suggestFileName
     * @return
     * @throws Exception
     */

    @RequestMapping(method = RequestMethod.GET, value = "/downloadAttachment")
    private String doDownloadAttachment(HttpServletRequest request,
                                        HttpServletResponse response,
                                        String absoluteFileName,
                                        String suggestFileName)
            throws
            Exception {

        return abstractService.downloadAttachment(request, response,
                absoluteFileName, suggestFileName);
    }

    /**
     * 上傳
     *
     * @param request
     * @param response
     * @param id
     * @param documentType
     * @param sourceType
     * @param fileDescription
     * @throws Exception
     */
    //    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/uploadAttachment")
    private void doUploadAttachment(HttpServletRequest request, HttpServletResponse response,
                                    Long id, Integer documentType, Integer sourceType,
                                    String fileDescription) throws Exception {


        abstractService.uploadAttachment(request, response,
                id, documentType, sourceType,
                fileDescription);


    }

    @RequestMapping(method = RequestMethod.POST, value = "/deleteAttachment")
    private void doDeleteAttachment(
            Long id) throws Exception {


        abstractService.deleteAttachment(id);


    }


}