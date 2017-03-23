package action;

import java.util.Date;
import java.util.Map;

import model.AfterSellOrder;
import model.User;
import service.AfterSellService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class AfterSellAction extends ActionSupport implements ModelDriven<AfterSellOrder>{
	private AfterSellOrder afterSellOrder = new AfterSellOrder();
	private String tradeId;
	@Override
	public AfterSellOrder getModel() {
		return afterSellOrder;
	}
	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}




	private AfterSellService afterSellService;
	public void setAfterSellService(AfterSellService afterSellService) {
		this.afterSellService = afterSellService;
	}
	
	/**
	 * 用户申请售后
	 * @return
	 */
	public String afterSales() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		User loginedUser = (User) session.get("loginedUser");
		if("".equals(afterSellOrder.getAfterselldesc())) {
			this.addActionError("描述信息不能为空");
			return "faild";
		}
		afterSellOrder.setSubTime(new Date());
		boolean isSuccess = afterSellService.handleApply(afterSellOrder, loginedUser);
		if(isSuccess) {
			this.addActionMessage("已经提交给后台");
			return SUCCESS;
		} else {
			return "faild";
		}
	}
	
	public String problemSolve() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		User loginedUser = (User) session.get("loginedUser");
		boolean isSuccess = afterSellService.handlePrombleSolve(tradeId, loginedUser);
		return SUCCESS;
	}
}
