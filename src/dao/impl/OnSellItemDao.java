package dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import model.Book;
import model.OnSellItem;
import model.User;
import dao.BaseDao;

public class OnSellItemDao extends BaseDao<OnSellItem> {

	@Override
	public boolean save(OnSellItem data) {
		try {
			this.getHibernateTemplate().save(data);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OnSellItem> querySellItemByPage(boolean isForSell, int begin, int pageSize) {
		List<OnSellItem> list = null;
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(OnSellItem.class);
			// 添加条件查询用于出售的书
			criteria.add(Restrictions.eq("isForSell", isForSell));
			criteria.add(Restrictions.eq("isSelled", false));
			list = (List<OnSellItem>) this.getHibernateTemplate().findByCriteria(criteria, begin, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public OnSellItem queryBySellItemId(String onSellItemId) {
		if (onSellItemId == null || "".equals(onSellItemId)) {
			return null;
		}
		try {
			return this.getHibernateTemplate().get(OnSellItem.class, onSellItemId);
		} catch (Exception e) {
			System.out.println("[OnSellItemDao].queryBySellItemId------查询失败!");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<OnSellItem> queryUserOnSellOrder(boolean isReqOrder, String userId, int begin, int pageSize) {
		List<OnSellItem> list = new ArrayList<OnSellItem>();
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(OnSellItem.class);
			// 添加条件查询用于出售的书
			criteria.add(Restrictions.eq("isForSell", !isReqOrder));
			User user = this.getHibernateTemplate().get(User.class, userId);
			criteria.add(Restrictions.eq("poster", user));
			list = (List<OnSellItem>) this.getHibernateTemplate().findByCriteria(criteria, begin, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	@Override
	public boolean deleteOnSellItem(OnSellItem onSellItem) {
		try {
			onSellItem.setBook(null);
			this.update(onSellItem);
			this.getHibernateTemplate().delete(onSellItem);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OnSellItem> puzzyQuery(boolean isByName, String searchText) {
		try {
			ArrayList<OnSellItem> resultList = new ArrayList<OnSellItem>();
			// 添加条件查询用于出售的书
			// 先查书
			Session session = this.getSessionFactory().openSession();
			List<Book> bookList = null;
			try {
				String hql = "";
				if (isByName) {
					hql = "from Book where bookName like '%" + searchText + "%'";
				} else {
					hql = "from Book where isbn like '%" + searchText + "%'";
				}
				bookList = session.createQuery(hql).list();
				System.out.println();
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 查出与书有关的订单
			for (Book book : bookList) {
				DetachedCriteria criteria = DetachedCriteria.forClass(OnSellItem.class);
				criteria.add(Restrictions.eq("book", book));
				criteria.add(Restrictions.eq("isSelled", false));
				List<OnSellItem> relatedOrder = (List<OnSellItem>) this.getHibernateTemplate().findByCriteria(criteria);
				resultList.addAll(relatedOrder);
			}
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
