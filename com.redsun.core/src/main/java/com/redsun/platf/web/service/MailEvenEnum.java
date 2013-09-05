package com.redsun.platf.web.service;


/**
 * <p>Title: com.walsin.platf.web.MailEvenEnum</p>
 * <p>Description: Mail模組定義</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: FreeLance</p>
 * @author Jason Huang
 * @version 1.0
 */
public enum MailEvenEnum {
    
    /** 密碼重置通知信件 */
    RESET_USER_PASSWORD(new Integer(1), "system/reset-user-password"),
    
    /** 啟用EP人員通知信件 */
    ENABLE_USER_MAIL(new Integer(2), "system/enable-user-mail"),
    
    /** EP08001 我的代辦請購單 - 採購員變更通知信件 */
    PR_BUYER_CHANGE_MAIL(new Integer(3), "pr/change-buyer-mail"),
    
    /** EP02004 單據負責人變更 - 請購員變更通知信件 */
    PR_RECEIPT_UNDERTAKER_CHANGE_MAIL(new Integer(4), "changeReceiptUndertaker/change-pr-buyer-mail"),
    
    /** EP02004 單據負責人變更 - 採購員變更通知信件 */
    PO_RECEIPT_UNDERTAKER_CHANGE_MAIL(new Integer(5), "changeReceiptUndertaker/change-po-buyer-mail"),
    
    /** EP02004 單據負責人變更 - 合約價格的採購員變更通知信件 */
    PB_RECEIPT_UNDERTAKER_MAIL(new Integer(6), "changeReceiptUndertaker/change-pb-buyer-mail"),
    
    /** EP02005 單據簽核人變更 通知信件 */
    CHANGE_DOCUMENT_APPROVED_MAIL(new Integer(7), "workflow/change-document-approver-mail"),
    
    /** EPB0101 已核准合約到期前XX日通知採購員 - 採購員通知信件 */
    PRICEBANK_ALERTS_BUYER_MAIL(new Integer(8), "batch/pricebankAlerts/pricebank-alerts-buyer-mail"),
    
    /** EPB0101 已核准合約到期前XX日通知採購員 - 採購員直屬主管通知信件 */
    PRICEBANK_ALERTS_BUYER_MASTER_MAIL(new Integer(9), "batch/pricebankAlerts/pricebank-alerts-buyer-master-mail"),
    
    /** EPB0100 已核准PR超過XX日未轉PO通知 - 請購員通知信件 */
    PR_CONVERSION_PO_ALERTS_MAIL(new Integer(10), "batch/prConversionPoAlerts/pr-conversion-po-alerts-mail"),
    
    /** EPB0102詢價單即將到期通知 - 採購員通知信件 */
    RFQ_ALERTS_PURCHASER_MAIL(new Integer(11), "batch/rfqAlerts/rfq-alerts-purchaser-mail"),
    
    /** EPB0102詢價單即將到期通知 - 採購員直屬主管通知信件 */
    RFQ_ALERTS_PURCHASER_MASTER_MAIL(new Integer(12), "batch/rfqAlerts/rfq-alerts-purchaser-master-mail"),
    
    /** EPB0103主管待簽核單據超過XX日尚未簽核通知 */
    APPROVE_ALERTS_MAIL(new Integer(13), "batch/approveAlerts/approve-alerts-mail"),
    
    /** EP08002 我的詢報價單 - 退回詢價單項目通知 */    
    RFQ_RETURN_MAIL(new Integer(14), "rfq/rfq-item-return"),
    
    /** EP08002 我的詢報價單 - 寄送廠商報價通知信件 */
    RFQ_NOTIFY_VENDOR_MAIL(new Integer(15), "rfq/rfq-notify-vendor"),
    
    /** PR關閉 email */
    COLSE_PR_ITEM_MAIL(new Integer(16), "pr/closePr"),
    
    /** PR不再收貨 email */
    STOP_PR_ITEM_RCV(new Integer(17), "pr/stopPrRcv"),
    
    /** 取消PR不再收貨 */
    CANCEL_STOP_PR_ITEM_RCV(new Integer(18), "pr/cancelStopPrRcv"),
    
    /** 取消PR不再收貨 */
    CONSUME_PRICEBANK_ERR(new Integer(19), "pr/consumePricebankErr"),
    
    /** EP08002 我的採單 - 寄送廠商報價通知信件 */
    PO_NOTIFY_VENDOR_MAIL(new Integer(20), "po/po-notify-vendor"),
    
    /** 料號申請SAP失敗通知 */
    MN_APPLY_FAIL_NOTIFY_MAIL(new Integer(21), "mn/apply-fail-notify"),        
    
    /** 請購流程信件表身 */
    WORKFLOW_PR_MAIL(new Integer(101), "/workflow/pr"),
    
    /** 採購流程信件表身 */
    WORKFLOW_PO_MAIL(new Integer(102), "/workflow/po"),
    
    /** 合約價格流程信件表身 */
    WORKFLOW_PRICEBANK_MAIL(new Integer(103), "/workflow/pricebank"),
    

    /** 料號流程信件表身 */
    WORKFLOW_MN_MAIL(new Integer(104), "/workflow/mn");
    
    /** 模組ID */
    private Integer enenId;
    
    /** Mail templet位置 */
    private String templet;
    
    MailEvenEnum(Integer enenId, String templet){
        this.enenId = enenId;
        this.templet = templet;
    }

    public Integer getEnenId() {
        return enenId;
    }

    public void setEnenId(Integer enenId) {
        this.enenId = enenId;
    }

    public String getTemplet() {
        return templet;
    }

    public void setTemplet(String templet) {
        this.templet = templet;
    }
}
