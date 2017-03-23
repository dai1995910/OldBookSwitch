package service;

import model.AfterSellOrder;
import model.User;

public interface AfterSellService {

	/**
	 * 处理用户申请的售后服务
	 * @param afterSellOrder
	 * @param loginedUser 
	 * @return
	 */
	boolean handleApply(AfterSellOrder afterSellOrder, User loginedUser);

	/**
	 * 处理用户售后问题解决
	 * @param afterSellOrder
	 * @param loginedUser 
	 * @return
	 */
	boolean handlePrombleSolve(String tradeId, User loginedUser);

}
