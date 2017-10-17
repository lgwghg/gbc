package com.webside.frame.db.hibernate;

import java.io.Serializable;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

/**
 * 基于hibernate的数据持久层单表操作 注意是单表操作，如果多表请使用seesion的sql查询
 */
@Repository
public class BaseDao<T> 
{

	@Resource
	protected HibernateTemplate hibernateTemplate;

	protected Class<T> entityClass;

	/**
	 * 通过反射获取子类的确定的泛型类
	 */
	@SuppressWarnings("unchecked")
	public BaseDao()
	{
		try
		{
			this.entityClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		} 
		catch (ClassCastException e)
		{
			// BaseLog.w(this.getClass(), "未找到泛型！");
		}
	}

	/**
	 * 获取Session对象 使用场景： 1， 本实体类 不能满足的情况下。 2，多实体关联查询
	 * 
	 * @return Session
	 */
	public Session em()
	{
		return hibernateTemplate.getSessionFactory().getCurrentSession();
	}

	/**
	 * 保存数据
	 * 
	 * @param entity
	 * @return Serializable 主键标识
	 */
	public Serializable save(T entity) 
	{
		return hibernateTemplate.save(entity);
	}

	/**
	 * 根据主键删除实体
	 * 
	 * @param id
	 */
	public void delete(Serializable id)
	{
		delete("id", id);
	}

	/**
	 * 根据主键批量删除实体
	 * 
	 * @param name
	 * @param ids
	 *            ：主键列表
	 * @return int
	 */
	public int delete(Object[] ids, String name)
	{
		// delete from xx where id in('','');
		StringBuilder sql = new StringBuilder("delete from ");
		sql.append(entityClass.getSimpleName());
		sql.append(" where ");
		sql.append(name);
		sql.append(" in(");
		int len = ids.length;
		
		for (int i = 0; i < len; i++) 
		{
			sql.append("?");
			if (i < len - 1)
			{
				sql.append(",");
			}
		}
		
		sql.append(")");
		
		return hibernateTemplate.bulkUpdate(sql.toString(), ids);
	}

	/**
	 * 根据指定属性批量删除实体
	 * 
	 * @param id
	 * @return int
	 */
	public void delete(List<Serializable> ids, String name) 
	{
		delete(ids.toArray(), name);
	}

	/**
	 * 根据属性相等删除实体 其他按条件删除的可以参考此方法 在service中编写 eg:delete("age",25)
	 * tips:要注意传参的数据类型 要和实体数据类型一致。 例如：这个age是int型，如果穿的是"25"而不是25就会报classcast异常
	 * 
	 * @param id
	 * @return int
	 */
	public void delete(String name, Object value) 
	{
		// delete Teacher where ?=?
		StringBuilder sql = new StringBuilder("delete ");
		sql.append(entityClass.getSimpleName());
		sql.append(" where ");
		sql.append(name);
		sql.append(" = ?");
		
		hibernateTemplate.bulkUpdate(sql.toString(), new Object[] { value });
	}

	/**
	 * 保存或更新数据
	 * 
	 * @param entity
	 * @return T
	 */
	public void saveOrUpdate(T entity)
	{
		hibernateTemplate.saveOrUpdate(entity);
	}

	/**
	 * 更新数据
	 * 
	 * @param entity
	 * @return T
	 */
	public void update(T entity) 
	{
		hibernateTemplate.update(entity);
	}

	/**
	 * 更新单一实体多属性更新 eg:update(p,"name","12","age",25) update entityClass set ?=?
	 * where id=? tips:要注意传参的数据类型 要和实体数据类型一致。
	 * 例如：这个age是int型，如果穿的是"25"而不是25就会报classcast异常
	 * 
	 * @param entity
	 * @return T
	 */
	public int update(Serializable pk, Object... paramValues) 
	{
		if (mod(paramValues))
		{
			StringWriter sql = new StringWriter();
			int n = paramValues.length / 2;
			Object[] values = new Object[n + 1];

			// 拼接sql
			sql.append("update ").append(entityClass.getSimpleName());
			sql.append(" set ");
			for (int i = 0; i < n; i++) 
			{
				sql.append(paramValues[i * 2].toString() + "=?");
				values[i] = paramValues[i * 2 + 1];
				
				if (i < n - 1) 
				{
					sql.append(",");
				}
			}
			
			sql.append(" where id=?");
			values[n] = pk;
			return hibernateTemplate.bulkUpdate(sql.toString(), values);
		} 
		else
		{
			return 0;
		}
	}

