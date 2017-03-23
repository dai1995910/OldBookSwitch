package action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import model.User;
import service.UserService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class UserAction extends ActionSupport implements ModelDriven<User> {

	private User user = new User();

	@Override
	public User getModel() {
		return user;
	}

	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public String execute() throws Exception {
		return super.execute();
	}

	/**
	 * 注册 如果用户名和学号已经注册则不能注册 done
	 */
	public String signUp() {
		// 检测是否已有当前用户，通过学号用户名检查
		int isExisted = userService.checkUsereExists(user);
		switch (isExisted) {
		case 1:
			// 表明学号已经被占用
			this.addActionError("用户名已经被占用");
			break;
		case 2:
			// 表明用户名已经被占用
			this.addActionError("学号已经被使用！");
			break;
		case 3:
			this.addActionError("用户名和学号皆被占用");
			break;
		default:
			// 保存用户的数据
			boolean saveResult = userService.save(user);
			if (saveResult)
				{
				this.addActionMessage("注册成功,请进行登陆!");
				
				}
			else
				this.addActionError("注册出现问题！请检查填入数据！");
			break;
		}
		return "signUp_success";
	}

	/**
	 * 登录
	 * 
	 * @return
	 */
	public String login() {
		// 检查是否已经登录过
		User loginedUser = (User) ActionContext.getContext().getSession()
				.get("loginedUser");
		if (loginedUser != null) {
			// 说明已经登录过了
			return "login_success";
		}

		// 正常的登录程序
		User loginUser = userService.checkLogin(user);
		if (loginUser != null) {
			// 说明登录成功
			// 将用户加入session中
			ActionContext.getContext().getSession()
					.put("loginedUser", loginUser);
			return "login_success";
		} else {
			// 说明该登录失败
			this.addActionError("登录失败，请核实用户名和密码正确！");
			return INPUT;
		}
	}

	/**
	 * 我的账户
	 * 
	 * @return
	 */
	public String myAccount() {
		// 获取到当前的用户
		// 通过session进行保存
		Map<String, Object> session = ActionContext.getContext().getSession();
		User loginedUser = (User) session.get("loginedUser");
		if (loginedUser == null) {
			// 表明登录有问题
			return "INPUT";
		}
		// 将数据交给前端
		// 发送给前端
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("userIntegral", loginedUser.getIntegral());
		request.setAttribute("userUsername", loginedUser.getUsername());
		request.setAttribute("userStuId", loginedUser.getStuId());
		request.setAttribute("userContact", loginedUser.getContact());
		return SUCCESS;
	}
	
	/**
	 * 修改用户的数据
	 * @return
	 */
	public String editAccount() {
		// 保存用户数据
		Map<String, Object> session = ActionContext.getContext().getSession();
		User loginedUser = (User) session.get("loginedUser");
		boolean result = userService.update(loginedUser, user);
		if(result) {
			this.addActionMessage("保存成功");
			return "edit_succ";
		} else {
			this.addActionError("保存失败");
			return "edit_faild";
		}
	}
	/**
	 * 登出
	 * @return
	 */
	public String loginOut(){
		
		ActionContext.getContext().getSession().clear();
		
			
		return "loginOut_success";
	}
}
