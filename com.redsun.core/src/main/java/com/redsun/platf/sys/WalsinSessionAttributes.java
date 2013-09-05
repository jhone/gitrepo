package com.redsun.platf.sys;

/**
 * <p>Title: com.walsin.platf.web.EPSessionAttributes</p>
 * <p>Description: EP系統Session Scope參數名稱列表</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: FreeLance</p>
 * @author Jason Huang
 * @version 1.0
 */
public interface WalsinSessionAttributes {
    

    /**  walsin 登入者基本資料 */
       public static final String EP_USER_INFO = "_epUser";

       /** walsin 登入者選單 */
       public static final String EP_USER_MENU = "_epUserMenu";

       /** 角色設定檔*/
       public static final String ROLE_CONFIG = "_roleConfig";

       /** 系統相關設定檔 */
       public static final String SYSTEM_CONFIG = "_systemConfig";

       /** 隨機ID */
       public final static String EP_RANDOM = "_random";

       /** EP2010 權限包設定 */
       public static final String EP02010_FORM = "ep02010Form";

       /** EP2002 角色設定 */
       public static final String EP02002_FORM = "ep02002Form";

       /** EP02001 使用者維護 */
       public static final String EP02001_FORM = "ep02001Form";

       /** EP02003 公佈欄管理 */
       public static final String EP02003_FORM = "ep02003Form";

       /** EP02011 系統功能設定 */
       public static final String EP02011_FORM = "ep02011Form";

       /** EP02012部門與廠別維護設定 */
       public static final String EP02012_FORM = "ep02012Form";

       /** EP03003 公司基本參數設定 */
       public static final String EP03003_FORM = "ep003003Form";
}
