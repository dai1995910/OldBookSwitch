package service.impl;

import java.util.Date;

import org.springframework.transaction.annotation.Transactional;

import dao.BaseDao;
import model.AfterSellOrder;
import model.Trade;
import model.User;
import service.AfterSellService;

@Transactional
public class AfterSellServiceImpl implements AfterSellService {
	private BaseDao<AfterSellOrder> afterSellOrderDao;
	private BaseDao<Trade> tradeDao;
	private BaseDao<User> userDao;

	public void setAfterSellOrderDao(BaseDao<AfterSellOrder> afterSellOrderDao) {
		this.afterSellOrderDao = afterSellOrderDao;
	}

	public void setTradeDao(BaseDao<Trade> tradeDao) {
		this.tradeDao = tradeDao;
	}

	public void setUserDao(BaseDao<User> userDao) {
		this.userDao = userDao;
	}

	@Override
	public boolean handleApply(AfterSellOrder afterSellOrder, User loginedUser) {
		Trade trade = tradeDao.queryById(afterSellOrder.getTrade().getTradeId());
		if (trade == null) {
			return false;
		}
		// 改变订单状态
		trade.setState("售后中");
		// 扣除卖家分数
		boolean isBuyer = TradeServiceImpl.isBuyer(loginedUser, trade.getOnSellItem());
		float price = trade.getOnSellItem().getBook().getPrice();
		User seller = null;
		if(trade.getActioner().getStuId().equals(loginedUser.getStuId())) {
			seller = trade.getOnSellItem().getPoster();
		} else {
			seller = trade.getActioner();
		}
		seller.setIntegral(seller.getIntegral() - price);
//		if (isBuyer) {
//			seller = trade.getOnSellItem().getPoster();
//			seller.setIntegral(seller.getIntegral() - price);
//		} else {
//			loginedUser.setIntegral(loginedUser.getIntegral() - price);
//			seller = loginedUser;
//		}

		if (userDao.update(seller) && tradeDao.update(trade)) {
			afterSellOrder.setTrade(trade);
			String asoId = new Date().getTime() + "" + loginedUser.getStuId();
			afterSellOrder.setAsOrderId(asoId);
			return afterSellOrderDao.save(afterSellOrder);
		} else {
			return false;
		}
	}

	@Override
	public boolean handlePrombleSolve(String tradeId, User loginedUser) {
		Trade trade = tradeDao.queryById(tradeId);
		if (trade == null) {
			return false;
		}
		AfterSellOrder afterSellOrder = afterSellOrderDao.queryAfterSellOrderByTradeId(tradeId);
		// 改变订单状态
		trade.setState("问题已解决");
		afterSellOrder.setHandleResult("问题已解决");
		// 将卖家的分数加回来
//		User seller = trade.getOnSellItem().getPoster();
//		seller.setIntegral(seller.getIntegral() + trade.getOnSellItem().getBook().getPrice());
		
		User seller = null;
		if(trade.getActioner().getStuId().equals(loginedUser.getStuId())) {
			seller = trade.getOnSellItem().getPoster();
		} else {
			seller = trade.getActioner();
		}
		seller.setIntegral(seller.getIntegral() + trade.getOnSellItem().getBook().getPrice());
		
//		boolean isBuyer = TradeServiceImpl.isBuyer(loginedUser, trade.getOnSellItem());
//		float price = trade.getOnSellItem().getBook().getPrice();
//		User seller = null;
//		if (!isBuyer) {
//			seller = trade.getOnSellItem().getPoster();
//			seller.setIntegral(seller.getIntegral() + price);
//		} else {
//			loginedUser.setIntegral(loginedUser.getIntegral() + price);
//			seller = loginedUser;
//		}

		if (userDao.update(seller) && tradeDao.update(trade)) {
			return afterSellOrderDao.update(afterSellOrder);
		} else {
			return false;
		}
	}

}
