package service;

import model.Book;

public interface BookService {

	/**
	 * 通过isbn查询用户所需要的书籍
	 * @param book
	 * @return
	 */
	Book searchBook(Book book);

}
