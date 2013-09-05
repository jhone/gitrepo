package com.redsun.platf.util.treenode;

import java.util.List;

/**
 * Created with IntelliJ IDEA. </br>
 * To change this template use File | Settings | File Templates.</br>
 * User: joker pan</br>
 * Date: 13-8-16</br>
 * Time: 下午1:44</br>
 * ------------------------------------------------------------------------------------</br>
 * Program ID   : 必須實現此接口，獲取entry.id name child root...                                                                     </br>
 * Program Name :                                                                      </br>
 * ------------------------------------------------------------------------------------</br>
 * <H3> Modification log </H3>
 * <pre>
 * Ver.    Date       Programmer    Remark
 * ------- ---------  ------------  ---------------------------------------------------
 * 1.0     13-8-16    joker pan    created
 * <pre/>
 */
public interface ITreeNodeEntity<T> {


    /**
     * 顯示id eg :node.getId().toString()
     *
     * @param node
     * @return
     */
    String getId(T node);

    /**
     * 顯示內容eg :node.getTxnName()
     *
     * @param node
     * @return
     */
    String getText(T node);

    /**
     * 取得当前给定ID的根node
     *
     * @param parentId
     * @return
     */
    List<T> getRootNodes(String parentId);

    /**
     * 取得当前node的第一直接下级
     *
     * @param node
     * @return
     */
    List<T> getSubNodes(T node);

    /**
     * 顯示parent id eg :node.forceRecycleParentId()
     *
     * @param node
     * @return
     */
    T getParentId(T node);

    /**
     * eg: SystemTxn.class
     *
     * @return
     */
    Class getEntityClass();
}