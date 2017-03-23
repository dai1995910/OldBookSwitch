package service.impl;

import org.springframework.transaction.annotation.Transactional;

import model.Book;
import service.BookService;
import utils.SearchBookUtils;
import dao.BaseDao;

@Transactional
public class BookServiceImpl implements BookService{
	private BaseDao<Book> bookDao;
	public void setBookDao(BaseDao<Book> bookDao) {
		this.bookDao = bookDao;
	}
	
	@Override
	public Book searchBook(Book book) {
		if(book == null || book.getISBN() == null || book.getISBN().equals("")) {
			return null;
		}
		Book resultBook = null;
		String isbn = book.getISBN();
		// 检查数据库中有无数据
		resultBook = bookDao.queryById(isbn);
		if(resultBook == null) {
			// 通过网络获取
			resultBook = SearchBookUtils.searchBookOnNet(isbn);
			// 如果查询到了书籍将其保存到数据库中，方便与下次使用
			if(resultBook != null) {
				bookDao.save(resultBook);
			}
		}
		return resultBook;
	}
	
}
