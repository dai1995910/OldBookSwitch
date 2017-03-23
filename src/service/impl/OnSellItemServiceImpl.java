package service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Book;
import model.OnSellItem;
import model.OnSellItemBean;
import model.PageBean;
import model.Trade;
import model.User;

import org.springframework.transaction.annotation.Transactional;

import service.OnSellItemService;
import dao.BaseDao;

@Transactional
public class OnSellItemServiceImpl implements OnSellItemService {
	private BaseDao<OnSellItem> onSellItemDao;
	private BaseDao<User> userDao;
	private BaseDao<Trade> tradeDao;
	

	public void setOnSellItemDao(BaseDao<OnSellItem> onSellItemDao) {
		this.onSellItemDao = onSellItemDao;
	}
	public void setUserDao(BaseDao<User> userDao) {
		this.userDao = userDao;
	}
	public void setTradeDao(BaseDao<Trade> tradeDao) {
		this.tradeDao = tradeDao;
	}
	@Override
	public PageBean<OnSellItem> indexQuery(boolean isForSell) {
		PageBean<OnSellItem> pageBean = querySellItemByPages(1, isForSell);
		return pageBean;
	}

	/**
	 * 用于根据页数进行查询的方法
	 * 
	 * @param i
	 * @return
	 */
	private PageBean<OnSellItem> querySellItemByPages(int currentPage, boolean isForSell) {
		PageBean<OnSellItem> pageBean = new PageBean<OnSellItem>();
		pageBean.setCurrPage(currentPage);
		// 这里先默认设置为8，因为首页显示的为8
		int pageSize = 8;
		pageBean.setPageSize(pageSize);
		// int totalCount = onSellItemDao.count();
		// 封装的页数
		// double tc = totalCount;
		// Double num = Math.ceil(tc / pageSize);
		// pageBean.setTotoalPage(num.intValue());
		// 每页显示的数据的起点
		int begin = (currentPage - 1) * pageSize;
		List<OnSellItem> list = onSellItemDao.querySellItemByPage(isForSell, begin, pageSize);
		pageBean.setValueList(list);
		return pageBean;
	}

	/**
	 * 查询单个在售或者在求商品的信息
	 */
	@Override
	public OnSellItem queryBySellItemId(String sellItemId) {
		return  onSellItemDao.queryBySellItemId(sellItemId);
	}

	/**
	 * 我的需求单，我的商品
	 */
	@Override
	public PageBean<OnSellItemBean> getUserOnSellOrder(boolean isReqOrder, String userId) {
		if(userId == null || "".equals(userId)) {
			return null;
		}
		List<OnSellItem> onSellOrders = onSellItemDao.queryUserOnSellOrder(isReqOrder, userId,0,8);
		// 进行封装
		return changeToPageBean(isReqOrder, onSellOrders);
	}

	/**
	 * 发布需求，我要卖书
	 */
	@Override
	public boolean publicOnSellItem(boolean isForSell, Book book, User loginedUser, OnSellItem userData) {
		if(book == null || loginedUser == null) {
			return false;
		}
		// 进行判断，因为需求单需要发布的时候扣除积分值
		if(!isForSell) {
			float bookPrice = book.getPrice();
			float integral = loginedUser.getIntegral();
			if(bookPrice > integral) {
				// 积分不够，不能购买
				return false;
			}
		}
		// 生成一个OnSellItem
		OnSellItem onSellItem = new OnSellItem();
		onSellItem.setBook(book);
		onSellItem.setDegree(userData.getDegree());
		onSellItem.setForSell(isForSell);
		onSellItem.setPoster(loginedUser);
		Date date = new Date();
		onSellItem.setSellItemId(date.getTime() + "" + loginedUser.getStuId());
		onSellItem.setSellTime(date);
		if(onSellItemDao.save(onSellItem)) {
			// 进行积分的扣除
			loginedUser.setIntegral(loginedUser.getIntegral() - book.getPrice());
			userDao.update(loginedUser);
			return true;
		} else {
			return false;
		}
	}

	/*
	 * 将OnSellItem的集合转换为用于页面显示的对象
	 */
	private PageBean<OnSellItemBean> changeToPageBean(boolean isReqOrder, List<OnSellItem> onSellOrders) {
		PageBean<OnSellItemBean> pageBean = new PageBean<OnSellItemBean>();
		ArrayList<OnSellItemBean> valueList = new ArrayList<OnSellItemBean>();
		for(OnSellItem onSellItem : onSellOrders) {
			OnSellItemBean onSellItemBean = new OnSellItemBean();
			onSellItemBean.setBookName(onSellItem.getBook().getBookName());
			onSellItemBean.setImgUrl(onSellItem.getBook().getImgUrl());
			onSellItemBean.setPrice(onSellItem.getBook().getPrice());
			onSellItemBean.setSellerContect(onSellItem.getPoster().getContact());
			onSellItemBean.setSellerName(onSellItem.getPoster().getUsername());
			onSellItemBean.setOnSellItemId(onSellItem.getSellItemId());
			if(onSellItem.isSelled()) {
				// 设置状态
				Trade relatedTrade = tradeDao.queryTradeByOnSellItemId(onSellItem.getSellItemId());
				onSellItemBean.setState(relatedTrade.getState());
			} else {
				// 设置状态
				onSellItemBean.setState("暂无回应");
				// 当还没有卖掉的时候，可以下架或者取消需求
				if(onSellItem.isForSell()) {
					onSellItemBean.setBtnText("下架商品");
					onSellItemBean.setBtnHref("osi_cancelSell");
				} else {
					onSellItemBean.setBtnText("取消需求");
					onSellItemBean.setBtnHref("osi_cancelReq");
				}
			}
			valueList.add(onSellItemBean);
		}
		pageBean.setValueList(valueList);
		return pageBean;
	}
	
	@Override
	public boolean handleCancel(User loginedUser, OnSellItem onSellItem) {
		//  获取到对应的OnSellItem
		String sellItemId = onSellItem.getSellItemId();
		if(sellItemId == null || "".equals(sellItemId)) {
			return false;
		}
		onSellItem = onSellItemDao.queryBySellItemId(onSellItem.getSellItemId());
		// 检验当前用户是否有权限做这个事情
		User poster = onSellItem.getPoster();
		if(!poster.getStuId().equals(loginedUser.getStuId())) {
			// 说明当前用户没有权限做这个事情
			return false;
		}
		/*
		 *  处理积分
		 *  如果isForSell为false表示需要返还积分
		 */
		boolean isForSell = onSellItem.getIsForSell();
		float price = onSellItem.getBook().getPrice();
		if(onSellItemDao.deleteOnSellItem(onSellItem)) {
			if(!isForSell) { // 表明是用来卖的，返还积分
				loginedUser.setIntegral(loginedUser.getIntegral() + price);
				return userDao.update(loginedUser);
			} else {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * 进行模糊查询
	 */
	@Override
	public PageBean<OnSellItem> fuzzyQuery(boolean isByName, String searchText) {
		if(searchText == null || "".equals(searchText))
			return null;
		
		List<OnSellItem> queryList = onSellItemDao.puzzyQuery(isByName, searchText);
		PageBean<OnSellItem> pageBean = new PageBean<OnSellItem>();
		pageBean.setValueList(queryList);
		return pageBean;
	}
}
