package action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import model.OnSellItem;
import model.PageBean;
import model.Trade;
import model.TradeBean;
import model.User;

import org.apache.struts2.ServletActionContext;

import service.OnSellItemService;
import service.TradeService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 订单相关Action
 * 
 * @author admin
 *
 */
public class TradeAction extends ActionSupport implements ModelDriven<Trade> {

	private Trade trade = new Trade();

	@Override
	public Trade getModel() {
		return trade;
	}

	private TradeService tradeService;

	public void setTradeService(TradeService tradeService) {
		this.tradeService = tradeService;
	}

	private OnSellItemService onSellItemService;

	public void setOnSellItemService(OnSellItemService onSellItemService) {
		this.onSellItemService = onSellItemService;
	}

	/**
	 * 确认订单
	 * 从订单确认页面到订单确认完成到订单页面
	 * 
	 * @return
	 */
	public String confirm() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		// 处理订单
		boolean result = tradeService.firstSave((Trade) session.get("trade"));
		return result ? "confirm" : "confirm_faild";
	}

	/**
	 * 从上架物品详情页面跳转到确认订单页面的Action 功能是获取到确认订单页面的数据
	 * 
	 * @return prepared表示准备好订单 not_prepared表示没有订单
	 */
	public String preptrade() {
		// 获取到发起人
		User actionUser = (User) ActionContext.getContext().getSession()
				.get("loginedUser");
		// 获取到上架物品
		String sellItemId = trade.getOnSellItem().getSellItemId();
		OnSellItem onSellItem = onSellItemService.queryBySellItemId(sellItemId);
		// 交给Service处理
		Trade trade = tradeService.preptrade(actionUser, onSellItem);
		if (trade == null) {
			this.addActionError("创建订单失败");
			return "prep_faild";
		}
		// 发送给前端
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("preptrade", trade);
		// 通过session进行保存
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("trade", trade);
		return "prep_success";
	}

	/**
	 * 查询当前用户的所有订单
	 * 
	 * @return
	 */
	public String myOrders() {
		// 获取到当前的用户
		// 通过session进行保存
		Map<String, Object> session = ActionContext.getContext().getSession();
		User loginedUser = (User) session.get("loginedUser");
		// 交给Service进行处理
		PageBean<TradeBean> tradebeansList = tradeService
				.getUserTradeList(loginedUser);
		if (tradebeansList == null) {
			return "get_orders_faild";
		} else {
			// 发送给前端
			HttpServletRequest request = ServletActionContext.getRequest();
			request.setAttribute("orders_pagebean", tradebeansList);
		}
		return "get_orders_succ";
	}
	
	/**
	 * 卖家：开始发货对应Action
	 * @return
	 */
	public String sendItem() {
		String tradeId = trade.getTradeId();
		Map<String, Object> session = ActionContext.getContext().getSession();
		User loginedUser = (User) session.get("loginedUser");
		boolean isSuccess = tradeService.handleSendItem(tradeId, loginedUser);
		if(isSuccess) {
			return SUCCESS;
		} else {
			this.addActionError("发货失败！");
			return "faild";
		}
	}
	
	/**
	 * 当买家收货后处理逻辑的Action
	 * @return
	 */
	public String recvItem() {
		String tradeId = trade.getTradeId();
		Map<String, Object> session = ActionContext.getContext().getSession();
		User loginedUser = (User) session.get("loginedUser");
		boolean isSuccess = tradeService.handleRecvItem(tradeId, loginedUser);
		if(isSuccess) {
			return SUCCESS;
		} else {
			this.addActionError("收货失败！");
			return "faild";
		}
	}
}
