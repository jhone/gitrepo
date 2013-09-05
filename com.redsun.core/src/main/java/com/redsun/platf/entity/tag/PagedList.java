package com.redsun.platf.entity.tag;

import com.redsun.platf.dao.base.impl.PagedDao;
import com.redsun.platf.entity.BaseEntity;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: com.walsin.platf.web.framework.PagedList</p>
 * <p>Description: 分頁物件</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: FreeLance</p>
 * @author Jason Huang
 * @version 1.0
 */
public class PagedList<T extends BaseEntity, PK extends Serializable>{
        
    /** 預設每頁20筆 */
    private final static int DEFAULT_PAGE_SIZE = 20;
    
    /** 目前頁數 */
    private int currentPage = 1;
    
    /** 目前資料 */
    private List<T> currentList = null;
    
    /** 加總筆數 */
    private int totalRows = 0;
    
    /** 加總頁數 */
    private int totalPages = 0;
    
    /** 每頁筆數 */
    private int pageSize = DEFAULT_PAGE_SIZE;
    
    /** HSQL字串 */
    private String queryString = null;
    
    /** Object[]參數 */
    private Object[] params = null;

    /** Map<Stirng, Object>參數 */
    private Map<String, Object> namedParams = null;
    
    /** 基本DAO物件 */
    private PagedDao<T,PK> dataAccessObject;
    
    /**
     *  建構子
     */
    public PagedList(){
    }
    
    /**
     * 建構子
     * @param dataAccessObject 基本DAO物件
     * @param queryString 查詢字串
     * @param params 查詢參數
     */
    public PagedList(PagedDao<T,PK> dataAccessObject, String queryString, Object[] params) {
        super();
        this.params = params;
        this.queryString = queryString;
        this.dataAccessObject = dataAccessObject;
    }
    
    /**
     * 建構子
     * @param dataAccessObject 基本DAO物件
     * @param queryString 查詢字串
     * @param namedParams 查詢參數
     */
    public PagedList(PagedDao<T,PK> dataAccessObject, String queryString, Map<String, Object> namedParams) {
        super();
        this.namedParams = namedParams;
        this.queryString = queryString;
        this.dataAccessObject = dataAccessObject;
    }
    
    /**
     * 前往指定頁數
     * @param pageNo 頁數
     */
    @SuppressWarnings("unchecked")
    public void gotoPage(int pageNo){
        totalRows = dataAccessObject.count(queryString, params, namedParams);
        if(totalRows == 0){
            totalPages = 0;
            currentPage = 0;
            currentList = Collections.EMPTY_LIST;
        }else{
            totalPages = (totalRows % pageSize > 0) ? (totalRows / pageSize + 1) : (totalRows / pageSize);
            currentPage = (pageNo > totalPages) ? totalPages : pageNo;
            int startRow = (pageSize * currentPage) - pageSize;
            currentList = (List<T>)dataAccessObject.find(queryString, params, namedParams, new Integer(startRow), new Integer(pageSize));
        }
    }

    /**
     * 取得目前所在頁數
     * @return 頁數
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * 取得目前頁數資料
     * @return
     */
    public List<T> getCurrentPageList() {
        return currentList;
    }
    
    /**
     * 取得目前總筆數
     * @return
     */
    public int getTotalRows() {
        return totalRows;
    }

    /**
     * 取得目前總頁數
     * @return
     */
    public int getTotalPages() {
        return totalPages;
    }    

    /**
     * 設定HQL字串
     * @param queryString
     */
    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    /**
     * 設定HQL參數
     * @param params
     */
    public void setParams(Object[] params) {
        this.params = params;
    }

    /**
     * 設定HSQL參數
     * @param namedParams
     */
    public void setNamedParams(Map<String, Object> namedParams) {
        this.namedParams = namedParams;
    }

    /**
     * 設定基本DAO物件
     * @param dataAccessObject
     */
    public void setDataAccessObject(PagedDao<T,PK> dataAccessObject) {
        this.dataAccessObject = dataAccessObject;
    }
    
    /**
     * 取得頁面筆數
     * @return
     */
    public int getPageSize() {
        return pageSize;
    }
    
    /**
     * 設定頁面筆數
     * @param pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCount() {
        return this.getTotalRows();  //To change body of created methods use File | Settings | File Templates.
    }

    public Object getResultList() {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }
}
