package dao.impl;

import model.AfterSellOrder;
import model.OnSellItem;
import model.Trade;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import dao.BaseDao;

public class AfterSellOrderDao extends BaseDao<AfterSellOrder> {
	@Override
	public boolean save(AfterSellOrder data) {
		try {
			this.getHibernateTemplate().save(data);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public AfterSellOrder queryAfterSellOrderByTradeId(String tradeId) {
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(AfterSellOrder.class);
			Trade trade = this.getHibernateTemplate().get(Trade.class, tradeId);
			criteria.add(Restrictions.eq("trade", trade));
			List<AfterSellOrder> trades = (List<AfterSellOrder>) this.getHibernateTemplate().findByCriteria(criteria);
			return trades.size() == 0 ? null : trades.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
