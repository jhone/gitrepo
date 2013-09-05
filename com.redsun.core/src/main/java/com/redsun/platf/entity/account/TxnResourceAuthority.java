package com.redsun.platf.entity.account;

import com.redsun.platf.entity.BaseEntity;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <pre>
 * -----------------------------------------------------------------------------
 * Program ID   : com.redsun.platf.entity.account.TxnResourceAuthority
 * Program Name :  资源群组下，txn的权限代码
 * ----------------------------------------------------------------------------- </pre>
 * <H3> Modification log </H3>
 * <pre>
 * Ver.    Date       Programmer    Remark
 * ------- ---------  ------------  ---------------------------------
 * 1.0     13-6-6      joker       权限码AUDIP ，和txn=>resource关联=>role
 *                                 authorCode=0x11111111;
 *                                 16个权限码，全为1时表示所有权限 ,@See:#AuthorityType
 * </pre>
 */
@Entity
//表名与类名不相同时重新定义表名.
@Table(name = "ACCT_TXN_RESOURCES")
//默认的缓存策略.
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TxnResourceAuthority extends BaseEntity {


    @Column(columnDefinition = "varchar(16) default '11111111111111'",
            length = 16,  nullable = false )
    private String authorCode ;//8个权限码，全为1时表示所有权限


//    //多对多定义

//    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinColumn(name = "TXN_ID")
//    private List<SystemTxn> txns = Lists.newArrayList();
//
//    public List<SystemTxn> getTxns() {
//        return txns;
//    }
//
//    public void setTxns(List<SystemTxn> txns) {
//        this.txns = txns;
//    }


    public String getAuthorCode() {
        return authorCode;
    }

    public void setAuthorCode(String authorCode) {
        this.authorCode = authorCode;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }



}