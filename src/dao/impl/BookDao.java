package dao.impl;

import java.io.Serializable;

import model.Book;
import dao.BaseDao;

public class BookDao extends BaseDao<Book>{
	/**
	 * 通过ISBN来查询一本书
	 */
	@Override
	public Book queryById(String isbn) {
		try {
			return this.getHibernateTemplate().get(Book.class, isbn);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 *  保存一本书
	 */
	@Override
	public boolean save(Book data) {
		try {
			this.getHibernateTemplate().save(data);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
