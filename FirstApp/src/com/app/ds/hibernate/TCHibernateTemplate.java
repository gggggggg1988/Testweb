package com.app.ds.hibernate;

;
import com.util.ArrayUtil;
import com.util.CollectionUtil;
import com.util.StringUtil;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.hibernate.transform.Transformers;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.*;

/**
 * 重构spring的模板类
 *
 * @author Yang.Cheng
 * @date 2013-5-8上午10:32:25
 * @Copyright(c) Chengdu Chant Software Technology Co.,LTD.
 */
public class TCHibernateTemplate extends HibernateTemplate {

//	private static final Logger logger = LoggerFactory.getLogger(TCHibernateTemplate.class);

	/**
	 * 保存集合数据
	 *
	 * @param objects
	 * @return
	 * @throws DataAccessException
	 */
	public List<Serializable> saveAll(final Collection<?> objects) throws DataAccessException {
		final List<Serializable> result = new ArrayList<Serializable>();
		if (!CollectionUtil.isEmpty(objects)) {
			executeWithNativeSession(new HibernateCallback<Serializable>() {



				public Serializable doInHibernate(Session arg0) throws HibernateException, SQLException {
					TCHibernateTemplate.this.checkWriteOperationAllowed(arg0);
					for (Object obj : objects) {
						Serializable rs = null;
						if (obj != null) {
							rs = arg0.save(obj);
						}
						result.add(rs);
					}

					return null;
				}
			});
		}
		return result;
	}

	/**
	 * 更新集合数据
	 *
	 * @param objects
	 * @throws DataAccessException
	 */
	public void updateAll(final Collection<?> objects) throws DataAccessException {
		if (!CollectionUtil.isEmpty(objects)) {
			executeWithNativeSession(new HibernateCallback<Serializable>() {


				public Serializable doInHibernate(Session arg0) throws HibernateException, SQLException {
					TCHibernateTemplate.this.checkWriteOperationAllowed(arg0);
					for (Object obj : objects) {
						if (obj != null) {
							arg0.update(obj);
						}
					}
					return null;
				}
			});
		}
	}
	/**
	 * 更新集合数据,返回boolean
	 *
	 * @param objects
	 * @return
	 * @throws DataAccessException
	 */
	public boolean updateAllList(final Collection<?> objects) throws DataAccessException {
		if (!CollectionUtil.isEmpty(objects)) {
			executeWithNativeSession(new HibernateCallback<Serializable>() {


				public Serializable doInHibernate(Session arg0) throws HibernateException, SQLException {
					TCHibernateTemplate.this.checkWriteOperationAllowed(arg0);
					for (Object obj : objects) {
						if (obj != null) {
							arg0.update(obj);
						}
					}
					return true;
				}
			});
		}
		return false;
	}

//	@Override
//	public void saveOrUpdateAll(Collection entities) throws DataAccessException {
//		if (!CollectionUtil.isEmpty(entities)) {
//			//super.clear();
//			super.saveOrUpdateAll(entities);
//		}
//	}

	/**
	 * 根据hql分页查询
	 *
	 * @param queryName
	 * @param paramNames
	 * @param values
	 * @param firstResult
	 *            <0 表示不分页
	 * @param maxResult
	 *            <0 表示不分页
	 * @return
	 * @throws DataAccessException
	 */
	public List findByNamedQueryAndNamedParamAndRange(final String queryName, final String[] paramNames,
			final Object[] values, final int firstResult, final int maxResult) throws DataAccessException {
		List result = new ArrayList();
		if (ArrayUtil.isEmpty(paramNames) || ArrayUtil.isEmpty(values) || paramNames.length != values.length) {
			return result;
		}
		result = executeWithNativeSession(new HibernateCallback<List>() {


			public List doInHibernate(org.hibernate.Session session) throws HibernateException {
				Query queryObject = session.getNamedQuery(queryName);
				TCHibernateTemplate.this.prepareQuery(queryObject);
				for (int i = 0; i < paramNames.length; i++) {
					TCHibernateTemplate.this.applyNamedParameterToQuery(queryObject, paramNames[i], values[i]);
				}
				if (firstResult >= 0) {
					queryObject.setFirstResult(firstResult);
				}
				if (maxResult > 0) {
					queryObject.setMaxResults(maxResult);
				}
				return queryObject.list();
			}
		});
		return result;
	}

