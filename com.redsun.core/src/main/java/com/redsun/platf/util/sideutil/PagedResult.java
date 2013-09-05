package com.redsun.platf.util.sideutil;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2012/11/30
 * Time: 上午 8:51
 *
 * @author  pyc add String[] id :parameter from model
 * Change History :
 *          Jackson1.9.6处理json报错 No serializer found for class org.hibernate.proxy.pojo.javassist.JavassistL
 *          2013/01/08 去掉oper 放在request paramters
 *          2013/07/19 easyui参数的支持:order page rows行数 sort排序字段
 *
 */
//@JsonIgnoreProperties( value={"hibernateLazyInitializer","handler"})

public class PagedResult<T> extends Page<T> {


    public PagedResult() {
        /*不可以小於0*/
               if (this.pageSize<0)
                   this.pageSize=10;

    }
    public PagedResult(Integer rows) {
           super(rows);
//           this.rows = rows;
       }

    /**
     *
     * @param rows 每頁行數
     * @param orderBy 排序字段
     */
    public PagedResult(Integer rows,String orderBy) {
           super(rows);
           this.setOrder(ASC);
           this.setOrderBy(orderBy);
//           this.rows = rows;
       }

    /**************************** paged jqGrid ***********************************************/
//    prmNames: {page:"page",rows:"rows", sort: "sidx",order: "sord",
// search:"_search", nd:"nd", id:"id",oper:"oper",
// editoper:"edit",addoper:"add",deloper:"del", subgridid:"id", npage: null,
// totalrows:"totalrows"},

    /**原來
     * prmNames:{page:"page",rows:"rows", sort: "sidx",order: "sord",
     * search:"search", nd:"nd", id:"id",oper:"oper",editoper:"edit",addoper:"add",deloper:"del"}
     */

    /**要改為
        * prmNames:{page:"pageNo",rows:"pageSize", sort: "sidx",order: "orderBy",
        * search:"search", nd:"nd", id:"id",oper:"oper",editoper:"edit",addoper:"add",deloper:"del"}
        */

   // Get the requested page. By default grid sets this to 1.
//    private Integer page =pageNo;  //pageNo=1
    // get how many rows we want to have into the grid - rowNum attribute in the
    // grid

    private Integer rows=pageSize;// 每页5条记录    //pageSize
    private String sort=orderBy;

    // sorting order - asc or desc
//    private String sord=orderBy;     //orderBy

    // get index row - i.e. user click to sort.
//    private String sidx=order;    //order

    // Your Total Pages
//    private Long total = totalCount; //  totalCount

    // All Records
//    private Long records = 0L;    //

    // Search Field
    private String searchField;

    // The Search String
    private String searchString;

    // he Search Operation
    // ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
    private String searchOper;
    private boolean search;

    //multipleSearch filters 返回json字竄
    private String  filters;

    private boolean loadonce = false;

    /**
     * * edit jqgrid ********************************************************
     */
    // selected id list: 2,4,6
    // fixed in 2011/12/02
    private String idselect;

//    private String oper = "";//add

    //Add  to model :pyc 2012/12/17
//    private List<String> id =new ArrayList<String>();
    private String[] id =new String[]{};


    public Integer getPage() {
        return getCurrentPage();
    }

    public void setPage(Integer page) {

        //this.currentPage = page;
        this.setCurrentPage(page);
    }

    public Integer getRows() {
        return pageSize;
    }

    public void setRows(Integer rows) {
        this.rows=rows;
        this.pageSize = rows;
    }
    //order by asc or desc
    public String getSord() {
        return order;
    }

    public void setSord(String sord) {
        this.order = sord;
    }
    //order by ui
    public String getSort() {
        return orderBy;
    }

    public void setSort(String sort) {
        this.sort = sort;
        this.orderBy=sort;
    }

    //    //Index Row
//    public String getSidx() {
//        return orderBy;
//    }
           //= order by
//    public void setSidx(String sidx) {
//        this.setOrderBy( sidx);
//    }

    public Long getTotal() {
        return totalCount;
    }

    public void setTotal(Long total) {
        this.totalCount = total;
    }

//    public Long getRecords() {
////        return records;
//        return totalCount;
//    }
//
//    public void setRecords(Long records) {
//        this.records = records;
//    }

    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getSearchOper() {
        return searchOper;
    }

    public void setSearchOper(String searchOper) {
        this.searchOper = searchOper;
    }

    public boolean isLoadonce() {
        return loadonce;
    }

    public void setLoadonce(boolean loadonce) {
        this.loadonce = loadonce;
    }

    public String getIdselect() {
        return idselect;
    }

    public void setIdselect(String idselect) {
        this.idselect = idselect;
    }

//    public String getOper() {
//        return oper;
//    }
//
//    public void setOper(String oper) {
//        this.oper = oper;
//    }



    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
        this.search = search;
    }

    public String getFilters() {
        return filters;
    }
    //json
    public void setFilters(String filters) {
        this.filters = filters;

    }

    public String[] getId() {
        return id;
    }

    public void setId( String[] id) {
        this.id = id;
    }

    @Override
   	public String toString() {
   		return ToStringBuilder.reflectionToString(this);
   	}

}
