package service;

import java.util.List;

import model.OnSellItem;
import model.PageBean;
import model.Trade;
import model.TradeBean;
import model.User;

/**
 * 订单相关的Service部分
 * @author admin
 *
 */
public interface TradeService {
	/**
	 * 通过主键来查询订单
	 * @param tradeId
	 * @return
	 */
	Trade queryById(String tradeId);
	
	/**
	 * 进行第一次订单的保存
	 * @param preptrade
	 * @return
	 */
	boolean firstSave(Trade preptrade);

	/**
	 * 从商品详情到确认订单
	 * @param actionUser
	 * @param sellItem
	 * @return
	 */
	Trade preptrade(User actionUser, OnSellItem sellItem);

	/**
	 * 通过用户ID来获取到当前用户相关的所有的订单
	 * @param loginedUser
	 * @return
	 */
	PageBean<TradeBean> getUserTradeList(User loginedUser);

	/**
	 * 处理卖家开始发货的action
	 * @param tradeId
	 * @param loginedUser
	 * @return
	 */
	boolean handleSendItem(String tradeId, User loginedUser);
	/**
	 * 处理卖家开始发货的action
	 * @param tradeId
	 * @param loginedUser
	 * @return
	 */
	boolean handleRecvItem(String tradeId, User loginedUser);
}
