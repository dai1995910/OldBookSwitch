package dao;

import java.util.List;

import model.OnSellItem;
import model.Trade;

import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

/**
 * DAO查询的基类，提供了方法的空实现
 * @author admin
 */
public class BaseDao<T> extends HibernateDaoSupport{
	
	public boolean save(T data) {
		return false;
	}

	public boolean update(T data) {
		return false;
	}

	public boolean delete(T data) {
		return false;
	}

	public T query(T data) {
		return null;
	}

	/**
	 * 查询出售的书籍
	 * @param begin
	 * @param pageSize
	 * @return
	 */
	public List<T> querySellItemByPage(boolean isForSell, int begin, int pageSize) {
		return null;
	}

	/**
	 *  根据SellItemId来查询对象
	 * @param onSellItem
	 * @return 
	 */
	public OnSellItem queryBySellItemId(String onSellItem) {
		return null;
	}

	public T queryById(String tradeId) {
		return null;
	}

	/**
	 * 订单相关，通过用户的学号来查询相关的账单
	 * @param stuId 
	 */
	public List<T> queryTradeByUserId(String stuId) {
		return null;
		
	}

	public List<OnSellItem> queryUserOnSellOrder(boolean isReqOrder,
			String userId, int begin, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 *  通过onSellItem查询其相关Trade
	 * @param onSellItemId
	 * @return
	 */
	public Trade queryTradeByOnSellItemId(String onSellItemId) {
		return null;
	}
	
	/**
	 * 删除OnSellItem，用于下架物品和取消需求
	 * @param onSellItem
	 * @return
	 */
	public boolean deleteOnSellItem(OnSellItem onSellItem) {
		return false;
	}

	public List<OnSellItem> puzzyQuery(boolean isByName, String searchText) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public T queryAfterSellOrderByTradeId(String tradeId){
		return null;
	}
}
