package action;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import model.Book;
import model.OnSellItem;
import model.OnSellItemBean;
import model.PageBean;
import model.TradeBean;
import model.User;

import org.apache.struts2.ServletActionContext;

import service.OnSellItemService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class OnSellItemAction extends ActionSupport implements
		ModelDriven<OnSellItem> {
	public String searchText;
	public boolean isByName;
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	public void setByName(boolean isByName) {
		this.isByName = isByName;
	}

	private OnSellItem onSellItem = new OnSellItem();

	@Override
	public OnSellItem getModel() {
		return onSellItem;
	}

	private OnSellItemService onSellItemService;

	public void setOnSellItemService(OnSellItemService onSellItemService) {
		this.onSellItemService = onSellItemService;
	}

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		return super.execute();
	}

	/**
	 * 当进入主页的时候加载的方法; 对应着卖书市场
	 * 
	 * @return
	 */
	public String index() {
		// 查询8条数据展示在界面上
		PageBean<OnSellItem> onSellItemPageBean = onSellItemService
				.indexQuery(true);
		if (onSellItemPageBean != null) {
			ActionContext.getContext().getValueStack().push(onSellItemPageBean);
		} else {
			this.addActionError("查询并无处于卖中的书籍可以用于显示!");
			return "load_faild_index";
		}
		return SUCCESS;
	}

	/**
	 * 查询需求市场的首页数据
	 * 
	 * @return
	 */
	public String reqIndex() {
		// 查询8条数据展示在界面上
		PageBean<OnSellItem> onSellItemPageBean = onSellItemService
				.indexQuery(false);
		if (onSellItemPageBean != null) {
			ActionContext.getContext().getValueStack().push(onSellItemPageBean);
		} else {
			this.addActionError("查询并无处于卖中的书籍可以用于显示!");
			return "load_faild_req";
		}
		return "req_success";
	}

	/**
	 * 查询单个在售商品query item
	 * 
	 * @return 在售商品的信息
	 */
	public String qitem() {
		String sellItemId = onSellItem.getSellItemId();
		OnSellItem queryResult = onSellItemService
				.queryBySellItemId(sellItemId);
		if (queryResult != null) {
			HttpServletRequest request = ServletActionContext.getRequest();
			request.setAttribute("OnSellItem", queryResult);
			// ActionContext.getContext().getValueStack().push(queryResult);
		} else {
			this.addActionError("无此在售物品!!");
			return "load_faild";
		}
		return "query_item_success";
	}

	/**
	 * 获取到用户的需求单 条件：isForSell为false的属于用户的OnSellItem 我的需求单
	 * 
	 * @return
	 */
	public String getUserReqOrder() {
		// 获取到当前的用户
		// 通过session进行保存
		Map<String, Object> session = ActionContext.getContext().getSession();
		User loginedUser = (User) session.get("loginedUser");
		// 逻辑交给Service
		PageBean<OnSellItemBean> pageBean = onSellItemService.getUserOnSellOrder(true,
				loginedUser.getStuId());
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("pageBean", pageBean);
		return "get_reqsuccess";
	}

	/**
	 * 获取到用户的商品：isForSell为true的属于用户的OnSellItem
	 * 我的商品
	 */
	public String getUserItem() {
		// 获取到当前的用户
		// 通过session进行保存
		Map<String, Object> session = ActionContext.getContext().getSession();
		User loginedUser = (User) session.get("loginedUser");
		// 逻辑交给Service
		PageBean<OnSellItemBean> pageBean = onSellItemService.getUserOnSellOrder(false,
				loginedUser.getStuId());
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("pageBean", pageBean);
		return "get_itemsuccess";
	}

	/**
	 * 发布需求的第二部
	 * 当用户首先输入ISBN获取到书籍信息后，并且确认发布需求
	 * 生成一个onSellItem
	 * 
	 * @return
	 */
	public String publicReq() {
		// 将数据放入session中，方便后面的逻辑能拿到数据
		Map<String, Object> session = ActionContext.getContext().getSession();
		Book book = (Book) session.get("searchedBook");
		User loginedUser = (User) session.get("loginedUser");
		boolean isSuccess = onSellItemService.publicOnSellItem(false, book, loginedUser, onSellItem);
		return "pubreq_success";
	}

	/**
	 * 我要卖书
	 * 
	 * @return
	 */
	public String publicSell() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		Book book = (Book) session.get("searchedBook");
		User loginedUser = (User) session.get("loginedUser");
		boolean isSuccess = onSellItemService.publicOnSellItem(true, book, loginedUser, onSellItem);
		if(isSuccess) {
			return "publicsell_success";
		} else {
			return "publicsell_faild";
		}
	}
	
	/**
	 * 用户下架商品
	 * @return
	 */
	public String cancelSell() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		User loginedUser = (User) session.get("loginedUser");
		boolean isSuccess = onSellItemService.handleCancel(loginedUser, onSellItem);
		if(isSuccess) {
			this.addActionMessage("下架成功");
			return "cancelSell_success";
		} else {
			this.addActionError("下架失败");
			return "cancelSell_faild";
		}
	}
	
	/**
	 * 取消需求
	 * @return
	 */
	public String cancelReq() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		User loginedUser = (User) session.get("loginedUser");
		boolean isSuccess = onSellItemService.handleCancel(loginedUser, onSellItem);
		if(isSuccess) {
			this.addActionMessage("下架成功");
			return "cancelReq_success";
		} else {
			this.addActionError("下架失败");
			return "cancelReq_faild";
		}
	}
	
	/**
	 * 模糊查询
	 * @return
	 */
	public String fuzzyQuery() {
		try{
			searchText=new String(searchText.getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		PageBean<OnSellItem> pageBean = onSellItemService.fuzzyQuery(isByName, searchText);
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("pageBean", pageBean);
		if(pageBean == null) {
			return "fyquery_faild";
		}
		return "fyquery_success";
	}
}
