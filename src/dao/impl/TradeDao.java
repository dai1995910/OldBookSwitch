package dao.impl;

import java.util.ArrayList;
import java.util.List;

import model.OnSellItem;
import model.Trade;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import dao.BaseDao;

public class TradeDao extends BaseDao<Trade> {

	@Override
	public Trade queryById(String tradeId) {
		Trade result = null;
		try {
			result = this.getHibernateTemplate().get(Trade.class, tradeId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean save(Trade data) {
		try {
			this.getHibernateTemplate().save(data);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*
	 * 订单：用户买的商品的订单
	 * 
	 * @see dao.BaseDao#queryByUserId(java.lang.String)
	 */
	@Override
	public List<Trade> queryTradeByUserId(String stuId) {
		List<Trade> resultList = new ArrayList<Trade>();
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(Trade.class);
			Session session = this.getHibernateTemplate().getSessionFactory()
					.openSession();
			// 获取到所有的Trade
			Query query = session.createQuery("from Trade");
			List<Trade> list = query.list();
			for (Trade t : list) {
				// 判断这个订单是不是用户产生的
			if (t.getActioner().getStuId().equals(stuId)) {
				// 如果是，表示是用户的订单列表中的一个
				resultList.add(t);
			} else {
					// 判断是不是用户卖的物品被别人买走
//					if (t.getOnSellItem().isForSell()) {
//						// 表明用户是卖家
//						resultList.add(t);
//					}
				if(t.getOnSellItem().getPoster().getStuId().equals(stuId)) {
					resultList.add(t);
				}
				}
			}
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Trade queryTradeByOnSellItemId(String onSellItemId) {
		try {
			OnSellItem onSellItem = this.getHibernateTemplate().get(OnSellItem.class, onSellItemId);
			DetachedCriteria criteria = DetachedCriteria
					.forClass(Trade.class);
			// 添加条件查询用于出售的书
			criteria.add(Restrictions.eq("onSellItem", onSellItem));
			List<Trade> trades = (List<Trade>) this.getHibernateTemplate()
					.findByCriteria(criteria);
			return trades.size() == 0 ? null :trades.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@Override
	public boolean update(Trade data) {
		try {
			this.getHibernateTemplate().update(data);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