	/**
	 * 更新多实体多属性 eg:update(ids,"name","12","age",25) update entityClass set ?=?
	 * where id in(?,?) tips:要注意传参的数据类型 要和实体数据类型一致。
	 * 例如：这个age是int型，如果穿的是"25"而不是25就会报classcast异常
	 * 
	 * @param entity
	 * @return T
	 */
	public int update(Object[] pks, Object... paramValues) 
	{
		if (mod(paramValues))
		{
			StringWriter sql = new StringWriter();
			int n = paramValues.length / 2;
			int pn = pks.length;
			Object[] values = new Object[n + pn];// 参数数组

			// 拼接sql
			sql.append("update ").append(entityClass.getSimpleName());
			sql.append(" set ");
			for (int i = 0; i < n; i++) 
			{
				sql.append(paramValues[i * 2].toString() + "=?");
				values[i] = paramValues[i * 2 + 1];
				
				if (i < n - 1) 
				{
					sql.append(",");
				}
			}
			sql.append(" where id in(");
			for (int i = 0; i < pn; i++) 
			{
				sql.append("?");
				values[n + i] = pks[i];// 追加主键参数
				if (i < pn - 1)
				{
					sql.append(",");
				}
			}
			sql.append(")");
			return hibernateTemplate.bulkUpdate(sql.toString(), values);
		} 
		else 
		{
			return 0;
		}
	}

	/**
	 * 更新数据 过滤空值属性(目前只支持String 和 int、 Integer类型的属性)
	 * 
	 * @param entity
	 * @return T
	 */
	public void updateWithValidator(T entity) throws IllegalAccessException,NoSuchFieldException 
	{
		if (entity != null) 
		{
			// 根据主键获取数据库中的实体
			Field field_id = entityClass.getDeclaredField("id");
			field_id.setAccessible(true);
			String fieldType_id = field_id.getType().getSimpleName();
			T entity_old = null;
			
			switch (fieldType_id)
			{
				case "Long":
					entity_old = find(Long.parseLong(String.valueOf(field_id
							.get(entity))));
					break;
				case "String":
					entity_old = find(String.valueOf(field_id.get(entity)));
					break;
				case "Integer":
					entity_old = find(Integer.parseInt(String.valueOf(field_id
							.get(entity))));
					break;
				default:
					break;
			}

			// 获取Entity中需要过滤空值的Fields
			Field[] fields = entityClass.getDeclaredFields();
			for (Field field : fields) 
			{
				Validator validator = field.getAnnotation(Validator.class);
				
				if (validator != null)
				{
					field.setAccessible(true);
					String fieldType = field.getType().getSimpleName();
					Object fieldValue = field.get(entity);
					Object fieldValue_old = field.get(entity_old);
					
					if (Validator.AuType.ISNULL.equals(validator.value())) 
					{// isNull
						switch (fieldType) 
						{
							case "String":
								if ((fieldValue == null || "null"
										.equals(fieldValue))
										&& fieldValue_old != null)
									field.set(entity,
											String.valueOf(fieldValue_old));
								break;
							case "Integer":
								if (fieldValue == null && fieldValue_old != null)
									field.set(entity, Integer.parseInt(String
											.valueOf(fieldValue_old)));
								break;
							case "int":
								if ("0".equals(fieldValue)
										&& fieldValue_old != null)
									field.set(entity, Integer.parseInt(String
											.valueOf(fieldValue_old)));
								break;
							case "Long":
								if (fieldValue == null && fieldValue_old != null)
									field.set(entity, Long.parseLong(String
											.valueOf(fieldValue_old)));
								break;
							case "long":
								if ("0".equals(fieldValue)
										&& fieldValue_old != null)
									field.set(entity, Long.parseLong(String
											.valueOf(fieldValue_old)));
								break;
							default:
								break;
						}
					} 
					else if (Validator.AuType.ISEMPTY.equals(validator.value()) && 
							(fieldValue == null || String.valueOf(fieldValue).length() == 0)) 
					{
						field.set(entity, String.valueOf(fieldValue_old));
					} 
					else if (Validator.AuType.ISGREATERTHAN.equals(validator.value()) &&
							(Integer.parseInt(String.valueOf(fieldValue)) > validator.compare())) 
					{
						field.set(entity, Integer.parseInt(String.valueOf(fieldValue_old)));
					}
				}
			}
			
			hibernateTemplate.merge(entity);
		}
	}

	/**
	 * 根据主键 查询唯一对象
	 * 
	 * @param id
	 * @return T
	 */
	public T find(Serializable id) {
		return findOne("id", id);
	}

