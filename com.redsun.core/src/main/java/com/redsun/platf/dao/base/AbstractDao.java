package com.redsun.platf.dao.base;

import com.redsun.platf.entity.IdEntity;
import com.redsun.platf.util.search.MorePropertyFilter;
import com.redsun.platf.util.sideutil.ReflectionUtils;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.hibernate.hql.ParameterTranslations;
import org.hibernate.hql.classic.QueryTranslatorImpl;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.impl.CriteriaImpl.OrderEntry;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.hibernate.transform.ResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.*;

/**
 * 基本的DAO，供子类paggedDao增加page.
 * <p/>
 * 尽可能不在Service层直接使用, 可以扩展泛型DAO子类使用
 * <p/>
 * 使用HibernateTemplate.HibernateDaoSupport
 *
 * @param <T>  DAO操作的对象类型
 * @param <PK> 主键类型
 * @author PYC
 * @version 1.0
 *          change history:
 *          2012/09/11 cancel extends   HibernateDaoSupport
 */

@Transactional
@Repository
@SuppressWarnings("unchecked")
//public class AbstractDao<T , PK extends Serializable> extends
//		HibernateDaoSupport implements IAbstractBaseDao<T, PK> {

public class AbstractDao<T extends IdEntity, PK extends java.io.Serializable>
        implements IAbstractBaseDao<T, PK> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

