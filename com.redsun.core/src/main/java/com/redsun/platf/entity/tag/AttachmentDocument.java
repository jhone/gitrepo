package com.redsun.platf.entity.tag;

/**
 * <p>Title: com.walsin.platf.orm.entity.AttachmentDocument</p>
 * <p>Description: 附加檔案文件類別</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: FreeLance</p>
 * @author Jason Huang
 * @version 1.0
 */
public enum AttachmentDocument {
    
    /** 請購單 - 表頭 */
    PR_HEADER(new Integer(1), new Integer(1)),
    
    /** 請購單 - 表身 */
    PR_ITEM(new Integer(1), new Integer(2)),
    
    /** 合約價格 */
    PRICE_BANK(new Integer(5), new Integer(1)),
    
    /** 合約價格廠商附件 */
    PRICE_BANK_VANDOR(new Integer(6), new Integer(1)),
    
    /** 詢價單表頭附件 */
    RFQ_HEADER(new Integer(7), new Integer(1)),
    
    /** 詢價單表身附件 */
    RFQ_ITEM(new Integer(7), new Integer(2)),
    
    /** 範例文件型態 **/    
    BULLETIN(new Integer(8), new Integer(1)),
    
    /** 報價廠商附件 */
    QUO_HEADER(new Integer(9), new Integer(1)),
    
    /** 採購單 - 表頭 */
    PO_HEADER(new Integer(10), new Integer(1)),
    
    /** 採購單 - 表身 */
    PO_ITEM(new Integer(10), new Integer(2)),
    
    /** 申請單(GDS) **/    
    APPLY_FORM(new Integer(50), new Integer(1)),
    
    /** 簽名檔 */
    SIGNATURE(new Integer(101), new Integer(1));
        
    /** 文件型態 **/
    private Integer documentType;
    
    /** 1:表頭 2:表身 3:其他**/
    private Integer sourceType;
    
    AttachmentDocument(Integer documentType, Integer sourceType){
        this.sourceType   = sourceType;
        this.documentType = documentType;
    }

    public Integer getDocumentType() {
        return documentType;
    }

    public void setDocumentType(Integer documentType) {
        this.documentType = documentType;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }
}
