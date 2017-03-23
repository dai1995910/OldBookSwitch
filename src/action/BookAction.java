package action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import model.Book;
import service.BookService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class BookAction extends ActionSupport implements ModelDriven<Book> {
	private Book book = new Book();

	@Override
	public Book getModel() {
		return book;
	}

	private BookService bookService;

	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}

	/**
	 * 查询一本书
	 * @return
	 */
	public String searchBook() {
		Book resultBook = bookService.searchBook(book);
		if (resultBook == null) {
			return "faild";
		}
		// 将数据放入session中，方便后面的逻辑能拿到数据
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("searchedBook", resultBook);
		// 将数据放到request中，给前端数据
		// 发送给前端
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("resultBook", resultBook);
		request.setAttribute("btnhref", "user_login");
		return SUCCESS;
	}
	public String searchBookForNeeds(){
		Book resultBook = bookService.searchBook(book);
		if (resultBook == null) {
			return "faild";
		}
		// 将数据放入session中，方便后面的逻辑能拿到数据
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("searchedBook", resultBook);
		// 将数据放到request中，给前端数据
		// 发送给前端
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("resultBook", resultBook);
		request.setAttribute("btnhref", "user_login");
		return "need_success";
		
	}
}
