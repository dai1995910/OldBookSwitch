package service;

import java.util.List;

import model.Book;
import model.OnSellItem;
import model.OnSellItemBean;
import model.PageBean;
import model.TradeBean;
import model.User;

public interface OnSellItemService {

	/**
	 * 当进入首页的时候，也就是进入卖书页面的时候，加载8条数据用于首页的展示
	 * 传入值为true的时候表示isForSell为true所以，此时表示的是卖书市场
	 * 反之表示为需求市场
	 * @return
	 */
	public PageBean<OnSellItem> indexQuery(boolean isForSell);

	public OnSellItem queryBySellItemId(String sellItemId);

	/**
	 * 获取用户：
	 * 	1. 需求单：传入值为true
	 *  2. 我的商品：传入值为false
	 * @return
	 */
	public PageBean<OnSellItemBean> getUserOnSellOrder(boolean isReqOrder, String userId);

	public boolean publicOnSellItem(boolean b, Book book, User loginedUser, OnSellItem onSellItem);
	
	/**
	 * 取消上架/取消需求
	 * @param loginedUser
	 * @param onSellItem
	 * @return
	 */
	public boolean handleCancel(User loginedUser, OnSellItem onSellItem);
	
	public PageBean<OnSellItem> fuzzyQuery(boolean isByName, String searchText);

}
