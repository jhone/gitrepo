package com.redsun.platf.dao.tag;

import com.redsun.platf.dao.base.IPagedDao;
import com.redsun.platf.dao.sys.SystemTxnDao;
import com.redsun.platf.dao.tag.easyui.model.entity.TSFunction;
import com.redsun.platf.entity.sys.SystemTxn;
import org.hibernate.SQLQuery;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 动态菜单栏生成
 *
 * @author Administrator
 */
@Transactional
public class ListtoMenu {
    /**
     * 拼装easyui菜单JSON方式
     *
     * @param parentFun 上级菜单
     * @param set1
     * @return
     */
    public static String getMenu(List<TSFunction> parentFun, List<TSFunction> set1) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("{\"menus\":[");
        for (TSFunction node : parentFun) {
            String iconClas = "default";//图标
            if (node.getTSIcon() != null) {
                iconClas = node.getTSIcon().getIconClas();
            }
            buffer.append("{\"menuid\":\"" + node.getId()
                    + "\",\"icon\":\"" + iconClas + "\","
                    + "\"menuname\":\"" + node.getFunctionName()
                    + "\",\"menus\":[");
            iterGet(set1, node.getId().toString(), buffer);
            buffer.append("]},");
        }
        buffer.append("]}");

        // 将,\n]替换成\n]

        String tmp = buffer.toString();

        tmp = tmp.replaceAll(",\n]", "\n]");
        tmp = tmp.replaceAll(",]}", "]}");
        return tmp;

    }

    static int count = 0;

    /**
     * @param set1
     */

    static void iterGet(List<TSFunction> set1, String pid, StringBuffer buffer) {

        for (TSFunction node : set1) {

            // 查找所有父节点为pid的所有对象，然后拼接为json格式的数据
            count++;
            if (node.getTSFunction().getId().equals(pid))

            {
                buffer.append("{\"menuid\":\"" + node.getId()
                        + " \",\"icon\":\"" + node.getTSIcon().getIconClas()
                        + "\"," + "\"menuname\":\"" + node.getFunctionName()
                        + "\",\"url\":\"" + node.getFunctionUrl() + "\"");
//                if (count == set1.size()) {
//                    buffer.append("}\n");
//                }
//                buffer.append("},\n");

            }
        }

    }


    /**
     * 拼装Bootstarp菜单
     *
     * @param pFunctions
     * @param functions
     * @return
     */
    public static String getBootMenu(List<TSFunction> pFunctions, List<TSFunction> functions) {
        StringBuffer menuString = new StringBuffer();
        menuString.append("<ul>");
        for (TSFunction pFunction : pFunctions) {
            menuString.append("<li><a href=\"#\"><span class=\"icon16 icomoon-icon-stats-up\"></span><b>" + pFunction.getFunctionName() + "</b></a>");
            int submenusize = pFunction.getTSFunctions().size();
            if (submenusize == 0) {
                menuString.append("</li>");
            }
            if (submenusize > 0) {
                menuString.append("<ul class=\"sub\">");
            }
            for (TSFunction function : functions) {

                if (function.getTSFunction().getId().equals(pFunction.getId())) {
                    menuString.append("<li><a href=\"" + function.getFunctionUrl() + "\" target=\"contentiframe\"><span class=\"icon16 icomoon-icon-file\"></span>" + function.getFunctionName() + "</a></li>");
                }
            }
            if (submenusize > 0) {
                menuString.append("</ul></li>");
            }
        }
        menuString.append("</ul>");
        return menuString.toString();

    }

    /**
     * 拼装EASYUI菜单
     *
     * @param parentFunctions
     * @return
     */

