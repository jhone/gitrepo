package com.redsun.platf.service.sys.impl;

import com.redsun.platf.util.search.MorePropertyFilter;
import com.redsun.platf.util.sideutil.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface IManager<T> {

	@Transactional(readOnly = true)
	public abstract T get(Long id);

	public abstract void save(T entity);

	/*** 删除. ******/
	public abstract void delete(Long id);

	/*** 使用属性过滤条件查询. ***/
	@Transactional(readOnly = true)
	public abstract Page<T> findPage(final Page<T> page,
                                     final List<MorePropertyFilter> filters);

	/**** 按编号查找 ***********/
	@Transactional(readOnly = true)
	public abstract T findUniqueBy(String s);

	/**
	 * 检查是否唯一.
	 * 
	 * @return No在数据库中唯一或等于oldValue时返回true.
	 */
	@Transactional(readOnly = true)
	public abstract boolean isPropertyUnique(String fieldName, String newValue,
                                             String oldValue);

	/**
	 * @return list all
	 */
	public abstract List<T> getAll();

}