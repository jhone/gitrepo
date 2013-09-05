package com.redsun.platf.dao.base.impl;

import com.redsun.platf.dao.base.AbstractDao;
import com.redsun.platf.dao.base.IPagedDao;
import com.redsun.platf.entity.BaseEntity;
import com.redsun.platf.util.search.MorePropertyFilter;
import com.redsun.platf.util.sideutil.Page;
import com.redsun.platf.util.sideutil.PagedResult;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 基本的DAO，供子类paggedDao增加page.
 * 
 * 尽可能不在Service层直接使用, 可以扩展泛型DAO子类使用
 * 
 * 使用HibernateTemplate.HibernateDaoSupport
 * 
 * @param <T>
 *            DAO操作的对象类型
 * @param <PK>
 *            主键类型
 * 
 * @author PYC
 * 
 * @version 1.0
 * 
 *          parent :
 *          <p>
 * @see {@link com.redsun.platf.dao.base.AbstractDao}.
 *      <p>
 *      extends :
 *      <p>
 * @see {@link com.redsun.platf.dao.base.IAbstractBaseDao}
 *      <p>
 *      Example :
 *      <p>
 * 
 *      <pre>
 * <code>
 * 	public class StorageStationDao extends PagedDao&lt;StorageStation, Long&gt; {
 * 		public StorageStationDao() {
 * 			super();
 * 			this.entityClass = StorageStation.class;
 * 		}
 * }
 * 
 * </code>
 * </pre>
 *
 * @author  pyc 2012.12.11 改propertyFilter－MoreFilter 增加類型、
 *                         增加日期格式
 * 
 */

@Transactional
@Repository
public class PagedDao<T extends BaseEntity, PK extends Serializable> extends AbstractDao<T, PK>
		implements IPagedDao<T, PK> {
//	@Resource(name = "sessionFactory")
//	SessionFactory sessionFactory;

//	public PagedDao(final SessionFactory sessionFactory,
//			final Class<T> entityClass) {
//		super(sessionFactory, entityClass);
//
//	}

	public PagedDao() {
		super();
	}

@Override
	public PagedResult<T> getAll(PagedResult<T> page) {
		return findPage(page);
	}

	/******/
@Override
	public PagedResult<T> findPage(PagedResult<T> page, List<MorePropertyFilter> filters) {
		Criterion[] criterions = buildCriterionByPropertyFilter(filters);

		return findPage(page, criterions);
	}

@Override
	@SuppressWarnings("unchecked")
	public PagedResult<T> findPage(final PagedResult<T> page, final String hql,
			final Object... values) {
		Assert.notNull(page, "page不能为空");

		Query q = createQuery(hql, values);

		if (page.isAutoCount()) {
			long totalCount = countHqlResult(hql, values);
			page.setTotalCount(totalCount);
		}

		setPageParameterToQuery(q, page);

		List<T> result = q.list();
		page.setResult(result);
		return page;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.redsun.platf.dao.springside.I#findPage(org.springside.modules.orm
	 * .Page, java.lang.String, java.util.Map)
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.redsun.platf.dao.springside.IB#findPage(org.springside.modules.orm
	 * .Page, java.lang.String, java.util.Map)
	 */
@Override
	@SuppressWarnings("unchecked")
	public PagedResult<T> findPage(final PagedResult<T> page, final String hql,
			final Map<String, ?> values) {
		Assert.notNull(page, "page不能为空");

		Query q = createQuery(hql, values);

		if (page.isAutoCount()) {
			long totalCount = countHqlResult(hql, values);
			page.setTotalCount(totalCount);
		}

		setPageParameterToQuery(q, page);

		List<T> result = q.list();
		page.setResult(result);
		return page;
	}

@Override
	@SuppressWarnings("unchecked")
	public PagedResult<T> findPage(final PagedResult<T> page, final Criterion... criterions) {
		Assert.notNull(page, "page不能为空");

		Criteria c = createCriteria(criterions);

		if (page.isAutoCount()) {
			long totalCount = countCriteriaResult(c);
			page.setTotalCount(totalCount);
		}

		setPageParameterToCriteria(c, page);

		List<T> result = c.list();
		page.setResult(result);

		return page;
	}

	/** page protected ************************************************************/
	/**
	 * 设置分页参数到Query对象,辅助函数.
	 */
	protected Query setPageParameterToQuery(final Query q, final PagedResult<T> page) {

		Assert.isTrue(page.getPageSize() > 0, "Page Size must larger than zero");

		// hibernate的firstResult的序号从0开始
		q.setFirstResult(page.getFirst() - 1);
		q.setMaxResults(page.getPageSize());
		return q;
	}

	/**
	 * 设置分页参数到Criteria对象,辅助函数.
	 */
	protected Criteria setPageParameterToCriteria(final Criteria c,
			final PagedResult<T> page) {

		Assert.isTrue(page.getPageSize() > 0, "Page Size must larger than zero");

		// hibernate的firstResult的序号从0开始
		c.setFirstResult(page.getFirst() - 1);
		c.setMaxResults(page.getPageSize());

		if (page.isOrderBySetted()) {
			String[] orderByArray = StringUtils.split(page.getOrderBy(), ',');
			String[] orderArray = StringUtils.split(page.getOrder(), ',');

			Assert.isTrue(orderByArray.length == orderArray.length,
					"分页多重排序参数中,排序字段与排序方向的个数不相等");

			for (int i = 0; i < orderByArray.length; i++) {
				if (Page.ASC.equals(orderArray[i])) {
					c.addOrder(Order.asc(orderByArray[i]));
				} else {
					c.addOrder(Order.desc(orderByArray[i]));
				}
			}
		}
		return c;
	}

	/**
	 * 执行count查询获得本次Hql查询所能获得的对象总数.
	 * 
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
	 */
	protected long countHqlResult(final String hql, final Object... values) {
		String countHql = prepareCountHql(hql);

		try {
			Long count = findUnique(countHql, values);
			return count;
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:"
					+ countHql, e);
		}
	}

	/**
	 * 执行count查询获得本次Hql查询所能获得的对象总数.
	 * 
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
	 */
	protected long countHqlResult(final String hql, final Map<String, ?> values) {
		String countHql = prepareCountHql(hql);

		try {
			Long count = findUnique(countHql, values);
			return count;
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:"
					+ countHql, e);
		}
	}

	private String prepareCountHql(String orgHql) {
		String fromHql = orgHql;
		// select子句与order by子句会影响count查询,进行简单的排除.
		fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
		fromHql = StringUtils.substringBefore(fromHql, "order by");

		String countHql = "select count(*) " + fromHql;
		return countHql;
	}



}
