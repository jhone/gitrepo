package com.redsun.platf.dao.sys;

import com.redsun.platf.dao.base.impl.PagedDao;
import com.redsun.platf.entity.tag.Attachment;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户对象的泛型DAO类.
 *
 * @author calvin
 */
@Component
public class AttachmentDao extends PagedDao<Attachment, Long> {

    public AttachmentDao() {
        super();
        this.entityClass = Attachment.class;
    }

    /**
     * 顯示所有附檔文件  j
     *
     * @param soruceId
     * @param docmentType
     * @param sourceType
     * @return
     */
    public List<Attachment> listAttachments(Integer soruceId, Integer docmentType, Integer sourceType) {

        Map<String, Object> nameParams = new HashMap<>();
//        Attachment attachment;
//        attachment.getSourceId();
//        attachment.getDocumentType();
//        attachment.getSourceType();

        nameParams.put("soruceId", soruceId);
        nameParams.put("sourceType", sourceType);
        nameParams.put("docmentType", docmentType);
        String queryString = "from Attachment";

        return find(queryString, nameParams);
    }

    /** find attachment
 public List<Attachment> listAttachments(Integer sourceId, Integer documentType, Integer sourceType) {
     String queryString="from Attachment ";
     String[] names=new String[]{"sourceId",  "documentType",  "sourceType"};
     Object[] values=new Integer[]{sourceId,  documentType,  sourceType};

     return  find(queryString,names,values);
     }
    **/
}