//    @Resource
//    @Qualifier("sessionFactory")
//    private SessionFactory sessionFactory;

    @Resource(name = "hibernateTemplate")
    private HibernateTemplate hibernateTemplate;

    protected Class<T> entityClass;


    private String HQL_LIST_ALL = "";
    private String HQL_COUNT_ALL = "";
    private String HQL_OPTIMIZE_PRE_LIST_ALL = "";
    private String HQL_OPTIMIZE_NEXT_LIST_ALL = "";
    private String pkName = null;


    @SuppressWarnings("unchecked")
    public AbstractDao() {
        super();
//        readPKName();

    }


    /**
     * 获得该类的属性和类型
     *
     * @param entityName 注解的实体类
     */
    private <T> void getProperty(Class entityName) {
        ClassMetadata cm = hibernateTemplate.getSessionFactory().getClassMetadata(entityName);
        String[] str = cm.getPropertyNames(); // 获得该类所有的属性名称
        for (int i = 0; i < str.length; i++) {
            String property = str[i];
            String type = cm.getPropertyType(property).getName(); // 获得该名称的类型
            System.out.println(property + "---&gt;" + type);
        }
    }

    /**
     * 获取所有数据表
     *
     * @return
     */
    public List<DBTable> getAllDbTableName() {
        List<DBTable> resultList = new ArrayList<DBTable>();
        SessionFactory factory = getSession().getSessionFactory();
        Map<String, ClassMetadata> metaMap = factory.getAllClassMetadata();
        for (String key : (Set<String>) metaMap.keySet()) {
            DBTable dbTable = new DBTable();
            AbstractEntityPersister classMetadata = (AbstractEntityPersister) metaMap.get(key);
            dbTable.setTableName(classMetadata.getTableName());
            dbTable.setEntityName(classMetadata.getEntityName());
            //update-begin--Author:anchao  Date:20130322 for：系统表参数Title
            Class<?> c;
            try {
                c = Class.forName(key);
//    				JeecgEntityTitle t = c.getAnnotation(JeecgEntityTitle.class);
//    				dbTable.setTableTitle(t!=null?t.name():"");

                String t = c.getSimpleName();
                dbTable.setTableTitle(t != null ? t : "");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            //update-end--Author:anchao  Date:20130322 for：系统表参数Title
            resultList.add(dbTable);
        }
        return resultList;
    }

    /**
     * 获取所有数据表
     *
     * @return
     */
    public Integer getAllDbTableSize() {
        SessionFactory factory = getSession().getSessionFactory();
        Map<String, ClassMetadata> metaMap = factory.getAllClassMetadata();
        return metaMap.size();
    }
    /**
     * 根據@id標識找到class的id字段名稱
     */
   /* protected void readPKName() {
        this.entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
        Field[] fields = this.entityClass.getSuperclass().getDeclaredFields();
        for (Field f : fields) {
            if (f.isAnnotationPresent(Id.class)) {
                this.pkName = f.getName();
                break;
            }
        }
        //找最上一級
        Class superIDClass = entityClass.getSuperclass();
        if (this.pkName == null && superIDClass != null) {

            while (superIDClass != null) {

                if (superIDClass == IdEntity.class) {
                    break;
                }
                superIDClass = superIDClass.getSuperclass();
            }
            fields = superIDClass.getDeclaredFields();
            for (Field f : fields) {
                if (f.isAnnotationPresent(Id.class)) {
                    this.pkName = f.getName();
                    logger.info("[DAO] found id class:{}  in super class:{}"
                            , superIDClass.getSimpleName(), IdEntity.class);

                    break;
                }
            }
        }

//        System.out.println( "[DAO] entity class:{} and pk :{}"
//                + entityClass.getCanonicalName()+","+ pkName);

        logger.info("[DAO] entity class:{}  pk :{}"
                , entityClass.getSimpleName(), pkName);

        Assert.notNull(pkName);
        //TODO @Entity name not null
        HQL_LIST_ALL = "from " + this.entityClass.getSimpleName() + " order by " + pkName + " desc";
        HQL_OPTIMIZE_PRE_LIST_ALL = "from " + this.entityClass.getSimpleName() + " where " + pkName + " > ? order by " + pkName + " asc";
        HQL_OPTIMIZE_NEXT_LIST_ALL = "from " + this.entityClass.getSimpleName() + " where " + pkName + " < ? order by " + pkName + " desc";
        HQL_COUNT_ALL = " select count(*) from " + this.entityClass.getSimpleName();
    }*/

    /**
     * eg. SimpleHibernateDao<UserAccount, Long> userDao = new SimpleHibernateDao<UserAccount,
     * Long>(sessionFactory, UserAccount.class);
     */
//    public AbstractDao(final SessionFactory sessionFactory,
//                       final Class<T> entityClass) {
//        this.sessionFactory = sessionFactory;
//        this.entityClass = entityClass;
//    }
    public AbstractDao(final Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public Session getSession() {
        //事务必须是开启的，否则获取不到
//        return sessionFactory.getCurrentSession();
        return hibernateTemplate.getSessionFactory().getCurrentSession();
    }

    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }


    /**
     * 执行HQL进行批量修改/删除操作.
     *
     * @param values 命名参数,按名称绑定.
     * @return 更新记录数.
     */
    @Override
    public int batchExecute(final String hql, final Map<String, ?> values) {
        return createQuery(hql, values).executeUpdate();
    }

    /**
     * 执行HQL进行批量修改/删除操作.
     *
     * @param values 数量可变的参数,按顺序绑定.
     * @return 更新记录数.
     */
    @Override
    public int batchExecute(final String hql, final Object... values) {
        return createQuery(hql, values).executeUpdate();
    }

    protected Criterion buildCriterion(final String propertyName,
                                       final Object propertyValue, final MorePropertyFilter.MatchType matchType) {
        Assert.hasText(propertyName, "propertyName设置错误");
        Criterion criterion = null;

        switch (matchType) {
            case EQ:
                criterion = Restrictions.eq(propertyName, propertyValue);
                break;
            case LIKE:
                criterion = Restrictions.like(propertyName, (String) propertyValue,
                        MatchMode.ANYWHERE);
                break;

            case LE:
                criterion = Restrictions.le(propertyName, propertyValue);
                break;
            case LT:
                criterion = Restrictions.lt(propertyName, propertyValue);
                break;
            case GE:
                criterion = Restrictions.ge(propertyName, propertyValue);
                break;
            case GT:
                criterion = Restrictions.gt(propertyName, propertyValue);
        }
        return criterion;
    }

    /**
     * @param filters
     * @return
     * @author pyc 2012.12.11 修改filter  MorePropertyFilter
     */
    protected Criterion[] buildCriterionByPropertyFilter(
            final List<MorePropertyFilter> filters) {
        List<Criterion> criterionList = new ArrayList<Criterion>();
//        for (PropertyFilter filter : filters) {
        for (MorePropertyFilter filter : filters) {
            if (!filter.hasMultiProperties()) {
                Criterion criterion = buildCriterion(filter.getPropertyName(),
                        filter.getMatchValue(), filter.getMatchType());
                criterionList.add(criterion);
            } else {//
                Disjunction disjunction = Restrictions.disjunction();
                for (String param : filter.getPropertyNames()) {
                    Criterion criterion = buildCriterion(param,
                            filter.getMatchValue(), filter.getMatchType());
                    disjunction.add(criterion);
                }
                criterionList.add(disjunction);
            }
        }
        return criterionList.toArray(new Criterion[criterionList.size()]);
    }

    /**
     * 查詢總筆數
     *
     * @param queryString 查詢字串HQL
     * @param namedParams 名字與值對應參數Map
     * @return 返回總筆數
     */
    private int count(final String queryString,
                      final Map<String, Object> namedParams) {
        // 取得HQL=>SQL
        QueryTranslatorImpl queryTranslator = new QueryTranslatorImpl(
                queryString,
                queryString,
                Collections.EMPTY_MAP,
                (org.hibernate.engine.SessionFactoryImplementor) hibernateTemplate.getSessionFactory());
        queryTranslator.compile(Collections.EMPTY_MAP, false);
        String sql = "select count(*) from (" + queryTranslator.getSQLString()
                + ") tmp_count_t";

        ParameterTranslations parameterTranslations = queryTranslator
                .getParameterTranslations();
        final Object[] paramList = new Object[parameterTranslations
                .getNamedParameterNames().size()];
        for (Object obj : parameterTranslations.getNamedParameterNames()) {
            int[] index = parameterTranslations
                    .getNamedParameterSqlLocations(obj.toString());
            if (index.length > 0)
                paramList[index[0]] = obj.toString();
        }

        // 重組SQL
        int index = 0;
        final StringBuffer adujSQL = new StringBuffer();
        for (int i = 0, size = sql.length(); i < size; i++) {
            adujSQL.append(sql.charAt(i) == '?' ? (":" + paramList[index++])
                    : sql.charAt(i));
        }

        // 進行SQL查詢
        BigDecimal count = (BigDecimal) this.getHibernateTemplate().execute(
                new HibernateCallback<Object>() {
                    public Object doInHibernate(Session session)
                            throws HibernateException, SQLException {
                        Query query = session.createSQLQuery(adujSQL.toString());
                        for (Iterator<String> it = namedParams.keySet()
                                .iterator(); it.hasNext(); ) {
                            String key = it.next();
                            Object value = namedParams.get(key);
                            if (value != null) {
                                if (value.getClass().isArray()) {
                                    query.setParameterList(key,
                                            (Object[]) value);
                                } else {
                                    query.setParameter(key, value);
                                }
                            }
                        }
                        return query.uniqueResult();
                    }
                });

        return (count == null) ? 0 : count.intValue();
    }

    /*
      * (non-Javadoc)
      *
      * @see com.redsun.platf.dao.IDao#count(java.lang.String,
      * java.lang.Object[])
      */
    @Override
    public int count(final String queryString, final Object[] params) {

        // 取得HQL=>SQL
        QueryTranslatorImpl queryTranslator = new QueryTranslatorImpl(
                queryString,
                queryString,
                Collections.EMPTY_MAP,
                (org.hibernate.engine.SessionFactoryImplementor) hibernateTemplate.getSessionFactory());
        queryTranslator.compile(Collections.EMPTY_MAP, false);
        final String sql = "select count(*) from ("
                + queryTranslator.getSQLString() + ") tmp_count_t";

        // 進行SQL查詢
        BigInteger count = (BigInteger) this.getHibernateTemplate().execute(
                new HibernateCallback<Object>() {
                    public Object doInHibernate(Session session)
                            throws HibernateException, SQLException {
                        Query query = session.createSQLQuery(sql);
//                        System.out.println("param:"+params);
                        if (params != null)
                            for (int i = 0; i < params.length; i++) {
//                            System.out.println("length:"+params[i]);
                                query.setParameter(i + 1, params[i]);
                            }

                        return query.uniqueResult();
                    }
                });

        return (count == null) ? 0 : count.intValue();
    }

    /*
      * (non-Javadoc)
      *
      * @see com.redsun.platf.dao.IDao#count(java.lang.String,
      * java.lang.Object[], java.util.Map)
      */
    @Override
    public int count(final String queryString, final Object[] params,
                     final Map<String, Object> namedParams) {
        if (params != null)
            return count(queryString, params);
        if (namedParams != null)
            return count(queryString, namedParams);
        return 0;
    }

    /**
     * 执行count查询获得本次Criteria查询所能获得的对象总数.
     */

    protected long countCriteriaResult(final Criteria c) {
        CriteriaImpl impl = (CriteriaImpl) c;

        // 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
        Projection projection = impl.getProjection();
        ResultTransformer transformer = impl.getResultTransformer();

        List<OrderEntry> orderEntries = null;
        try {
            orderEntries = (List<OrderEntry>) ReflectionUtils.getFieldValue(
                    impl, "orderEntries");
            ReflectionUtils.setFieldValue(impl, "orderEntries",
                    new ArrayList<OrderEntry>());
        } catch (Exception e) {
            logger.error("不可能抛出的异常:{}", e.getMessage());
        }

        // 执行Count查询
        Long totalCountObject = (Long) c.setProjection(Projections.rowCount())
                .uniqueResult();
        long totalCount = (totalCountObject != null) ? totalCountObject : 0;

        // 将之前的Projection,ResultTransformer和OrderBy条件重新设回去
        c.setProjection(projection);

        if (projection == null) {
            c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        }
        if (transformer != null) {
            c.setResultTransformer(transformer);
        }
        try {
            ReflectionUtils.setFieldValue(impl, "orderEntries", orderEntries);
        } catch (Exception e) {
            logger.error("不可能抛出的异常:{}", e.getMessage());
        }

        return totalCount;
    }

    /**
     * 根据Criterion条件创建Criteria. 与find()函数可进行更加灵活的操作.
     *
     * @param criterions 数量可变的Criterion.
     */
    @Override
    public Criteria createCriteria(final Criterion... criterions) {
        Criteria criteria = getSession().createCriteria(entityClass);
        for (Criterion c : criterions) {
            criteria.add(c);
        }
        return criteria;
    }

    /**
     * 根据查询HQL与参数列表创建Query对象. 与find()函数可进行更加灵活的操作.
     *
     * @param values 命名参数,按名称绑定.
     */
    @Override
    public Query createQuery(final String queryString,
                             final Map<String, ?> values) {
        Assert.hasText(queryString, "queryString不能为空");
        Query query = getSession().createQuery(queryString);
        if (values != null) {
            for (String param : values.keySet()) {
                query.setParameter(param, values.get(param));
            }

            //query.setProperties(values);
        }

        return query;
    }

    /**
     * 根据查询HQL与参数列表创建Query对象. 与find()函数可进行更加灵活的操作.
     *
     * @param values 数量可变的参数,按顺序绑定.
     */
    @Override
    public Query createQuery(final String queryString, final Object... values) {
        Assert.hasText(queryString, "queryString不能为空");
        Query query = getSession().createQuery(queryString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i , values[i]);
            }
        }
        return query;
    }

    @Override
    public void delete(final PK id) {
        Assert.notNull(id, "id不能为空");
//        String hql = "delete from " + entityClass.getSimpleName() + " where id=?";
//        System.out.println(hql);
//        Query q = createQuery(hql, id);
//        q.executeUpdate();


        delete(get(id));
        logger.debug("delete entity {},id is {}", entityClass.getSimpleName(),
                id);
    }

    /*
      * (non-Javadoc)
      *
      * @see com.redsun.platf.dao.IDao#delete(T)
      */
    @Override
    public void delete(T entity) {

//        System.out.println(entity);
        Assert.notNull(entity, "entity不能为空");
//       System.out.println("deleteing abstractdao ");
        getHibernateTemplate().delete(entity, LockMode.PESSIMISTIC_WRITE);
        getHibernateTemplate().flush();       //必須要

    }


    /*
      * (non-Javadoc)
      *
      * @see com.redsun.platf.dao.IDao#deleteAll(java.util.Collection)
      */
    @Override
    public void deleteAll(Collection<T> entities) {
        if (entities != null)
            getHibernateTemplate().deleteAll(entities);
        getHibernateTemplate().flush();
    }

    /*
      * (non-Javadoc)
      *
      * @see com.redsun.platf.dao.IDao#deleteAll(T[])
      */
    @Override
    public void deleteAll(T[] entities) {
        if (entities != null)
            getHibernateTemplate().deleteAll(Arrays.asList(entities));
    }

    /**
     * 2. 为Criteria添加distinct transformer. 预加载关联对象的HQL会引起主对象重复, 需要进行distinct处理.
     */
    public Criteria distinct(Criteria criteria) {
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return criteria;
    }

    /**
     * 2. 为Query添加distinct transformer. 预加载关联对象的HQL会引起主对象重复, 需要进行distinct处理.
     */
    public Query distinct(Query query) {
        query.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return query;
    }

    /**
     * 按Criteria查询对象列表.
     *
     * @param criterions 数量可变的Criterion.
     */

    @Transactional(readOnly = true)
    @Override
    public List<T> find(final Criterion... criterions) {
        return createCriteria(criterions).list();
    }

    @Transactional(readOnly = true)
    @Override
    public List<T> find(List<MorePropertyFilter> filters) {
        Criterion[] criterions = buildCriterionByPropertyFilter(filters);
        return find(criterions);
    }

    /**
     * 基本共用查詢
     *
     * @param queryString 查詢字串HQL
     * @return 返回集合物件List<T>
     */
    @Transactional(readOnly = true)
    protected List<T> find(String queryString) {
        return (List<T>) getHibernateTemplate().find(queryString);
    }

    /*
      * (non-Javadoc)
      *
      * @see com.redsun.platf.dao.springside.S#find(java.lang.String,
      * java.util.Map)
      */
    @Override
    @Transactional(readOnly = true)
    public <X> List<X> find(final String hql, final Map<String, ?> values) {
        return createQuery(hql, values).list();
    }

    @Override
    @Transactional(readOnly = true)
    public <X> List<X> find(String hql, Object... values) {

        return createQuery(hql, values).list();
    }

    /*
      * (non-Javadoc)
      *
      * @see com.redsun.platf.dao.IDao#find(java.lang.String, java.lang.Object[],
      * java.util.Map, java.lang.Integer, java.lang.Integer)
      */
    @Override
    @Transactional(readOnly = true)
    public List<T> find(final String queryString, final Object[] params,
                        final Map<String, Object> namedParams, final Integer firstResult,
                        final Integer maxResults) {
        return (List<T>) this.getHibernateTemplate().execute(
                new HibernateCallback<Object>() {
                    public Object doInHibernate(Session session)
                            throws HibernateException, SQLException {
                        Query query = wrapQuery(session, queryString, params,
                                namedParams);
                        if (firstResult != null)
                            query.setFirstResult(firstResult.intValue());
                        if (maxResults != null)
                            query.setMaxResults(maxResults.intValue());
                        return query.list();
                    }
                });
    }

    /**
     * 基本共用查詢
     *
     * @param queryString 查詢字串HQL
     * @param names       名稱陣列
     * @param values      值陣列
     * @return 返回集合物件List<T>
     */
    @Transactional(readOnly = true)
    protected List<T> find(String queryString, String[] names, Object[] values) {
        return (List<T>) getHibernateTemplate().findByNamedParam(queryString,
                names, values);
    }

    /**
     * 基本共用查詢
     *
     * @param queryString 查詢字串HQL
     * @param values      值陣列
     * @return 返回集合物件List<T>
     */
    @Transactional(readOnly = true)
    protected List<T> find(StringBuffer queryString, List<Object> values) {
        return (List<T>) getHibernateTemplate().find(queryString.toString(),
                values.toArray());
    }

    /**
     * 基本共用查詢
     *
     * @param queryString 查詢字串HQL
     * @param nameParams  名字與值對應參數Map
     * @return 返回集合物件List<T>
     */
    protected List<T> find(StringBuffer queryString,
                           Map<String, Object> nameParams) {
        return find(queryString.toString(), nameParams);
    }

    /**
     * 按属性查找对象列表, 匹配方式为相等.
     */
    @Transactional(readOnly = true)
    public List<T> findBy(final String propertyName, final Object value) {
        Assert.hasText(propertyName, "propertyName不能为空");
        Assert.hasText(value.toString(), "value不能为空");
        Criterion criterion = Restrictions.eq(propertyName, value);
        return find(criterion);
    }

    @Override
    public List<T> findBy(String propertyName, Object value, MorePropertyFilter.MatchType matchType) {
        Assert.hasText(propertyName, "propertyName不能为空");
        Criterion criterion = Restrictions.eq(propertyName, value);
        return find(criterion);
    }


    /*
    * (non-Javadoc)
    *
    * @see com.redsun.platf.dao.IDao#findById(java.lang.Integer)
    */
    @Override
    public T findById(PK id) {
        return getHibernateTemplate().get(getEntityClass(), id);
    }

    /*
      * (non-Javadoc)
      *
      * @see com.redsun.platf.dao.IDao#findById(java.lang.String)
      */
    @Override
    @Transactional(readOnly = true)
    public T findById(String id) {
        Long nId = Long.parseLong(id);
        return getHibernateTemplate().get(getEntityClass(), nId);
    }

    /**
     * 按Criteria查询唯一对象.
     *
     * @param criterions 数量可变的Criterion.
     */
    @Override
    @Transactional(readOnly = true)
    public T findUnique(final Criterion... criterions) {
        return (T) createCriteria(criterions).uniqueResult();
    }

    /**
     * 基本共用查詢(for單一回傳值)
     *
     * @param hql    查詢字串HQL
     * @param values 名字與值對應參數Map
     * @return 返回一個單一值
     */
    @Transactional(readOnly = true)
    @Override
    public <X> X findUnique(final String hql, final Map<String, ?> values) {
        return (X) createQuery(hql, values).uniqueResult();
    }

    /**
     * 按HQL查询唯一对象.
     *
     * @param values 数量可变的参数,按顺序绑定.
     */
    @Override
    @Transactional(readOnly = true)
    public <X> X findUnique(final String hql, final Object... values) {
        return (X) createQuery(hql, values).uniqueResult();
    }

    /**
     * 按属性查找唯一对象, 匹配方式为相等.
     */
    @Transactional(readOnly = true)
    public T findUniqueBy(final String propertyName, final Object value) {
        Assert.hasText(propertyName, "propertyName不能为空");
        Criterion criterion = Restrictions.eq(propertyName, value);
        return (T) createCriteria(criterion).uniqueResult();
    }

    /*
      * (non-Javadoc)
      *
      * @see com.redsun.platf.dao.IDao#flush()
      */
    @Override
    public void flush() {
        getHibernateTemplate().flush();
    }

    /**
     * 按id列表获取对象列表.
     */
    @Override
    @Transactional(readOnly = true)
    public List<T> get(final Collection<PK> ids) {
        return find(Restrictions.in(getIdName(), ids));
    }

    @Override
    @Transactional(readOnly = true)
    public T get(final PK id) {
        Assert.notNull(id, "id不能为空");
        return (T) getSession().get(entityClass, id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> getAll() {
        // same as load all

        return loadAll();//getHibernateTemplate().loadAll(getEntityClass());
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> getAll(String orderByProperty, boolean isAsc) {
        Criteria c = createCriteria();
        if (isAsc) {
            c.addOrder(Order.asc(orderByProperty));
        } else {
            c.addOrder(Order.desc(orderByProperty));
        }
        return c.list();
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    /**
     * 2. 取得对象的主键名.
     */
    @Transactional(readOnly = true)
    public String getIdName() {
        ClassMetadata meta = hibernateTemplate.getSessionFactory().getClassMetadata(entityClass);
        return meta.getIdentifierPropertyName();
    }

    /**
     * 初始化对象. 使用load()方法得到的仅是对象Proxy, 在传到View层前需要进行初始化. 如果传入entity,
     * 则只初始化entity的直接属性,但不会初始化延迟加载的关联集合和属性. 如需初始化关联属性,需执行:
     * Hibernate.initialize(user.getRoles())，初始化User的直接属性和关联集合.
     * Hibernate.initialize
     * (user.getDescription())，初始化User的直接属性和延迟加载的Description属性.
     */
    @Override
    public void initProxyObject(Object proxy) {
        Hibernate.initialize(proxy);
    }

    /**
     * 2 判断对象的属性值在数据库内是否唯一.
     * <p/>
     * 在修改对象的情景下,如果属性新修改的值(value)等于属性原来的值(orgValue)则不作比较.
     */
    public boolean isPropertyUnique(final String propertyName,
                                    final Object newValue, final Object oldValue) {
        if (newValue == null || newValue.equals(oldValue)) {
            return true;
        }
        Object object = findUniqueBy(propertyName, newValue);
        return (object == null);
    }

    protected Object Iterate(final String hql,
                             final Map<String, Object> nameParams) {
        return getHibernateTemplate().execute(new HibernateCallback<Object>() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                for (Iterator<String> it = nameParams.keySet().iterator(); it
                        .hasNext(); ) {
                    String name = it.next();
                    Object value = nameParams.get(name);
                    if (value.getClass().isArray()) {
                        query.setParameterList(name, (Object[]) value);
                    } else {
                        query.setParameter(name, value);
                    }
                }
                return query.iterate();
            }
        });
    }

    /*
      * (non-Javadoc)
      *
      * @see com.redsun.platf.dao.IDao#loadAll()
      */
    @Override
    @Transactional(readOnly = true)
    public List<T> loadAll() {
        return getHibernateTemplate().loadAll(getEntityClass());
    }

    /*
      * (non-Javadoc)
      *
      * @see com.redsun.platf.dao.IDao#refresh(T)
      */
    @Override
    @Transactional(readOnly = true)
    public void refresh(T entity) {
        if (entity != null)
            getHibernateTemplate().refresh(entity);
    }

    /*
      * (non-Javadoc)
      *
      * @see com.redsun.platf.dao.IDao#save(T)
      */
    @Override
    public void save(T entity) {
        Assert.notNull(entity, "entity不能为空");
        if (entity != null) {
            getHibernateTemplate().save(entity);
            getHibernateTemplate().flush();
        }
        logger.debug("save entity: {}", entity);
    }

    /*
      * (non-Javadoc)
      *
      * @see com.redsun.platf.dao.IDao#saveAll(java.util.Collection)
      */
    @Override
    public void saveAll(Collection<T> entitys) {
        if (entitys != null) {
            for (Iterator<T> it = entitys.iterator(); it.hasNext(); ) {
                getHibernateTemplate().save(it.next());
            }
            getHibernateTemplate().flush();
        }
    }

    /*
      * (non-Javadoc)
      *
      * @see com.redsun.platf.dao.IDao#saveOrUpdate(T)
      */
    @Override
    public void saveOrUpdate(T entity) {
        if (entity != null)
            getHibernateTemplate().saveOrUpdate(entity);
        getHibernateTemplate().flush();
    }

    /*
      * (non-Javadoc)
      *
      * @see com.redsun.platf.dao.IDao#saveOrUpdateAll(java.util.Collection)
      */
    @Override
    public void saveOrUpdateAll(Collection<T> entities) {
        if (entities != null)
            getHibernateTemplate().saveOrUpdateAll(entities);
        getHibernateTemplate().flush();
    }

    /*
      * (non-Javadoc)
      *
      * @see com.redsun.platf.dao.IDao#saveOrUpdateAll(T[])
      */
    @Override
    public void saveOrUpdateAll(T[] entities) {
        if (entities != null)
            getHibernateTemplate().saveOrUpdateAll(Arrays.asList(entities));
        getHibernateTemplate().flush();
    }


    /**
     * 基本共用查詢(for單一回傳值)
     *
     * @param hql    查詢字串HQL
     * @param params 值陣列
     * @return 返回一個單一值
     */
    protected Object unique(final String hql, final Object[] params) {
        return getHibernateTemplate().execute(new HibernateCallback<Object>() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                for (int i = 0, size = params.length; i < size; i++) {
                    query.setParameter(i, params[i]);
                }
                return query.uniqueResult();
            }
        });
    }

    /*
      * (non-Javadoc)
      *
      * @see com.redsun.platf.dao.IDao#update(T)
      */
    @Override
    public void update(T entity) {
        Assert.notNull(entity, "entity不能为空");
        if (entity != null) {
            logger.debug("update.... entity: {}", entity);
//            getHibernateTemplate().refresh(entity);
//            logger.debug("merge.... entity: {}", entity);
            getHibernateTemplate().update(entity);
            getHibernateTemplate().flush();
            logger.debug("updated entity: {}", entity);
        }

    }

    /**
     * 建立查詢參數
     *
     * @param session     Session物件
     * @param queryString 查詢字串HQL
     * @param params      陣列參數
     * @param namedParams 名字與值對應參數Map
     * @return 返回Query物件
     */
    private Query wrapQuery(Session session, String queryString,
                            Object[] params, Map<String, Object> namedParams) {
        Query query = session.createQuery(queryString);

        if (params != null) {
            for (int i = 0, size = params.length; i < size; i++)
                query.setParameter(i, params[i]);
        }

        if (namedParams != null) {
            for (Iterator<String> it = namedParams.keySet().iterator(); it
                    .hasNext(); ) {
                String name = it.next();
                Object value = namedParams.get(name);
                if (value != null) {
                    if (value.getClass().isArray()) {
                        query.setParameterList(name, (Object[]) value);
                    } else if (value instanceof Collection<?>) {
                        query.setParameterList(name, (Collection<?>) value);
                    } else {
                        query.setParameter(name, value);
                    }
                }
            }
        }
        return query;
    }

    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * 取得下一個ID
     *
     * @return
     */
    public String getKey() {

        return (String) getHibernateTemplate().execute(new HibernateCallback<Object>() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
//   	                Query query = session.createSQLQuery("select to_char(GPM840M_S.nextval) FROM dual");
                String hql = "select max(id)+1 from " + entityClass.getName();
                Query query = session.createSQLQuery(hql);
                return query.uniqueResult().toString();
            }
        });
    }

    public  SQLQuery createSQLQuery(String sql){
        return getSession().createSQLQuery(sql);
    }
}