//    margin-left: 240px;

    public static String getEasyuiMenu(final IPagedDao dao,
                                       List<SystemTxn> parentFunctions,
                                       final StringBuffer menuString, final Long parentId) {
//        StringBuffer menuString=new StringBuffer();


        for (SystemTxn function : parentFunctions) {
            Long currentParentId = function.getParentId() == null ? 0L : function.getParentId().getId();

            if (parentId != currentParentId) {
                System.out.println(currentParentId + "!=" + parentId);
                continue;
            }

            String currentFun = function.getTxnName();
            String icon = "folder";
            if (function.getIcon() != null) {
                icon = function.getIcon();
            }

            List<SystemTxn> subFunctions = getChildTxn(dao, function.getId());


            if (subFunctions.size() > 0) {
                menuString.append("<div  title=\"" + function.getTxnName() +
                        "\" iconCls=\"" + icon + "\""
                        + "' class=\"easyui-accordion\" fit=\"false\" border=\"false\">");

                menuString.append(" <ul id=\"" + function.getId() + "\">");// class='easyui-tree'>");

                //loop子項
                getEasyuiMenu(dao, subFunctions, menuString, function.getId());

                menuString.append(" </ul> \r\n");
                menuString.append("</div> \r\n ");
//                  menuString.append("</div><!--end " + currentFun + "-->\r\n ");
//                  System.out.println("end");

            } else {


                //沒有子項,直接輸入當前項
//                  System.out.println("end menu" );
//                menuString.append("<ul>\r\n");
//                menuString.append("<li><div onclick=\"addTab(\'" +
//                        function.getTxnName() + "\',\'" +
//                        function.getTxnUrl() +
//                        "&clickFunctionId=" + function.getId()
//                        + "\',\'" + icon +
//                        "\')\"  title=\"" + function.getTxnName() +
//                        "\" url=\"" + function.getTxnUrl() +
//                        "\" iconCls=\"" + icon + "\"> <a class=\"" +
//                        function.getTxnName() +
//                        "\" href=\"#\" > <span class=\"icon " +
//                        icon + "\" >&nbsp;</span> <span class=\"nav\" >" +
//                        function.getTxnName() + "</span></a></div></li>");
                menuString.append("<li>");

                String url = (function.getTxnUrl() == null ? "#" : function.getTxnUrl());

                menuString.append("<span>")
                        .append(" <a href='" + url + "'")
                        .append(" title='" + function.getTxnName() + "'")
                        .append(" class='easyui-linkbutton'")
                        .append(" data-options=\"plain:true,toggle:true,iconCls:'" +
                                icon + "'\"")
                        .append(" style='font-size:14px;width: 200px'")
                        .append(">\n")
                        .append(function.getTxnName())
                        .append("</a>\n")
                        .append("</span>");

                menuString.append("</li>\n");


            }


        }


        return menuString.toString();

    }

    @SuppressWarnings("unchecked")
    @Transactional
    static List<SystemTxn> getChildTxn(IPagedDao dao, Long parentId) {
        String hql;
        if (parentId == 0l)
            hql = "select {c.*} from sys_txn as c where c.parent_id=:p or (c.parent_id is null) ";
        else
            hql = "select {c.*} from sys_txn as c where c.parent_id=:p ";

        SQLQuery query = dao.createSQLQuery(hql);
        query.addEntity("c", SystemTxn.class);  //必须为add
        query.setParameter("p", parentId);

        return query.list();

    }

    @Transactional
    public static List<SystemTxn> getRootTxn(SystemTxnDao dao) {

        String hql;

        hql = "select {c.*} from sys_txn as c where c.parent_id=:p or (c.parent_id is null) ";

        SQLQuery query = dao.createSQLQuery(hql);
        query.addEntity("c", SystemTxn.class);  //必须为add
        query.setParameter("p", 0l);

        return query.list();
    }

// 	public static String getEasyuiMenu(List<TSFunction> parentFunctions, List<TSFunction> functions) {
//		StringBuffer menuString=new StringBuffer();
//		for (TSFunction pFunction : parentFunctions) {
//			menuString.append("<div  title=\""+pFunction.getFunctionName()+"\" iconCls=\""+pFunction.getTSIcon().getIconClas()+"\">");
//			int submenusize=pFunction.getTSFunctions().size();
//			if(submenusize==0)
//			{
//				menuString.append("</div>");
//			}
//			if(submenusize>0)
//			{
//				menuString.append("<ul>");
//			}
//			for (TSFunction function : functions) {
//
//				if(function.getTSFunction().getId().equals(pFunction.getId()))
//				{
//					String icon="folder";
//					if(function.getTSIcon()!=null)
//					{
//						icon=function.getTSIcon().getIconClas();
//					}
//					//menuString.append("<li><div> <a class=\""+function.getFunctionName()+"\" iconCls=\""+icon+"\" target=\"tabiframe\"  href=\""+function.getFunctionUrl()+"\"> <span class=\"icon "+icon+"\" >&nbsp;</span> <span class=\"nav\">"+function.getFunctionName()+"</span></a></div></li>");
//					menuString.append("<li><div onclick=\"addTab(\'"+function.getFunctionName()+"\',\'"+function.getFunctionUrl()+"&clickFunctionId="+function.getId()+"\',\'"+icon+"\')\"  title=\""+function.getFunctionName()+"\" url=\""+function.getFunctionUrl()+"\" iconCls=\""+icon+"\"> <a class=\""+function.getFunctionName()+"\" href=\"#\" > <span class=\"icon "+icon+"\" >&nbsp;</span> <span class=\"nav\" >"+function.getFunctionName()+"</span></a></div></li>");
//				}
//			}
//			if(submenusize>0)
//			{
//				menuString.append("</ul></div>");
//			}
//		}
//		return menuString.toString();
//
//	}

}


