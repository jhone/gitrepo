package com.redsun.platf.entity.account;

import com.redsun.platf.entity.BaseEntity;
import com.redsun.platf.entity.sys.SystemValue;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 权限.
 * 
 * 注释见{@link SystemValue}.
 *
 *
 * <pre>
 * -----------------------------------------------------------------------------
 * Program ID   : com.redsun.platf.entity.account.Authority
 * Program Name :
 * ----------------------------------------------------------------------------- <pre/>
 * <H3> Modification log </H3>
 * <pre>
 * Ver.    Date       Programmer    Remark
 * ------- ---------  ------------  ---------------------------------
 * 1.0     12-1-6    joker       created
 * 1.1     13-6-6    joker       增加权限码AUDIP ，和txn&resource关联
 *
 */
@Entity
@Table(name = "ACCT_AUTHORITY")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Authority extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -686104653658976091L;
//          StdSerializerProvider
	/**
	 * SpringSecurity中默认的角色/授权名前缀.
	 */
	public static final String AUTHORITY_PREFIX = "ROLE_";
//    @NotNull(message = "authority.name.null")
//    @Pattern(regexp = "[a-zA-Z0-9_]{5,10}", message = "authority.name.illegal")
	private String name;
//    @Size(min = 5, max=10, message = "prefixedName.length.illegal")
    private String prefixedName;

    private  long authorCode=0x00000000;

	public Authority() {
	}

	public Authority(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	@Column(nullable = false, unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	@Transient
		public String getPrefixedName() {
			return AUTHORITY_PREFIX + name;
		}
//    @Transient //needn't
	public void setPrefixedName(String s) {
        this.prefixedName=s;
		}

    public long getAuthorCode() {
        return authorCode;
    }

    public void setAuthorCode(long authorCode) {
        this.authorCode = authorCode;
    }

    @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
