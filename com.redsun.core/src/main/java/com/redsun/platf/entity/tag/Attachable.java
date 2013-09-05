package com.redsun.platf.entity.tag;

/**
* <p>Title: com.walsin.platf.orm.entity.IAttachement</p>
* <p>Description: 附檔文件介面</p>
* <p>Copyright: Copyright (c) 2010</p>
* <p>Company: FreeLance</p>
* @author Jason Huang
* @version 1.0
*/
public interface Attachable {
   
   /** 主檔ID */
   public Integer getId();
   
   /** 附檔文件 */
   public AttachmentDocument getAttachmentDocument();
}
