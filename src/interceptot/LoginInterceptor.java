package interceptot;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import model.User;

import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class LoginInterceptor extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Map<String, Object> session = invocation.getInvocationContext()
				.getSession();
		User loginedUser = (User) session.get("loginedUser");
		if (loginedUser == null) {
			 HttpServletRequest request= (HttpServletRequest) invocation.getInvocationContext().get(StrutsStatics.HTTP_REQUEST);
			 String requestURI = request.getRequestURI();
			 ActionSupport action = (ActionSupport) invocation.getAction();
			 action.addActionError("请登录！！");
			 if(requestURI.indexOf("user_login") != -1 || requestURI.indexOf("user_signUp") != -1) {
				 return invocation.invoke();
			 }
			return Action.INPUT;
		} else {
			// 正常进行
			return invocation.invoke();
		}
	}
}
