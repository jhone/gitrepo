package com.redsun.platf.util.treenode;

import com.redsun.platf.entity.BaseEntity;
import com.redsun.platf.util.LogUtils;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA. </br>
 * To change this template use File | Settings | File Templates.</br>
 * User: joker pan</br>
 * Date: 13-8-16</br>
 * Time: 上午11:30</br>
 * ------------------------------------------------------------------------------------</br>
 * Program ID   :                                                                      </br>
 * Program Name :                                                                      </br>
 * ------------------------------------------------------------------------------------</br>
 * <H3> Modification log </H3>
 * <pre>
 * Ver.    Date       Programmer    Remark
 * ------- ---------  ------------  ---------------------------------------------------
 * 1.0     13-8-16    joker pan    created
 * <pre/>
 */
public class TreeNodeBuilder<T extends BaseEntity> {

    private Logger logger = LogUtils.getLogger(this.getClass());
    ITreeNodeEntity<T> tree;

//    public TreeNodeBuilder(ITreeNodeEntity<T> tree) {
//        this.tree = tree;
//    }


    @SuppressWarnings("unchecked")
    public List<TreeNode> buildTree(String id, ITreeNodeEntity<T> tree) throws
            Exception {
        this.tree = tree;
        logger.debug("create tree...");
        //顯示tree
        List<TreeNode> nodes = new ArrayList<TreeNode>();

        nodes.add(listMenu(id));

        logger.info("request nodes :{}", nodes);

        return nodes;

    }
     /*
             String hql = "";
             if (id == 0l) {
                 hql = "from SystemTxn where parentid=0";

             }
     */

    private TreeNode listMenu(String id) {

//          List<SystemCompany> txns = getDao().getAll();

        logger.debug("txn records:{}");


        NodeUi root = new NodeUi();
        root.setId("0");
        root.setState(TreeNode.NODE_STATE_OPEN);
        root.setIconCls("ui-icon-suitcase");
        root.setText("root");
        root.setChildren(new LinkedList<TreeNode>());

        buildNodes(tree.getRootNodes(id), root);//add loop all children

        logger.debug("root {}" + root);

        return root;
    }

    /*
    *自循環
    *
     */
    private void buildNodes(final List<T> txns, TreeNode parentNode) {
        if (parentNode == null) {
            logger.warn("parent  is null");
            return;
        }

        Long parentId = Long.parseLong(parentNode.getId());

        for (T entry : txns) {
            logger.debug("builder node Id:" + entry.getId());

//              Long currentParentId = entry.forceRecycleParentId() == null ? 0L : (Long) entry.forceRecycleParentId().getId();
            T pid = (T) tree.getParentId(entry);

            Long currentParentId = (pid == null) ? 0L : Long.parseLong(tree.getId(pid));

            if (!parentId.equals(currentParentId)) continue;

            NodeUi node = new NodeUi();

            node.setId(tree.getId(entry)); //id String

            node.setText(tree.getText(entry));  //顯示內容

            System.out.println("id  = " + node.getId());
//            System.out.println("text= "+node.getText());

            List<T> subTxns = tree.getSubNodes(entry);// entry.getChildTxn();
            if (subTxns != null) {
                node.setState(TreeNode.NODE_STATE_OPEN);
                node.setText("icon-reload");
                node.setText(tree.getText(entry));  //
                node.setChildren(new LinkedList<TreeNode>());

                for (T t : subTxns) {
                    logger.debug(node.getId() + ",has sub id  ={},text ={} ",
                            tree.getId(t), tree.getText(t));
                }
                buildNodes(subTxns, node);   //loop all children

            } else {
                node.setState(TreeNode.NODE_STATE_CLOSED);
                node.setIconCls("icon-ok");
                //                node.setIcon("ui-icon-suitcase");
            }

            //add current node to parent

            parentNode.getChildren().add(node);

        }
    }

    /**
     * easy ui tree
     * "id":1,
     * "text":"Folder1",
     * "iconCls":"icon-ok",
     * "children"
     * "iconCls":"icon-reload"
     * "checked":true,
     */
    class NodeUi extends TreeNode {

        private boolean checked;


        //        public String getIconCls() {
        //            return this.getIconCls();
        //        }

        public Boolean isChecked() {
            return this.checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }
    }




}