	/**
	 * 执行sql
	 *
	 * @param queryName
	 * @param paramName
	 * @param value
	 * @throws DataAccessException
	 */
	public void executeByNamedQueryAndNamedParam(String queryName, String paramName, Object value)
			throws DataAccessException {
		executeByNamedQueryAndNamedParam(queryName, new String[] { paramName }, new Object[] { value });
	}

	/**
	 * 根据键值对执行sql
	 *
	 * @param queryName
	 * @param paramNames
	 * @param values
	 * @throws DataAccessException
	 */
	public void executeByNamedQueryAndNamedParam(final String queryName, final String[] paramNames,
			final Object[] values) throws DataAccessException {
		if (ArrayUtil.isEmpty(paramNames) || ArrayUtil.isEmpty(values) || values.length != paramNames.length) {
			return;
		}
		executeWithNativeSession(new HibernateCallback() {


			public Object doInHibernate(Session session) throws HibernateException {
				TCHibernateTemplate.this.checkWriteOperationAllowed(session);
				Query q = session.getNamedQuery(queryName);
				for (int i = 0; i < paramNames.length; i++) {
					TCHibernateTemplate.this.applyNamedParameterToQuery(q, paramNames[i], values[i]);
				}
				q.executeUpdate();
				return null;
			}
		});
	}

