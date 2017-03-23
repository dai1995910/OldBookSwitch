package dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import model.User;
import dao.BaseDao;

public class UserDao extends BaseDao<User> {
	
	@Override
	public boolean save(User data) {
		try {
			this.getHibernateTemplate().save(data);
		} catch (Exception e) {
			System.out.println("[UserDao].save()------保存失败");
			return false;
		}
		return true;
	}
	
	@Override
	public boolean update(User data) {
		try {
			User oldUser = this.getHibernateTemplate().get(User.class, data.getStuId());
			// 进行更新
			oldUser.update(data);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[UserDao].update()------更新失败");
			return false;
		}
		return true;
	}
	
	/*
	 * 用户查询根据用户名和学号来进行查询
	 */
	@Override
	public User query(User data) {
		User result = null;
		try {
			// 构建查询条件
			Session session = this.getHibernateTemplate().getSessionFactory().openSession();
			Criteria criteria = session.createCriteria(User.class);
			if(data.getStuId() != null && !"".equals(data.getStuId())) {
				criteria.add(Restrictions.eq("stuId", data.getStuId()));
			}
			if(data.getUsername() != null && !"".equals(data.getUsername())) {
				criteria.add(Restrictions.eq("username", data.getUsername()));
			}
			List<User> list = criteria.list();
			if(list.size() != 0) {
				// 返回第一个作为信号表示已经有注册的了
				result = list.get(0);
			}
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("[UserDao].query------查询失败");
		}
		return result;
	}
}
