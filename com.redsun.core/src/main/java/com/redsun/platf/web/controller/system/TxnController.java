package com.redsun.platf.web.controller.system;

import com.redsun.platf.dao.base.IPagedDao;
import com.redsun.platf.entity.sys.SystemTxn;
import com.redsun.platf.service.account.AccountManager;
import com.redsun.platf.util.treenode.TreeNode;
import com.redsun.platf.util.treenode.TreeNodeBuilder;
import com.redsun.platf.util.treenode.entitynode.TxnNode;
import com.redsun.platf.web.controller.AbstractStandardController;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2012/12/25
 * Time: 下午 4:03
 * To change this template use File | Settings | File Templates.
 * history:
 *
 * @author pyc 2012/12/25 created
 */

@Controller
@RequestMapping("/system/txn")
@Transactional
@SuppressWarnings("unchecked")
public class TxnController extends AbstractStandardController<SystemTxn> {
    @Resource
    AccountManager userManager;

    @Override
    public String getUrl() {
        return "system/txn";
    }

    @Override
    public IPagedDao<SystemTxn, Long> getDao() {
        return getDaoFactory().getSystemTxnDao();
    }


    @Override
    protected void prepareModel() throws Exception {

        SystemTxn entity;
        if (id != null) {
            entity = getDao().get(id);
        } else {
            entity = new SystemTxn();
            entity.setParentId(null);
        }
        this.setModel(entity);
    }

    @Override
      protected boolean beforeSave() {
             //沒有parent| parentid =id

          if (getModel().getParentId() == null ||
                  getModel().getParentId().getId() == null
                  ||getModel().getParentId().getId().equals(getModel().getId())
                  )
              getModel().setParentId(null);
          return true;
      }


    /**
     * 存在上级不能删除
     * @param idList
     * @return
     */
    @Override
    protected boolean beforeDelete(String[] idList) throws Exception{
                 for (String id :idList){
                      List<SystemTxn> en=getDao().findBy("parentId.id"
                              ,Long.parseLong( id));
                     if (en.size()>0){
//                         System.out.println(en);
                         throw new SQLException(id+":存在下级，请先删除下级项目，无法删除！");
                     }



                 }

        return true;
    }


    @RequestMapping(method = RequestMethod.GET, value = "/txn_menu")
    private ModelAndView validModel(HttpServletRequest request) throws
            Exception {


//        List<SystemTxn> txns=getDao().findBy("parentId.id",null);
        String hql = "select {c.*} from sys_txn as c where c.parent_id=:p or (c.parent_id is null) ";
                      SQLQuery query = getDao().createSQLQuery(hql);
                      query.addEntity("c", SystemTxn.class);  //必须为add
                      query.setParameter("p",0L);
        List<SystemTxn> txns=query.list();

        ModelMap map=new ModelMap();
        map.put("txns",txns);
        System.out.println(txns.size());
        return new ModelAndView(getUrl()+"/menu",map);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/tree")
    @SuppressWarnings("unchecked")
    private List<TreeNode> listTreeModel(String id) throws
            Exception {

        TxnNode node = new TxnNode(getDao());
        return new TreeNodeBuilder().buildTree("0", node);

    }


}
