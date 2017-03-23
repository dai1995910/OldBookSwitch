package service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.OnSellItem;
import model.PageBean;
import model.Trade;
import model.TradeBean;
import model.User;

import org.springframework.transaction.annotation.Transactional;

import service.TradeService;
import dao.BaseDao;

/**
 * 订单相关Service实现类
 * 
 * @author admin
 *
 */
@Transactional
public class TradeServiceImpl implements TradeService {
	private BaseDao<Trade> tradeDao;

	public void setTradeDao(BaseDao<Trade> tradeDao) {
		this.tradeDao = tradeDao;
	}

	private BaseDao<User> userDao;

	public void setUserDao(BaseDao<User> userDao) {
		this.userDao = userDao;
	}

	private BaseDao<OnSellItem> onSellItemDao;

	public void setOnSellItemDao(BaseDao<OnSellItem> onSellItemDao) {
		this.onSellItemDao = onSellItemDao;
	}

	@Override
	public Trade queryById(String tradeId) {
		return tradeDao.queryById(tradeId);
	}

	/**
	 * 保存订单，并处理积分
	 */
	@Override
	public boolean firstSave(Trade preptrade) {
		// 处理积分
		User payer = null;
		boolean isForSell = preptrade.getOnSellItem().isForSell();
		if (isForSell) {
			// 表明是卖，说明是买的订单，说明发起者扣分
			payer = preptrade.getActioner();
		} else {
			// 说明是求购的订单，所以上架者扣分
//			payer = preptrade.getOnSellItem().getPoster();
		}

		User poster = preptrade.getOnSellItem().getPoster();
		if (poster.getStuId().equals(preptrade.getActioner().getStuId())) {
			// 自己买了自己的东西
			return false;
		}
		// 进行扣分
		float payerBalance = payer.getIntegral(); // 支付者余额
		float price = preptrade.getOnSellItem().getBook().getPrice();
		if (price > payerBalance) {
			// 说明余额不足
			return false;
		}

		// 改变订单状态
		preptrade.setState("买家已付款");
		// preptrade.getOnSellItem().setSelled(true);
		OnSellItem ons = onSellItemDao.queryBySellItemId(preptrade.getOnSellItem().getSellItemId());
		ons.setSelled(true);
		if (tradeDao.save(preptrade)) {
			// 保存成功，进行积分的扣除
			User user = new User();
			user.setStuId(payer.getStuId());
			// User theUser = userDao.query(user);
			// // 余额足够扣除积分
			// theUser.setIntegral(payerBalance - price);
			// userDao.update(theUser);
			user.setIntegral(payerBalance - price);
			userDao.update(user);
			return true;
		} else {
			return false;
		}

	}

	@Override
	public Trade preptrade(User actionUser, OnSellItem onSellItem) {
		if (onSellItem.isSelled()) {
			// 表明已经卖掉了
			return null;
		}
		if (onSellItem.getPoster().getStuId().equals(actionUser.getStuId())) {
			// 自己买了自己的东西
			return null;
		}
		// 判断是不是购买
		if (onSellItem.isForSell()) {
			// 判断积分是否够
			float payerBalance = actionUser.getIntegral();
			float price = onSellItem.getBook().getPrice();
			if (price > payerBalance) {
				return null;
			}
		}
		// 创建初步订单
		Trade trade = new Trade();
		// 发起人买商品
		trade.setActioner(actionUser);
		trade.setOnSellItem(onSellItem);
		Date date = new Date();
		long time = date.getTime();
		String tradeId = time + "" + actionUser.getStuId() + onSellItem.getPoster().getStuId();
		trade.setTradeId(tradeId);
		trade.setTradingDate(date);
		return trade;
	}

	@Override
	public PageBean<TradeBean> getUserTradeList(User loginedUser) {
		String stuId = loginedUser.getStuId();
		if (stuId == null || "".equals(stuId)) {
			return null;
		}
		List<Trade> queryList = tradeDao.queryTradeByUserId(stuId);
		// ？ 这里要商量怎么写PageBean
		PageBean<TradeBean> pageBean = new PageBean<TradeBean>();

		return changeToPageBean(queryList, loginedUser, pageBean);
	}

	/**
	 * 将用户的订单转化为页面显示的TradeBean
	 * 
	 * @return
	 */
	private PageBean<TradeBean> changeToPageBean(List<Trade> tradeList, User loginedUser,
			PageBean<TradeBean> pageBean) {
		if (tradeList == null || tradeList.size() <= 0) {
			return null;
		}
		ArrayList<TradeBean> tradeBeanList = new ArrayList<TradeBean>();
		// 进行转换
		for (Trade trade : tradeList) {
			TradeBean tradeBean = new TradeBean();
			tradeBean.setBookName(trade.getOnSellItem().getBook().getBookName());
			tradeBean.setImgUrl(trade.getOnSellItem().getBook().getImgUrl());
			tradeBean.setPrice(trade.getOnSellItem().getBook().getPrice());
			tradeBean.setTradeState(trade.getState());
			tradeBean.setTradeId(trade.getTradeId());
			OnSellItem onSellItem = trade.getOnSellItem();
			// 判断用户在这个订单中的角色
			if (isBuyer(loginedUser, onSellItem)) {
				// 表明是买家
				User seller = null;
				if(!trade.getActioner().getStuId().equals(loginedUser.getStuId())) {
					seller = trade.getActioner();
				} else {
					seller = trade.getOnSellItem().getPoster();
				}
				tradeBean.setOther("卖家");
				tradeBean.setOtherName(seller.getUsername());
				tradeBean.setOtherContact(seller.getContact());
				String btnText = stateToBtnText(trade, true);
				tradeBean.setBtnText(btnText);
				tradeBean.setBtnHref(btnTextToHref(btnText, trade));
			} else {
				User buyer = null;
				if(!trade.getActioner().getStuId().equals(loginedUser.getStuId())) {
					buyer = trade.getActioner();
				} else {
					buyer = trade.getOnSellItem().getPoster();
				}
				// 表明是卖家
				tradeBean.setOther("买家");
				tradeBean.setOtherName(buyer.getUsername());
				tradeBean.setOtherContact(buyer.getContact());
				String btnText = stateToBtnText(trade, false);
				tradeBean.setBtnText(btnText);
				tradeBean.setBtnHref(btnTextToHref(btnText, trade));
			}
			tradeBeanList.add(tradeBean);
		}
		pageBean.setValueList(tradeBeanList);
		return pageBean;
	}