	/**
	 * 根据条件查询唯一对象 findOne("name", "xxx", "status", "0");
	 * 
	 * @param paramValues
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public T findOne(Object... paramValues) 
	{
		if (!mod(paramValues))
		{
			return null;
		}
		
		List<T> results = (List<T>) hibernateTemplate.findByCriteria(createCriteria(paramValues));
		return results == null || results.isEmpty() ? null : results.get(0);
	}

	/**
	 * 根据条件查询唯一对象
	 * 
	 * @param DetachedCriteria
	 *            dc
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public T findOne(DetachedCriteria dc)
	{
		List<T> results = (List<T>) hibernateTemplate.findByCriteria(dc);
		return results == null || results.isEmpty() ? null : results.get(0);
	}

	/**
	 * 加载全部对象
	 * 
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll()
	{
		DetachedCriteria dc = DetachedCriteria.forClass(entityClass);
		return (List<T>) hibernateTemplate.findByCriteria(dc);
	}

	/**
	 * 根据属性名和属性值查询对象. findOne("name", "xxx", "status", "0");
	 * 
	 * @param paramValues
	 * @return 符合条件的对象列表
	 */
	@SuppressWarnings("unchecked")
	public List<T> find(Object... paramValues)
	{
		if (!mod(paramValues)) {
			return null;
		}
		
		return (List<T>) hibernateTemplate.findByCriteria(createCriteria(paramValues));
	}

	/**
	 * 根据属性名和属性值查询对象.取指定条数 findOne(10,"name", "xxx", "status", "0");
	 * 
	 * @param paramValues
	 * @return 符合条件的对象列表
	 */
	@SuppressWarnings("unchecked")
	public List<T> findLen(int len, Object... paramValues)
	{
		if (!mod(paramValues)) {
			return null;
		}
		
		return (List<T>) hibernateTemplate.findByCriteria(createCriteria(paramValues), 0, len);
	}

	/**
	 * 多逻辑条件查询 比如 大于 小于 in or like order 等
	 * 
	 * @param dc
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> find(DetachedCriteria dc)
	{
		return (List<T>) hibernateTemplate.findByCriteria(dc);
	}

	/**
	 * 多逻辑条件查询 带分页 比如 大于 小于 in or like order 等
	 * 
	 * @param dc
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> find(DetachedCriteria dc, int firstResult, int maxResults) 
	{
		return (List<T>) hibernateTemplate.findByCriteria(dc, firstResult, maxResults);
	}

	/**
	 * 按照主键统计
	 * 
	 * @return
	 */
	public Long count() 
	{
		DetachedCriteria dc = DetachedCriteria.forClass(entityClass);
		dc.setProjection(Projections.count("id"));
		return (Long) hibernateTemplate.findByCriteria(dc).get(0);
	}

	/**
	 * 按照指定属性统计,适合非主键模式
	 * 
	 * @return
	 */
	public Long count(String name) 
	{
		DetachedCriteria dc = DetachedCriteria.forClass(entityClass);
		dc.setProjection(Projections.count(name));
		
		return (Long) hibernateTemplate.findByCriteria(dc).get(0);
	}

	/**
	 * 按条件统计
	 * 
	 * @tips: count操作 记录放在 同一dc的最后面，防止后续使用dc条件进行，查询带来的重复执行count sql的问题
	 * @param dc
	 * @return
	 */
	public Long count(DetachedCriteria dc) 
	{
		dc.setProjection(Projections.count("id"));
		return (Long) hibernateTemplate.findByCriteria(dc, 0, 1).get(0);// 添加0,1是为了解决重用dc
	}

	/**
	 * 按相等条件统计 eg:("name", "xxx", "status", "0");
	 * 
	 * @param paramValues
	 * @return Long
	 */
	public Long count(Object... paramValues)
	{
		if (!mod(paramValues)) {
			return null;
		}
		
		DetachedCriteria dc = createCriteria(paramValues);
		dc.setProjection(Projections.count("id"));
		
		return (Long) hibernateTemplate.findByCriteria(dc).get(0);
	}

	/**
	 * 拼接相等条件
	 * 
	 * @param paramValues
	 *            属性名,值,属性名,值
	 * @return
	 */
	private DetachedCriteria createCriteria(Object... paramValues)
	{
		DetachedCriteria dc = DetachedCriteria.forClass(entityClass);
		
		for (int i = 0; i < paramValues.length; i += 2) 
		{
			dc.add(Restrictions.eq(paramValues[i].toString(),(Object) paramValues[i + 1]));
		}
		
		return dc;
	}

	/**
	 * 判断可变参数数量是否正确
	 * 
	 * @param n
	 * @return
	 */
	private boolean mod(Object[] paramValues) 
	{
		int n = paramValues.length;
		
		if (n == 0 || n % 2 == 1) 
		{
			return false;
		}
		
		return true;
	}
}