	public void executeByNamedQuery(final String queryName, final Object... objs) throws DataAccessException {
		if (ArrayUtil.isEmpty(objs)) {
			return;
		}
		executeWithNativeSession(new HibernateCallback() {


			public List doInHibernate(org.hibernate.Session session) throws HibernateException {
				Query queryObject = session.getNamedQuery(queryName);
				TCHibernateTemplate.this.prepareQuery(queryObject);
				for (int i = 0; i < objs.length; i++) {
					queryObject.setParameter(i, objs[i]);
				}
				queryObject.executeUpdate();
				return null;
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void applyNamedParameterToQuery(Query queryObject, String paramName, Object value)
			throws HibernateException {
		if (value instanceof Collection) {
			queryObject.setParameterList(paramName, (Collection) value);
		} else if (value instanceof Object[]) {
			queryObject.setParameterList(paramName, (Object[]) value);
		} else {
			queryObject.setParameter(paramName, value);
		}
	}

	/**
	 * 在使用hibernatetemplte的情况下，原则上不允许使用sessionFactory来进行操作 {@inheritDoc}
	 */
	@Deprecated
	@Override
	public SessionFactory getSessionFactory() {
		return super.getSessionFactory();
	}

	/**
	 * 根据参数查询
	 *
	 * @param entityClass
	 * @param param
	 * @return
	 * @see Criterion 对象的生成是用Restrictions类的静态方法生成
	 */
	public <T> List<T> query(final Class<T> entityClass, final Criterion param, final Order order) {
		if (entityClass == null) {
			return null;
		}
		return (List<T>) TCHibernateTemplate.this.execute(new HibernateCallback<List<T>>() {


			public List<T> doInHibernate(Session session) throws HibernateException {
				Criteria cri = session.createCriteria(entityClass);
				if (param != null)
					cri.add(param);
				if (order == null)
					cri.addOrder(Order.desc("id"));
				else
					cri.addOrder(order);
				cri.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
				return cri.list();
			}
		});
	}

	public void executeByHql(final String hql, final Map<String, Object> whereMap) {
        if (CollectionUtil.isEmpty(whereMap)) {
            return;
        }
        executeWithNativeSession(new HibernateCallback() {


            public List doInHibernate(org.hibernate.Session session) throws HibernateException {
                Query query = session.createQuery(hql);
                if (!CollectionUtil.isEmpty(whereMap)) {
                    query.setProperties(whereMap);
                }
                query.executeUpdate();
                return null;
            }
        });
    }

    public void executeHql(final String hql) {
        executeWithNativeSession(new HibernateCallback() {

            public List doInHibernate(org.hibernate.Session session) throws HibernateException {
                Query query = session.createQuery(hql);
                query.executeUpdate();
                return null;
            }
        });
    }

	/**
	 * 根据hql查询
	 *
	 * @param hql
	 * @param whereMap
	 * @return
	 */
	public <T> List<T> queryByHql(final String hql, final Map<String, Object> whereMap) {
		if (StringUtil.isEmpty(hql)) {
			return null;
		}
		return (List<T>) TCHibernateTemplate.this.execute(new HibernateCallback() {


			public List<T> doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery(hql);
				if (whereMap != null) {
					if (whereMap.containsKey("@min")) {
						int min = Integer.parseInt(whereMap.get("@min").toString());
						min = min < 1 ? 0 : min;
						query.setFirstResult(min);
						whereMap.remove("@min");
					}
					if (whereMap.containsKey("@max")) {
						int max = Integer.parseInt(whereMap.get("@max").toString());
						if (max > 0) {
							query.setMaxResults(max);
						}
						whereMap.remove("@max");
					}
					if (!CollectionUtil.isEmpty(whereMap)) {
						query.setProperties(whereMap);
					}
				}
				return query.list();
			}
		});
	}

	/**
	 * 根据hql查询
	 *
	 * @param hql
	 * @param whereMap
	 * @return
	 */
	public <T> List<T> queryByHql(final String hql, final Map<String, Object> whereMap, final int start, final int range) {
		if (StringUtil.isEmpty(hql)) {
			return null;
		}
		return (List<T>) TCHibernateTemplate.this.execute(new HibernateCallback() {


			public List<T> doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery(hql);
				if(start>-1) {
					query.setFirstResult(start);
					query.setMaxResults(range);
				}
				if (!CollectionUtil.isEmpty(whereMap)) {
					query.setProperties(whereMap);
				}
				return query.list();
			}
		});
	}

	/**
	 * 查询指定字段并返回结果集
	 *
	 * @param hql
	 * @param whereMap
	 * @return
	 * @Deprecated
	 */
	public List<Object[]> queryByHqlAndFiel(final String hql, final Map<String, Object> whereMap) {
		if (StringUtil.isEmpty(hql)) {
			return null;
		}
		return (List<Object[]>) TCHibernateTemplate.this.execute(new HibernateCallback() {


			public List<Object[]> doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery(hql);
				if (!CollectionUtil.isEmpty(whereMap)) {
					query.setProperties(whereMap);
				}
				return query.list();
			}
		});
	}

	/**
	 * 根据字段更新记录
	 *
	 * @param entityClass
	 * @param param
	 * @return
	 */
	public <T> int updataByFiel(final Class<T> entityClass, final Map<String, Object> fielMap, final Criterion param) {
		if (entityClass == null || CollectionUtil.isEmpty(fielMap)) {
			return 0;
		}
		StringBuilder temp = new StringBuilder();
		for (String key : fielMap.keySet()) {
			Object value = fielMap.get(key);
			if (value instanceof String || value instanceof Date) {
				temp.append(key).append("='").append(value).append("',");
			} else {
				temp.append(key).append("=").append(value).append(",");
			}
		}
		if (temp.length() > 0) {
			temp.deleteCharAt(temp.length() - 1);
		}
		final String fielSet = temp.toString();
		return (Integer) TCHibernateTemplate.this.execute(new HibernateCallback() {


			public Integer doInHibernate(Session session) throws HibernateException {
				String hql = "update  " + entityClass.getCanonicalName() + "  set  " + fielSet;
				if (param != null) {
					hql += " where " + param.toString();
				}
				return session.createQuery(hql).executeUpdate();
			}
		});
	}

	/**
	 * 根据字段更新记录
	 *
	 * @param entityClass
	 * @param param
	 * @return
	 */
	public <T> int updataFielByWhereMap(final Class<T> entityClass, final Map<String, Object> fielMap,
			final Map<String, Object> param) {
		if (entityClass == null || CollectionUtil.isEmpty(fielMap)) {
			return 0;
		}
		String temp = "";
		for (String key : fielMap.keySet()) {
			temp += key + "=" + fielMap.get(key) + ",";
		}
		temp = temp.substring(0, temp.lastIndexOf(","));
		final String fielSet = temp;
		return (Integer) TCHibernateTemplate.this.execute(new HibernateCallback() {


			public Integer doInHibernate(Session session) throws HibernateException {
				String hql = "update  " + entityClass.getCanonicalName() + "  set  " + fielSet;
				if (param != null) {
					hql += " where " + Restrictions.allEq(param).toString();
				}
				return session.createQuery(hql).executeUpdate();
			}
		});
	}

	/**
	 * 根据条件查询 ，每个子条件间是and关系
	 *
	 * @param entityClass
	 * @param whereMap
	 * @return
	 */
	public <T> List<T> queryByWhereMap(final Class<T> entityClass, final Map<String, Object> whereMap) {
		if (entityClass == null) {
			return null;
		}
		return (List<T>) TCHibernateTemplate.this.execute(new HibernateCallback() {


			public List<T> doInHibernate(Session session) throws HibernateException {
				Criteria cri = session.createCriteria(entityClass);
				if (!CollectionUtil.isEmpty(whereMap)) {
					cri.add(Restrictions.allEq(whereMap));
				}
				return cri.addOrder(Order.desc("id")).list();
			}
		});
	}

//	/**
//	 * 根据参数查询
//	 *
//	 * @param entityClass
//	 * @param param
//	 * @return
//	 * @see Criterion 对象的生成是用Restrictions类的静态方法生成
//	 */
//	public <T> ListObject<T> queryPage(final Class<T> entityClass, final Criterion param, final Integer firstResult,
//			final Integer maxResult) {
//		return queryPage(entityClass, param, firstResult, maxResult, null);
//	}

//	/**
//	 * 根据参数查询事体集合
//	 *
//	 * @param entityClass
//	 * @param param
//	 * @return
//	 */
//	public <T> List<T> query(final Class<T> entityClass, final Criterion param) {
//		ListObject list = queryPage(entityClass, param, -1, -1, null);
//		if (list != null && list.getList() != null) {
//			return list.getList();
//		}
//		return null;
//	}

	/**
	 * 查询单个对象,如果对象数多于一个,则抛出异常
	 *
	 * @param entityClass
	 * @param param
	 * @return
	 */
	public <T> T querySingle(final Class<T> entityClass, final Criterion param) throws SQLException {
		List<T> list = queryList(entityClass, param);
		T result = null;
		if (list != null && list.size() > 0) {
			if (list.size() > 1) {
				throw new SQLException("this data mast be single!entityClass[" + entityClass + "];param[" + param + "]");
			}
			result = list.get(0);
		}
		return result;
	}

	/**
	 * 查询集合
	 *
	 * @param entityClass
	 * @param param
	 * @return
	 */
	public <T> List<T> queryList(final Class<T> entityClass, final Criterion param) {
		if (entityClass == null) {
			return null;
		}
		return (List) TCHibernateTemplate.this.execute(new HibernateCallback() {


			public List doInHibernate(Session session) throws HibernateException {
				List data = null;
				Criteria cri = session.createCriteria(entityClass);
				if (param != null) {
					cri.add(param);
				}
				data = cri.list();
				return data;
			}
		});
	}

	/**
	 * 以参数查询
	 *
	 * @param entityClass
	 * @param param
	 * @see Criterion 对象的生成是用Restrictions类的静态方法生成
	 * @param firstResult
	 * @param maxResult
	 * @param orderList
	 * @return
	 */
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	public <T> ListObject<T> queryPageByMultiOrders(final Class<T> entityClass, final Criterion param,
//			final Integer firstResult, final Integer maxResult, final Collection<Order> orderList) {
//		if (entityClass == null) {
//			return null;
//		}
//		return (ListObject) TCHibernateTemplate.this.execute(new HibernateCallback() {
//
//			@Override
//			public ListObject doInHibernate(Session session) throws HibernateException {
//				ListObject data = new ListObject();
//				Criteria cri = session.createCriteria(entityClass);
//				Criteria count = session.createCriteria(entityClass);
//				count.setProjection(Projections.count("id"));
//				if (param != null) {
//					count.add(param);
//					cri.add(param);
//				}
//				data.setCount(Integer.parseInt(count.uniqueResult().toString()));
//				if (CollectionUtil.isNotEmpty(orderList)) {
//					for (Order order : orderList) {
//						cri.addOrder(order);
//					}
//				} else {
//					cri.addOrder(Order.desc("id"));
//				}
//				if (firstResult >= 0) {
//					cri.setFirstResult(firstResult);
//				}
//				if (maxResult > 0) {
//					cri.setMaxResults(maxResult);
//				}
//				data.setList(cri.list());
//				return data;
//			}
//		});
//	}

	/**
	 * 根据参数查询
	 *
	 * @param entityClass
	 * @param param
	 * @return
	 * @see Criterion 对象的生成是用Restrictions类的静态方法生成
	 */
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	public <T> ListObject<T> queryPage(final Class<T> entityClass, final Criterion param, final Integer firstResult,
//			final Integer maxResult, final Order order) {
//		if (entityClass == null) {
//			return null;
//		}
//		return (ListObject) TCHibernateTemplate.this.execute(new HibernateCallback() {
//
//			@Override
//			public ListObject doInHibernate(Session session) throws HibernateException {
//				ListObject data = new ListObject();
//				Criteria cri = session.createCriteria(entityClass);
//				Criteria count = session.createCriteria(entityClass);
//				count.setProjection(Projections.count("id"));
//				if (param != null) {
//					count.add(param);
//					cri.add(param);
//				}
//				Object result = count.uniqueResult();
//				data.setCount(result == null ? 0 : Integer.parseInt(result.toString()));
//				if (order != null) {
//					cri.addOrder(order);
//				} else {
//					cri.addOrder(Order.desc("id"));
//				}
//				if (firstResult >= 0) {
//					cri.setFirstResult(firstResult);
//				}
//				if (maxResult > 0) {
//					cri.setMaxResults(maxResult);
//				}
//				cri.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//				data.setList(cri.list());
//				return data;
//			}
//		});
//	}

	/**
	 * 根据参数查询
	 *
	 * @param entityClass
	 * @param param
	 * @return
	 * @see Criterion 对象的生成是用Restrictions类的静态方法生成
	 */
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	public <T> ListObject<T> queryPageWithMultiOrder(final Class<T> entityClass, final Criterion param, final Integer firstResult,
//			final Integer maxResult, final List<Order> orders) {
//		if (entityClass == null) {
//			return null;
//		}
//		return (ListObject) TCHibernateTemplate.this.execute(new HibernateCallback() {
//
//			@Override
//			public ListObject doInHibernate(Session session) throws HibernateException {
//				ListObject data = new ListObject();
//				Criteria cri = session.createCriteria(entityClass);
//				Criteria count = session.createCriteria(entityClass);
//				count.setProjection(Projections.count("id"));
//				if (param != null) {
//					count.add(param);
//					cri.add(param);
//				}
//				Object result = count.uniqueResult();
//				data.setCount(result == null ? 0 : Integer.parseInt(result.toString()));
//				if (orders != null) {
//					for (Order order:orders) {
//						cri.addOrder(order);
//					}
//				} else {
//					cri.addOrder(Order.desc("id"));
//				}
//				if (firstResult >= 0) {
//					cri.setFirstResult(firstResult);
//				}
//				if (maxResult > 0) {
//					cri.setMaxResults(maxResult);
//				}
//				cri.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//				data.setList(cri.list());
//				return data;
//			}
//		});
//	}

	/**
	 * 根据参数查询
	 *
	 * @param entityClass
	 * @return
	 * @see Criterion 对象的生成是用Restrictions类的静态方法生成
	 */
//	public <T> ListObject<T> queryPage(final Class<T> entityClass, final Integer firstResult, final Integer maxResult) {
//		if (entityClass == null) {
//			return null;
//		}
//		return (ListObject) TCHibernateTemplate.this.execute(new HibernateCallback() {
//
//			@Override
//			public ListObject doInHibernate(Session session) throws HibernateException {
//				ListObject data = new ListObject();
//				Criteria cri = session.createCriteria(entityClass);
//				Criteria count = session.createCriteria(entityClass);
//				count.setProjection(Projections.count("id"));
//				data.setCount(Integer.parseInt(count.uniqueResult().toString()));
//				if (firstResult >= 0) {
//					cri.setFirstResult(firstResult);
//				}
//				if (maxResult > 0) {
//					cri.setMaxResults(maxResult);
//				}
//				cri.addOrder(Order.desc("id"));
//				data.setList(cri.list());
//				return data;
//			}
//		});
//	}

	/**
	 * 根据条件删除，每一个子条件间是and关系
	 *
	 * @param entityClass
	 * @param whereMap
	 * @return
	 */
	public <T> int delete(final Class<T> entityClass, final Map<String, Object> whereMap) {
		if (entityClass == null || CollectionUtil.isEmpty(whereMap)) {
			return 0;
		}
		return (Integer) TCHibernateTemplate.this.execute(new HibernateCallback() {


			public Integer doInHibernate(Session session) throws HibernateException {
				String hql = "delete from " + entityClass.getCanonicalName() + "  where  "
						+ Restrictions.allEq(whereMap).toString();
				return session.createQuery(hql).executeUpdate();
			}
		});
	}

	/**
	 * 根据参数删除 如果参数的值是字符串 需要将值用单引号包裹
	 *
	 * @param entityClass
	 * @param param
	 * @return
	 * @see Criterion 对象的生成是用Restrictions类的静态方法生成
	 */
	public <T> int delete(final Class<T> entityClass, final Criterion param) {
		if (entityClass == null || param == null) {
			return 0;
		}
		return (Integer) TCHibernateTemplate.this.execute(new HibernateCallback() {


			public Integer doInHibernate(Session session) throws HibernateException {
				String hql = "delete from " + entityClass.getCanonicalName() + "  where  " + param.toString();
				return session.createQuery(hql).executeUpdate();
			}
		});
	}

	/**
	 * 自行书写hql以满足复杂条件下的删除操作
	 *
	 * @param hql
	 * @param whereMap
	 * @return
	 */
	public <T> int delete(final String hql, final Map<String, Object> whereMap) {
		if (StringUtil.isEmpty(hql) || CollectionUtil.isEmpty(whereMap)) {
			return 0;
		}
		return (Integer) TCHibernateTemplate.this.execute(new HibernateCallback() {


			public Integer doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery(hql);
				for (String key : whereMap.keySet()) {
					query.setParameter(key, whereMap.get(key));
				}
				return query.executeUpdate();
			}
		});
	}

	/**
	 * 根据条件获取记录总数
	 *
	 * @param entityClass
	 * @param param
	 * @return
	 */
	public <T> Integer count(final Class<T> entityClass, final Criterion param) {
		if (entityClass == null) {
			return 0;
		}
		return (Integer) TCHibernateTemplate.this.execute(new HibernateCallback() {


			public Integer doInHibernate(Session session) throws HibernateException {
				Criteria count = session.createCriteria(entityClass);
				count.setProjection(Projections.count("id"));
				count.add(param);
				return Integer.parseInt(count.uniqueResult().toString());
			}
		});
	}

	/**
	 * 自行书写hql以满足复杂条件下的统计操作
	 *
	 * @param hql
	 * @param whereMap
	 * @return
	 */
	public int count(final String hql, final Map<String, Object> whereMap) {
		if (StringUtil.isEmpty(hql)) {
			return 0;
		}
		return (Integer) TCHibernateTemplate.this.execute(new HibernateCallback() {


			public Integer doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery(hql);
				if (!CollectionUtil.isEmpty(whereMap)) {
					query.setProperties(whereMap);
				}

				return Integer.parseInt(query.uniqueResult().toString());
			}
		});
	}

	/**
	 * 根据条件统计，每一个子条件间是and关系
	 *
	 * @param entityClass
	 * @param whereMap
	 * @return
	 */
	public <T> int count(final Class<T> entityClass, final Map<String, Object> whereMap) {
		if (entityClass == null) {
			return 0;
		}
		return TCHibernateTemplate.this.execute(new HibernateCallback<Integer>() {


			public Integer doInHibernate(Session session) throws HibernateException {
				Criteria count = session.createCriteria(entityClass);
				count.setProjection(Projections.count("id"));
				count.add(Restrictions.allEq(whereMap));
				return Integer.parseInt(count.uniqueResult().toString());
			}
		});
	}

	/**
	 * 根据条件获取某列最大值，每一个子条件间是and关系
	 * @param entityClass
	 * @param maxColumn
	 * @param param
	 * @return
	 */
	public <T> Object max(final Class<T> entityClass,final String maxColumn ,final Criterion param) {
		if (entityClass == null) {
			return 0;
		}
		return TCHibernateTemplate.this.execute(new HibernateCallback<Object>() {


			public Object doInHibernate(Session session) throws HibernateException {
				Criteria max = session.createCriteria(entityClass);
				max.setProjection(Projections.max(maxColumn));
				if (param!=null) {
					max.add(param);
				}
				return max.uniqueResult();
			}
		});
	}

	/**
	 * 根据hql查询分页数据
	 * @param hql
	 * @param start
	 * @param range
	 * @return
	 */
//	public ListObject<Map<String,Object>> queryByPage4Hql(String hql,Integer start,Integer range) {
//		final StringBuilder exHql = new StringBuilder(hql);
//		final Integer firstResult = start;
//		final Integer maxResult = range;
//		return TCHibernateTemplate.this.execute(new HibernateCallback<ListObject<Map<String,Object>>>() {
//			@Override
//			public ListObject<Map<String,Object>> doInHibernate(Session session) throws HibernateException {
//				Query query=session.createQuery(exHql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
//				if (firstResult!=null&&firstResult >= 0) {
//					query.setFirstResult(firstResult);
//				}
//				if (maxResult!=null&&maxResult > 0) {
//					query.setMaxResults(maxResult);
//				}
//				List result = query.list();
//				List<Map<String,Object>> rsList = new ArrayList<Map<String,Object>>();
//				if (result!=null) {
//					rsList = (List<Map<String,Object>>)result;
//				}
//				return new ListObject<Map<String,Object>>(rsList.size(),rsList);
//			}
//		});
//    }

	/**
	 * 根据hql查询数据
	 * @param hql
	 * @return
	 */
	public List<Map<String, Object>> queryByHql(final String hql) {
		return TCHibernateTemplate.this.execute(new HibernateCallback<List<Map<String,Object>>>() {

			public List<Map<String,Object>> doInHibernate(Session session) throws HibernateException {
				Query query=session.createQuery(hql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List result = query.list();
				List<Map<String,Object>> rsList = new ArrayList<Map<String,Object>>();
				if (result!=null) {
					rsList = (List<Map<String,Object>>)result;
				}
				return rsList;
			}
		});
	}

	/**
	 * 根据hql查询数据并返回对应实体类数据列表
	 * @param hql
	 * @return
	 */
	public <T> List<T> queryBeanListByHql(final String hql,final Class<T> entityClass) {
		return TCHibernateTemplate.this.execute(new HibernateCallback<List<T>>() {

			public List<T> doInHibernate(Session session) throws HibernateException {
				Query query=session.createQuery(hql).setResultTransformer(Transformers.aliasToBean(entityClass));
				return (List<T>)query.list();
			}
		});
	}

//	/**
//	 * 根据查询语句queryHql和统计语句countHql分页查询数据
//	 * @param queryHql
//	 * @param countHql
//	 * @param entityClass
//	 * @param firstResult
//	 * @param maxResult
//	 * @return
//	 */
//	public <T> ListObject<T> queryBeanPageByHql(final String queryHql,
//			final String countHql, final Class<T> entityClass,final Integer firstResult,final Integer maxResult) {
//		return TCHibernateTemplate.this.execute(new HibernateCallback<ListObject<T>>() {
//			@Override
//			public ListObject<T> doInHibernate(Session session) throws HibernateException {
//				Query query=session.createQuery(queryHql).setResultTransformer(Transformers.aliasToBean(entityClass));
//				if (firstResult!=null&&firstResult >= 0) {
//					query.setFirstResult(firstResult);
//				}
//				if (maxResult!=null&&maxResult > 0) {
//					query.setMaxResults(maxResult);
//				}
//				List<T> result = (List<T>)query.list();
//				Long count = 0L;
//				Query countQuery = session.createQuery(countHql);
//				List<?> tmpres = countQuery.list();
//				if (CollectionUtil.isNotEmpty(tmpres)) {
//					count = Long.parseLong(String.valueOf(tmpres.get(0)));
//				}
//				return new ListObject<T>(count.intValue(),result);
//			}
//		});
//	}

//    /**
//     * 根据查询语句queryHql和统计语句countHql及参数分页查询数据
//     * （此处使用名称占位符处理，参数Map中需要对应sql中的占位符名称）
//     * @param queryHql
//     * @param countHql
//     * @param param
//     * @param entityClass
//     * @param firstResult
//     * @param maxResult
//     * @return
//     */
//    public <T> ListObject<T> queryBeanPageWidthParamByHql(final String queryHql,
//                                                final String countHql,final Map<String,Object> param, final Class<T> entityClass,final Integer firstResult,final Integer maxResult) {
//        return TCHibernateTemplate.this.execute(new HibernateCallback<ListObject<T>>() {
//            @Override
//            public ListObject<T> doInHibernate(Session session) throws HibernateException {
//                Query query=session.createQuery(queryHql).setResultTransformer(Transformers.aliasToBean(entityClass));
//                if (firstResult!=null&&firstResult >= 0) {
//                    query.setFirstResult(firstResult);
//                }
//                if (maxResult!=null&&maxResult > 0) {
//                    query.setMaxResults(maxResult);
//                }
//                if (param!=null) {
//                    Iterator<String> keys = param.keySet().iterator();
//                    while (keys.hasNext()) {
//                        String key = keys.next();
//                        query.setParameter(key,param.get(key));
//                    }
//                }
//                List<T> result = (List<T>)query.list();
//                Long count = 0L;
//                Query countQuery = session.createQuery(countHql);
//                if (param!=null) {
//                    Iterator<String> keys = param.keySet().iterator();
//                    while (keys.hasNext()) {
//                        String key = keys.next();
//                        countQuery.setParameter(key,param.get(key));
//                    }
//                }
//                List<?> tmpres = countQuery.list();
//                if (CollectionUtil.isNotEmpty(tmpres)) {
//                    count = Long.parseLong(String.valueOf(tmpres.get(0)));
//                }
//                return new ListObject<T>(count.intValue(),result);
//            }
//        });
//    }

    public <T> List<T> queryByDetachedCriteria(final DetachedCriteria dc) {
        return TCHibernateTemplate.this.execute(new HibernateCallback<List<T>>() {

            public List<T> doInHibernate(Session session) throws HibernateException {
                return dc.getExecutableCriteria(session).list();
            }
        });
    }

	/**
	 * 根据sql查询bean，bean必须由hibernate管理的才能用
	 * created by huzy 2017/10/11 下午3:18
	 * @param sql sql语句
	 * @param tClass 实体类，必须由hibernate管理
	 * @param params 参数
	 * @param <T>
	 * @return
	 */
	public <T> List<T> queryBeanBySql(final String sql, final Class<T> tClass, final Map<String, Object> params) {
		return this.execute(new HibernateCallback<List<T>>() {

			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery sqlQuery = session.createSQLQuery(sql);
				if(params!=null){
					for (Map.Entry<String, Object> entry : params.entrySet()) {
						sqlQuery.setParameter(entry.getKey(),entry.getValue());
					}
				}
				sqlQuery.addEntity(tClass);
				return sqlQuery.list();
			}
		});
	}
}