	/*
	 * 用于判断是买家还是卖家
	 */
	public static boolean isBuyer(User loginedUser, OnSellItem onSellItem) {
		if (loginedUser.getStuId().equals(onSellItem.getPoster().getStuId())) {
			if (onSellItem.isForSell())
				// 说明是卖家
				return false;
			// 说明是买家
			return true;
		} else {
			if (onSellItem.isForSell())
				return true;
			return false;
		}
	}

	/*
	 * 判断当前订单的状态，来决定按钮的文字 返回的为按钮的文字
	 */
	private String stateToBtnText(Trade trade, boolean isBuyer) {
		String state = trade.getState();
		if (isBuyer) {
			// 买家相关的按钮逻辑
			if ("卖家已发货".equals(state)) {
				return "确认收货";
			}
			if ("交易完成".equals(state)) {
				return "申请售后";
			}
			if ("售后中".equals(state)) {
				return "问题解决";
			}
		} else {
			// 卖家相关的按钮逻辑
			if ("买家已付款".equals(state)) {
				return "开始发货";
			}
		}
		return "";
	}

	/*
	 * 根据按钮内容判断应该传入什么action
	 */
	private String btnTextToHref(String btnText, Trade trade) {
		String href = "";
		String tradeId = trade.getTradeId();
		switch (btnText) {
		case "确认收货":
			// return "trade_recvItem?tradeId="+tradeId;
			return "trade_recvItem";
		case "申请售后":
			return "aftersell_afterSales";
		// return "aftersell_afterSales?tradeId="+tradeId;
		case "开始发货":
			return "trade_sendItem";
		// return "trade_sendItem?tradeId="+tradeId;
		case "问题解决":
			return "aftersell_problemSolve";
		// return "aftersell_problemSolve?tradeId="+tradeId;
		default:
			return "";
		}
	}

	@Override
	public boolean handleSendItem(String tradeId, User loginedUser) {
		Trade trade = tradeDao.queryById(tradeId);
		if (trade == null) {
			return false;
		}
		// 判断是不是他的商品
		// User poster = trade.getOnSellItem().getPoster();
		// if (!poster.getStuId().equals(loginedUser.getStuId()) ||
		// !trade.getOnSellItem().isForSell()) {
		// return false;
		// }
		if (!checkUser(true, trade, loginedUser)) {
			return false;
		}
		trade.setState("卖家已发货");
		return tradeDao.save(trade);
	}

	@Override
	public boolean handleRecvItem(String tradeId, User loginedUser) {
		// 进行订单的合法检测
		Trade trade = tradeDao.queryById(tradeId);
		if (trade == null) {
			return false;
		}
		// 判断是不是他买的的商品
		// User poster = trade.getOnSellItem().getPoster();
		// if(!trade.getActioner().getStuId().equals(loginedUser.getStuId()) ||
		// !trade.getOnSellItem().isForSell()) {
		// return false;
		// }
		if (!checkUser(false, trade, loginedUser)) {
			return false;
		}
		// 进行积分的逻辑
		float price = trade.getOnSellItem().getBook().getPrice();
		if(trade.getOnSellItem().getIsForSell()) {
			loginedUser.setIntegral(loginedUser.getIntegral() - price);
		}
		if (userDao.update(loginedUser)) {
			User seller = null;
			if (!trade.getActioner().getStuId().equals(loginedUser.getStuId())) {
				seller = trade.getActioner();
			} else {
				seller = trade.getOnSellItem().getPoster();
			}
			seller.setIntegral(seller.getIntegral() + price);
			if (userDao.update(seller)) {
				trade.setState("交易完成");
				return tradeDao.update(trade);
			}
		}
		return false;
	}

	/**
	 * 检查是否是有权限进行
	 * 
	 * @return
	 */
	private boolean checkUser(boolean isSeller, Trade trade, User loginedUser) {
		boolean isForSell = trade.getOnSellItem().isForSell();
		String loginedUserId = loginedUser.getStuId();
		OnSellItem onSellItem = trade.getOnSellItem();
		if (isSeller) {
			// 是卖家，只有当物品是用于出售并且Poster是自己合法
			if (isForSell) {
				if (onSellItem.getPoster().getStuId().equals(loginedUserId)) {
					return true;
				}
			} else {
				// 有可能是需求单
				if (trade.getActioner().getStuId().equals(loginedUserId)) {
					// 表明自己是需求单的发起人，说明是卖家
					return true;
				}
			}
		} else {
			// 是买家
			if (isForSell) {
				// 是一个出售单，此时当自己是Actioner的时候合法
				if (trade.getActioner().getStuId().equals(loginedUserId)) {
					return true;
				}
			} else {
				// 是一个需求单，当自己是需求单的Poster的时候合法
				if (onSellItem.getPoster().getStuId().equals(loginedUserId)) {
					return true;
				}
			}
		}
		return false;
	}

}
